/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digispherecorp.enterprise.rabbitmq.ra.cci.sp;

import java.io.Serializable;
import java.util.ArrayList;
import javax.resource.cci.Record;

/**
 *
 * @author walle
 * @param <K>
 * <p>
 *
 * A custom Indexed Record implementation similar to Common Client Interface
 * (CCI)
 * <tt>IndexedRecord</tt> but directly extends the <tt>ArrayList</tt> class and
 * implements Record and Serializable interface.
 *
 * <p>
 * @see IndexedRecord
 * @see MappedRecord
 */
public abstract class AbstractCCIListRecord<K> extends ArrayList<K> implements Record, Serializable {

    private static final long serialVersionUID = -7021218048867067324L;

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

}
