package org.apache.camel.component.websocket.examples;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.websocket.WebsocketConstants;
import org.apache.camel.impl.DefaultCamelContext;

import java.io.File;

/**
 * WebSockets and Camel in action! Just run the main method and follow the printed instructions.
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
