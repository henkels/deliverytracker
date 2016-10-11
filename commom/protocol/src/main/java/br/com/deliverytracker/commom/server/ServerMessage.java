package br.com.deliverytracker.commom.server;

public interface ServerMessage {

    void accept(ServerMessageVisitor visitor);
}
