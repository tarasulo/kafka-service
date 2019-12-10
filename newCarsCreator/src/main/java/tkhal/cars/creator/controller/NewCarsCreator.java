package tkhal.cars.creator.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * NewCarsCreator is a program for running socket server
 * and creating new objects type car
 */
public class NewCarsCreator {

    private final static Logger LOGGER = LoggerFactory.getLogger(NewCarsCreator.class);
    private CarsFactory carsFactory;
    private ServerSocket ss = null;
    private NewCarsServer newCarsServer;

    public NewCarsCreator() {
        this.carsFactory = new CarsFactory();
        this.newCarsServer = new NewCarsServer();
    }

    /**
     * runServer() is a method starting Socket Server
     * and creating new cars
     */
    public void runServer() {
        // start Socket server
        ss = newCarsServer.startServer();
        try {
            Socket socket = ss.accept();
            while (true) {
                // creating new car
                ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
                os.writeObject(carsFactory.createCar());
            }
        } catch (IOException e) {
            LOGGER.error(e.toString());
        }
    }

    /**
     * This is the main method
     * which start running the class
     */
    public static void main(String[] args) {
        NewCarsCreator newCarsCreator = new NewCarsCreator();
        newCarsCreator.runServer();
    }
}
