package tkhal.sender.controller;

import model.Car;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reader.ReadPropsFromFile;

import java.util.Properties;
/**
 * @author Taras Khalak
 */
public class KafkaCarsProducer {
    /**
     * This is a class for creating Kafka Producer
     * and sending new cars to topic
     */
    private String topicName;
    private final Logger LOGGER = LoggerFactory.getLogger(NewCarsSender.class);
    private ReadPropsFromFile readPropsFromFile;

    public KafkaCarsProducer() {
        readPropsFromFile = new ReadPropsFromFile();
    }

    public Producer<String, Car> makeProducer() {
        /**
         * This method reads properties from file
         * and creating Kafka Producer
         */
        new KafkaCarsProducer();
        //reading producer properties from file
        Properties props = readPropsFromFile.read("configProducer.properties");
        topicName = props.getProperty("topicName1");

        // starting KafkaProducer
        Producer<String, Car> producer = new KafkaProducer<String, Car>(props);
        return producer;
    }

    public void sendCar(Producer<String, Car> producer, Car car) {
        /**
         * This method sending cars to topic
         */
        // KafkaProducer sending car to topic
        try {
            producer.send(new ProducerRecord<String, Car>(topicName, car));
            LOGGER.info("Created new car: " + car.toString());
        } catch (Exception e) {
            LOGGER.error("Sending failed " + e.toString());
        }
    }
}
