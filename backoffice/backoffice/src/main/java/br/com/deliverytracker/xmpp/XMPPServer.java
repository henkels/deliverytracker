package br.com.deliverytracker.xmpp;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.deliverytracker.backoffice.MessageProcessor;
import br.com.deliverytracker.backoffice.MessageSender;
import br.com.deliverytracker.commom.ToMapSerializer;
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

    private static final String FIREBASE_SENDER_ID = "611076071574";
    private static final String FIREBASE_SERVER_KEY = "AIzaSyB6AqOLfP4YkfgMW75YVvSzNGRuy95f-Fw";
    private static final String USER_NAME = FIREBASE_SENDER_ID + "@gcm.googleapis.com";
    private static final String PASSWORD = FIREBASE_SERVER_KEY;

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

        private String fromObject(Object object) {
            if (object == null) {
                return null;
            }
            return object.toString();
        }

        @Override
        public GcmPacketExtension parse(XmlPullParser parser, int initialDepth) throws XmlPullParserException, IOException, SmackException {
            String json = parser.nextText();
            Gson gson = new GsonBuilder().create();
            @SuppressWarnings("unchecked")
            Map<String, Object> map = gson.fromJson(json, LinkedHashMap.class);
            XMPPMessage message = new XMPPMessage();
            message.message_id = fromObject(map.get(XMPPMessage.MESSAGE_ID));
            message.message_type = fromObject(map.get(XMPPMessage.MESSAGE_TYPE));
            message.from = fromObject(map.get(XMPPMessage.FROM));
            String str = fromObject(map.get(XMPPMessage.TIME_TO_LIVE));
            if (str != null) {
                message.time_to_live = (int) Double.parseDouble(str);
            }
            message.error = fromObject(map.get(XMPPMessage.ERROR));
            message.control_type = fromObject(map.get(XMPPMessage.CONTROL_TYPE));
            @SuppressWarnings("unchecked")
            Map<String, String> dataMap = (Map<String, String>) map.get(XMPPMessage.DATA);
            if (dataMap != null) {
                message.data = ToMapSerializer.unserialize(dataMap);
            }
            return new GcmPacketExtension(message);
        }
    }

    private static final class GcmPacketExtension extends DefaultExtensionElement {

        private final XMPPMessage message;

        public GcmPacketExtension(XMPPMessage message) {
            super(GCM_ELEMENT_NAME, GCM_NAMESPACE);
            this.message = message;
        }

        public XMPPMessage getMessage() {
            return message;
        }

        @Override
        public String toXML() {
            return String.format("<%s xmlns=\"%s\">%s</%s>", GCM_ELEMENT_NAME, GCM_NAMESPACE, StringUtils.escapeForXML(buildGoogleMobileDataJson()), GCM_ELEMENT_NAME);
        }

        private String buildGoogleMobileDataJson() {
            StringBuilder sb = new StringBuilder();
            //{
            sb.append("{\n");
            //"to":"REGISTRATION_ID"
            sb.append("  \"to\": \"");
            sb.append(message.to);
            sb.append("\",\n");
            //"message_id":"m-1366082849205" 
            sb.append("  \"message_id\": \"");
            sb.append(message.message_id);
            sb.append("\",\n");
            //"data":
            //{
            //      "hello":"world",
            //}
            if (message.data != null) {
                sb.append("  \"data\": \n");
                sb.append(ToMapSerializer.toJson(message.data));
                sb.append(",\n");
            }
            //"time_to_live":"600",
            sb.append("  \"time_to_live\": ");
            sb.append(message.time_to_live);
            sb.append(",\n");
            //"delay_while_idle": true/false,
            sb.append("  \"delay_while_idle\": false,\n");
            //"delivery_receipt_requested": true/false
            sb.append("  \"delivery_receipt_requested\": true\n}");
            return sb.toString();
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
