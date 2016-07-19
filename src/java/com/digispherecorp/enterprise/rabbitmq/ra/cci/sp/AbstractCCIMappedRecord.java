/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digispherecorp.enterprise.rabbitmq.ra.cci.sp;

import java.io.Serializable;
import java.util.HashMap;
import javax.resource.cci.Record;

/**
 *
 * @author walle
 * @param <K>
 * @param <V>
 *
 * <p>
 *
 * A custom Mapped Record implementation similar to Common Client Interface
 * (CCI)
 * <tt>MappedRecord</tt> but directly extends the <tt>HashMap</tt> class and
 * implements Record and Serializable interface.
 *
 * <p>
 * @see MappedRecord
 * @see IndexedRecord
 *
 */
public abstract class AbstractCCIMappedRecord<K, V> extends HashMap<K, V> implements Record, Serializable {

    private static final long serialVersionUID = 4022959724929787132L;

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
