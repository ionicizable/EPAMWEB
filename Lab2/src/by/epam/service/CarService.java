package by.epam.service;

import by.epam.dao.CarDao;
import by.epam.entities.Car;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class CarService {
    private CarDao carDao;

    public CarService() {
        carDao = new CarDao();
    }

    public ArrayList<Car> readAll() {
        ArrayList<Car> cars = carDao.readAll();
        return cars;
    }

    public void create(Car car) {
        int newId = carDao.getMaxId() + 1;
        car.setId(newId);
        carDao.create(car);
    }

    public void update(int id, Car newCar) {
        carDao.update(id, newCar);
    }

    public Car readCar(int id) {
        return carDao.readCar(id);
    }

    public void delete(int id, String brand) {
        try {
            Logger log = LogManager.getLogger();
            Car car = readCar(id);
            if (car.getBrand().equals(brand)) {
                carDao.delete(id);
                log.info("Car deleted");
            } else {
                System.out.println("Wrong control name");
            }
        } catch (IllegalArgumentException ignored) {
        }
    }
}
