/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digispherecorp.enterprise.rabbitmq.ra.mc.pool;

import com.digispherecorp.api.reusable.pool.base.AbstractBlockValidateReusablePool;
import com.digispherecorp.api.reusable.pool.base.IReusable;
import com.digispherecorp.enterprise.rabbitmq.ra.cci.RabbitMQCCIConnection;
import com.digispherecorp.enterprise.rabbitmq.ra.mc.RabbitMQManagedConnection;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.resource.ResourceException;
import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.ManagedConnection;
import javax.resource.spi.ManagedConnectionFactory;
import javax.security.auth.Subject;

/**
 *
 * @author Wal
 */
public class ManagedConnectionPoolManager extends AbstractBlockValidateReusablePool {

    private static volatile ManagedConnectionPoolManager instance;
    private static final long serialVersionUID = 1646776004661018191L;

    private final Set<ManagedConnection> managedConnections = new HashSet<>();

    private ManagedConnectionPoolManager() {
        super();
        init();
    }

    private void init() {
        this.setMaximumPoolSize(500).setExpirationTime(3600000);
        validatePool();
    }

    public static ManagedConnectionPoolManager newInstance() {
        if (instance == null) {
            synchronized (ManagedConnectionPoolManager.class) {
                if (instance == null) {
                    instance = new ManagedConnectionPoolManager();
                }
            }
        }
        return instance;
    }

    public static ManagedConnectionPoolManager getInstance() {
        return instance;
    }

    @Override
    public synchronized IReusable acquireReusable() {
        return null;
    }

    @Override
    public IReusable acquireReusable(Class... cls) {
        return null;
    }

    @Override
    public void validatePool() {
        scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                long timeNow = System.currentTimeMillis();
                Collection<Map.Entry<IReusable<ManagedConnection>, Long>> collection = Collections.synchronizedCollection(locked.entrySet());
                if (!collection.isEmpty()) {
                    synchronized (collection) {
                        Iterator<Map.Entry<IReusable<ManagedConnection>, Long>> iterator = collection.iterator();
                        while (iterator.hasNext()) {
                            Map.Entry<IReusable<ManagedConnection>, Long> next = iterator.next();
                            if ((timeNow - next.getValue()) >= expirationTime) {
                                if (((RabbitMQCCIConnection) ((RabbitMQManagedConnection) next.getKey().getReusable()).getConnection()).isClosed()) {
                                    Collections.synchronizedCollection(unlocked.entrySet()).add(next);
                                    collection.remove(next);
                                }
                            }
                        }
                    }
                }
            }
        }, 240, 2700, TimeUnit.SECONDS);
        super.validatePool();
    }

    @Override
    public IReusable acquireReusable(Object... obj) {
        IReusable<ManagedConnection> acquireReusable = super.acquireReusable();
        if (acquireReusable != null) {
            managedConnections.add(acquireReusable.getReusable());
            return acquireReusable;
        } else {
            if ((unlocked.size() + locked.size()) < maximumPoolSize) {
                long timeNow = System.currentTimeMillis();
                IReusable<ManagedConnection> createReusable = createReusable(obj);
                locked.put(createReusable, timeNow);
                managedConnections.add(createReusable.getReusable());
                return createReusable;
            } else {
                final CountDownLatch aLatch = new CountDownLatch(1);
                this.scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
                    @Override
                    public void run() {
                        if (!unlocked.isEmpty()) {
                            aLatch.countDown();
                        }
                    }
                }, 0, 2, TimeUnit.SECONDS);
                try {
                    aLatch.await();
                } catch (InterruptedException ex) {
                    Logger.getLogger(ManagedConnectionPoolManager.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex);
                }
            }
            acquireReusable = super.acquireReusable();
            if (acquireReusable != null) {
                managedConnections.add(acquireReusable.getReusable());
            }
            return acquireReusable;
        }
    }

    @Override
    public IReusable createReusable() {
        return null;
    }

    @Override
    public IReusable createReusable(Object... obj) {
        try {
            return new ManagedConnectionReusable(((ManagedConnectionFactory) obj[0]).createManagedConnection((Subject) obj[1], (ConnectionRequestInfo) obj[2]));
        } catch (ResourceException ex) {
            Logger.getLogger(ManagedConnectionPoolManager.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex);
        }
        return null;
    }

    @Override
    public IReusable createReusable(Class... cls) {
        return null;
    }

    @Override
    public void expirePool() {
        Collections.synchronizedSet(managedConnections).clear();
        super.expirePool();
    }

    public Set<ManagedConnection> getManagedConnections() {
        return managedConnections;
    }
}
