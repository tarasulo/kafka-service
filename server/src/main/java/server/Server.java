package server;

import model.Car;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import servise.CarService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private final static Logger logger = LoggerFactory.getLogger(Server.class);
    public static final int port = 4444;
    private static CarService carService = new CarService();
    private ServerSocket ss = null;

    public void runServer() {
        try {
            ss = new ServerSocket(port);
            logger.info("System is ready to accept the connection");
            while (true) {
                Socket socket = ss.accept();
                ObjectInputStream is = new ObjectInputStream(socket.getInputStream());

                Car car = (Car) is.readObject();
                carService.makeCar(car);
                ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
                os.writeObject(car);
            }
        } catch (IOException | ClassNotFoundException e) {
            logger.error(e.toString());
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.runServer();
    }
}
