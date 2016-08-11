package br.com.deliverytracker.backoffice.impl;

import java.util.Map;

import br.com.deliverytracker.backoffice.MessageProcessor;
import br.com.deliverytracker.backoffice.MessageSender;

public class MessageProcessorImpl implements MessageProcessor {

	@Override
	public void processMessage(Map<String, Object> messageData, MessageSender messageSender) {
		System.out.println(messageData);
	}

}
