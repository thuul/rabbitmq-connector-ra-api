/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digispherecorp.enterprise.rabbitmq.ra.endpoint;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author walle
 *
 * <p>
 * A simple POJO class that can collect and aggregate message bytes from a
 * Rabbit MQ Messaging Broker.
 *
 * @see RabbitMQAMQPMessageListener
 */
public class RabbitMQMessage implements Serializable {

    private static final long serialVersionUID = -7092695860928067113L;

    private List<byte[]> messages = new ArrayList<>();

    public List<byte[]> getMessages() {
        return messages;
    }

    public void setMessages(List<byte[]> messages) {
        this.messages = messages;
    }

}
