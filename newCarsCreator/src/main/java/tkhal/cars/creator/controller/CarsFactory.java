package tkhal.cars.creator.controller;

import model.Car;
import servise.CarService;

public class CarsFactory {

    private CarService carService = new CarService();

    public Car createCar() {
        Car car = new Car();
        carService.makeCar(car);
        return car;
    }
}
