package service;

import dao.CarDao;
import model.FinalCar;

public class FinalCarService {

    private CarDao carDao = new CarDao();

    public FinalCarService() {
    }

    public FinalCar add(FinalCar finalCar) {
        return carDao.add(finalCar);
    }

    public FinalCar update(FinalCar finalCar) {
        return carDao.update(finalCar);
    }

    public FinalCar findCar(Long id) {
        return carDao.get(id);
    }

    public void deleteCar(FinalCar finalCar) {
        carDao.delete(finalCar);
    }
}
