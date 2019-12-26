package tkhal.cars.creator.controller;

import model.Car;
import servise.CarService;

/**
 * CarsFactory is a class for creating new cars
 */
public class CarsFactory {

    private CarService carService = new CarService();
    /**
     * createCar() is a method creates
     * and @return new car
     */
    public Car createCar() {
        Car car = new Car();
        carService.makeCar(car);
        return car;
    }
}
