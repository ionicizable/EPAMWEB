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

    public void create(CarPart carPart) {
        int newId = carPartDao.getMaxId() + 1;
        carPart.setId(newId);
        carPartDao.create(carPart);
    }

    public void update(int id, CarPart newCarPart) {
        carPartDao.update(id, newCarPart);
    }

    public CarPart readCarPart(int id) {
        return carPartDao.readCarPart(id);
    }

    public void delete(int id, String name) {
        try {
            CarPart carPart = readCarPart(id);
            if (carPart.getName().equals(name)) {
                carPartDao.delete(id);
            }
        }catch (Exception e){
            System.out.println("Error");
        }
    }
}

