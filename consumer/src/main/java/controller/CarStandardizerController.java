package controller;

import authentication.Authentication;
import model.Car;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class CarStandardizerController {

    private final static Logger logger = LoggerFactory.getLogger(CarStandardizerController.class);
    private static String topicName;
    private static boolean auth = false;
    private static Car standardizerCar = null;

    public static void main(String[] args) {

        auth = Authentication.isValid();
        Properties props = new Properties();

        try {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream input = classloader.getResourceAsStream("configConsumer.properties");
            props.load(input);
            topicName = props.getProperty("topicName2");
        } catch (IOException e) {
            logger.error(String.valueOf(e));
        }

        if (auth) {
            KafkaConsumer<String, Car> consumer = new KafkaConsumer<String, Car>(props);
            consumer.subscribe(Collections.singletonList(topicName));
            while (true) {
                ConsumerRecords<String, Car> messages = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, Car> message : messages) {
                    try {
                        logger.info("Car standardized controller received after filtration "
                                + message.value().toString());
                        standardizerCar = message.value();
                    } catch (Exception e) {
                        logger.error(String.valueOf(e));
                    }

                    // Cars standardizer starts work
                    standardizerCar.setBrand(standardizerCar.getBrand().toUpperCase());
                    standardizerCar.setModel(standardizerCar.getModel().toUpperCase());

                    logger.info(" Hello Mates! we got new car: " + standardizerCar.getBrand()
                            + " model: " + standardizerCar.getModel() + " with engine " + standardizerCar.getEngine()
                            + " from year - " + standardizerCar.getYear());
                }
            }
        }
    }
}
