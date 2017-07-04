package br.com.deliverytracker.backoffice;

import br.com.deliverytracker.commom.XMPPMessage;

public interface MessageSender {

    void sendMessage(XMPPMessage message);
}
