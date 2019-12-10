package tkhal.receiver.controller;

import model.Car;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CarStandardizer is a class for reading cars
 * and returning standardized cars
 */
public class CarStandardizer {
    private final Logger LOGGER = LoggerFactory.getLogger(CarStandardizer.class);

    /**
     * This is the method which
     * standardized car
     * and @return car
     */
    public Car changeCar(Car car) {
        // standardizing cars parameters
        car.setBrand(car.getBrand().toUpperCase());
        car.setModel(car.getModel().toUpperCase());
        LOGGER.info(" Hello Mates! we got new car: {} model: {} with engine {} from year - {} !",
                car.getBrand(), car.getModel(), car.getEngine(),
                car.getYear());
        return car;
    }
}
