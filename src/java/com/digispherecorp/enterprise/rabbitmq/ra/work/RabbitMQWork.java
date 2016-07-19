/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digispherecorp.enterprise.rabbitmq.ra.work;

import com.digispherecorp.enterprise.rabbitmq.ra.activatn.EndPointActivationCapsule;
import javax.resource.spi.ActivationSpec;
import javax.resource.spi.ResourceAdapter;
import javax.resource.spi.endpoint.MessageEndpointFactory;
import javax.resource.spi.work.Work;
import javax.resource.spi.work.WorkManager;

/**
 *
 * @author walle
 */
public abstract class RabbitMQWork implements Work {

    protected ResourceAdapter resourceAdapter;
    protected WorkManager workManager;
    protected MessageEndpointFactory endpointFactory;
    protected ActivationSpec activationSpec;
    protected EndPointActivationCapsule capsule;

    public RabbitMQWork() {
    }

    public RabbitMQWork(ResourceAdapter resourceAdapter) {
        this.resourceAdapter = resourceAdapter;
    }

    public RabbitMQWork(MessageEndpointFactory endpointFactory, ActivationSpec spec, EndPointActivationCapsule capsule) {
        this.endpointFactory = endpointFactory;
        this.activationSpec = spec;
        this.capsule = capsule;
    }

    public RabbitMQWork(WorkManager workManager, MessageEndpointFactory endpointFactory, ActivationSpec spec, EndPointActivationCapsule capsule) {
        this(endpointFactory, spec, capsule);
        this.workManager = workManager;
    }

    @Override
    public abstract void release();

    @Override
    public abstract void run();

    public EndPointActivationCapsule getCapsule() {
        return capsule;
    }

}
