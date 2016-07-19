/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digispherecorp.enterprise.rabbitmq.ra.cci;

import java.util.ArrayList;
import java.util.List;
import javax.resource.ResourceException;
import javax.resource.cci.IndexedRecord;
import javax.resource.cci.MappedRecord;
import javax.resource.cci.RecordFactory;

/**
 *
 * @author walle
 */
public class RabbitMQCCIRecordFactory implements RecordFactory {

    private final List<MappedRecord> mappedRecords;
    private final List<IndexedRecord> indexedRecords;

    public RabbitMQCCIRecordFactory() {
        this.mappedRecords = new ArrayList<>();
        this.indexedRecords = new ArrayList<>();
    }

    @Override
    public MappedRecord createMappedRecord(String recordName) throws ResourceException {
        MappedRecord record = new RabbitMQCCIMappedRecord(recordName);
        mappedRecords.add(record);
        return record;
    }

    @Override
    public IndexedRecord createIndexedRecord(String recordName) throws ResourceException {
        IndexedRecord record = new RabbitMQCCIIndexedRecord(recordName);
        indexedRecords.add(record);
        return record;
    }

}
