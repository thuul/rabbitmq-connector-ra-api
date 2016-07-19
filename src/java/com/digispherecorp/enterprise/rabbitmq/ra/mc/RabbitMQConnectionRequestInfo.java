/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digispherecorp.enterprise.rabbitmq.ra.mc;

import javax.resource.spi.ConnectionRequestInfo;

/**
 *
 * @author walle
 */
public class RabbitMQConnectionRequestInfo implements ConnectionRequestInfo {

    private String connectionId;

    private String user;
    private String password;
    private String host;
    private String port;
    private String virtualHost;

    private String queueName;
    private String exchangeName;
    private String routingKey;

    public RabbitMQConnectionRequestInfo() {
    }

    public RabbitMQConnectionRequestInfo(String user, String password, String host, String port, String virtualHost) {
        this.user = user;
        this.password = password;
        this.host = host;
        this.port = port;
        this.virtualHost = virtualHost;
    }

    public RabbitMQConnectionRequestInfo(String user, String password, String host, String port, String virtualHost, String queueName) {
        this.user = user;
        this.password = password;
        this.host = host;
        this.port = port;
        this.virtualHost = virtualHost;
        this.queueName = queueName;
    }

    public RabbitMQConnectionRequestInfo(String user, String password, String host, String port, String virtualHost, String queueName, String exchangeName, String routingKey) {
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

    public String getAmqpURL() {
        return getRabbitmqURL();
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

    public String getConnectionId() {
        return connectionId;
    }

    public RabbitMQConnectionRequestInfo setConnectionId(String connectionId) {
        this.connectionId = connectionId;
        return this;
    }

    public String getRabbitmqURL() {
        return new StringBuilder().append("amqp://")
                .append(getUser())
                .append(":")
                .append(getPassword())
                .append("@")
                .append(getHost())
                .append(":")
                .append(getPort())
                .append("/")
                .append(getVirtualHost()).toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof RabbitMQConnectionRequestInfo) {
            RabbitMQConnectionRequestInfo other = (RabbitMQConnectionRequestInfo) obj;
            return (isEqual(user, other.user)
                    && isEqual(password, other.password)
                    && isEqual(host, other.host)
                    && isEqual(port, other.port)
                    && isEqual(virtualHost, other.virtualHost));
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        String result = "" + user + password + host + port + virtualHost;
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
