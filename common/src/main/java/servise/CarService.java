package servise;

import model.Car;

import java.util.Random;

public class CarService extends Car {

    private String brand;
    private String model;
    private int year;
    private double engine;
    private Car car = new Car();
    private String brands[] = {"audi", "bmv", "mazda", "mercedes", "opel", "honda"};
    private String models[] = {"cr-v", "combo", "x5", "vito", "q6"};
    private int years[] = {1990, 1991, 1992, 1993, 1994, 1995, 1996, 1997, 1998, 1999,
            2000, 2001, 2002, 2003, 2004, 2005, 2006, 2007, 2008, 2009, 2010, 2011, 2012,
            2013, 2014, 2015, 2016, 2017, 2018, 2019};
    private double engines[] = {1.2, 1.7, 2.2, 3.0, 3.5};

    private static int randomForSwitch(int n) {
        Random randomGenerator = new Random();
        return randomGenerator.nextInt(n);
    }

    private String makeModel(int n) {
        model = models[randomForSwitch(n)];
        return model;
    }

    private String makeBrand(int n) {
        brand = brands[randomForSwitch(n)];
        return brand;
    }

    private int makeYear(int n) {
        year = years[randomForSwitch(n)];
        return year;
    }

    private double makeEngine(int n) {
        engine = engines[randomForSwitch(n)];
        return engine;
    }

    public Car makeCar(Car newCar) {
        newCar.setBrand(makeBrand(6));
        newCar.setModel(makeModel(5));
        newCar.setYear(makeYear(30));
        newCar.setEngine(makeEngine(5));
        return newCar;
    }
}
