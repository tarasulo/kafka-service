import model.Car;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class FilterTest {
    private static Car tempCar = null;
    private static Car tempCar2 = null;
    private static String topicName = null;
    private static String topicName2 = null;
    private static Car result = null;


    @Test
    public void correctFilter() throws InterruptedException {
        tempCar = new Car("bmw", "q6", 1999, 1.9);
        tempCar2 = new Car("bmw", "q6", 2010, 2.5);

        Properties props = new Properties();
        try {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream input = classloader.getResourceAsStream("configProducer.properties");
            props.load(input);
            topicName = props.getProperty("topicName1");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Producer<String, Car> producer = new KafkaProducer<String, Car>(props);
        try {
            producer.send(new ProducerRecord<String, Car>(topicName, tempCar));
            System.out.println("Created new car: " + tempCar.toString());
            producer.send(new ProducerRecord<String, Car>(topicName, tempCar2));
            System.out.println("Created new car: " + tempCar2.toString());
        } catch (Exception e) {
            System.out.println("Sending failed " + e.toString());
        }

        RunFilter runFilter = new RunFilter();
        runFilter.start();

        Thread.sleep(1030);
        Properties propsConsumer = new Properties();
        try {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream input = classloader.getResourceAsStream("configConsumer.properties");
            propsConsumer.load(input);
            topicName2 = propsConsumer.getProperty("topicName2");
        } catch (IOException e) {
            e.printStackTrace();
        }
        KafkaConsumer<String, Car> consumer = new KafkaConsumer<String, Car>(propsConsumer);
        consumer.subscribe(Collections.singletonList(topicName2));
        while (true) {
            ConsumerRecords<String, Car> messages = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, Car> message : messages) {
                try {
                    result = message.value();
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (result != null) {
                break;
            }
        }

        Assert.assertEquals(tempCar2.toString(), result.toString());
    }
}
