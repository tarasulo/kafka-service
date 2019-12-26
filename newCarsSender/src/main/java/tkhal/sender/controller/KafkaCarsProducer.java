package tkhal.sender.controller;

import model.Car;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * KafkaCarsProducer is a class for creating Kafka Producer
 * and sending new cars to topic
 */
public class KafkaCarsProducer {
    private String topicName = "Topic1";
    private final Logger LOGGER = LoggerFactory.getLogger(NewCarsSender.class);

    public KafkaCarsProducer() {
    }

    /**
     * makeProducer() is a method reads properties from file
     * and creating Kafka Producer
     */
    public Producer<String, Car> makeProducer() {
        new KafkaCarsProducer();
        //reading producer properties from file
        Properties props = new Properties();
        props.put("bootstrap.servers", "host.docker.internal:9092");
        props.put("key.serializer", "serialize.CarSerializer");
        props.put("value.serializer", "serialize.CarSerializer");

        // starting KafkaProducer
        Producer<String, Car> producer = new KafkaProducer<String, Car>(props);
        return producer;
    }

    /**
     * sendCar method sending cars to topic
     */
    public void sendCar(Producer<String, Car> producer, Car car) {
        // KafkaProducer sending car to topic
        try {
            producer.send(new ProducerRecord<String, Car>(topicName, car));
            LOGGER.info("Created new car: " + car.toString());
        } catch (Exception e) {
            LOGGER.error("Sending failed " + e.toString());
        }
    }
}
