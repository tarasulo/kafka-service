package serialize;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Car;
import org.apache.kafka.common.serialization.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class CarSerializer implements Serializer<Car> {

    private final static Logger logger = LoggerFactory.getLogger(CarSerializer.class);

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public byte[] serialize(String topic, Car car) {
        byte[] retVal = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            retVal = objectMapper.writeValueAsString(car).getBytes();
        } catch (Exception exception) {
            logger.error("Error in serializing object" + car + "   " + exception.toString());
        }
        return retVal;
    }

    @Override
    public void close() {

    }
}
