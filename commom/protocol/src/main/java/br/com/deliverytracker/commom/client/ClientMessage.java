package br.com.deliverytracker.commom.client;

public interface ClientMessage {

    void accept(ClientMessageVisitor visitor);
}
