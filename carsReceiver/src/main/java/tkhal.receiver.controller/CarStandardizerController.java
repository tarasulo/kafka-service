package tkhal.receiver.controller;

import model.Car;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.time.Duration;

/**
 * @author Taras Khalak
 */
public class CarStandardizerController {
    /**
     * This is application for reading messages from Kafka topic
     * after filtration, then standardize car and writing to database
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(CarStandardizerController.class);
    private static Car standardizerCar = null;
    private static AfterFilterConsumer afterFilterConsumer;
    private static KafkaConsumer<String, Car> consumer;
    private static JdbcCarsWriter jdbcCarsWriter;
    private static CarStandardizer standardizer;

    public CarStandardizerController() {
        afterFilterConsumer = new AfterFilterConsumer();
        jdbcCarsWriter = new JdbcCarsWriter();
        standardizer = new CarStandardizer();
    }

    public static void main(String[] args) throws SQLException {
        /**
         * This is the main method which getting cars, standardize,
         * and writing them to database
         */
        new CarStandardizerController();

        // starting Kafka consumer
        consumer = afterFilterConsumer.startConsumer();

        while (true) {
            // reading messages
            ConsumerRecords<String, Car> messages = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, Car> message : messages) {
                try {
                    LOGGER.info("Car standardized controller received after filtration "
                            + message.value().toString());
                    standardizerCar = message.value();
                } catch (Exception e) {
                    LOGGER.error(String.valueOf(e));
                }
                // standardizing message
                standardizerCar = standardizer.changeCar(standardizerCar);
                // writing message to database
                jdbcCarsWriter.writeToDataBase(standardizerCar);
            }
        }
    }
}
