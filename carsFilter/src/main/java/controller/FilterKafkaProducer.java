package controller;

import model.Car;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FilterKafkaProducer {
    private String topicFilteredName;
    private final static Logger LOGGER = LoggerFactory.getLogger(FilterKafkaProducer.class);
    private Producer <String, Car> producer;

    public Producer <String, Car> createProducer() {
        Properties propsRedirect = new Properties();

        try {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream input = classloader.getResourceAsStream("configProducer.properties");
            propsRedirect.load(input);
            topicFilteredName = propsRedirect.getProperty("topicName2");
        } catch (IOException e) {
            LOGGER.error(String.valueOf(e));
        }
        producer = new KafkaProducer<String, Car>(propsRedirect);
        return producer;
    }

    public void sendCar(Car tempCar) {
        try {
            producer.send(new ProducerRecord<String, Car>(topicFilteredName, tempCar));
        } catch (Exception e) {
            LOGGER.error("Resend failed " + e);
        }
    }
}
