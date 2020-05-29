package com.epam.Shop.web;

import com.epam.Shop.entities.Cars;
import com.epam.Shop.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CarController {
    @Autowired
    public final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/getCars")
    public ResponseEntity<List<Cars>> getCars(){
        return ResponseEntity.ok(carService.getAll());
    }

    @PostMapping("/createCar")
    public ResponseEntity<Void> createCar(@RequestBody Cars cars){
        carService.createCar(cars);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/deleteCar{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Integer id){
        carService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
