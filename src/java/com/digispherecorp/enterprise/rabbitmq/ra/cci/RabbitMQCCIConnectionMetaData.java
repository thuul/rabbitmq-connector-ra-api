/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digispherecorp.enterprise.rabbitmq.ra.cci;

import java.io.Serializable;
import javax.resource.cci.ConnectionMetaData;

/**
 *
 * @author walle
 */
public class RabbitMQCCIConnectionMetaData implements ConnectionMetaData, Serializable {

    private static final long serialVersionUID = -6757077004580415138L;

    private String eISProductName;
    private String eISProductVersion;
    private String userName;

    public RabbitMQCCIConnectionMetaData() {
    }

    public RabbitMQCCIConnectionMetaData(String eISProductName, String eISProductVersion, String userName) {
        this.eISProductName = eISProductName;
        this.eISProductVersion = eISProductVersion;
        this.userName = userName;
    }

    @Override
    public String getEISProductName() {
        return eISProductName;
    }

    public void setEISProductName(String eISProductName) {
        this.eISProductName = eISProductName;
    }

    @Override
    public String getEISProductVersion() {
        return eISProductVersion;
    }

    public void setEISProductVersion(String eISProductVersion) {
        this.eISProductVersion = eISProductVersion;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
