/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digispherecorp.enterprise.rabbitmq.ra.cci;

import javax.resource.cci.ResourceAdapterMetaData;

/**
 *
 * @author walle
 */
public class RabbitMQResourceAdapterMetaData implements ResourceAdapterMetaData {

    @Override
    public String getAdapterVersion() {
        return "1.0.0";
    }

    @Override
    public String getAdapterVendorName() {
        return "Digisphere CC SA";
    }

    @Override
    public String getAdapterName() {
        return "Digisphere CC RabbitMQ Resource Adapter";
    }

    @Override
    public String getAdapterShortDescription() {
        return "Custom Resource Adapter for RabbitMQ";
    }

    @Override
    public String getSpecVersion() {
        return "3.4.5";
    }

    @Override
    public String[] getInteractionSpecsSupported() {
        return new String[]{""};
    }

    @Override
    public boolean supportsExecuteWithInputAndOutputRecord() {
        return false;
    }

    @Override
    public boolean supportsExecuteWithInputRecordOnly() {
        return false;
    }

    @Override
    public boolean supportsLocalTransactionDemarcation() {
        return true;
    }

}
