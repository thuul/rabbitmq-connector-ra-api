/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digispherecorp.enterprise.rabbitmq.ra.mc.pool;

import com.digispherecorp.api.reusable.pool.base.AbstractReusable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.resource.ResourceException;
import javax.resource.spi.ManagedConnection;

/**
 *
 * @author Wal
 */
public class ManagedConnectionReusable extends AbstractReusable<ManagedConnection> {

    private static final long serialVersionUID = -8644462997796462380L;

    public ManagedConnectionReusable(ManagedConnection managedConnection) {
        this.reusableObj = managedConnection;
    }

    public ManagedConnectionReusable() {
    }

    @Override
    public void expire() {
        try {
            this.reusableObj.destroy();
            super.expire();
        } catch (ResourceException ex) {
            Logger.getLogger(ManagedConnectionReusable.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex);
        }
    }
}
