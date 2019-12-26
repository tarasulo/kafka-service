package tkhal.filter.controller;

import model.Car;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * This is the class for creating Kafka Producer
 */
public class FilterKafkaProducer {
    private String topicFilteredName = "Topic2";
    private final static Logger LOGGER = LoggerFactory.getLogger(FilterKafkaProducer.class);
    private Producer<String, Car> producer;

    public FilterKafkaProducer() {
    }

    /**
     * This is the method which reading properties from the file,
     * creating Kafka Producer
     * and @return producer
     */
    public Producer<String, Car> createProducer() {

        new FilterKafkaProducer();
        //reading Kafka producer properties from config file
        Properties propsRedirect = new Properties();
        propsRedirect.put("bootstrap.servers", "host.docker.internal:9092");
        propsRedirect.put("key.serializer", "serialize.CarSerializer");
        propsRedirect.put("value.serializer", "serialize.CarSerializer");

        // creating new Kafka producer
        producer = new KafkaProducer<String, Car>(propsRedirect);
        return producer;
    }

    public void sendCar(Car tempCar) {
        /**
         * This is the method which sending car
         * to the topic
         */
        try {
            producer.send(new ProducerRecord<String, Car>(topicFilteredName, tempCar));
        } catch (Exception e) {
            LOGGER.error("Resend failed " + e);
        }
    }
}
