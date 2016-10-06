package br.com.deliverytracker.commom.client;

public interface ClientMessageVisitor {

    void visit(DeliveryInfo msg);
}
