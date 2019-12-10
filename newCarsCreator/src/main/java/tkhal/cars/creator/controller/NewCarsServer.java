package tkhal.cars.creator.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reader.ReadPropsFromFile;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Properties;

/**
 * NewCarsServer is a class for creating socket server
 */
public class NewCarsServer {

    private final static Logger LOGGER = LoggerFactory.getLogger(NewCarsServer.class);
    private Integer port;
    private ServerSocket ss = null;
    private ReadPropsFromFile readPropsFromFile;

    public NewCarsServer() {
        readPropsFromFile = new ReadPropsFromFile();
    }

    /**
     * startServer() is a method reads properties from file
     * and creating Socket Server
     */
    public ServerSocket startServer() {
        new NewCarsServer();
        //reading socket properties from file
        Properties props = readPropsFromFile.read("configSocket.properties");
        port = Integer.parseInt(props.getProperty("port"));

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