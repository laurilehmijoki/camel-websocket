/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.component.websocket;

import org.eclipse.jetty.websocket.WebSocket.Connection;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;

/**
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultWebsocketTest {

    private static final int CLOSE_CODE = -1;
    private static final String MESSAGE = "message";
    private static final String CONNECTION_KEY = "random-connection-key";

    @Mock
    private Connection connection;

    @Mock
    private WebsocketConsumer consumer;

    @Mock
    private NodeSynchronization sync;

    private DefaultWebsocket defaultWebsocket;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        // sync = new NodeSynchronizationImpl(new MemoryWebsocketStore());
        defaultWebsocket = new DefaultWebsocket(sync, consumer);
        defaultWebsocket.setConnectionKey(CONNECTION_KEY);
    }

    /**
     * Test method for {@link org.apache.camel.component.websocket.DefaultWebsocket#onClose(int, java.lang.String)} .
     */
    @Test
    public void testOnClose() {
        defaultWebsocket.onClose(CLOSE_CODE, MESSAGE);
        InOrder inOrder = inOrder(connection, consumer, sync);
        inOrder.verify(sync, times(1)).removeSocket(defaultWebsocket);
        inOrder.verifyNoMoreInteractions();
    }

    /**
     * Test method for {@link org.apache.camel.component.websocket.DefaultWebsocket#onOpen(org.eclipse.jetty.websocket.WebSocket.Connection)} .
     */
    @Test
    public void testOnOpen() {
        defaultWebsocket.onOpen(connection);

        /*
         * keyCaptor not functional anymore, because addSocket cannot be called with connectionKey
         * 
         * InOrder inOrder = inOrder(connection, consumer, sync); ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class); inOrder.verify(sync,
         * times(1)).addSocket((eq(defaultWebsocket))); inOrder.verifyNoMoreInteractions();
         */

        assertEquals(connection, defaultWebsocket.getConnection());
    }

    /**
     * Test method for {@link org.apache.camel.component.websocket.DefaultWebsocket#onMessage(java.lang.String)} .
     */
    @Test
    public void testOnMessage() {
        defaultWebsocket.setConnectionKey(CONNECTION_KEY);
        defaultWebsocket.onMessage(MESSAGE);
        InOrder inOrder = inOrder(connection, consumer, sync);
        inOrder.verify(consumer, times(1)).sendExchange(CONNECTION_KEY, MESSAGE);
        inOrder.verifyNoMoreInteractions();
    }

    /**
     * Test method for {@link org.apache.camel.component.websocket.DefaultWebsocket#onMessage(java.lang.String)} .
     */
    @Test
    public void testOnMessageWithNullConsumer() {
        defaultWebsocket = new DefaultWebsocket(sync, null);
        defaultWebsocket.setConnectionKey(CONNECTION_KEY);
        defaultWebsocket.onMessage(MESSAGE);
        InOrder inOrder = inOrder(connection, consumer, sync);
        inOrder.verify(consumer, times(0)).sendExchange(CONNECTION_KEY, MESSAGE);
        inOrder.verifyNoMoreInteractions();
    }

    /**
     * Test method for {@link org.apache.camel.component.websocket.DefaultWebsocket#getConnection()} .
     */
    @Test
    public void testGetConnection() {
        assertNull(defaultWebsocket.getConnection());
        defaultWebsocket.onOpen(connection);
        assertEquals(connection, defaultWebsocket.getConnection());
        defaultWebsocket.setConnection(null);
        assertNull(defaultWebsocket.getConnection());
        defaultWebsocket.setConnection(connection);
        assertEquals(connection, defaultWebsocket.getConnection());
    }

    /**
     * Test method for {@link org.apache.camel.component.websocket.DefaultWebsocket#setConnection(org.eclipse.jetty.websocket.WebSocket.Connection)} .
     */
    @Test
    public void testSetConnection() {
        testGetConnection();
    }

    /**
     * Test method for {@link org.apache.camel.component.websocket.DefaultWebsocket#getConnectionKey()} .
     */
    @Test
    public void testGetConnectionKey() {
        defaultWebsocket.setConnectionKey(null);
        assertNull(defaultWebsocket.getConnectionKey());
        defaultWebsocket.onOpen(connection);
        assertNotNull(defaultWebsocket.getConnectionKey());
        defaultWebsocket.setConnectionKey(CONNECTION_KEY);
        assertEquals(CONNECTION_KEY, defaultWebsocket.getConnectionKey());
        defaultWebsocket.setConnectionKey(null);
        assertNull(defaultWebsocket.getConnectionKey());
    }

    /**
     * Test method for {@link org.apache.camel.component.websocket.DefaultWebsocket#setConnectionKey(java.lang.String)} .
     */
    @Test
    public void testSetConnectionKey() {
        testGetConnectionKey();
    }
}
