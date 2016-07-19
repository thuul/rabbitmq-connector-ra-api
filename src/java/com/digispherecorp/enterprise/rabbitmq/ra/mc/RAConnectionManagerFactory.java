/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digispherecorp.enterprise.rabbitmq.ra.mc;

import javax.resource.spi.ConnectionManager;

/**
 *
 * @author walle
 */
public class RAConnectionManagerFactory {

    private static volatile RAConnectionManagerFactory instance;
    private ConnectionManager connectionManager;

    private RAConnectionManagerFactory() {
        connectionManager = new RabbitMQConnectionManager();
    }

    public static RAConnectionManagerFactory newInstance() {
        synchronized (RAConnectionManagerFactory.class) {
            if (instance == null) {
                instance = new RAConnectionManagerFactory();
            }
        }
        return instance;
    }

    public static RAConnectionManagerFactory getInstance() {
        return instance;
    }

    public ConnectionManager getConnectionManager() {
        return connectionManager;
    }

    public void setConnectionManager(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

}
