/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digispherecorp.enterprise.rabbitmq.ra.tranxa;

import javax.transaction.xa.Xid;

/**
 *
 * @author walle
 */
public class RabbitMQXid implements Xid {

    private int formatId;
    private byte[] globalTransactionId;
    private byte[] branchQualifier;

    public RabbitMQXid() {
    }

    public RabbitMQXid(int formatId, byte[] globalTransactionId, byte[] branchQualifier) {
        this.formatId = formatId;
        this.globalTransactionId = globalTransactionId;
        this.branchQualifier = branchQualifier;
    }

    @Override
    public int getFormatId() {
        return formatId;
    }

    public void setFormatId(int formatId) {
        this.formatId = formatId;
    }

    @Override
    public byte[] getGlobalTransactionId() {
        return globalTransactionId;
    }

    public void setGlobalTransactionId(byte[] globalTransactionId) {
        this.globalTransactionId = globalTransactionId;
    }

    @Override
    public byte[] getBranchQualifier() {
        return branchQualifier;
    }

    public void setBranchQualifier(byte[] branchQualifier) {
        this.branchQualifier = branchQualifier;
    }

}
