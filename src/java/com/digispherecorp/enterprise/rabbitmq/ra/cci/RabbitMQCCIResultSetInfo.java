/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digispherecorp.enterprise.rabbitmq.ra.cci;

import java.io.Serializable;
import javax.resource.ResourceException;
import javax.resource.cci.ResultSetInfo;

/**
 *
 * @author walle
 */
public class RabbitMQCCIResultSetInfo implements ResultSetInfo, Serializable {

    private static final long serialVersionUID = 466708582944442896L;

    @Override
    public boolean updatesAreDetected(int type) throws ResourceException {
        return false;
    }

    @Override
    public boolean insertsAreDetected(int type) throws ResourceException {
        return false;
    }

    @Override
    public boolean deletesAreDetected(int type) throws ResourceException {
        return false;
    }

    @Override
    public boolean supportsResultSetType(int type) throws ResourceException {
        return false;
    }

    @Override
    public boolean supportsResultTypeConcurrency(int type, int concurrency) throws ResourceException {
        return false;
    }

    @Override
    public boolean othersUpdatesAreVisible(int type) throws ResourceException {
        return false;
    }

    @Override
    public boolean othersDeletesAreVisible(int type) throws ResourceException {
        return false;
    }

    @Override
    public boolean othersInsertsAreVisible(int type) throws ResourceException {
        return false;
    }

    @Override
    public boolean ownUpdatesAreVisible(int type) throws ResourceException {
        return false;
    }

    @Override
    public boolean ownInsertsAreVisible(int type) throws ResourceException {
        return false;
    }

    @Override
    public boolean ownDeletesAreVisible(int type) throws ResourceException {
        return false;
    }

}
