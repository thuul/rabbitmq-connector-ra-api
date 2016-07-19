/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digispherecorp.enterprise.rabbitmq.ra.activatn;

import com.digispherecorp.api.base.schedules.IRunnableTaskSchedulerService;
import javax.resource.spi.ActivationSpec;
import javax.resource.spi.endpoint.MessageEndpointFactory;

/**
 *
 * @author walle
 */
public class EndPointActivationCapsule {

    private final MessageEndpointFactory endpointFactory;
    private final ActivationSpec activationSpec;
    private IRunnableTaskSchedulerService schedulerService;
    private boolean activated;

    public EndPointActivationCapsule(MessageEndpointFactory endpointFactory, ActivationSpec activationSpec) {
        this.endpointFactory = endpointFactory;
        this.activationSpec = activationSpec;
    }

    public MessageEndpointFactory getEndpointFactory() {
        return endpointFactory;
    }

    public ActivationSpec getActivationSpec() {
        return activationSpec;
    }

    public IRunnableTaskSchedulerService getSchedulerService() {
        return schedulerService;
    }

    public void setSchedulerService(IRunnableTaskSchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

}
