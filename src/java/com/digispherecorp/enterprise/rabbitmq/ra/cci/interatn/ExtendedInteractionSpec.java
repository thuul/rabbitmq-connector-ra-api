/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digispherecorp.enterprise.rabbitmq.ra.cci.interatn;

import javax.resource.ResourceException;
import javax.resource.cci.InteractionSpec;
import javax.resource.cci.Record;
import javax.resource.spi.ConnectionRequestInfo;

/**
 *
 * @author walle
 *
 * <p>
 * <tt>ExtendedInteractionSpec</tt> extends the <tt>InteractionSpec</tt>
 * interface to provide functionality extension by declaring very important
 * methods (execute), it also extends <tt>AutoClosable</tt> to make the
 * implementing classes, auto closable.
 *
 * <p>
 * Concrete implementations of this class assume that the class will always
 * have a default no-argument constructor except if there is a need for extra
 * arguments to be passed into the execute method.
 *
 * <p>
 * <tt>ConnectionRequestInfo</tt> will be set from Connection object when
 * <tt>Interaction</tt> class calls either of its execute method. The
 * implementation does not need to bother setting this method.
 *
 * @see InteractionSpec
 * @see Interaction
 * @see AutoCloseable
 *
 */
public interface ExtendedInteractionSpec extends InteractionSpec, AutoCloseable {

    /**
     *
     * @param input
     * @param output
     * @throws ResourceException
     */
    void execute(Record input, Record output) throws ResourceException;

    /**
     *
     * @param input
     * @return Record
     * @throws ResourceException
     */
    Record execute(Record input) throws ResourceException;

    /**
     *
     * @return ConnectionRequestInfo
     */
    ConnectionRequestInfo getConnectionRequestInfo();

    /**
     *
     * @param connectionRequestInfo
     */
    void setConnectionRequestInfo(ConnectionRequestInfo connectionRequestInfo);

    @Override
    public void close() throws ResourceException;

}
