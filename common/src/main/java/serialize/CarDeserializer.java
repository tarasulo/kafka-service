package serialize;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Car;
import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class CarDeserializer implements Deserializer<Car> {

    private final static Logger logger = LoggerFactory.getLogger(CarSerializer.class);

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public Car deserialize(String topic, byte[] car) {
        ObjectMapper mapper = new ObjectMapper();
        Car newcar = null;
        try {
            newcar = mapper.readValue(car, Car.class);
        } catch (Exception exception) {
            logger.error("Error in deserializing {} bytes {} .", car, exception);
        }
        return newcar;
    }

    @Override
    public void close() {
    }
}
