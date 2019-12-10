package tkhal.filter.controller;

import model.Car;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Taras Khalak
 */
public class CarsFilter {
    /**
     * This is the class for filtering cars
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(MessageFilterController.class);

    public Boolean filter(Car tempCar) {
        /**
         * This is the method which filtering cars
         * and @return true after success
         */
        if (tempCar.getEngine() > 2.0 & tempCar.getYear() > 2000) {
            return true;
        } else {
            LOGGER.info("Sorry, these {} does not meet the filtering requirements ", tempCar.toString());
            return false;
        }
    }
}
