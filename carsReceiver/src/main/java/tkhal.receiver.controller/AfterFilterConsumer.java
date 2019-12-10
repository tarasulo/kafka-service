package tkhal.receiver.controller;

import model.Car;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import reader.ReadPropsFromFile;

import java.util.Collections;
import java.util.Properties;
/**
 * @author Taras Khalak
 */
public class AfterFilterConsumer {
    /**
     * This is class for reading properties from file,
     * making Kafka Consumer
     * and getting cars from topic
     */
    private String topicName;
    private ReadPropsFromFile readPropsFromFile;

    public AfterFilterConsumer() {
        readPropsFromFile = new ReadPropsFromFile();
    }

    public KafkaConsumer<String, Car> startConsumer() {
        /**
         * This is the method which reads properties from the file,
         * makes Kafka Consumer,
         * and gets cars from the topic
         */
        new AfterFilterConsumer();

        // reading Kafka consumer properties from config file
        Properties props = readPropsFromFile.read("configConsumer.properties");
        topicName = props.getProperty("topicName2");

        // creating Kafka consumer
        KafkaConsumer<String, Car> consumer = new KafkaConsumer<String, Car>(props);
        consumer.subscribe(Collections.singletonList(topicName));
        return consumer;
    }
}
