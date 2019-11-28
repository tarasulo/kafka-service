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
import java.net.Socket;
import java.util.Properties;

public class NewCarsSender {

    private final static Logger LOGGER = LoggerFactory.getLogger(NewCarsSender.class);
    private static String topicName;
    private static Integer port;
    private static String host;

    public static void main(String[] args) throws InterruptedException, IOException, ClassNotFoundException {

        Properties props = new Properties();
        Properties socketProps = new Properties();
        try {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream input = classloader.getResourceAsStream("configSocket.properties");
            socketProps.load(input);
            port = Integer.parseInt(socketProps.getProperty("port"));
            host = socketProps.getProperty("host");
        } catch (IOException e) {
            LOGGER.error(String.valueOf(e));
        }

        try {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream input = classloader.getResourceAsStream("configProducer.properties");
            props.load(input);
            topicName = props.getProperty("topicName1");
        } catch (IOException e) {
            LOGGER.error(String.valueOf(e));
        }

        Producer<String, Car> producer = new KafkaProducer<String, Car>(props);
        Socket socket = new Socket(host, port);
        while (true) {
            ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
            Car newCar = (Car) is.readObject();
            // Getting connection from the server and starting it
            try {
                producer.send(new ProducerRecord<String, Car>(topicName, newCar));
                LOGGER.info("Created new car: " + newCar.toString());
            } catch (Exception e) {
                LOGGER.error("Sending failed " + e.toString());
            }
            // We should wait for 1 sec for the next car creating
            Thread.sleep(1000);
        }
    }
}
