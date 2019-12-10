package tkhal.filter.controller;

import model.Car;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reader.ReadPropsFromFile;

import java.util.Collections;
import java.util.Properties;
/**
 * @author Taras Khalak
 */
public class FilterKafkaConsumer {
    /**
     * This is the class for creating Kafka Consumer
     */
    private static String topicName;
    private final Logger LOGGER = LoggerFactory.getLogger(MessageFilterController.class);
    private ReadPropsFromFile propsFromFile;

    public FilterKafkaConsumer() {
       propsFromFile = new ReadPropsFromFile();
    }

    public KafkaConsumer<String, Car> startConsumer() {
        /**
         * This is the method which reading properties from the file,
         * creating Kafka Consumer
         * and @return consumer
         */
        new FilterKafkaConsumer();
        // reading properties from config file
        Properties props = propsFromFile.read("configConsumer.properties");
        topicName = props.getProperty("topicName1");

        //creating new Kafka consumer
        KafkaConsumer<String, Car> consumer = new KafkaConsumer<String, Car>(props);
        consumer.subscribe(Collections.singletonList(topicName));
        LOGGER.info("Subscribed to topic " + topicName);
        return consumer;
    }

}
