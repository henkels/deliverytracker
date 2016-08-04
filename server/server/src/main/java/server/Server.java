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

	private void run()
			throws SmackException, IOException, XMPPException, NoSuchAlgorithmException, KeyManagementException {
		Base64Utils.init();
		// Builder config = XMPPTCPConnectionConfiguration.builder();
		// config.setSecurityMode(SecurityMode.ifpossible);
		// config.setHost("gcm.googleapis.com");
		// config.setPort(5235);
		// config.setServiceName(SERVICE_NAME);
		// config.setSocketFactory(SSLSocketFactory.getDefault());
		// config.setUsernameAndPassword(USER_NAME, PASSWORD);
		// config.setDebuggerEnabled(true);
		// mConnection = new XMPPTCPConnection(config.build());
		// mConnection.setPacketReplyTimeout(10000);

		// SSLContext sslContext = null;
		//
		// sslContext = SSLContext.getInstance("TLS");
		// // SASL PLAIN,
		// TrustManager tm = new X509TrustManager() {
		// @Override
		// public void checkClientTrusted(X509Certificate[] x509Certificates,
		// String s) throws CertificateException {
		// }
		//
		// @Override
		// public void checkServerTrusted(X509Certificate[] x509Certificates,
		// String s) throws CertificateException {
		// }
		//
		// @Override
		// public X509Certificate[] getAcceptedIssuers() {
		// return new X509Certificate[0];
		// }
		// };
		// sslContext.init(null, new TrustManager[] { tm }, null);

		// SASLAuthentication.registerSASLMechanism(new SASLPlainMechanism());

		XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder().setHost(SERVER_HOST)//
				.setPort(SERVER_PORT)//
				.setServiceName(SERVICE_NAME)//
				.setSecurityMode(SecurityMode.ifpossible)//
				.setUsernameAndPassword(USER_NAME, PASSWORD)//
				// .setSASLAuthenticationEnabled()//
				// .setCustomSSLContext(sslContext)//
				// .setEnabledSSLProtocols(new String[] {
				// SASLPlainMechanism.PLAIN })//
				.setSocketFactory(SSLSocketFactory.getDefault())//
				.setResource("RES").setDebuggerEnabled(true)//
				.build();

		XMPPTCPConnection conn = new XMPPTCPConnection(config);

		conn.connect();

		conn.login();

		conn.addAsyncStanzaListener(new StanzaListener() {

			public void processPacket(Stanza packet) throws NotConnectedException {
				System.out.println(packet);

			}
		}, new StanzaFilter() {

			public boolean accept(Stanza stanza) {
				return true;
			}
		});

		while (true) {

		}
	}

}