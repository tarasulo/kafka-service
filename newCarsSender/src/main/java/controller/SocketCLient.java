package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Properties;

public class SocketCLient {

    private Integer port;
    private String host;
    private final static Logger LOGGER = LoggerFactory.getLogger(SocketCLient.class);
    private Socket socket;

    public Socket runClient() throws IOException {
        Properties socketProps = new Properties();
        try {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream input = classloader.getResourceAsStream("configSocket.properties");
            socketProps.load(input);
            port = Integer.parseInt(socketProps.getProperty("port"));
            host = socketProps.getProperty("host");
        } catch (IOException e) {
            LOGGER.error(String.valueOf(e));
        }
        socket = new Socket(host, port);
        return socket;
    }
}
