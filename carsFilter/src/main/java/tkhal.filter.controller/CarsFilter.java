package tkhal.filter.controller;

import model.Car;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CarsFilter class for filtering cars
 */
public class CarsFilter {

    private final static Logger LOGGER = LoggerFactory.getLogger(MessageFilterController.class);

    /**
     * This is the method which filtering cars
     * and @return true after success
     */
    public Boolean filter(Car tempCar) {
        if (tempCar.getEngine() > 2.0 & tempCar.getYear() > 2000) {
            return true;
        } else {
            LOGGER.info("Sorry, these {} does not meet the filtering requirements ", tempCar.toString());
            return false;
        }
    }
}
