package server;

import model.Car;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import servise.CarService;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

public class Server {

    private final static Logger LOGGER = LoggerFactory.getLogger(Server.class);
    private Integer port;

    private CarService carService = new CarService();
    private ServerSocket ss = null;

    public void runServer() {
        Properties props = new Properties();
        try {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream input = classloader.getResourceAsStream("configSocket.properties");
            props.load(input);
            port = Integer.parseInt(props.getProperty("port"));
        } catch (IOException e) {
            LOGGER.error(String.valueOf(e));
        }
        try {
            ss = new ServerSocket(port);
            LOGGER.info("System is ready to accept the connection");
            Socket socket = ss.accept();
            while (true) {
                Car car = new Car();
                carService.makeCar(car);
                ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
                os.writeObject(car);
            }
        } catch (IOException e) {
            LOGGER.error(e.toString());
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.runServer();
    }
}
