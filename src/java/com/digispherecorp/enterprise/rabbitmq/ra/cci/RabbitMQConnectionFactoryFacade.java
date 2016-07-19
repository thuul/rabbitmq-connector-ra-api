/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digispherecorp.enterprise.rabbitmq.ra.cci;

import com.digispherecorp.enterprise.rabbitmq.ra.mc.RabbitMQConnectionRequestInfo;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.resource.spi.ActivationSpec;
import javax.resource.spi.ConnectionRequestInfo;

/**
 *
 * @author walle
 */
public class RabbitMQConnectionFactoryFacade implements Serializable {

    private static final long serialVersionUID = 8081775282966091585L;

    private static volatile RabbitMQConnectionFactoryFacade instance;

    private final ConnectionFactory factory = new ConnectionFactory();
    private Connection connection;
    private RabbitMQConnectionRequestInfo requestInfo;

    private final Map<ActivationSpec, ConnectionRequestInfo> connectionMap = new HashMap<>();

    private RabbitMQConnectionFactoryFacade() {
        super();
    }

    public static RabbitMQConnectionFactoryFacade newInstance() {
        synchronized (RabbitMQConnectionFactoryFacade.class) {
            if (instance == null) {
                instance = new RabbitMQConnectionFactoryFacade();
            }
        }
        return instance;
    }

    public static RabbitMQConnectionFactoryFacade getInstance() {
        return instance;
    }

    public ConnectionRequestInfo getConnectionRequestInfo(ActivationSpec activationSpec) {
        return (requestInfo = (RabbitMQConnectionRequestInfo) connectionMap.get(activationSpec));
    }

    public void setConnectionRequestInfo(ActivationSpec activationSpec, ConnectionRequestInfo requestInfo) {
        connectionMap.put(activationSpec, requestInfo);
        this.requestInfo = (RabbitMQConnectionRequestInfo) requestInfo;
    }

    public Connection getConnection() {
        try {
            factory.setUsername(requestInfo.getUser());
            factory.setPassword(requestInfo.getPassword());
            factory.setHost(requestInfo.getHost());
            factory.setPort(Integer.valueOf(requestInfo.getPort()));
            connection = factory.newConnection();
        } catch (NumberFormatException | IOException | TimeoutException ex) {
            Logger.getLogger(RabbitMQConnectionFactoryFacade.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex);
        }
        return connection;
    }

    public RabbitMQConnectionRequestInfo getRequestInfo() {
        return requestInfo;
    }

    public Map<ActivationSpec, ConnectionRequestInfo> getConnectionMap() {
        return connectionMap;
    }

}
