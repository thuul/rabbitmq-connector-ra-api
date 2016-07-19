/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digispherecorp.enterprise.rabbitmq.ra.activatn;

import com.digispherecorp.enterprise.rabbitmq.ra.endpoint.RabbitMQAMQPMessageListener;
import java.io.Serializable;
import javax.resource.ResourceException;
import javax.resource.spi.Activation;
import javax.resource.spi.ActivationSpec;
import javax.resource.spi.ConfigProperty;
import javax.resource.spi.InvalidPropertyException;
import javax.resource.spi.ResourceAdapter;

/**
 *
 * @author walle
 *
 *
 * <p> Implements <tt>ActivationSpec</tt> class of javax.resource.spi
 * <p>
 * The simple name variable is optional and not really needed. However, it can
 * be set per activation specification class to uniquely identify which Message
 * Driven Bean (MDB) are mapped to which <tt>ActivationSpec</tt> class and which
 * are being activated or deactivated.
 *
 * @see RabbitMQAMQPMessageListener
 * @see ActivationSpec
 *
 *
 *
 */
@Activation(messageListeners = {RabbitMQAMQPMessageListener.class})
public class RabbitMQActivationSpec implements ActivationSpec, Serializable {

    private static final long serialVersionUID = 1586602911733946124L;

    @ConfigProperty(
            supportsDynamicUpdates = false,
            type = String.class
    )
    private String user;

    @ConfigProperty(type = String.class, confidential = true)
    private String password;

    @ConfigProperty(type = String.class)
    private String host;

    @ConfigProperty(type = String.class)
    private String port;

    @ConfigProperty(type = String.class)
    private String virtualHost;

    @ConfigProperty(type = String.class)
    private String queueName;

    @ConfigProperty(type = String.class)
    private String exchangeName;

    @ConfigProperty(type = String.class)
    private String routingKey;

    @ConfigProperty(type = String.class)
    private String destinationType;

    private String amqpURL;

    private String simpleName = this.toString();

    private ResourceAdapter adapter;

    @Override
    public void validate() throws InvalidPropertyException {
    }

    @Override
    public ResourceAdapter getResourceAdapter() {
        return adapter;
    }

    @Override
    public void setResourceAdapter(ResourceAdapter ra) throws ResourceException {
        this.adapter = ra;
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

    public String getDestinationType() {
        return destinationType;
    }

    public void setDestinationType(String destinationType) {
        this.destinationType = destinationType;
    }

    public String getSimpleName() {
        return simpleName;
    }

    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }

    public String getAmqpURL() {
        return amqpURL;
    }

}
