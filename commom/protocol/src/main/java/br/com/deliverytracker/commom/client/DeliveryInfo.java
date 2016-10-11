package br.com.deliverytracker.commom.client;

import br.com.deliverytracker.commom.LocationData;

public class DeliveryInfo implements ClientMessage {

    public String id;

    public String description;

    public String sender;

    public String transporter;

    public long senddate;

    public long initialEta;

    public long currentEta;

    public LocationData currentLocation;

    public long lastAtualizationTime;

    public DeliveryInfo() {
        this(null, null, null, null, 0, 0, 0, null, 0);
    }

    public DeliveryInfo(String id, String description, String sender, String transporter, long senddate, long initialEta, long currentEta, LocationData currentLocation, long lastAtualizationTime) {
        super();
        this.id = id;
        this.description = description;
        this.sender = sender;
        this.transporter = transporter;
        this.senddate = senddate;
        this.initialEta = initialEta;
        this.currentEta = currentEta;
        this.currentLocation = currentLocation;
        this.lastAtualizationTime = lastAtualizationTime;
    }


    @Override
    public void accept(ClientMessageVisitor visitor) {
        visitor.visit(this);
    }

}
