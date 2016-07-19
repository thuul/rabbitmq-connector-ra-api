/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digispherecorp.enterprise.rabbitmq.ra.cci.interatn;

import java.io.Serializable;
import javax.resource.cci.Interaction;

/**
 *
 * @author walle
 *
 * <p>
 * <tt>ExtendedInteraction</tt> extends <tt>Interaction</tt> interface to
 * provide functionality extension by extending <tt>AutoClosable</tt> to make
 * the implementing classes, auto closable.
 *
 * <p>
 * This interface is only extended to provide a single point of entry for
 * concrete implementation of the <tt>Interaction</tt> interface used in this RA
 * implementation.
 *
 * @see Interaction
 * @see AutoCloseable
 * @see Serializable
 *
 *
 */
public interface ExtendedInteraction extends Interaction, AutoCloseable, Serializable {
}
