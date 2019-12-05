package newCarsCreator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.util.Properties;

public class NewCarsServer {

    private final static Logger LOGGER = LoggerFactory.getLogger(NewCarsServer.class);
    private Integer port;
    private ServerSocket ss = null;

    public ServerSocket startServer() {
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
        } catch (IOException e) {
            LOGGER.error(e.toString());
        }
        return ss;
    }
}