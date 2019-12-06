package tkhal.receiver.controller;

import model.Car;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.time.Duration;

public class CarStandardizerController {

    private final static Logger LOGGER = LoggerFactory.getLogger(CarStandardizerController.class);
    private static Car standardizerCar = null;
    private static AfterFilterConsumer afterFilterConsumer = new AfterFilterConsumer();
    private static KafkaConsumer<String, Car> consumer;
    private static JdbcCarsWriter jdbcCarsWriter = new JdbcCarsWriter();
    private static CarStandardizer standardizer = new CarStandardizer();

    public static void main(String[] args) throws SQLException {

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
