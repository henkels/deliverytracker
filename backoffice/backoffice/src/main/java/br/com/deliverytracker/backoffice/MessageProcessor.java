package br.com.deliverytracker.backoffice;

import java.util.Map;

public interface MessageProcessor {

	void processMessage(Map<String, Object> messageData, MessageSender messageSender);

}
