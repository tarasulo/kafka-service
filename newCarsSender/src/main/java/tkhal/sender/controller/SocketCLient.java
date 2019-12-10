package tkhal.sender.controller;

import reader.ReadPropsFromFile;

import java.io.IOException;
import java.net.Socket;
import java.util.Properties;
/**
 * @author Taras Khalak
 */
public class SocketCLient {
    /**
     * This is a class for creating and running socket client
     */
    private Integer port;
    private String host;
    private Socket socket;
    private ReadPropsFromFile readPropsFromFile;

    public SocketCLient() {
        readPropsFromFile = new ReadPropsFromFile();
    }

    public Socket runClient() throws IOException {
        /**
         * This method reads properties from file
         * and creating Socket Client
         */
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
