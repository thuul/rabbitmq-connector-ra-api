/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digispherecorp.enterprise.rabbitmq.ra.cci;

import com.digispherecorp.enterprise.rabbitmq.ra.cci.interatn.RabbitMQCCIInteraction;
import com.digispherecorp.enterprise.rabbitmq.ra.cci.interatn.RabbitMQInterationFactory;
import com.digispherecorp.enterprise.rabbitmq.ra.mc.RabbitMQConnectionRequestInfo;
import java.io.Serializable;
import java.util.UUID;
import javax.resource.ResourceException;
import javax.resource.cci.Connection;
import javax.resource.cci.ConnectionMetaData;
import javax.resource.cci.Interaction;
import javax.resource.cci.LocalTransaction;
import javax.resource.cci.ResultSetInfo;
import javax.resource.spi.ConnectionRequestInfo;

/**
 *
 * @author walle
 */
public class RabbitMQCCIConnection implements Connection, AutoCloseable, Serializable {

    private static final long serialVersionUID = 1099470481841675176L;
    private final String uuid;

    private boolean closed;
    private Interaction interaction;
    private LocalTransaction localTransaction;
    private ConnectionMetaData connectionMetaData;
    private ResultSetInfo resultSetInfo;
    private ConnectionRequestInfo connectionRequestInfo;

    public RabbitMQCCIConnection(boolean withTransaction) throws ResourceException {
        this.uuid = UUID.randomUUID().toString();
        this.connectionRequestInfo = new RabbitMQConnectionRequestInfo();
        init(withTransaction);
    }

    public RabbitMQCCIConnection(ConnectionRequestInfo connectionRequestInfo, boolean withTransaction) throws ResourceException {
        this.uuid = UUID.randomUUID().toString();
        this.connectionRequestInfo = connectionRequestInfo;
        init(withTransaction);
    }

    private void init(boolean withTransaction) throws ResourceException {
        if (withTransaction) {
            this.localTransaction = new RabbitMQCCILocalTransaction();
        }
        connectionMetaData = new RabbitMQCCIConnectionMetaData();
    }

    @Override
    public Interaction createInteraction() throws ResourceException {
        interaction = RabbitMQInterationFactory.getInstance()
                .createInteraction(RabbitMQCCIInteraction.class, this);
        return interaction;
    }

    public ConnectionRequestInfo getConnectionRequestInfo() {
        return connectionRequestInfo;
    }

    @Override
    public LocalTransaction getLocalTransaction() throws ResourceException {
        return localTransaction;
    }

    @Override
    public ConnectionMetaData getMetaData() throws ResourceException {
        return connectionMetaData;
    }

    @Override
    public ResultSetInfo getResultSetInfo() throws ResourceException {
        return resultSetInfo;
    }

    public boolean isClosed() {
        return closed;
    }

    @Override
    public void close() throws ResourceException {
        if (interaction != null) {
            interaction.close();
        }
        interaction = null;
        localTransaction = null;
        connectionMetaData = null;
        connectionRequestInfo = null;
        closed = Boolean.TRUE;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof RabbitMQCCIConnection) {
            RabbitMQCCIConnection other = (RabbitMQCCIConnection) obj;
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
