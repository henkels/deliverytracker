package br.com.deliverytracker.xmpp;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.net.ssl.SSLSocketFactory;

import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.StanzaListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.StanzaTypeFilter;
import org.jivesoftware.smack.packet.DefaultExtensionElement;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Stanza;
import org.jivesoftware.smack.provider.ExtensionElementProvider;
import org.jivesoftware.smack.provider.ProviderManager;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smack.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import br.com.deliverytracker.backoffice.MessageProcessor;
import br.com.deliverytracker.backoffice.MessageSender;
import br.com.deliverytracker.commom.JSonSerializer;
import br.com.deliverytracker.commom.XMPPMessage;

public class XMPPServer implements StanzaListener, MessageSender {

	private static final String GCM_ELEMENT_NAME = "gcm";
	private static final String GCM_NAMESPACE = "google:mobile:data";

	static {
		Base64Utils.init();
		ProviderManager.addExtensionProvider(GCM_ELEMENT_NAME, GCM_NAMESPACE, new GcmPacketExtensionProvider());
	}

	private final Logger logger = LoggerFactory.getLogger(XMPPServer.class);

	private static final String SERVER_HOST = "fcm-xmpp.googleapis.com";
	private static final int SERVER_PORT = 5235;

	private static final String SERVICE_NAME = "gcm.googleapis.com";

	private static final String USER_NAME = "497175095084@gcm.googleapis.com";
	private static final String PASSWORD = "AIzaSyD4P94m9lVzUG73GI7Q5dRqZVgxg4Tq-Qo";

	private final Map<String, XMPPMessage> pendingMessages = new ConcurrentHashMap<>();

	private XMPPTCPConnection connection;

	private final MessageProcessor processor;

	public XMPPServer(MessageProcessor processor) {
		this.processor = processor;
	}

	public void sendMessage(XMPPMessage message) {
		message.message_id = "m-" + UUID.randomUUID().toString();
		internalSendMessage(message);
	}

	private void internalSendMessage(XMPPMessage message) {
		try {
			pendingMessages.put(message.message_id, message);
			connection.sendStanza(new GcmPacketExtension(message).toStanza());
		} catch (SmackException.NotConnectedException e) {
			logger.warn("GCMClient", "No connection", e);
		}
	}

	public void start() throws Exception {
		this.connection = connect();
	}

	public void stop() throws Exception {
		this.connection.disconnect();
	}

	@Override
	public void processPacket(Stanza stanza) throws SmackException.NotConnectedException {
		Message incomingMessage = (Message) stanza;
		GcmPacketExtension gcmPacket = (GcmPacketExtension) incomingMessage.getExtension(GCM_NAMESPACE);
		XMPPMessage message = gcmPacket.getMessage();

		try {
			String messageType = message.message_type;

			if (messageType == null) {
				handleAppStreamMessage(message);
				return;
			}

			switch (messageType) {
			case "ack":
				handleAckReceipt(message);
				break;
			case "nack":
				handleNackReceipt(message);
				break;
			case "receipt":
				handleDeliveryReceipt(message);
				break;
			case "control":
				handleControlMessage(message);
				break;
			default:
				logger.warn("Received unknown GCM message: " + messageType);
			}
		} catch (Exception e) {
			logger.warn("GCMClient", "Failed to process packet", e);
		}
	}

	private void handleControlMessage(XMPPMessage message) {
		String controlType = message.control_type;

		if ("CONNECTION_DRAINING".equals(controlType)) {
			logger.warn("GCM Connection is draining! Initiating reconnect...");
			reconnect();
		} else {
			logger.warn("Received unknown GCM control message: " + controlType);
		}
	}

	private void handleDeliveryReceipt(XMPPMessage message) {
		logger.warn("Got delivery receipt!");
	}

	private void handleNackReceipt(XMPPMessage message) {
		String messageId = message.message_id;
		String errorCode = message.error;

		if (errorCode == null) {
			logger.warn("Null GCM error code!");
			if (messageId != null) {
				pendingMessages.remove(messageId);
			}

			return;
		}

		switch (errorCode) {
		case "BAD_REGISTRATION":
			handleBadRegistration(message);
			break;
		case "DEVICE_UNREGISTERED":
			handleBadRegistration(message);
			break;
		case "INTERNAL_SERVER_ERROR":
			handleServerFailure(message);
			break;
		case "INVALID_JSON":
			handleClientFailure(message);
			break;
		case "QUOTA_EXCEEDED":
			handleClientFailure(message);
			break;
		case "SERVICE_UNAVAILABLE":
			handleServerFailure(message);
			break;
		}
	}

	private void handleAckReceipt(XMPPMessage message) {
		String messageId = (String) message.message_id;

		if (messageId != null) {
			pendingMessages.remove(messageId);
		}
	}

