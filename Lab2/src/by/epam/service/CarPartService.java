package by.epam.service;

import by.epam.dao.CarPartDao;
import by.epam.entities.CarPart;

import java.util.ArrayList;

public class CarPartService {

    private CarPartDao carPartDao;

    public CarPartService() {
       carPartDao = new CarPartDao();
    }

    public ArrayList<CarPart> readAll() {
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
        CarPart carPart = ReadCarPart(id);
        if(carPart.getName().equals(name)){
            carPartDao.delete(id);
        } else {
            throw new IllegalArgumentException("Контрольное имя неверно");
        }
    }
}

