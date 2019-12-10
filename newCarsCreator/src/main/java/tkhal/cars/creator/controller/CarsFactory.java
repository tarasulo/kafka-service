package tkhal.cars.creator.controller;

import model.Car;
import servise.CarService;
/**
 * @author Taras Khalak
 */
public class CarsFactory {
    /**
     * This is a class for creating new cars
     */
    private CarService carService = new CarService();

    public Car createCar() {
        /**
         * This method creates new cars
         */
        Car car = new Car();
        carService.makeCar(car);
        return car;
    }
}
