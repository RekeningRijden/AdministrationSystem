package main.core.jms;

import main.service.CarService;
import main.service.DriverService;
import main.service.IntegrationService;
import main.service.OwnershipService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.core.communcation.Communicator;

/**
 * Created by Eric on 17-06-16.
 */

/**
 * Class for setting up everything needed for this webapp
 */
@Singleton
@Startup
public class JMSInit implements Serializable{

    @Inject
    private IntegrationService integrationService;
    private List<JMSConsumer> consumers;
    private List<JMSProducer> producers;

    /**
     * Initiates the needed components for JMS, this method is called at startup
     */
    @PostConstruct
    public void init() {
        createConsumersAndProducers();
    }

    /**
     * Creates the producers and consumers for this app
     */
    public void createConsumersAndProducers() {
        String EXCHANGE_TYPE_DIRECT = "direct";
        consumers = new ArrayList<>();
        producers = new ArrayList<>();

        //The producers to communnicate to our other systems
        String[] producerQueues = {"portugal_foreign_invoice"};
        String[] consumerQueues = {"portugal_foreign_movement_administration"};

        try {

            for(String queue : consumerQueues) {
                JMSConsumer consumer = new JMSConsumer(queue, integrationService);
                consumers.add(consumer);
            }

            for (String queue : producerQueues) {
                JMSProducer producer = new JMSProducer(EXCHANGE_TYPE_DIRECT, queue);
                producers.add(producer);
            }
        } catch (IOException | TimeoutException e) {
            Logger.getLogger(JMSInit.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * Closes all open connections when the server shuts down or the app gets
     * undeployed
     */
    @PreDestroy
    public void destroy() {
        for (JMSConsumer consumer : consumers) {
            try {
                consumer.closeConnection();
            } catch (TimeoutException | IOException e) {
                Logger.getLogger(JMSInit.class.getName()).log(Level.SEVERE, null, e);
            }
        }

        for (JMSProducer producer : producers) {
            try {
                producer.closeConnection();
            } catch (TimeoutException | IOException e) {
                Logger.getLogger(JMSInit.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    public JMSProducer getProducerByExchangeName(String exchangeName) {
        for (JMSProducer producer : producers) {
            if (producer.getExchangeName().equals(exchangeName)) {
                return producer;
            }
        }
        return null;
    }
}
