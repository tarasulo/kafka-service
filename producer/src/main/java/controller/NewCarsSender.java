package controller;

import model.Car;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Properties;

public class NewCarsSender {

    private final static Logger logger = LoggerFactory.getLogger(NewCarsSender.class);
    private static String topicName;

    public static void main(String[] args) throws InterruptedException, IOException, ClassNotFoundException {

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
            Car car = new Car();
            Socket socket = new Socket("localhost", 4444);
            ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
            os.writeObject(car);
            ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
            Car newCar = (Car) is.readObject();
            socket.close();
            // Getting connection from the server and starting it
            try {
                producer.send(new ProducerRecord<String, Car>(topicName, newCar));
                logger.info("Created new car: " + newCar.toString());
            } catch (Exception e) {
                logger.error("Sending failed " + e.toString());
            }
            // We should wait for 1 sec for the next car creating
            Thread.sleep(1000);
        }
    }
}
