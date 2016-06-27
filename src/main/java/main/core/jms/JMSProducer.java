package main.core.jms;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

/**
 * Created by Eric on 17-06-16.
 */
public class JMSProducer implements Serializable{

    private Channel channel;
    private Connection connection;
    private String exchangeName;
    private String exchangeType;

    public JMSProducer(String exchangeType, String queue) throws IOException, TimeoutException {
        this.exchangeType = exchangeType;
        this.exchangeName = queue + "_exchange";

        ConnectionFactory factory = new ConnectionFactory();
        factory.setPort(5672);

        //Lokaal in docker
//        factory.setHost("192.168.99.100");
//        factory.setUsername("test");
//        factory.setPassword("test");

        //Productie
        factory.setHost("rabbitmq.seclab.marijn.ws");
        factory.setUsername("portugal");
        factory.setPassword("s63a");


        connection = factory.newConnection();
        channel = connection.createChannel();

        channel.exchangeDeclare(exchangeName, exchangeType);
        channel.queueDeclare(queue, true, false, false, null);
        channel.queueBind(queue, exchangeName, "PT");
    }

    /**
     * Closes the connections
     *
     * @throws IOException
     * @throws TimeoutException
     */
    public void closeConnection() throws IOException, TimeoutException {
        channel.close();
        connection.close();
    }

    /**
     * Gets the routing key based on the param country and sends it to the
     * exchange with the routing key
     *
     * @param messagebody The body of the message to be sent
     */
    public void sendMessage(String messagebody) throws IOException {
        channel.basicPublish(exchangeName, "PT", null, messagebody.getBytes());
    }

    public String getExchangeName(){
        return this.exchangeName;
    }
}
