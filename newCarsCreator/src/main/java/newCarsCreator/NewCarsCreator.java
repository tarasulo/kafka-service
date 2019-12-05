package newCarsCreator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class NewCarsCreator {

    private final static Logger LOGGER = LoggerFactory.getLogger(NewCarsCreator.class);
    private CarsFactory carsFactory = new CarsFactory();
    private ServerSocket ss = null;
    private NewCarsServer newCarsServer = new NewCarsServer();

    public void runServer() {
        ss = newCarsServer.startServer();
        try {
            Socket socket = ss.accept();
            while (true) {
                ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
                os.writeObject(carsFactory.createCar());
            }
        } catch (IOException e) {
            LOGGER.error(e.toString());
        }
    }

    public static void main(String[] args) {
        NewCarsCreator newCarsCreator = new NewCarsCreator();
        newCarsCreator.runServer();
    }
}
