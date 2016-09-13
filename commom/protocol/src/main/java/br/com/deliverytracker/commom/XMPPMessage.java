package br.com.deliverytracker.commom;

public class XMPPMessage {

    public static final String MESSAGE_ID = "message_id";
    public String message_id;
    public static final String MESSAGE_TYPE = "message_type";
    public String message_type;
    //	public String to;
    public static final String FROM = "from";
    public String from;
    public static final String TIME_TO_LIVE = "time_to_live";
    public int time_to_live;
    //	public boolean isReceipt;
    public static final String ERROR = "error";
    public String error;
    public static final String CONTROL_TYPE = "control_type";
    public String control_type;
    public static final String DATA = "data";
    public Object data;

    //	 "time_to_live": 0,
    //	  "from": "f8DuXXE86QI:APA91bEU18hTavBNR73r62eI_HgszVIVw3dAYK5FIkPvZXhRj55Hiui8uFeAtuFfWc-1GTMPrEQ-2vS_GgURyXxhlqHv5WzTIxopAYvXzAmJ7pYuhur53GKn98qoLK5t73AtvuVElLli",
    //	  "message_id": "5",
    //	  "category": "br.com.deliverytracker.receivingmanager"

    public XMPPMessage ack() {
        XMPPMessage ack = new XMPPMessage();
        ack.message_id = this.message_id;
        ack.message_type = "ack";
        //		ack.to = this.from;
        return ack;
    }

}
