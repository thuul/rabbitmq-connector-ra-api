/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digispherecorp.enterprise.rabbitmq.ra.rms;

/**
 *
 * @author walle
 * 
 * <p>
 * 
 * Common interface for RabbitMQ Messaging Broker Publish/Subscribe 
 * Destination Type <p>
 * 
 * @see DirectPublishSubscribeMessage
 * @see FanoutPublishSubscribeMessage
 * @see HeaderPublishSubscribeMessage
 * @see TopicPublishSubscribeMessage
 * 
 */
public interface IPublishSubscribeMessage {

    /**
     *
     * @return String
     */
    String getSubscribeType();
}
