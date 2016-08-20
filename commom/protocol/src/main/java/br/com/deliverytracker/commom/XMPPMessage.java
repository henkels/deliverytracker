package br.com.deliverytracker.commom;

public class XMPPMessage {

	public String message_type;
	public String to;
	public String from;
	public String message_id;
	public boolean isReceipt;
	public String error;
	public String control_type;
	public ToMapSerializer data;
	

	public XMPPMessage ack() {
		XMPPMessage ack = new XMPPMessage();
		ack.message_type = "ack";
		ack.to = this.from;
		ack.message_id = this.message_id;
		return ack;
	}

}
