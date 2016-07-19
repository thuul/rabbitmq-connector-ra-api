/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digispherecorp.enterprise.rabbitmq.ra.cci.sp;

import javax.resource.cci.Record;

/**
 *
 * @author walle
 */
public abstract class AbstractCCIRecord implements Record {

    private static final long serialVersionUID = 7359362829094472777L;

    private String recordName;
    private String shortDescription;

    @Override
    public String getRecordName() {
        return recordName;
    }

    @Override
    public void setRecordName(String name) {
        this.recordName = name;
    }

    @Override
    public void setRecordShortDescription(String description) {
        this.shortDescription = description;
    }

    @Override
    public String getRecordShortDescription() {
        return shortDescription;
    }

    @Override
    public Record clone() throws CloneNotSupportedException {
        return (Record) super.clone();
    }

}
