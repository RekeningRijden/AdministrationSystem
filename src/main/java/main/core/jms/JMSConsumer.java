package main.core.jms;

import com.rabbitmq.client.*;
import main.service.IntegrationService;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Eric on 17-06-16.
 */
public class JMSConsumer {

    private Channel channel;

    private Connection connection;

    private IntegrationService integrationService;

    /**
     * Creates a connection and declares the queue and listens for message on that queue
     *
     * @param queueName The name of the queue
     * @throws IOException
     * @throws TimeoutException
     */
    public JMSConsumer(final String queueName, IntegrationService integrationService) throws IOException, TimeoutException {
        this.integrationService = integrationService;
        ConnectionFactory factory = new ConnectionFactory();
        factory.setPort(5672);

//        //Lokaal in docker
//        factory.setHost("192.168.99.100");
//        factory.setUsername("test");
//        factory.setPassword("test");

//        Productie
        factory.setHost("rabbitmq.seclab.marijn.ws");
        factory.setUsername("portugal");
        factory.setPassword("s63a");

        connection = factory.newConnection();
        channel = connection.createChannel();

        channel.queueDeclare(queueName, true, false, false, null);

        System.out.println("Waiting for messages on queue: " + queueName);
        JMSHandler handler = new JMSHandler(integrationService);
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("Received message: " + message + " on queue " + queueName + " with routing key: " + envelope.getRoutingKey());
                handler.handleMessage(message);
            }
        };
        channel.basicConsume(queueName, true, consumer);
    }

    /**
     * Closes the open connections
     *
     * @throws IOException
     * @throws TimeoutException
     */
    public void closeConnection() throws IOException, TimeoutException {
        channel.close();
        connection.close();
    }
}
