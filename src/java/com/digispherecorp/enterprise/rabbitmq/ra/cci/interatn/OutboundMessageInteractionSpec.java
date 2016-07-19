/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digispherecorp.enterprise.rabbitmq.ra.cci.interatn;

import com.digispherecorp.enterprise.rabbitmq.ra.cci.RabbitMQCCIConnection;
import com.digispherecorp.enterprise.rabbitmq.ra.cci.sp.RabbitMQMessageRecord;
import com.digispherecorp.enterprise.rabbitmq.ra.mc.RabbitMQConnectionRequestInfo;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.resource.ResourceException;
import javax.resource.cci.Record;
import javax.resource.spi.ConnectionRequestInfo;

/**
 *
 * @author walle
 */
public final class OutboundMessageInteractionSpec implements ExtendedInteractionSpec {

    private static final long serialVersionUID = 3503629115770067285L;

    private final transient ConnectionFactory connectionFactory;
    private transient Connection connection;
    private transient Channel channel;
    private ConnectionRequestInfo connectionRequestInfo;

    public OutboundMessageInteractionSpec() {
        connectionFactory = new ConnectionFactory();
    }

    private void init() {
        connectionFactory.setUsername(((RabbitMQConnectionRequestInfo) connectionRequestInfo).getUser());
        connectionFactory.setPassword(((RabbitMQConnectionRequestInfo) connectionRequestInfo).getPassword());
        connectionFactory.setHost(((RabbitMQConnectionRequestInfo) connectionRequestInfo).getHost());
        connectionFactory.setPort(Integer.valueOf(((RabbitMQConnectionRequestInfo) connectionRequestInfo).getPort()));
    }

    @Override
    public void execute(Record input, Record output) throws ResourceException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Record execute(Record input) throws ResourceException {
        try {
            RabbitMQMessageRecord messageRecord = ((RabbitMQMessageRecord) input);
            connection = connectionFactory.newConnection();
            channel = connection.createChannel();
            if (messageRecord.getExchangeType() != null && !messageRecord.getExchangeType().isEmpty()) {
                channel.exchangeDeclare(((RabbitMQConnectionRequestInfo) connectionRequestInfo).getExchangeName(), messageRecord.getExchangeType(), true);
            }
            channel.basicPublish(((RabbitMQConnectionRequestInfo) connectionRequestInfo).getExchangeName(),
                    ((RabbitMQConnectionRequestInfo) connectionRequestInfo).getRoutingKey(),
                    MessageProperties.PERSISTENT_TEXT_PLAIN, messageRecord.getMessageByte());
        } catch (IOException | TimeoutException ex) {
            Logger.getLogger(OutboundMessageInteractionSpec.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex);
            throw new ResourceException(ex);
        }
        return null;
    }

    @Override
    public void close() throws ResourceException {
        try {
            if (connection != null) {
                if (connection.isOpen()) {
                    connection.close();
                }
            }
            connection = null;
        } catch (IOException ex) {
            Logger.getLogger(RabbitMQCCIConnection.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex);
        }
    }

    @Override
    public ConnectionRequestInfo getConnectionRequestInfo() {
        return connectionRequestInfo;
    }

    @Override
    public void setConnectionRequestInfo(ConnectionRequestInfo connectionRequestInfo) {
        this.connectionRequestInfo = connectionRequestInfo;
        init();
    }

}
