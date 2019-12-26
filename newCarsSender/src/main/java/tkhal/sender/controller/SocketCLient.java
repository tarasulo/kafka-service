package tkhal.sender.controller;

import java.io.IOException;
import java.net.Socket;

/**
 * SocketCLient is a class for creating and running socket client
 */
public class SocketCLient {

    private Integer port = Integer.valueOf(System.getenv("PORT"));
    private String host = System.getenv("SERVER_HOST");
    private Socket socket;

    public SocketCLient() {
    }

    /**
     * runClient() method reads properties from file
     * and creating Socket Client
     */
    public Socket runClient() throws IOException {
        new SocketCLient();
        // socket connection
        socket = new Socket(host, port);
        return socket;
    }
}