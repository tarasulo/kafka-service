package tkhal.sender.controller;

import model.Car;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class KafkaCarsProducer {

    private String topicName;
    private final Logger LOGGER = LoggerFactory.getLogger(NewCarsSender.class);

    public Producer<String, Car> makeProducer() {

        //reading producer properties from file
        Properties props = new Properties();
        try {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream input = classloader.getResourceAsStream("configProducer.properties");
            props.load(input);
            topicName = props.getProperty("topicName1");
        } catch (IOException e) {
            LOGGER.error(String.valueOf(e));
        }

        // starting KafkaProducer
        Producer<String, Car> producer = new KafkaProducer<String, Car>(props);
        return producer;
    }

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
