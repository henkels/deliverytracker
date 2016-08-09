package br.com.deliverytracker.backoffice;

import br.com.deliverytracker.xmpp.XMPPServer;

public class Server {

    public static void main(String[] args) {
        Server srv = new Server();
        try {
            srv.run();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private void run() throws Exception {
        XMPPServer server = new XMPPServer();
        server.start();
        while (true) {

        }
    }
}
