/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digispherecorp.enterprise.rabbitmq.ra.cci;

import com.digispherecorp.enterprise.rabbitmq.ra.mc.RabbitMQConnectionManager;
import com.digispherecorp.enterprise.rabbitmq.ra.mc.RabbitMQConnectionRequestInfo;
import com.digispherecorp.enterprise.rabbitmq.ra.mc.RabbitMQManagedConnectionFactory;
import java.util.UUID;
import javax.naming.NamingException;
import javax.naming.Reference;
import javax.resource.ResourceException;
import javax.resource.cci.Connection;
import javax.resource.cci.ConnectionFactory;
import javax.resource.cci.ConnectionSpec;
import javax.resource.cci.RecordFactory;
import javax.resource.cci.ResourceAdapterMetaData;
import javax.resource.spi.ConnectionManager;

/**
 *
 * @author walle
 */
public class RabbitMQCCIConnectionFactory implements ConnectionFactory {

    private static final long serialVersionUID = -8735895284813952541L;
    private final String uuid;

    private Reference reference;
    private ConnectionSpec connectionSpec;

    private RabbitMQManagedConnectionFactory managedConnectionFactory;
    private ConnectionManager cxManager;

    public RabbitMQCCIConnectionFactory() {
        this.uuid = UUID.randomUUID().toString();
    }

    public RabbitMQCCIConnectionFactory(RabbitMQManagedConnectionFactory managedConnectionFactory, ConnectionManager cxManager) {
        this();
        this.managedConnectionFactory = managedConnectionFactory;
        this.cxManager = cxManager;
        if (this.cxManager == null) {
            this.cxManager = new RabbitMQConnectionManager();
        }
    }

    @Override
    public Connection getConnection() throws ResourceException {

        Object allocateConnection = cxManager.allocateConnection(managedConnectionFactory, new RabbitMQConnectionRequestInfo(
                ((RabbitMQCCIConnectionSpec) connectionSpec).getUser(),
                ((RabbitMQCCIConnectionSpec) connectionSpec).getPassword(),
                ((RabbitMQCCIConnectionSpec) connectionSpec).getHost(),
                ((RabbitMQCCIConnectionSpec) connectionSpec).getPort(),
                ((RabbitMQCCIConnectionSpec) connectionSpec).getVirtualHost(),
                ((RabbitMQCCIConnectionSpec) connectionSpec).getQueueName(),
                ((RabbitMQCCIConnectionSpec) connectionSpec).getExchangeName(),
                ((RabbitMQCCIConnectionSpec) connectionSpec).getRoutingKey()));
        return (Connection) allocateConnection;
    }

    @Override
    public Connection getConnection(ConnectionSpec properties) throws ResourceException {
        connectionSpec = properties;
        Object allocateConnection = cxManager.allocateConnection(managedConnectionFactory, new RabbitMQConnectionRequestInfo(
                ((RabbitMQCCIConnectionSpec) connectionSpec).getUser(),
                ((RabbitMQCCIConnectionSpec) connectionSpec).getPassword(),
                ((RabbitMQCCIConnectionSpec) connectionSpec).getHost(),
                ((RabbitMQCCIConnectionSpec) connectionSpec).getPort(),
                ((RabbitMQCCIConnectionSpec) connectionSpec).getVirtualHost(),
                ((RabbitMQCCIConnectionSpec) connectionSpec).getQueueName(),
                ((RabbitMQCCIConnectionSpec) connectionSpec).getExchangeName(),
                ((RabbitMQCCIConnectionSpec) connectionSpec).getRoutingKey()));
        return (Connection) allocateConnection;
    }

    @Override
    public RecordFactory getRecordFactory() throws ResourceException {
        return new RabbitMQCCIRecordFactory();
    }

    @Override
    public ResourceAdapterMetaData getMetaData() throws ResourceException {
        return new RabbitMQResourceAdapterMetaData();
    }

    @Override
    public void setReference(Reference reference) {
        this.reference = reference;
    }

    @Override
    public Reference getReference() throws NamingException {
        return reference;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof RabbitMQCCIConnectionFactory) {
            RabbitMQCCIConnectionFactory other = (RabbitMQCCIConnectionFactory) obj;
            return isEqual(uuid, other.uuid) && isEqual(toString(), other.toString());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        String result = "" + uuid;
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
