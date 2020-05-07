package by.epam.service;

import by.epam.dao.CarDao;
import by.epam.entities.Car;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;

public class CarService {
    private CarDao carDao;
    Logger log = LogManager.getLogger();

    public CarService() {
        carDao = new CarDao();
    }

    public ArrayList<Car> readAll() throws SQLException {
        return carDao.readAll();
    }

    public void create(Car car) {
        try {
            int newId = carDao.getMaxId() + 1;
            car.setId(newId);
            carDao.create(car);
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    public void update(int id, Car newCar) {
        carDao.update(id, newCar);
    }

    public Car readCar(int id) throws SQLException {
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
        } catch (IllegalArgumentException | SQLException e) {
            log.error(e.getMessage());
        }
    }
}
