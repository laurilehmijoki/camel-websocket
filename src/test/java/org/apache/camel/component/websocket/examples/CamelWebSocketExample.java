package org.apache.camel.component.websocket.examples;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.websocket.WebsocketConstants;
import org.apache.camel.impl.DefaultCamelContext;

import java.io.File;

/**
 * This Camel example demonstrates a synchronous communication model between two WebSocket
 * clients. In this model its possible to send synchronous messages from A to B even though B would
 * sit behind a firewall.
 * <p/>
 * <pre>
 *     Client A                                                                      Client B
 *     |                                                                             |
 *     |                                                                             |
 *     | (send message)                                                              | (receive message)
 *     |                                                                             |
 *     |                                                                             |
 *     +------ Camel WebSocket endpoint --- logger --- Camel WebSocket endpoint -----+
 * </pre>
 * <p/>
 */
public class CamelWebSocketExample {

    public static void main(String[] args) throws Exception {
        CamelContext camelContext = new DefaultCamelContext();

        camelContext.addRoutes(new RouteBuilder() {

            public void configure() {
                from("websocket://foo")
                        .log("${body}")
                        .setHeader(WebsocketConstants.SEND_TO_ALL, constant(Boolean.TRUE))
                        .to("websocket://foo");
            }
        });

        camelContext.start();

        System.out.println(
                String.format(
                        "***\n\nTo try out WebSockets with Camel, open %s in two browser instances.\n\n***",
                        new File("").getAbsolutePath() + "/src/test/resources/index.html"));
    }
}
