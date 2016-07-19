/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digispherecorp.enterprise.rabbitmq.ra.tranxa;

import javax.naming.InitialContext;
import javax.transaction.TransactionManager;

/**
 *
 * @author walle
 */
public class JBossTransactionManagerLocator {

    private static final String TM_JNDI_NAME = "java:jboss/TransactionManager";

    public TransactionManager getTransactionManager() throws Exception {
        InitialContext ctx = null;

        try {
            ctx = new InitialContext();
            return (TransactionManager) ctx.lookup(TM_JNDI_NAME);
        } finally {
            try {
                if (ctx != null) {
                    ctx.close();
                }
            } catch (Exception ignore) {
            }
        }
    }
}
