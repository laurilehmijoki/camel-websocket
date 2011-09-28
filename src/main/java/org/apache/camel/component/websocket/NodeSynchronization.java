package org.apache.camel.component.websocket;

public interface NodeSynchronization {

	/** adds the Websocket to both (always if present) stores */
	public void addSocket(DefaultWebsocket socket);

	/** deletes the Websocket from both stores */
	public void removeSocket(String id);

	/** deletes the Websocket from both stores */
	public void removeSocket(DefaultWebsocket socket);

}
