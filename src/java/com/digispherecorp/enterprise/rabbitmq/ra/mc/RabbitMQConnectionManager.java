/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digispherecorp.enterprise.rabbitmq.ra.mc;

import com.digispherecorp.api.reusable.pool.base.IReusable;
import com.digispherecorp.enterprise.rabbitmq.ra.mc.pool.ManagedConnectionPoolManager;
import java.util.Set;
import javax.resource.ResourceException;
import javax.resource.spi.ConnectionManager;
import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.ManagedConnection;
import javax.resource.spi.ManagedConnectionFactory;

/**
 *
 * @author walle
 */
public class RabbitMQConnectionManager implements ConnectionManager {

    private static final long serialVersionUID = 3290860996837726304L;

    @Override
    public Object allocateConnection(ManagedConnectionFactory mcf, ConnectionRequestInfo cxRequestInfo) throws ResourceException {

        ManagedConnectionPoolManager instance = ManagedConnectionPoolManager.getInstance();

        Set<ManagedConnection> managedConnections = instance.getManagedConnections();

        ManagedConnection managedConnection = mcf.matchManagedConnections(managedConnections, null, cxRequestInfo);
        if (managedConnection != null) {
            return managedConnection.getConnection(null, cxRequestInfo);
        }
        IReusable<ManagedConnection> reusable = instance.acquireReusable(mcf, null, cxRequestInfo);
        return reusable.getReusable().getConnection(null, cxRequestInfo);
    }

}
