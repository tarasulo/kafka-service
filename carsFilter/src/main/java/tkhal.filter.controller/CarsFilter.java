package tkhal.filter.controller;

import model.Car;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CarsFilter {

    private final static Logger LOGGER = LoggerFactory.getLogger(MessageFilterController.class);

    public Car filter(Car tempCar) {
        // filtering car
        if (tempCar.getEngine() > 2.0 & tempCar.getYear() > 2000) {
            return tempCar;
        } else {
            LOGGER.info("Sorry, these {} does not meet the filtering requirements ", tempCar.toString());
            return null;
        }
    }
}
