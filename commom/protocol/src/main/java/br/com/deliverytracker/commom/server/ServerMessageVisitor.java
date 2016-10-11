package br.com.deliverytracker.commom.server;

public interface ServerMessageVisitor {

    void visit(LinkUserToDevice msg);
}
