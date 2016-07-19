/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digispherecorp.enterprise.rabbitmq.ra.cci;

import java.io.Serializable;
import javax.resource.ResourceException;
import javax.resource.cci.LocalTransaction;

/**
 *
 * @author walle
 */
public class RabbitMQCCILocalTransaction implements LocalTransaction, Serializable {

    private static final long serialVersionUID = 6944306316374153084L;

    @Override
    public void begin() throws ResourceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void commit() throws ResourceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void rollback() throws ResourceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
