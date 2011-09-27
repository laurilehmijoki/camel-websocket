package org.apache.camel.component.websocket;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultEndpoint;
import org.apache.camel.util.ObjectHelper;

public class WebsocketEndpoint extends DefaultEndpoint {

	// Todo: Change to Options
	private NodeSynchronization sync;
	private String remaining;

	private WebsocketStore memoryStore;
	private WebsocketStore globalStore;

	private WebsocketConfiguration websocketConfiguration;

	public WebsocketEndpoint() {

	}
	

	public WebsocketStore getMemoryStore() {
		return memoryStore;
	}


	public WebsocketStore getGlobalStore() {
		return globalStore;
	}


	public WebsocketEndpoint(String uri, WebsocketComponent component,
			String remaining, WebsocketConfiguration websocketConfiguration)
			throws InstantiationException, IllegalAccessException {
		super(uri, component);
		this.remaining = remaining;

		this.memoryStore = new MemoryWebsocketStore();
		// TODO: init globalStore

		this.websocketConfiguration = websocketConfiguration;

		if (websocketConfiguration.getGlobalStore() != null) {
			this.globalStore = (WebsocketStore) ObjectHelper.loadClass(
					this.websocketConfiguration.getGlobalStore()).newInstance();
		}

		// this.sync = new NodeSynchronizationImpl(this.memoryStore, null);
		this.sync = new NodeSynchronizationImpl(this.memoryStore,
				this.globalStore);

	}

	@Override
	public Consumer createConsumer(Processor processor) throws Exception {

		// init consumer
		WebsocketConsumer consumer = new WebsocketConsumer(this, processor);

		// register servlet
		((WebsocketComponent) super.getComponent()).addServlet(this.sync,
				consumer, this.remaining);

		return consumer;
	}

	@Override
	public Producer createProducer() throws Exception {

		// register servlet without consumer
		((WebsocketComponent) super.getComponent()).addServlet(this.sync, null,
				this.remaining);

		return new WebsocketProducer(this, this.memoryStore);
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	// TODO --> implement store factory
}
