package br.com.deliverytracker.backoffice.server;

import java.util.ArrayList;
import java.util.List;

import br.com.deliverytracker.backoffice.impl.MessageProcessorImpl;
import br.com.deliverytracker.xmpp.XMPPServer;

public class Server {

	private static final int WORKERS_COUNT = 1;

	public static void main(String[] args) {
		Server srv = new Server();
		try {
			srv.run();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	private void run() throws Exception {
		List<XMPPServer> servers = new ArrayList<>();
		for (int i = 0; i < WORKERS_COUNT; i++) {
			XMPPServer server = new XMPPServer(new MessageProcessorImpl());
			server.start();
			Thread.sleep(5);
			servers.add(server);
		}
		synchronized (this) {
			wait();
		}
		while (servers.size() > 0) {
			XMPPServer server = servers.get(0);
			server.stop();
		}
	}
}
