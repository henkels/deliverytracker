package br.com.deliverytracker.commom.server;

public class LinkUserToDevice implements ServerMessage {

    public String userMail;
    public String deviceID;

    public LinkUserToDevice() {
        this(null, null);
    }

    public LinkUserToDevice(String userMail, String deviceID) {
        this.userMail = userMail;
        this.deviceID = deviceID;
    }

    @Override
    public void accept(ServerMessageVisitor visitor) {
        visitor.visit(this);
    }

}
