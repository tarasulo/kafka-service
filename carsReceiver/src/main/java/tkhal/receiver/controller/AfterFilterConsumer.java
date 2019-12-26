package tkhal.receiver.controller;

import model.Car;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Properties;

/**
 * AfterFilterConsumer is a class for reading properties from file,
 * making Kafka Consumer
 * and getting cars from topic
 */
public class AfterFilterConsumer {
    private String topicName = "Topic2";
    private final Logger LOGGER = LoggerFactory.getLogger(AfterFilterConsumer.class);

    public AfterFilterConsumer() {
    }

    /**
     * startConsumer() is a method which reads properties from the file,
     * makes Kafka Consumer,
     * and gets cars from the topic
     */
    public KafkaConsumer<String, Car> startConsumer() {
        new AfterFilterConsumer();

        // reading Kafka consumer properties from config file
        Properties props = new Properties();
        props.put("bootstrap.servers", "host.docker.internal:9092");
        props.put("key.deserializer", "serialize.CarDeserializer");
        props.put("value.deserializer", "serialize.CarDeserializer");
        props.put("group.id", "test");

        // creating Kafka consumer
        KafkaConsumer<String, Car> consumer = new KafkaConsumer<String, Car>(props);
        consumer.subscribe(Collections.singletonList(topicName));
        LOGGER.info("Subscribed to topic " + topicName);
        return consumer;
    }
}
