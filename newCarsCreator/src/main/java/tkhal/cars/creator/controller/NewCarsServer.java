package tkhal.cars.creator.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reader.ReadPropsFromFile;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Properties;
/**
 * @author Taras Khalak
 */
public class NewCarsServer {
    /**
     * This is a class for creating socket server
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(NewCarsServer.class);
    private Integer port;
    private ServerSocket ss = null;
    private ReadPropsFromFile readPropsFromFile;

    public NewCarsServer() {
        readPropsFromFile = new ReadPropsFromFile();
    }

    public ServerSocket startServer() {
        /**
         * This method reads properties from file
         * and creating Socket Server
         */
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