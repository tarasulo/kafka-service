package controller;

import model.Car;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class MessageFilterController {

    private final static Logger LOGGER = LoggerFactory.getLogger(MessageFilterController.class);
    private static String topicName;
    private static String topicFilteredName;
    private static Car tempCar = null;

    public static void main(String[] args) {

        Properties props = new Properties();
        Properties propsRedirect = new Properties();

        try {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream input = classloader.getResourceAsStream("configConsumer.properties");
            props.load(input);
            topicName = props.getProperty("topicName1");
        } catch (IOException e) {
            LOGGER.error(String.valueOf(e));
        }

        try {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream input = classloader.getResourceAsStream("configProducer.properties");
            propsRedirect.load(input);
            topicFilteredName = propsRedirect.getProperty("topicName2");
        } catch (IOException e) {
            LOGGER.error(String.valueOf(e));
        }

        KafkaConsumer<String, Car> consumer = new KafkaConsumer<String, Car>(props);
        consumer.subscribe(Collections.singletonList(topicName));
        LOGGER.info("Subscribed to topic " + topicName);
        Producer<String, Car> producer = new KafkaProducer<String, Car>(propsRedirect);

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
                if (tempCar.getEngine() > 2.0 & tempCar.getYear() > 2000) {
                    try {
                        producer.send(new ProducerRecord<String, Car>(topicFilteredName, tempCar));
                    } catch (Exception e) {
                        LOGGER.error("Resend failed " + e);
                    }
                } else {
                    LOGGER.info("Sorry, these {} does not meet the filtering requirements ", tempCar.toString());
                }
            }
        }
    }
}
