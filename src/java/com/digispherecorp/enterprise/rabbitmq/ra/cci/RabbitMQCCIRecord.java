/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digispherecorp.enterprise.rabbitmq.ra.cci;

import javax.resource.cci.Record;

/**
 *
 * @author walle
 */
public class RabbitMQCCIRecord implements Record {

    private static final long serialVersionUID = 6102630154071903684L;
    
    private String name;
    private String description;

    @Override
    public String getRecordName() {
        return name;
    }

    @Override
    public void setRecordName(String name) {
        this.name = name;
    }

    @Override
    public void setRecordShortDescription(String description) {
        this.description = description;
    }

    @Override
    public String getRecordShortDescription() {
        return description;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
