package controller;

import model.Car;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class MessageFilterController {

    private final static Logger LOGGER = LoggerFactory.getLogger(MessageFilterController.class);
    private static Car tempCar = null;
    private static KafkaConsumer<String, Car> consumer;
    private static Producer<String, Car> producer;
    private static FilterKafkaConsumer filterKafkaConsumer = new FilterKafkaConsumer();
    private static FilterKafkaProducer filterKafkaProducer = new FilterKafkaProducer();
    private static CarsFilter carsFilter = new CarsFilter();

    public static void main(String[] args) {

        consumer = filterKafkaConsumer.startConsumer();
        producer = filterKafkaProducer.createProducer();

        while (true) {
            ConsumerRecords<String, Car> messages = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, Car> message : messages) {
                try {
                    LOGGER.info("Filter controller received "
                            + message.value().toString());
                    tempCar = message.value();
                } catch (Exception e) {
                    LOGGER.error(String.valueOf(e));
                }
                // Cars filter starts
                tempCar = carsFilter.filter(tempCar);
                if (tempCar != null) {
                    filterKafkaProducer.sendCar(tempCar);
                }
            }
        }
    }
}
