package br.com.deliverytracker.commom.data;

public class Package {

    public String description;

    public String sender;

    public String transporter;

    public long senddate;

    public long initialEta;

    public long currentEta;

    public LocationData location;

    public long lastAtualizationTime;

    public Package() {

    }
}
