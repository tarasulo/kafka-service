package tkhal.filter.controller;

import model.Car;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Properties;

/**
 * FilterKafkaConsumer class for creating Kafka Consumer
 */
public class FilterKafkaConsumer {
    private static String topicName = "Topic1";
    private final Logger LOGGER = LoggerFactory.getLogger(MessageFilterController.class);

    public FilterKafkaConsumer() {
    }

    /**
     * startConsumer is a method which reading properties from the file,
     * creating Kafka Consumer
     * and @return consumer
     */
    public KafkaConsumer<String, Car> startConsumer() {
        new FilterKafkaConsumer();
        // reading properties from config file
        Properties props = new Properties();
        props.put("bootstrap.servers", "host.docker.internal:9092");
        props.put("key.deserializer", "serialize.CarDeserializer");
        props.put("value.deserializer", "serialize.CarDeserializer");
        props.put("group.id", "test");
        //creating new Kafka consumer
        KafkaConsumer<String, Car> consumer = new KafkaConsumer<String, Car>(props);
        consumer.subscribe(Collections.singletonList(topicName));
        LOGGER.info("Subscribed to topic " + topicName);
        return consumer;
    }

}
