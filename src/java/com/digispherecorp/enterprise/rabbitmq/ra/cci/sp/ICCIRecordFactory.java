/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digispherecorp.enterprise.rabbitmq.ra.cci.sp;

import javax.resource.ResourceException;

/**
 *
 * 
 * <p>A custom RecordFactory interface similar to Common Client Interface (CCI) 
 * <tt>RecordFactory</tt> but instead creates custom <tt>AbstractCCIMappedRecord</tt> 
 * and <tt>AbstractCCIListRecord</tt> classes.
 *
 * 
 * @author walle
 * @see MappedRecord
 * @see IndexedRecord
 * @see AbstractCCIMappedRecord
 * @see AbstractCCIListRecord
 *
 **/
public interface ICCIRecordFactory {

    /**
     *
     * @param recordName
     * @return AbstractCCIMappedRecord
     * @throws ResourceException
     */
    public AbstractCCIMappedRecord createMappedRecord(String recordName) throws ResourceException;

    /**
     *
     * @param recordName
     * @return AbstractCCIListRecord
     * @throws ResourceException
     */
    public AbstractCCIListRecord createIndexedRecord(String recordName) throws ResourceException;

}
