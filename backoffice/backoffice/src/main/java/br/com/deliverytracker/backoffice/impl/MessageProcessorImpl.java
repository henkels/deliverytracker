package br.com.deliverytracker.backoffice.impl;

import br.com.deliverytracker.backoffice.MessageProcessor;
import br.com.deliverytracker.backoffice.MessageSender;
import br.com.deliverytracker.commom.XMPPMessage;

public class MessageProcessorImpl implements MessageProcessor {

	@Override
	public void processMessage(XMPPMessage message, MessageSender messageSender) {
		System.out.println(message);
	}

}
