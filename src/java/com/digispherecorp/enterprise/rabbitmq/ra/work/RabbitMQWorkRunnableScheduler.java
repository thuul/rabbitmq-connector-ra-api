/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digispherecorp.enterprise.rabbitmq.ra.work;

import com.digispherecorp.api.base.schedules.IRunnableTaskSchedulerService;
import com.digispherecorp.api.core.thread.SimpleDeamonThreadFactory;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import javax.resource.spi.work.Work;

/**
 *
 * @author walle
 */
public class RabbitMQWorkRunnableScheduler implements IRunnableTaskSchedulerService {

    private static final long serialVersionUID = -4139979250652083721L;

    private ScheduledExecutorService scheduler;
    private ScheduledFuture<?> runnableScheduledFuture;
    private ScheduledFuture<?> monitorRunnableScheduledFuture;
    private Work work;
    private long delay;
    private long execPeriod;

    public RabbitMQWorkRunnableScheduler(Work work) {
        this.work = work;
        this.scheduler = Executors.newSingleThreadScheduledExecutor(new SimpleDeamonThreadFactory());
    }

    @Override
    public void scheduleRunnableTask() {
        this.runnableScheduledFuture = this.scheduler.schedule(this.work, getDelay(), TimeUnit.SECONDS);
    }

    @Override
    public void scheduleRepeatedRunnableTask() {
        this.runnableScheduledFuture = this.scheduler.scheduleAtFixedRate(work, getDelay(), getExecPeriod(), TimeUnit.SECONDS);
    }

    @Override
    public ScheduledExecutorService getScheduler() {
        return scheduler;
    }

    @Override
    public void setScheduler(ScheduledExecutorService scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    public ScheduledFuture<?> getRunnableScheduledFuture() {
        return runnableScheduledFuture;
    }

    @Override
    public ScheduledFuture<?> getMonitorRunnableScheduledFuture() {
        return monitorRunnableScheduledFuture;
    }

    @Override
    public Runnable getRunnableProcess() {
        return work;
    }

    @Override
    public void setRunnableProcess(Runnable runnableProcess) {
        this.work = (Work) runnableProcess;
    }

    @Override
    public long getDelay() {
        return delay;
    }

    @Override
    public void setDelay(long delay) {
        this.delay = delay;
    }

    @Override
    public long getExecPeriod() {
        return execPeriod;
    }

    @Override
    public void setExecPeriod(long execPeriod) {
        this.execPeriod = execPeriod;
    }

    @Override
    public boolean checkIfTaskRunning() {
        return !runnableScheduledFuture.isDone() || !runnableScheduledFuture.isCancelled();
    }

    @Override
    public boolean cancelRunningTask() {
        if (checkIfTaskRunning()) {
            return runnableScheduledFuture.cancel(true);
        }
        return true;
    }
}
