package tkhal.receiver.controller;

import model.Car;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CarStandardizer {
    private final Logger LOGGER = LoggerFactory.getLogger(CarStandardizer.class);

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
