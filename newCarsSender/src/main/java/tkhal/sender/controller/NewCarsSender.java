package tkhal.sender.controller;

import model.Car;
import org.apache.kafka.clients.producer.Producer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * @author Taras Khalak
 */
public class NewCarsSender {
    /**
     * This is application for reading messages from Socket Server
     * and sending them to topic by Kafka Producer
     */
    private static Socket socket;
    private static SocketCLient socketCLient;
    private static Producer<String, Car> producer;
    private static KafkaCarsProducer kafkaCarsProducer;

    public NewCarsSender() {
        socketCLient = new SocketCLient();
        kafkaCarsProducer = new KafkaCarsProducer();
    }

    public static void main(String[] args) throws InterruptedException, IOException, ClassNotFoundException {
        /**
         * This is the main method which getting cars from Socket Server
         * and sending them to topic by Kafka Producer
         */
        new NewCarsSender();
        // Socket client connection
        socket = socketCLient.runClient();

        // KafkaProducer connection
        producer = kafkaCarsProducer.makeProducer();
        while (true) {
            // sending new car by KafkaProducer to topic
            ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
            Car newCar = (Car) is.readObject();
            kafkaCarsProducer.sendCar(producer, newCar);
            // We should wait for 1 sec for the next car creating
            Thread.sleep(1000);
        }
    }
}
