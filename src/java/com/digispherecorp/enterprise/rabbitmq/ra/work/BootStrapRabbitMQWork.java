/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digispherecorp.enterprise.rabbitmq.ra.work;

import com.digispherecorp.enterprise.rabbitmq.ra.activatn.EndPointActivationCapsule;
import com.digispherecorp.enterprise.rabbitmq.ra.activatn.RabbitMQActivationSpec;
import com.digispherecorp.enterprise.rabbitmq.ra.base.RabbitMQResourceAdapter;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.resource.spi.ResourceAdapter;
import javax.resource.spi.work.WorkException;

/**
 *
 * @author walle
 *
 */
public class BootStrapRabbitMQWork extends RabbitMQWork {

    private final Set<EndPointActivationCapsule> activationSetInner = new HashSet<>();

    public BootStrapRabbitMQWork(ResourceAdapter resourceAdapter) {
        super(resourceAdapter);
    }

    @Override
    public void release() {
    }

    @Override
    public void run() {
        
        Logger.getLogger(BootStrapRabbitMQWork.class.getName()).info("Boot Strap checking for Messaging Activation Config");
        
        Set<EndPointActivationCapsule> activationSet = ((RabbitMQResourceAdapter) resourceAdapter).getActivationSet();

        if (!activationSet.isEmpty()) {
            synchronized (activationSet) {
                activationSetInner.addAll(activationSet);
                activationSet.clear();
                for (EndPointActivationCapsule capsuleInner : activationSetInner) {
                    if (capsuleInner.isActivated()) {
                        continue;
                    }
                    try {
                        workManager = ((RabbitMQResourceAdapter) resourceAdapter).getWorkManager();
                        if (((RabbitMQActivationSpec) capsuleInner.getActivationSpec()).getDestinationType() != null) {
                            workManager.doWork(new PublishSubcribeRabbitMQWork(workManager, capsuleInner.getEndpointFactory(), capsuleInner.getActivationSpec(), capsuleInner));
                        } else {
                            workManager.doWork(new QueueRabbitMQWork(workManager, capsuleInner.getEndpointFactory(), capsuleInner.getActivationSpec(), capsuleInner));
                        }
                        capsuleInner.setActivated(true);
                    } catch (WorkException ex) {
                        Logger.getLogger(BootStrapRabbitMQWork.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex);
                    }
                }
            }
        }
    }

    public Set<EndPointActivationCapsule> getActivationSetInner() {
        return activationSetInner;
    }

}
