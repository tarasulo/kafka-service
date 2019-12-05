import server.Server;

public class RunServer extends Thread {
    @Override
    public void run() {
        Server server = new Server();
        server.runServer();
    }
}
