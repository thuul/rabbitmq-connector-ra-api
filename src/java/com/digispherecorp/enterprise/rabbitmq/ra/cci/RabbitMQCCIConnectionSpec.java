/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digispherecorp.enterprise.rabbitmq.ra.cci;

import java.io.Serializable;
import javax.resource.cci.ConnectionSpec;

/**
 *
 * @author walle
 */
public class RabbitMQCCIConnectionSpec implements ConnectionSpec, Serializable {

    private static final long serialVersionUID = -6001979173440620804L;

    private String user;
    private String password;
    private String host;
    private String port;
    private String virtualHost;

    private String queueName;
    private String exchangeName;
    private String routingKey;

    public RabbitMQCCIConnectionSpec() {
    }

    public RabbitMQCCIConnectionSpec(String user, String password, String host, String port, String virtualHost, String queueName, String exchangeName, String routingKey) {
        this.user = user;
        this.password = password;
        this.host = host;
        this.port = port;
        this.virtualHost = virtualHost;
        this.queueName = queueName;
        this.exchangeName = exchangeName;
        this.routingKey = routingKey;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getVirtualHost() {
        return virtualHost;
    }

    public void setVirtualHost(String virtualHost) {
        this.virtualHost = virtualHost;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public String getExchangeName() {
        return exchangeName;
    }

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof RabbitMQCCIConnectionSpec) {
            RabbitMQCCIConnectionSpec other = (RabbitMQCCIConnectionSpec) obj;
            return (isEqual(user, other.user)
                    && isEqual(password, other.password)
                    && isEqual(host, other.host)
                    && isEqual(port, other.port)
                    && isEqual(virtualHost, other.virtualHost)
                    && isEqual(queueName, other.queueName)
                    && isEqual(exchangeName, other.exchangeName)
                    && isEqual(routingKey, other.routingKey));
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        String result = "" + user + password + host + port + virtualHost + queueName + exchangeName + routingKey;
        return result.hashCode();
    }

    private boolean isEqual(Object o1, Object o2) {
        if (o1 == null) {
            return o2 == null;
        } else {
            return o1.equals(o2);
        }
    }
}
