package controller;

import model.Car;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Properties;

public class AfterFilterConsumer {

    private String topicName;
    private final static Logger LOGGER = LoggerFactory.getLogger(CarStandardizerController.class);

    public KafkaConsumer<String, Car> startConsumer() {
        Properties props = new Properties();
        try {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream input = classloader.getResourceAsStream("configConsumer.properties");
            props.load(input);
            topicName = props.getProperty("topicName2");
        } catch (IOException e) {
            LOGGER.error(String.valueOf(e));
        }
        KafkaConsumer<String, Car> consumer = new KafkaConsumer<String, Car>(props);
        consumer.subscribe(Collections.singletonList(topicName));
        return consumer;
    }
}
