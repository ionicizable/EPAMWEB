package com.epam.Shop.service;

import com.epam.Shop.entities.Cars;
import com.epam.Shop.repo.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {
    @Autowired
    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Cars> getAll() {
        return carRepository.findAll();
    }

    public void createCar(Cars cars){
        List<Cars> carsList = getAll();
        int result = 0;
        for (Cars car : carsList) {
            if (car.getId() > result) {
                result = car.getId();
            }
        }
        cars.setId(result+1);
        carRepository.saveAndFlush(cars);
    }

    public void deleteCar(Cars car){
        carRepository.delete(car);
    }

    public void deleteById(Integer id){
        carRepository.deleteById(id);
    }

}
