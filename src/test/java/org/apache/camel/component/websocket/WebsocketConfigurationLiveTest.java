package org.apache.camel.component.websocket;

import java.util.Collection;

import org.apache.camel.builder.RouteBuilder;
//import org.apache.camel.component.hazelcast.HazelcastConstants;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import org.apache.camel.component.websocket.WebsocketConstants;

//import com.thoughtworks.xstream.XStream;

public class WebsocketConfigurationLiveTest extends CamelTestSupport {

    @Test
    public void testWebsocketCallWithGlobalStore() throws Exception {
        //LOG.info("*** open URL http://localhost:1989 and start chating ***");
    	Thread.sleep(10 * 60 * 1000);
    }

    
    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            public void configure() {
                
                WebsocketComponent component = getContext().getComponent("websocket", WebsocketComponent.class);
                component.setHost("localhost");
                component.setPort(1989);
                component.setStaticResources("src/test/resources");

                
                from("websocket://foo")
                    .log("${body}")
                    .setHeader(WebsocketConstants.SEND_TO_ALL, constant(true))
                    .to("websocket://foo");
                
                
                /*
                from("websocket://foo?globalStore=org.apache.camel.component.websocket.HazelcastStore")
            	from("websocket://foo?globalStore=com.catify.persistence.hazelcast.websockethazelcaststore.HazelcastStore")
            	 from("websocket://foo?globalStore=org.apache.camel.component.websocket.WebsocketConfigurationTestMemStoreTestImpl")
        		.log("message received:").log("---> ${body}")
                .setHeader(WebsocketConstants.SEND_TO_ALL, constant(true))
              //  .toF("hazelcast:%sHazelcastStore", HazelcastConstants.SEDA_PREFIX);
               
                
              //  fromF("hazelcast:%sHazelcastStore", HazelcastConstants.SEDA_PREFIX)
        	  //	.log("message received:").log("---> ${body}")
        	  //	.setHeader(WebsocketConstants.SEND_TO_ALL, constant(true))
                  .to("websocket://foo").setHeader(WebsocketConstants.SEND_TO_ALL, constant(true));
                
                //.marshal().xstream();
                //System.out.println(getContext().getEndpoints().isEmpty());
                
                WebsocketEndpoint endpoint = (WebsocketEndpoint) getContext().getEndpoints().iterator().next();
                
                //("foo");
                //s().iterator().next();
                
                Collection<DefaultWebsocket> coll = endpoint.getMemoryStore().getAll();
                DefaultWebsocket ws = coll.iterator().next();
                
                
        		XStream xstream = new XStream();;
				
				String xml = xstream.toXML(ws.getConnection());
				
				//String xml = xstream.toXML(ws);
				
				System.out.println(xml);
				
				
				
        		//this.iMap.put(ws.getConnectionKey(), xml);
        		
        		//DefaultWebsocket ws = (DefaultWebsocket)xstream.fromXML(key);
        		//return ws;
                
                //getDefaultWS
                //--> serialisieren
                
                */
            }
        };
    }
}