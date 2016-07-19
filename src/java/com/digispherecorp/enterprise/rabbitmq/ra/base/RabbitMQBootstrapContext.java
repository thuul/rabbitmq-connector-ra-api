/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digispherecorp.enterprise.rabbitmq.ra.base;

import com.digispherecorp.enterprise.rabbitmq.ra.work.RabbitMQWorkManager;
import java.util.Timer;
import javax.resource.spi.BootstrapContext;
import javax.resource.spi.UnavailableException;
import javax.resource.spi.XATerminator;
import javax.resource.spi.work.WorkContext;
import javax.resource.spi.work.WorkManager;
import javax.transaction.TransactionSynchronizationRegistry;

/**
 *
 * @author walle
 */
public class RabbitMQBootstrapContext implements BootstrapContext {

    private BootstrapContext appServerContext;
    private WorkManager workManager;
    private XATerminator xATerminator;
    private Timer timer;
    private TransactionSynchronizationRegistry transactionSynchronizationRegistry;

    public RabbitMQBootstrapContext() {
    }

    public RabbitMQBootstrapContext(BootstrapContext serverContext) {
        this.appServerContext = serverContext;
    }

    @Override
    public WorkManager getWorkManager() {
        workManager = new RabbitMQWorkManager();
        return workManager;
    }

    @Override
    public XATerminator getXATerminator() {
        xATerminator = appServerContext.getXATerminator();
        return xATerminator;
    }

    @Override
    public Timer createTimer() throws UnavailableException {
        timer = appServerContext.createTimer();
        return timer;
    }

    @Override
    public boolean isContextSupported(Class<? extends WorkContext> workContextClass) {
        return appServerContext.isContextSupported(workContextClass);
    }

    @Override
    public TransactionSynchronizationRegistry getTransactionSynchronizationRegistry() {
        transactionSynchronizationRegistry = appServerContext.getTransactionSynchronizationRegistry();
        return transactionSynchronizationRegistry;
    }

}
