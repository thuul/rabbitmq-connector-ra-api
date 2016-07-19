/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digispherecorp.enterprise.rabbitmq.ra.mc;

import com.digispherecorp.enterprise.rabbitmq.ra.cci.RabbitMQCCIConnection;
import com.digispherecorp.enterprise.rabbitmq.ra.cci.RabbitMQCCIConnectionFactory;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Set;
import java.util.UUID;
import javax.resource.ResourceException;
import javax.resource.cci.Connection;
import javax.resource.cci.ConnectionFactory;
import javax.resource.spi.ConnectionDefinition;
import javax.resource.spi.ConnectionManager;
import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.ManagedConnection;
import javax.resource.spi.ManagedConnectionFactory;
import javax.resource.spi.ResourceAdapter;
import javax.resource.spi.ResourceAdapterAssociation;
import javax.security.auth.Subject;

/**
 *
 * @author walle
 */
@ConnectionDefinition(
        connection = Connection.class,
        connectionImpl = RabbitMQCCIConnection.class,
        connectionFactory = ConnectionFactory.class,
        connectionFactoryImpl = RabbitMQCCIConnectionFactory.class
)
public class RabbitMQManagedConnectionFactory implements ManagedConnectionFactory, ResourceAdapterAssociation, Serializable {

    private static final long serialVersionUID = -896719818968456887L;
    private final String uuid;

    private PrintWriter writeOut;
    private ConnectionFactory connectionFactory;
    private ResourceAdapter resourceAdapter;

    public RabbitMQManagedConnectionFactory() {
        uuid = UUID.randomUUID().toString();
    }

    @Override
    public Object createConnectionFactory(ConnectionManager cxManager) throws ResourceException {

        ConnectionManager connectionManager = RAConnectionManagerFactory.getInstance().getConnectionManager();

        this.connectionFactory = new RabbitMQCCIConnectionFactory(this, connectionManager);
        return connectionFactory;
    }

    @Override
    public Object createConnectionFactory() throws ResourceException {
        this.connectionFactory = new RabbitMQCCIConnectionFactory(this, null);
        return connectionFactory;
    }

    @Override
    public ManagedConnection createManagedConnection(Subject subject, ConnectionRequestInfo cxRequestInfo) throws ResourceException {
        return new RabbitMQManagedConnection(subject, cxRequestInfo);
    }

    @Override
    public ManagedConnection matchManagedConnections(Set connectionSet, Subject subject, ConnectionRequestInfo cxRequestInfo) throws ResourceException {
        if (!connectionSet.isEmpty()) {
            synchronized (connectionSet) {
                for (Object connObj : connectionSet) {
                    if (connObj instanceof RabbitMQManagedConnection) {
                        RabbitMQManagedConnection mc = (RabbitMQManagedConnection) connObj;
                        if (mc.getCxRequestInfo() == null) {
                            return mc;
                        }
                        if (isEqual(((RabbitMQConnectionRequestInfo) mc.getCxRequestInfo()).getQueueName(), ((RabbitMQConnectionRequestInfo) cxRequestInfo).getQueueName())
                                && isEqual(((RabbitMQConnectionRequestInfo) mc.getCxRequestInfo()).getExchangeName(), ((RabbitMQConnectionRequestInfo) cxRequestInfo).getExchangeName())
                                && isEqual(((RabbitMQConnectionRequestInfo) mc.getCxRequestInfo()).getRoutingKey(), ((RabbitMQConnectionRequestInfo) cxRequestInfo).getRoutingKey())) {
                            return mc;
                        }
                    }
                }
            }
        }
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws ResourceException {
        writeOut = out;
    }

    @Override
    public PrintWriter getLogWriter() throws ResourceException {
        return writeOut;
    }

    @Override
    public ResourceAdapter getResourceAdapter() {
        return resourceAdapter;
    }

    @Override
    public void setResourceAdapter(ResourceAdapter ra) throws ResourceException {
        this.resourceAdapter = ra;
    }

    public ConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof RabbitMQManagedConnectionFactory) {
            RabbitMQManagedConnectionFactory other = (RabbitMQManagedConnectionFactory) obj;
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
