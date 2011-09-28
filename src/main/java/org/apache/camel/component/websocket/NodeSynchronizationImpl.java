package org.apache.camel.component.websocket;

public class NodeSynchronizationImpl implements NodeSynchronization {

	private WebsocketStore memoryStore;

	private WebsocketStore globalStore;

	public NodeSynchronizationImpl(WebsocketStore memoryStore,
			WebsocketStore globalStore) {
		this.memoryStore = memoryStore;
		this.globalStore = globalStore;
	}

	public NodeSynchronizationImpl(WebsocketStore memoryStore) {
		this.memoryStore = memoryStore;
	}

	@Override
	public void addSocket(DefaultWebsocket socket) {
		memoryStore.add(socket);
		if (this.globalStore != null) {
			globalStore.add(socket);
		}
	}

	@Override
	public void removeSocket(String id) {
		memoryStore.remove(id);
		if (this.globalStore != null) {
			globalStore.remove(id);
		}
	}

	@Override
	public void removeSocket(DefaultWebsocket socket) {
		memoryStore.remove(socket);
		if (this.globalStore != null) {
			globalStore.remove(socket);
		}
	}

}
