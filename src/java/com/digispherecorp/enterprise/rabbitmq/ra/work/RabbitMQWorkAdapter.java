/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digispherecorp.enterprise.rabbitmq.ra.work;

import java.util.logging.Logger;
import javax.resource.spi.work.WorkEvent;
import javax.resource.spi.work.WorkListener;

/**
 *
 * @author walle
 */
public class RabbitMQWorkAdapter implements WorkListener {

    @Override
    public void workAccepted(WorkEvent e) {
        Logger.getLogger(RabbitMQWorkAdapter.class.getName()).info("work accepted");
    }

    @Override
    public void workRejected(WorkEvent e) {
        Logger.getLogger(RabbitMQWorkAdapter.class.getName()).info("work rejected");
    }

    @Override
    public void workStarted(WorkEvent e) {
        Logger.getLogger(RabbitMQWorkAdapter.class.getName()).info("work started");
    }

    @Override
    public void workCompleted(WorkEvent e) {
        Logger.getLogger(RabbitMQWorkAdapter.class.getName()).info("work done");
    }

}
