package by.epam.service;

import by.epam.dao.CarPartDao;
import by.epam.entities.CarPart;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class CarPartService {

    private CarPartDao carPartDao;

    public CarPartService() {
       carPartDao = new CarPartDao();
    }

    public ArrayList<CarPart> ReadAll() {
        ArrayList<CarPart> carParts = carPartDao.readAll();
        return carParts;
    }

    public void Create(CarPart carPart) {
        int newId = carPartDao.getMaxId() + 1;
        carPart.setId(newId);
        carPartDao.create(carPart);
    }

    public void Update(int id, CarPart newCarPart) {
        carPartDao.Update(id, newCarPart);
    }

    public CarPart ReadCarPart(int id) {
        return carPartDao.readCarPart(id);
    }

    public void Delete(int id, String name) {
        Logger log = LogManager.getLogger();
        CarPart carPart = ReadCarPart(id);
        if(carPart.getName().equals(name)){
            carPartDao.delete(id);
            log.info("CarPart deleted");
        } else {
            log.error("Wrong control name");
            throw new IllegalArgumentException("");
        }
    }
}

