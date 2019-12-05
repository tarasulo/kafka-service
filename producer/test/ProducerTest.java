import model.Car;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class ProducerTest {

    private static Car tempCar = null;

    @BeforeClass
    public static void runFactory() throws InterruptedException {
        String topicName = null;
        RunServer runServer = new RunServer();
        runServer.start();
        Thread.sleep(1030);

        RunNewCarsSender runNewCarsSender = new RunNewCarsSender();
        runNewCarsSender.start();

        Thread.sleep(2030);

        Properties props = new Properties();
        try {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream input = classloader.getResourceAsStream("configConsumer.properties");
            props.load(input);
            topicName = props.getProperty("topicName1");
        } catch (IOException e) {
            e.printStackTrace();
        }
        KafkaConsumer<String, Car> consumer = new KafkaConsumer<String, Car>(props);
        consumer.subscribe(Collections.singletonList(topicName));
        while(true) {
            ConsumerRecords<String, Car> messages = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, Car> message : messages) {
                try {
                    tempCar = message.value();
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (tempCar != null) {
                break;
            }
        }

    }

    @Test
    public void validCarEngine() {
        boolean actual = false;
        if (tempCar.getEngine() >= 1.2 || tempCar.getEngine() <= 3.5) {
            actual = true;
        }
        Assert.assertTrue(actual);
    }

    @Test
    public void validCarYear() {
        boolean actual = false;
        int year = tempCar.getYear();
        if (year >= 1990 || year <= 2019) {
            actual = true;
        }
        Assert.assertTrue(actual);
    }

    @Test
    public void validCarModel() {
        boolean actual = false;
        String model = tempCar.getModel();
        if (model.equals("cr-v") || model.equals("combo") || model.equals("x5")
                || model.equals("vito") || model.equals("q6")) {
            actual = true;
        }
        Assert.assertTrue(actual);
    }

    @Test
    public void validBrand() {
        boolean actual = false;
        String brand = tempCar.getBrand();
        if (brand.equals("audi") || brand.equals("bmv") || brand.equals("mazda")
                || brand.equals("mercedes") || brand.equals("opel") || brand.equals("honda")) {
            actual = true;
        }
        Assert.assertTrue(actual);
    }
}
