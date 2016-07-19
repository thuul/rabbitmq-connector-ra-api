/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digispherecorp.enterprise.rabbitmq.ra.mc;

import javax.resource.ResourceException;
import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.ManagedConnectionMetaData;

/**
 *
 * @author walle
 */
public class RabbitMQManagedConnectionMetaData implements ManagedConnectionMetaData{
    
    private final RabbitMQConnectionRequestInfo requestInfo;

    public RabbitMQManagedConnectionMetaData(ConnectionRequestInfo requestInfo) {
        this.requestInfo = (RabbitMQConnectionRequestInfo) requestInfo;
    }

    @Override
    public String getEISProductName() throws ResourceException {
        return "RabbitMQ Messaging Broker";
    }

    @Override
    public String getEISProductVersion() throws ResourceException {
        return "";
    }

    @Override
    public int getMaxConnections() throws ResourceException {
        return 250;
    }

    @Override
    public String getUserName() throws ResourceException {
        return requestInfo.getUser();
    }

    public RabbitMQConnectionRequestInfo getRequestInfo() {
        return requestInfo;
    }
    
}
