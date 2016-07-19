/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digispherecorp.enterprise.rabbitmq.ra.work;

import com.digispherecorp.api.base.schedules.IRunnableTaskSchedulerService;
import javax.resource.spi.work.ExecutionContext;
import javax.resource.spi.work.Work;
import javax.resource.spi.work.WorkException;
import javax.resource.spi.work.WorkListener;
import javax.resource.spi.work.WorkManager;

/**
 *
 * @author walle
 */
public class RabbitMQWorkManager extends RabbitMQWorkExecutorManager implements WorkManager {

    private static final long serialVersionUID = 401264531764997355L;

    @Override
    public void doWork(Work work) throws WorkException {
        IRunnableTaskSchedulerService schedule = new RabbitMQWorkRunnableScheduler(work);
        schedule.setDelay(180);
        schedule.scheduleRunnableTask();
        ((RabbitMQWork)work).getCapsule().setSchedulerService(schedule);
        if (work instanceof BootStrapRabbitMQWork) {
            return;
        }
        taskSet.add(schedule);
    }

    @Override
    public void doWork(Work work, long startTimeout, ExecutionContext execContext, WorkListener workListener) throws WorkException {
    }

    @Override
    public long startWork(Work work) throws WorkException {
        return -1;
    }

    @Override
    public long startWork(Work work, long startTimeout, ExecutionContext execContext, WorkListener workListener) throws WorkException {
        return -1;
    }

    @Override
    public void scheduleWork(Work work) throws WorkException {
        IRunnableTaskSchedulerService schedule = new RabbitMQWorkRunnableScheduler(work);
        schedule.setDelay(180);
        schedule.setExecPeriod(180);
        schedule.scheduleRepeatedRunnableTask();
        if (work instanceof BootStrapRabbitMQWork) {
            return;
        }
        taskSet.add(schedule);
    }

    @Override
    public void scheduleWork(Work work, long startTimeout, ExecutionContext execContext, WorkListener workListener) throws WorkException {
    }

}
