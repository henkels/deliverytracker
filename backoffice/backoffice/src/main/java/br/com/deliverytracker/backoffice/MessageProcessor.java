package br.com.deliverytracker.backoffice;

import br.com.deliverytracker.commom.XMPPMessage;

public interface MessageProcessor {

	void processMessage(XMPPMessage message, MessageSender messageSender);

}
