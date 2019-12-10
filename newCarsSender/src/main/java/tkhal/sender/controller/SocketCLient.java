package tkhal.sender.controller;

import reader.ReadPropsFromFile;

import java.io.IOException;
import java.net.Socket;
import java.util.Properties;

/**
 * SocketCLient is a class for creating and running socket client
 */
public class SocketCLient {

    private Integer port;
    private String host;
    private Socket socket;
    private ReadPropsFromFile readPropsFromFile;

    public SocketCLient() {
        readPropsFromFile = new ReadPropsFromFile();
    }

    /**
     * runClient() method reads properties from file
     * and creating Socket Client
     */
    public Socket runClient() throws IOException {
        new SocketCLient();
        // reading Socket properties from config file
        Properties socketProps = readPropsFromFile.read("configSocket.properties");
        port = Integer.parseInt(socketProps.getProperty("port"));
        host = socketProps.getProperty("host");
        // socket connection
        socket = new Socket(host, port);
        return socket;
    }
}
