/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digispherecorp.enterprise.rabbitmq.ra.cci.interatn;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.resource.cci.Connection;
import javax.resource.cci.Interaction;
import javax.resource.cci.InteractionSpec;

/**
 *
 * @author walle
 */
public class RabbitMQInterationFactory {

    private static volatile RabbitMQInterationFactory instance;

    private RabbitMQInterationFactory() {
    }

    public static RabbitMQInterationFactory newInstance() {
        synchronized (RabbitMQInterationFactory.class) {
            if (instance == null) {
                instance = new RabbitMQInterationFactory();
            }
        }
        return instance;
    }

    public static RabbitMQInterationFactory getInstance() {
        return instance;
    }

    public Interaction createInteraction(String className, Connection connection) {
        Interaction interaction = null;
        try {
            interaction = (Interaction) Class.forName(className).getConstructor(Connection.class).newInstance(connection);
        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(RabbitMQInterationFactory.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex);
        }
        return interaction;
    }

    public Interaction createInteraction(Class clazz, Connection connection) {
        Interaction interaction = null;
        try {
            interaction = (Interaction) clazz.getConstructor(Connection.class).newInstance(connection);
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(RabbitMQInterationFactory.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex);
        }
        return interaction;
    }

    public InteractionSpec createInteractionSpec(String className) {
        InteractionSpec interactionSpec = null;
        try {
            interactionSpec = (InteractionSpec) Class.forName(className).getConstructor().newInstance();
        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(RabbitMQInterationFactory.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex);
        }
        return interactionSpec;
    }

    public InteractionSpec createInteractionSpec(Class clazz) {
        InteractionSpec interactionSpec = null;
        try {
            interactionSpec = (InteractionSpec) clazz.getConstructor().newInstance();
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(RabbitMQInterationFactory.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex);
        }
        return interactionSpec;
    }

}
