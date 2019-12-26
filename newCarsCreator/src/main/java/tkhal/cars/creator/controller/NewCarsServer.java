package tkhal.cars.creator.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * NewCarsServer is a class for creating socket server
 */
public class NewCarsServer {

    private final static Logger LOGGER = LoggerFactory.getLogger(NewCarsServer.class);
    private Integer port = Integer.valueOf(System.getenv("PORT"));
    private ServerSocket ss = null;

    public NewCarsServer() {
    }

    /**
     * startServer() is a method creating Socket Server
     */
    public ServerSocket startServer() {
        new NewCarsServer();

        // starting ServerSocket
        try {
            ss = new ServerSocket(port);
            LOGGER.info("System is ready to accept the connection");
        } catch (IOException e) {
            LOGGER.error(e.toString());
        }
        return ss;
    }
}