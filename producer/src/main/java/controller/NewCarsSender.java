package controller;

import model.Car;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import servise.CarService;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class NewCarsSender {

    private final static Logger logger = LoggerFactory.getLogger(NewCarsSender.class);
    private static String topicName;
    private static CarService carService = new CarService();

    public static void main(String[] args) throws InterruptedException {

        Properties props = new Properties();

        try {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream input = classloader.getResourceAsStream("configProducer.properties");
            props.load(input);
            topicName = props.getProperty("topicName1");
        } catch (IOException e) {
            logger.error(String.valueOf(e));
        }

        Producer<String, Car> producer = new KafkaProducer<String, Car>(props);

        while (true) {
            //Creating new car
            Car car = new Car();
            carService.makeCar(car);
            // Getting connection from the server and starting it
            try {
                producer.send(new ProducerRecord<String, Car>(topicName, car));
                logger.info("Created new car: " + car.toString());
            } catch (Exception e) {
                logger.error("Sending failed " + e.toString());
            }
            // We should wait for 1 sec for the next car creating
            Thread.sleep(1000);
        }
    }
}
