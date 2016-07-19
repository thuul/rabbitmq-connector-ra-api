/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digispherecorp.enterprise.rabbitmq.ra.mc;

import com.digispherecorp.enterprise.rabbitmq.ra.cci.RabbitMQCCIConnection;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.UUID;
import javax.resource.ResourceException;
import javax.resource.cci.Connection;
import javax.resource.spi.ConnectionEventListener;
import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.LocalTransaction;
import javax.resource.spi.ManagedConnection;
import javax.resource.spi.ManagedConnectionMetaData;
import javax.security.auth.Subject;
import javax.transaction.xa.XAResource;

/**
 *
 * @author walle
 */
public class RabbitMQManagedConnection implements ManagedConnection, Serializable {

    private static final long serialVersionUID = 7516278487277498655L;
    private final String uuid;

    private PrintWriter writeOut;
    private Subject subject;
    private Connection connection;
    private Object activeConnHandle;
    private ConnectionEventListener listener;
    private ConnectionRequestInfo cxRequestInfo;

    public RabbitMQManagedConnection(Subject subject, ConnectionRequestInfo cxRequestInfo) {
        this.uuid = UUID.randomUUID().toString();
        this.cxRequestInfo = cxRequestInfo;
        addConnectionEventListener(new RabbitMQConnectionEventListener(this));
    }

    @Override
    public Object getConnection(Subject subject, ConnectionRequestInfo cxRequestInfo) throws ResourceException {
        connection = new RabbitMQCCIConnection(cxRequestInfo, false);
        return connection;
    }

    @Override
    public void destroy() throws ResourceException {
        connection.close();
        connection = null;
    }

    @Override
    public void cleanup() throws ResourceException {
        connection.close();
    }

    @Override
    public void associateConnection(Object connection) throws ResourceException {
        this.setActiveConnHandle(activeConnHandle);
    }

    @Override
    public final void addConnectionEventListener(ConnectionEventListener listener) {
        this.listener = listener;
    }

    @Override
    public void removeConnectionEventListener(ConnectionEventListener listener) {
    }

    @Override
    public XAResource getXAResource() throws ResourceException {
        return null;
    }

    @Override
    public LocalTransaction getLocalTransaction() throws ResourceException {
        return null;
    }

    @Override
    public ManagedConnectionMetaData getMetaData() throws ResourceException {
        return new RabbitMQManagedConnectionMetaData(cxRequestInfo);
    }

    @Override
    public void setLogWriter(PrintWriter out) throws ResourceException {
        writeOut = out;
    }

    public String getUuid() {
        return uuid;
    }

    @Override
    public PrintWriter getLogWriter() throws ResourceException {
        return writeOut;
    }

    public Object getActiveConnHandle() {
        return activeConnHandle;
    }

    public void setActiveConnHandle(Object activeConnHandle) {
        this.activeConnHandle = activeConnHandle;
    }

    public ConnectionRequestInfo getCxRequestInfo() {
        return cxRequestInfo;
    }

    public void setCxRequestInfo(ConnectionRequestInfo cxRequestInfo) {
        this.cxRequestInfo = cxRequestInfo;
    }

    public Connection getConnection() {
        return connection;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof RabbitMQManagedConnection) {
            RabbitMQManagedConnection other = (RabbitMQManagedConnection) obj;
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
