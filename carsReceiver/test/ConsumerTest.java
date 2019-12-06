import dao.CarDaoJdbcImpl;
import model.Car;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConsumerTest {
    private static Car tempCar = null;
    private static Car result = null;
    private static String topicName = null;
    private static Connection connection;

    @Test
    public void standardizer() throws InterruptedException, SQLException {
        tempCar = new Car("bmw", "q6", 2019, 3.5);
        Properties props = new Properties();
        try {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream input = classloader.getResourceAsStream("configProducer.properties");
            props.load(input);
            topicName = props.getProperty("topicName2");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Producer<String, Car> producer = new KafkaProducer<String, Car>(props);
        try {
            producer.send(new ProducerRecord<String, Car>(topicName, tempCar));
        } catch (Exception e) {
            System.out.println("Sending failed " + e.toString());
        }

        RunCarStendardizerController runCarStendardizerController = new RunCarStendardizerController();
        runCarStendardizerController.start();
        Thread.sleep(2030);

        Properties propsJdbc = new Properties();

        try {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream input = classloader.getResourceAsStream("configJdbc.properties");
            propsJdbc.load(input);
            Class.forName(propsJdbc.getProperty("jdbc.driver"));
            connection =
                    DriverManager.getConnection(propsJdbc.getProperty("db.url")
                            + propsJdbc.getProperty("credentials"));

            CarDaoJdbcImpl carDaoJdbc = new CarDaoJdbcImpl(connection);
            result = carDaoJdbc.getBylastId();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Assert.assertEquals(tempCar.getBrand().toUpperCase(), result.getBrand().toUpperCase());
        Assert.assertEquals(tempCar.getModel().toUpperCase(), result.getModel().toUpperCase());
    }
}
