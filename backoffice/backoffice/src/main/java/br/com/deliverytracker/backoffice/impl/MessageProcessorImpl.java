package br.com.deliverytracker.backoffice.impl;

import br.com.deliverytracker.backoffice.MessageProcessor;
import br.com.deliverytracker.backoffice.MessageSender;
import br.com.deliverytracker.commom.LocationData;
import br.com.deliverytracker.commom.XMPPMessage;
import br.com.deliverytracker.commom.client.DeliveryInfo;
import br.com.deliverytracker.commom.server.LinkUserToDevice;
import br.com.deliverytracker.commom.server.ServerMessage;
import br.com.deliverytracker.commom.server.ServerMessageVisitor;

public class MessageProcessorImpl implements MessageProcessor, ServerMessageVisitor {

    private MessageSender messageSender;

    @Override
    public void processMessage(XMPPMessage message, MessageSender messageSender) {
        this.messageSender = messageSender;
        ServerMessage msg = (ServerMessage) message.data;
        msg.accept(this);
    }

    @Override
    public void visit(LinkUserToDevice msg) {
        System.out.println(String.format("User %s linked to device %s", msg.userMail, msg.deviceID));
        XMPPMessage newMsg = new XMPPMessage();
        newMsg.to = msg.deviceID;
        DeliveryInfo info = new DeliveryInfo();
        info.id = "01";
        info.description = "dx";
        info.sender = "sd";
        info.transporter = "ti";
        info.senddate = 0;
        info.initialEta = 0;
        info.currentEta = 0;
        info.currentLocation = new LocationData();
        info.lastAtualizationTime = 0;
        newMsg.data = info;
        messageSender.sendMessage(newMsg);
    }

}
