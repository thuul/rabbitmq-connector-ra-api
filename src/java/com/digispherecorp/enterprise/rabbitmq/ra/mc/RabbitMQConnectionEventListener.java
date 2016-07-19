/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digispherecorp.enterprise.rabbitmq.ra.mc;

import java.util.logging.Logger;
import javax.resource.spi.ConnectionEvent;
import javax.resource.spi.ConnectionEventListener;
import javax.resource.spi.ManagedConnection;

/**
 *
 * @author walle
 */
public class RabbitMQConnectionEventListener implements ConnectionEventListener {

    private final ManagedConnection managedConnection;

    public RabbitMQConnectionEventListener() {
        this.managedConnection = null;
    }

    public RabbitMQConnectionEventListener(ManagedConnection managedConnection) {
        this.managedConnection = managedConnection;
    }

    @Override
    public void connectionClosed(ConnectionEvent event) {
        Logger.getLogger(RabbitMQConnectionEventListener.class.getName()).info(event.toString());
    }

    @Override
    public void localTransactionStarted(ConnectionEvent event) {
        Logger.getLogger(RabbitMQConnectionEventListener.class.getName()).info(event.toString());
    }

    @Override
    public void localTransactionCommitted(ConnectionEvent event) {
        Logger.getLogger(RabbitMQConnectionEventListener.class.getName()).info(event.toString());
    }

    @Override
    public void localTransactionRolledback(ConnectionEvent event) {
        Logger.getLogger(RabbitMQConnectionEventListener.class.getName()).info(event.toString());
    }

    @Override
    public void connectionErrorOccurred(ConnectionEvent event) {
        Logger.getLogger(RabbitMQConnectionEventListener.class.getName()).info(event.getException().getLocalizedMessage().concat(" : ")
                .concat(event.toString()).concat("@ ").concat(String.valueOf(event.getId())));
    }

}
