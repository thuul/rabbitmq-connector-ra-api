/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digispherecorp.enterprise.rabbitmq.ra.base;

import com.digispherecorp.enterprise.rabbitmq.ra.activatn.EndPointActivationCapsule;
import com.digispherecorp.enterprise.rabbitmq.ra.activatn.RabbitMQActivationSpec;
import com.digispherecorp.enterprise.rabbitmq.ra.cci.RabbitMQConnectionFactoryFacade;
import com.digispherecorp.enterprise.rabbitmq.ra.cci.interatn.RabbitMQInterationFactory;
import com.digispherecorp.enterprise.rabbitmq.ra.mc.RAConnectionManagerFactory;
import com.digispherecorp.enterprise.rabbitmq.ra.mc.pool.ManagedConnectionPoolManager;
import com.digispherecorp.enterprise.rabbitmq.ra.work.BootStrapRabbitMQWork;
import com.digispherecorp.enterprise.rabbitmq.ra.work.RabbitMQWorkManager;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.resource.ResourceException;
import javax.resource.spi.ActivationSpec;
import javax.resource.spi.BootstrapContext;
import javax.resource.spi.Connector;
import javax.resource.spi.ResourceAdapter;
import javax.resource.spi.ResourceAdapterInternalException;
import javax.resource.spi.endpoint.MessageEndpointFactory;
import javax.resource.spi.work.WorkException;
import javax.resource.spi.work.WorkManager;
import javax.transaction.TransactionSynchronizationRegistry;
import javax.transaction.xa.XAResource;

/**
 *
 * @author walle
 */
@Connector(
        description = "RabbitMQ Broker Adapter",
        displayName = "OutboundResourceAdapter",
        vendorName = "Digisphere CC SA.",
        eisType = "Messaging Broker",
        version = "1.0.0"
)
public class RabbitMQResourceAdapter implements ResourceAdapter, Serializable {

    private static final long serialVersionUID = -5995603286207934242L;
    private final String uuid;

    private BootStrapRabbitMQWork bootStrapRabbitMQWork;
    private XAResource[] xaResources;
    private BootstrapContext ctx;
    private WorkManager workManager;
    private Timer timer;
    private TransactionSynchronizationRegistry synchronizationRegistry;
    private final Set<EndPointActivationCapsule> activationSet = new HashSet<>();

    public RabbitMQResourceAdapter() {
        uuid = UUID.randomUUID().toString();
    }

    @Override
    public void start(BootstrapContext ctx) throws ResourceAdapterInternalException {

        this.ctx = new RabbitMQBootstrapContext(ctx);
        this.workManager = getCtx().getWorkManager();

        ((RabbitMQWorkManager) workManager).scheduleRunnableTask();

        try {
            bootStrapRabbitMQWork = new BootStrapRabbitMQWork(this);
            workManager.scheduleWork(bootStrapRabbitMQWork);
        } catch (WorkException ex) {
            Logger.getLogger(RabbitMQResourceAdapter.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex);
        }

        RAConnectionManagerFactory.newInstance();
        ManagedConnectionPoolManager.newInstance();
        RabbitMQInterationFactory.newInstance();
        RabbitMQConnectionFactoryFacade.newInstance();

        Logger.getLogger(RabbitMQResourceAdapter.class.getName()).log(Level.INFO, "XXXXXXXXXXXXXXXXXXX Boot Strapping Resource Adapter XXXXXXXXXXXXXXXXXXX");
        Logger.getLogger(RabbitMQResourceAdapter.class.getName()).log(Level.INFO, "BOOTSTRAP CLASS: {0}", this.ctx.toString());
    }

    @Override
    public void stop() {

        Logger.getLogger(RabbitMQResourceAdapter.class.getName()).log(Level.INFO, "XXXXXXXXXXXXXXXXXXX shutting down Resource Adapter XXXXXXXXXXXXXXXXXXX");

        ((RabbitMQWorkManager) workManager).shutdownRunnableTask();
        ManagedConnectionPoolManager.getInstance().expirePool();
        RAConnectionManagerFactory.getInstance().setConnectionManager(null);
        RabbitMQConnectionFactoryFacade.getInstance().getConnectionMap().clear();
    }

    @Override
    public void endpointActivation(MessageEndpointFactory endpointFactory, ActivationSpec spec) throws ResourceException {

        Logger.getLogger(RabbitMQResourceAdapter.class.getName()).log(Level.INFO, "XXXXXXXXXXXXXXXXXXX End Point Activation XXXXXXXXXXXXXXXXXXX");
        Logger.getLogger(RabbitMQResourceAdapter.class.getName()).log(Level.INFO, "Activation CLASS: {0}", ((RabbitMQActivationSpec) spec).getSimpleName());
        Logger.getLogger(RabbitMQResourceAdapter.class.getName()).log(Level.INFO, "End Point Factory CLASS: {0}", endpointFactory.toString());

        Set<EndPointActivationCapsule> synchronizedSet = Collections.synchronizedSet(activationSet);
        synchronized (synchronizedSet) {
            synchronizedSet.add(new EndPointActivationCapsule(endpointFactory, spec));
        }
    }

    @Override
    public void endpointDeactivation(MessageEndpointFactory endpointFactory, ActivationSpec spec) {

        Set<EndPointActivationCapsule> synchronizedSet = Collections.synchronizedSet(bootStrapRabbitMQWork.getActivationSetInner());
        synchronized (synchronizedSet) {
            for (EndPointActivationCapsule capsule : synchronizedSet) {
                if (capsule.getEndpointFactory().equals(endpointFactory)) {
                    if (capsule.getSchedulerService() != null) {
                        ((RabbitMQWorkManager) workManager).shutdownRunnableTask(capsule.getSchedulerService());
                    }
                    activationSet.remove(capsule);
                }
            }
        }
        Logger.getLogger(RabbitMQResourceAdapter.class.getName()).log(Level.INFO, "XXXXXXXXXXXXXXXXXXX End Point Deactivation XXXXXXXXXXXXXXXXXXX");
        Logger.getLogger(RabbitMQResourceAdapter.class.getName()).log(Level.INFO, "Deactivated CLASS: {0}", ((RabbitMQActivationSpec) spec).getSimpleName());
    }

    @Override
    public XAResource[] getXAResources(ActivationSpec[] specs) throws ResourceException {
        return null;
    }

    public BootstrapContext getCtx() {
        return ctx;
    }

    public WorkManager getWorkManager() {
        return workManager;
    }

    public Timer getTimer() {
        return timer;
    }

    public TransactionSynchronizationRegistry getSynchronizationRegistry() {
        return synchronizationRegistry;
    }

    public XAResource[] getXaResources() {
        return xaResources;
    }

    public Set<EndPointActivationCapsule> getActivationSet() {
        return activationSet;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof RabbitMQResourceAdapter) {
            RabbitMQResourceAdapter other = (RabbitMQResourceAdapter) obj;
            return isEqual(uuid, other.uuid) && isEqual(toString(), other.toString());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        String result = "" + uuid;
        return result.hashCode();
    }

    @Override
    public String toString() {
        return uuid;
    }

    private boolean isEqual(Object o1, Object o2) {
        if (o1 == null) {
            return o2 == null;
        } else {
            return o1.equals(o2);
        }
    }

}
