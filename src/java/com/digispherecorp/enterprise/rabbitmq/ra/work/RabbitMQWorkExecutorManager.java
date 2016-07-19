/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digispherecorp.enterprise.rabbitmq.ra.work;

import com.digispherecorp.api.base.schedules.IRunnableTaskSchedulerService;
import com.digispherecorp.api.base.schedules.IScheduledExecutorServiceFacade;
import com.digispherecorp.api.core.thread.SimpleDeamonThreadFactory;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.resource.spi.work.Work;

/**
 *
 * @author walle
 */
public class RabbitMQWorkExecutorManager implements IScheduledExecutorServiceFacade {

    private static final long serialVersionUID = 3180409927762490036L;

    private final ScheduledExecutorService scheduler;
    private ScheduledFuture<?> runnableScheduledFuture;
    protected final Set<IRunnableTaskSchedulerService> taskSet = new LinkedHashSet<>();

    public RabbitMQWorkExecutorManager() {
        this.scheduler = Executors.newSingleThreadScheduledExecutor(new SimpleDeamonThreadFactory());
    }

    @Override
    public void scheduleRunnableTask() {
        Logger.getLogger(RabbitMQWorkExecutorManager.class.getName()).info("checking and removing expired schedules...");
    }

    @Override
    public void scheduleRunnableTask(IRunnableTaskSchedulerService irtss) {
    }

    @Override
    public void shutdownRunnableTask() {

        Logger.getLogger(RabbitMQWorkExecutorManager.class.getName()).info("initiating shut down for all scheduler services.......");

        Iterator<IRunnableTaskSchedulerService> iterator = taskSet.iterator();
        while (iterator.hasNext()) {
            IRunnableTaskSchedulerService next = iterator.next();
            ((Work) ((RabbitMQWorkRunnableScheduler) next).getRunnableProcess()).release();
            if (next.checkIfTaskRunning()) {
                next.getRunnableScheduledFuture().cancel(true);
            }
            ScheduledExecutorService schedulerInner = next.getScheduler();
            schedulerInner.shutdown();
            try {
                if (schedulerInner.awaitTermination(60, TimeUnit.SECONDS)) {
                    schedulerInner.shutdownNow();
                    if (!schedulerInner.awaitTermination(60, TimeUnit.SECONDS)) {
                        Logger.getLogger(RabbitMQWorkExecutorManager.class.getName()).info("Executor Service did not terminate");
                    }
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(RabbitMQWorkExecutorManager.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex);
                schedulerInner.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
        if (runnableScheduledFuture != null) {
            runnableScheduledFuture.cancel(true);
        }
        scheduler.shutdown();
        try {
            if (scheduler.awaitTermination(60, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
                if (!scheduler.awaitTermination(60, TimeUnit.SECONDS)) {
                    Logger.getLogger(RabbitMQWorkExecutorManager.class.getName()).info("Executor Service did not terminate");
                }
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(RabbitMQWorkExecutorManager.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex);
            scheduler.shutdownNow();
            Thread.currentThread().interrupt();
        }
        taskSet.clear();
    }

    @Override
    public void shutdownRunnableTask(IRunnableTaskSchedulerService irtss) {

        Logger.getLogger(RabbitMQWorkExecutorManager.class.getName()).info("initiating shut down for a scheduler service.......");

        ((Work) ((RabbitMQWorkRunnableScheduler) irtss).getRunnableProcess()).release();
        irtss.cancelRunningTask();
        ScheduledExecutorService schedulerInner = irtss.getScheduler();
        schedulerInner.shutdown();
        try {
            if (schedulerInner.awaitTermination(60, TimeUnit.SECONDS)) {
                schedulerInner.shutdownNow();
                if (!schedulerInner.awaitTermination(60, TimeUnit.SECONDS)) {
                    Logger.getLogger(RabbitMQWorkExecutorManager.class.getName()).info("Executor Service did not terminate");
                }
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(RabbitMQWorkExecutorManager.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex);
            schedulerInner.shutdownNow();
            Thread.currentThread().interrupt();
        }
        taskSet.remove(irtss);
    }

    public Set<IRunnableTaskSchedulerService> getTaskSet() {
        return taskSet;
    }

}
