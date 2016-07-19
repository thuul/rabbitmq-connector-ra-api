/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digispherecorp.enterprise.rabbitmq.ra.endpoint;

import javax.resource.ResourceException;

/**
 *
 * @author walle
 *
 * <p>
 * Base Message Driven Bean (MDB) interface for this RabbitMQ Connector Resource
 * Adapter implementation.
 *
 * <p>
 * Application Server component listening for InBound Communication from this RA
 * will implement this interface and either provide activation configuration
 * through (activationConfig of @MessageDriven or through EJB XML configuration)
 *
 * @see RabbitMQMessage
 * @see ResourceException
 *
 *
 */
public interface RabbitMQAMQPMessageListener {

    /**
     *
     * @param message
     * @throws ResourceException
     */
    public void onMessage(RabbitMQMessage message) throws ResourceException;

}
