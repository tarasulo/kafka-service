import model.Car;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Properties;

public class ServerTest {
    private static Integer port;
    private static String host;
    private static Car newCar = null;
    private static Socket socket;

    @BeforeClass
    public static void readServer() throws IOException, ClassNotFoundException, InterruptedException {

        RunServer runServer = new RunServer();
        runServer.start();
        Thread.sleep(1030);

        Properties socketProps = new Properties();
        try {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream input = classloader.getResourceAsStream("configSocket.properties");
            socketProps.load(input);
            port = Integer.parseInt(socketProps.getProperty("port"));
            host = socketProps.getProperty("host");
        } catch (IOException e) {
            System.out.println(e);
        }
        socket = new Socket(host, port);
        ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
        newCar = (Car) is.readObject();
    }

    @Test
    public void validCarEngine() {
        boolean actual = false;
        if (newCar.getEngine() >= 1.2 || newCar.getEngine() <= 3.5) {
            actual = true;
        }
        Assert.assertTrue(actual);
    }

    @Test
    public void validCarYear() {
        boolean actual = false;
        int year = newCar.getYear();
        if (year >= 1990 || year <= 2019) {
            actual = true;
        }
        Assert.assertTrue(actual);
    }

    @Test
    public void validCarModel() {
        boolean actual = false;
        String model = newCar.getModel();
        if (model.equals("cr-v") || model.equals("combo") || model.equals("x5")
                || model.equals("vito") || model.equals("q6")) {
            actual = true;
        }
        Assert.assertTrue(actual);
    }

    @Test
    public void validBrand() {
        boolean actual = false;
        String brand = newCar.getBrand();
        if (brand.equals("audi") || brand.equals("bmv") || brand.equals("mazda")
                || brand.equals("mercedes") || brand.equals("opel") || brand.equals("honda")) {
            actual = true;
        }
        Assert.assertTrue(actual);
    }

}
