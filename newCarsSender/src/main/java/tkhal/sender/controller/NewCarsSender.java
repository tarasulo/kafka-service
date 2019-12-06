package tkhal.sender.controller;

import model.Car;
import org.apache.kafka.clients.producer.Producer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class NewCarsSender {

    private static Socket socket;
    private static SocketCLient socketCLient = new SocketCLient();
    private static Producer<String, Car> producer;
    private static KafkaCarsProducer kafkaCarsProducer = new KafkaCarsProducer();

    public static void run() throws InterruptedException, IOException, ClassNotFoundException {

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
    public static void main(String[] args) throws InterruptedException, IOException, ClassNotFoundException {
        run();
    }
}
