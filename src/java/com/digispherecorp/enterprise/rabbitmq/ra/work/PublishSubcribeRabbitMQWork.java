/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digispherecorp.enterprise.rabbitmq.ra.work;

import com.digispherecorp.enterprise.rabbitmq.ra.activatn.EndPointActivationCapsule;
import com.digispherecorp.enterprise.rabbitmq.ra.activatn.RabbitMQActivationSpec;
import com.digispherecorp.enterprise.rabbitmq.ra.base.RabbitMQResourceAdapter;
import com.digispherecorp.enterprise.rabbitmq.ra.cci.RabbitMQConnectionFactoryFacade;
import com.digispherecorp.enterprise.rabbitmq.ra.endpoint.RabbitMQAMQPMessageListener;
import com.digispherecorp.enterprise.rabbitmq.ra.endpoint.RabbitMQMessage;
import com.digispherecorp.enterprise.rabbitmq.ra.mc.RabbitMQConnectionRequestInfo;
import com.digispherecorp.enterprise.rabbitmq.ra.rms.IPublishSubscribeMessage;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.resource.ResourceException;
import javax.resource.spi.ActivationSpec;
import javax.resource.spi.UnavailableException;
import javax.resource.spi.endpoint.MessageEndpoint;
import javax.resource.spi.endpoint.MessageEndpointFactory;
import javax.resource.spi.work.WorkManager;

/**
 *
 * @author walle
 */
public class PublishSubcribeRabbitMQWork extends RabbitMQWork {

    private String uuid;
    private Connection connection;
    private MessageEndpoint endpoint;
    private RabbitMQAMQPMessageListener listener;
    private IPublishSubscribeMessage ipsm;

    public PublishSubcribeRabbitMQWork(WorkManager workManager, MessageEndpointFactory endpointFactory, ActivationSpec spec, EndPointActivationCapsule capsule) {
        super(workManager, endpointFactory, spec, capsule);
        init();
    }

    private void init() {
        activationSpec = super.activationSpec;
        try {
            if ((endpoint = endpointFactory.createEndpoint(null)) != null) {
                listener = ((RabbitMQAMQPMessageListener) endpoint);
            }
        } catch (UnavailableException ex) {
            Logger.getLogger(QueueRabbitMQWork.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex);
        }
        uuid = "WorkScheduler:".concat(UUID.randomUUID().toString());
    }

    @Override
    public void release() {
        try {
            if (connection != null) {
                if (connection.isOpen()) {
                    connection.close();
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(QueueRabbitMQWork.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex);
        }
    }

    @Override
    public void run() {
        try {
            Logger.getLogger(QueueRabbitMQWork.class.getName()).log(Level.INFO, "Work Schedule Polling started @ ".concat(uuid));

            if (ipsm == null) {
                try {
                    Class<?> className = Class.forName(((RabbitMQActivationSpec) activationSpec).getDestinationType());
                    ipsm = (IPublishSubscribeMessage) className.getConstructor().newInstance();
                } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    Logger.getLogger(BootStrapRabbitMQWork.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex);
                }
            }
            final Channel channel;
            RabbitMQConnectionFactoryFacade instance = RabbitMQConnectionFactoryFacade.getInstance();
            if (instance.getConnectionRequestInfo(activationSpec) == null) {
                instance.setConnectionRequestInfo(
                        activationSpec,
                        new RabbitMQConnectionRequestInfo(((RabbitMQActivationSpec) activationSpec).getUser(),
                                ((RabbitMQActivationSpec) activationSpec).getPassword(),
                                ((RabbitMQActivationSpec) activationSpec).getHost(),
                                ((RabbitMQActivationSpec) activationSpec).getPort(),
                                ((RabbitMQActivationSpec) activationSpec).getVirtualHost()));
            }
            try {
                connection = instance.getConnection();
                channel = connection.createChannel();

                channel.exchangeDeclare(((RabbitMQActivationSpec) activationSpec).getExchangeName(), ipsm.getSubscribeType(), isExchangeDurabe(ipsm));

                final Consumer consumer = new DefaultConsumer(channel) {

                    @Override
                    public void handleConsumeOk(String consumerTag) {
                        super.handleConsumeOk(consumerTag);
                    }

                    @Override
                    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                        super.handleDelivery(consumerTag, envelope, properties, body);

                        if (!envelope.isRedeliver()) {
                            try {
                                RabbitMQMessage mQMessage = new RabbitMQMessage();
                                mQMessage.getMessages().add(body);
                                try {
                                    receiveMessages(mQMessage);
                                } catch (ResourceException ex) {
                                    Logger.getLogger(QueueRabbitMQWork.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex);
                                }
                            } finally {
                                channel.basicAck(envelope.getDeliveryTag(), true);
                            }
                        }
                    }
                };
                channel.basicConsume(((RabbitMQActivationSpec) activationSpec).getQueueName(), false, consumer);
            } catch (IOException ex) {
                Logger.getLogger(RabbitMQResourceAdapter.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex);
            }
        } catch (Exception ex) {
            Logger.getLogger(QueueRabbitMQWork.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex);
        }
    }

    private void receiveMessages(RabbitMQMessage mQMessage) throws ResourceException {
        listener.onMessage(mQMessage);
    }

    private boolean isExchangeDurabe(IPublishSubscribeMessage irmqps) {
        return (irmqps.getSubscribeType().equals("topic") || irmqps.getSubscribeType().equals("direct") || irmqps.getSubscribeType().equals("header"));
    }

}
