package tkhal.filter.controller;

import model.Car;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

/**
 * This is application for reading messages from Kafka topic
 * filtration them, and sending to new Kafka topic
 */
public class MessageFilterController {
    private final static Logger LOGGER = LoggerFactory.getLogger(MessageFilterController.class);
    private static Car tempCar = null;
    private static KafkaConsumer<String, Car> consumer;
    private static Producer<String, Car> producer;
    private static FilterKafkaConsumer filterKafkaConsumer;
    private static FilterKafkaProducer filterKafkaProducer;
    private static CarsFilter carsFilter;

    public MessageFilterController() {
        filterKafkaConsumer = new FilterKafkaConsumer();
        filterKafkaProducer = new FilterKafkaProducer();
        carsFilter = new CarsFilter();
    }

    /**
     * filteredCar() is a method which getting cars, filtration them,
     * and writing to the new Kafka topic
     */
    public void filteredCar() {
        // starting new Kafka consumer
        consumer = filterKafkaConsumer.startConsumer();
        // starting new Kafka producer
        producer = filterKafkaProducer.createProducer();

        while (true) {
            // reading messages from Kafka topic
            ConsumerRecords<String, Car> messages = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, Car> message : messages) {
                try {
                    LOGGER.info("Filter controller received "
                            + message.value().toString());
                    tempCar = message.value();
                } catch (Exception e) {
                    LOGGER.error(String.valueOf(e));
                }
                // Cars filter starting work
                if (carsFilter.filter(tempCar)) {
                    // sending car by Kafka producer
                    filterKafkaProducer.sendCar(tempCar);
                }
            }
        }
    }

    /**
     * This is the main method which getting cars, filtration them,
     * and writing to the new Kafka topic
     */
    public static void main(String[] args) {
        MessageFilterController messageFilterController = new MessageFilterController();
        messageFilterController.filteredCar();
    }
}
