package br.com.deliverytracker.backoffice.impl;

import br.com.deliverytracker.backoffice.MessageProcessor;
import br.com.deliverytracker.backoffice.MessageSender;
import br.com.deliverytracker.commom.XMPPMessage;
import br.com.deliverytracker.commom.server.LinkUserToDevice;
import br.com.deliverytracker.commom.server.ServerMessage;
import br.com.deliverytracker.commom.server.ServerMessageVisitor;

public class MessageProcessorImpl implements MessageProcessor, ServerMessageVisitor {

    @Override
    public void processMessage(XMPPMessage message, MessageSender messageSender) {
        ServerMessage msg = (ServerMessage) message.data;
        msg.accept(this);
    }

    @Override
    public void visit(LinkUserToDevice msg) {
        System.out.println(String.format("User %s linked to device %s", msg.userMail, msg.deviceID));
    }

}
