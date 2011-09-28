package org.apache.camel.component.websocket;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.Processor;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;



public class WebsocketConfigurationTest {

    private static final String REMAINING = "foo";
    private static final String URI       = "websocket://" + REMAINING;
    private static final String PARAMETERS = "org.apache.camel.component.websocket.WebsocketConfigurationTestMemStoreTestImpl"; 
    
    @Mock
    private WebsocketComponent component;

    @Mock
    private CamelContext camelContext;

    private WebsocketEndpoint websocketEndpoint;
    
    private WebsocketConfiguration wsConfig = new WebsocketConfiguration();
	
    @Before
    public void setUp() throws Exception {
    	component = new WebsocketComponent();
    	component.setCamelContext(camelContext);
    }
    
	 @Test
	    public void testParameters() throws Exception {

		 assertNull(wsConfig.getGlobalStore());
		 
		 wsConfig.setGlobalStore(PARAMETERS);
		 
		 assertNotNull(wsConfig.getGlobalStore());
		 		 
		 websocketEndpoint = new WebsocketEndpoint(URI, component, REMAINING, wsConfig);
		 
		 //System.out.println(URI);
		 //System.out.println(component);
		 //System.out.println(REMAINING);
		 //System.out.println(wsConfig.getGlobalStore());
		 
	    }
	 	
}
