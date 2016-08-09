package server;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLSocketFactory;

import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.StanzaListener;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.StanzaFilter;
import org.jivesoftware.smack.packet.DefaultExtensionElement;
import org.jivesoftware.smack.packet.ExtensionElement;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Stanza;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

public class Server {

    private final String SERVER_HOST = "fcm-xmpp.googleapis.com";
    // private final int SERVER_PORT = 5236;
    private final int SERVER_PORT = 5235;

    private final String SERVICE_NAME = "gcm.googleapis.com";

    private final String USER_NAME = "497175095084@gcm.googleapis.com";
    private final String PASSWORD = "AIzaSyD4P94m9lVzUG73GI7Q5dRqZVgxg4Tq-Qo";

    public static void main(String[] args) {
        Server srv = new Server();
        try {
            srv.run();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private void run() throws SmackException, IOException, XMPPException, NoSuchAlgorithmException, KeyManagementException {
        Base64Utils.init();

        XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()//
                .setHost(SERVER_HOST)//
                .setPort(SERVER_PORT)//
                .setServiceName(SERVICE_NAME)//
                .setSecurityMode(SecurityMode.ifpossible)//
                .setUsernameAndPassword(USER_NAME, PASSWORD)//
                .setSocketFactory(SSLSocketFactory.getDefault())//
                .setSendPresence(false)//
                .setDebuggerEnabled(true)//
                .build();

        XMPPTCPConnection conn = new XMPPTCPConnection(config);

        conn.connect();

        conn.login();

        conn.addAsyncStanzaListener(new StanzaListener() {

            public void processPacket(Stanza stanza) throws NotConnectedException {
                print(stanza);
            }
        }, new StanzaFilter() {

            public boolean accept(Stanza stanza) {
                print(stanza);
                return true;
            }
        });

        XMPPTCPConnection conn2 = new XMPPTCPConnection(config);

        conn2.connect();

        conn2.login();

        conn2.addAsyncStanzaListener(new StanzaListener() {

            public void processPacket(Stanza stanza) throws NotConnectedException {
                print(stanza);
            }
        }, new StanzaFilter() {

            public boolean accept(Stanza stanza) {
                print(stanza);
                return true;
            }
        });

        while (true) {

        }

    }

    private void print(Stanza stanza) {
        if (stanza instanceof Message) {
            Message m = (Message) stanza;
            System.out.println(m.getSubject());
            
        }
        for (ExtensionElement element : stanza.getExtensions()) {
            element.getElementName();

            System.out.println(element);
        }

        System.out.println(stanza);
    }

}
