package tkhal.receiver.controller;

import model.Car;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Taras Khalak
 */
public class CarStandardizer {
    /**
     * This is class for reading cars
     * and returning standardized cars
     */
    private final Logger LOGGER = LoggerFactory.getLogger(CarStandardizer.class);

    public Car changeCar(Car car) {
        /**
         * This is the method which
         * standardized car and @return car
         */

        // standardizing cars parameters
        car.setBrand(car.getBrand().toUpperCase());
        car.setModel(car.getModel().toUpperCase());
        LOGGER.info(" Hello Mates! we got new car: {} model: {} with engine {} from year - {} !",
                car.getBrand(), car.getModel(), car.getEngine(),
                car.getYear());
        return car;
    }
}
