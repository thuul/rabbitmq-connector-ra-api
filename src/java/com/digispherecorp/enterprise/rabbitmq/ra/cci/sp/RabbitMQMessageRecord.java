/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digispherecorp.enterprise.rabbitmq.ra.cci.sp;

/**
 *
 * @author walle
 */
public class RabbitMQMessageRecord extends AbstractCCIRecord {

    private static final long serialVersionUID = 1L;

    private final byte[] messageByte;
    private final String exchangeType;

    public RabbitMQMessageRecord(byte[] messageByte, String exchangeType) {
        this.messageByte = messageByte;
        this.exchangeType = exchangeType;
    }

    public byte[] getMessageByte() {
        return messageByte;
    }

    public String getExchangeType() {
        return exchangeType;
    }
}