	private void handleAppStreamMessage(XMPPMessage message) throws SmackException.NotConnectedException {
		logger.info(String.format("Got Message : [%s]", message));
		processor.processMessage(message, this);

		XMPPMessage ack = message.ack();

		Stanza request = new GcmPacketExtension(ack).toStanza();
		connection.sendStanza(request);
	}

	private void handleBadRegistration(XMPPMessage message) {
		// TODO
		// logger.warn("Got GCM unregistered notice!");
		// String messageId = message.message_id;

		// if (messageId != null) {
		// XMPPMessage unacknowledgedMessage =
		// pendingMessages.remove(messageId);
		// if (unacknowledgedMessage != null) {
		// unregisteredQueue.put(
		// new UnregisteredEvent(unacknowledgedMessage.getGcmId(), null,
		// unacknowledgedMessage.getNumber(),
		// unacknowledgedMessage.getDeviceId(), System.currentTimeMillis()));
		// }
		// }
	}

	private void handleServerFailure(XMPPMessage message) {
		String messageId = message.message_id;

		if (messageId != null) {
			XMPPMessage unacknowledgedMessage = pendingMessages.remove(messageId);

			if (unacknowledgedMessage != null) {
				sendMessage(unacknowledgedMessage);
			}
		}
	}

	private void handleClientFailure(XMPPMessage message) {
		logger.warn("Unrecoverable error: " + message.error);
		String messageId = message.message_id;

		if (messageId != null) {
			pendingMessages.remove(messageId);
		}
	}

	private void reconnect() {
		try {
			this.connection.disconnect();
		} catch (Exception e) {
			logger.warn("GCMClient", "Disconnect attempt", e);
		}

		while (true) {
			try {
				this.connection = connect();
				return;
			} catch (XMPPException | IOException | SmackException e) {
				logger.warn("GCMClient", "Reconnecting", e);
				Util.sleep(1000);
			}
		}
	}

	private XMPPTCPConnection connect() throws XMPPException, IOException, SmackException {

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

		conn.addConnectionListener(new LoggingConnectionListener());
		conn.addAsyncStanzaListener(this, new StanzaTypeFilter(Message.class));

		conn.login();
		return conn;
	}

	private static class GcmPacketExtensionProvider extends ExtensionElementProvider<GcmPacketExtension> {

		@Override
		public GcmPacketExtension parse(XmlPullParser parser, int initialDepth)
				throws XmlPullParserException, IOException, SmackException {
			String json = parser.nextText();
			return new GcmPacketExtension(json);
		}
	}

	private static final class GcmPacketExtension extends DefaultExtensionElement {

		private final XMPPMessage message;

		public GcmPacketExtension(XMPPMessage message) {
			super(GCM_ELEMENT_NAME, GCM_NAMESPACE);
			this.message = message;
		}

		public GcmPacketExtension(String json) {
			this(JSonSerializer.toObject(json, XMPPMessage.class));
		}

		public XMPPMessage getMessage() {
			return message;
		}

		@Override
		public String toXML() {
			return String.format("<%s xmlns=\"%s\">%s</%s>", GCM_ELEMENT_NAME, GCM_NAMESPACE,
					StringUtils.escapeForXML(JSonSerializer.toJSON(message)), GCM_ELEMENT_NAME);
		}

		public Stanza toStanza() {
			Message message = new Message();
			message.addExtension(this);
			return message;
		}
	}

	private class LoggingConnectionListener implements ConnectionListener {

		@Override
		public void connected(XMPPConnection xmppConnection) {
			logger.warn("GCM XMPP Connected.");
		}

		@Override
		public void authenticated(XMPPConnection xmppConnection, boolean resumed) {
			logger.warn("GCM XMPP Authenticated.");
			reconnectionSuccessful();
		}

		@Override
		public void reconnectionSuccessful() {
			logger.warn("GCM XMPP Reconnected, resending... Pending Size: " + pendingMessages.size());
			HashMap<String, XMPPMessage> resendMessages = new HashMap<>(pendingMessages);

			for (Entry<String, XMPPMessage> resendMessage : resendMessages.entrySet()) {
				sendMessage(resendMessage.getValue());
			}
		}

		@Override
		public void reconnectionFailed(Exception e) {
			logger.warn("GCM XMPP Reconnection failed!", e);
			reconnect();
		}

		@Override
		public void reconnectingIn(int seconds) {
			logger.warn(String.format("GCM XMPP Reconnecting in %d secs", seconds));
		}

		@Override
		public void connectionClosedOnError(Exception e) {
			logger.warn("GCM XMPP Connection closed on error. Pending Size: " + pendingMessages.size());
		}

		@Override
		public void connectionClosed() {
			logger.warn("GCM XMPP Connection closed. Pending Size: " + pendingMessages.size());
			reconnect();
		}
	}
}
