/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digispherecorp.enterprise.rabbitmq.ra.cci.interatn;

import com.digispherecorp.enterprise.rabbitmq.ra.cci.RabbitMQCCIConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.resource.ResourceException;
import javax.resource.cci.Connection;
import javax.resource.cci.InteractionSpec;
import javax.resource.cci.Record;
import javax.resource.cci.ResourceWarning;

/**
 *
 * @author walle
 */
public class RabbitMQCCIInteraction implements ExtendedInteraction {

    private static final long serialVersionUID = 5881328146728519876L;

    private final Connection connection;
    private final List<ResourceWarning> resourceWarnings = new ArrayList<>();

    public RabbitMQCCIInteraction(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void close() throws ResourceException {
        resourceWarnings.clear();
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public boolean execute(InteractionSpec ispec, Record input, Record output) throws ResourceException {
        try (ExtendedInteractionSpec interactionSpec = ((ExtendedInteractionSpec) ispec)) {
            try {
                interactionSpec.setConnectionRequestInfo(((RabbitMQCCIConnection) connection).getConnectionRequestInfo());
                interactionSpec.execute(input, output);
            } finally {
                interactionSpec.close();
            }
            return true;
        } catch (Exception ex) {
            resourceWarnings.add((ResourceWarning) ex);
            throw new ResourceException(ex);
        }
    }

    @Override
    public Record execute(InteractionSpec ispec, Record input) throws ResourceException {
        Record execute = null;
        try (ExtendedInteractionSpec interactionSpec = ((ExtendedInteractionSpec) ispec)) {
            try {
                interactionSpec.setConnectionRequestInfo(((RabbitMQCCIConnection) connection).getConnectionRequestInfo());
                execute = interactionSpec.execute(input);
            } finally {
                interactionSpec.close();
            }
        } catch (Exception ex) {
            resourceWarnings.add((ResourceWarning) ex);
            throw new ResourceException(ex);
        }
        return execute;
    }

    @Override
    public ResourceWarning getWarnings() throws ResourceException {
        if (!resourceWarnings.isEmpty()) {
            synchronized (resourceWarnings) {
                Collections.reverse(resourceWarnings);
                return resourceWarnings.get(0);
            }
        }
        return null;
    }

    @Override
    public void clearWarnings() throws ResourceException {
        synchronized (resourceWarnings) {
            resourceWarnings.clear();
        }
    }

}
