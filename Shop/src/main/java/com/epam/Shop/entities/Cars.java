package com.epam.Shop.entities;

import javax.persistence.Entity;

import javax.persistence.Id;

@Entity
public class Cars {
    @Id
    private int id;
    private String brand;
    private String model;

    public Cars(int id, String brand, String model) {
        this.id = id;
        this.brand = brand;
        this.model = model;
    }

    public Cars() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String toStringFile() {
        String temp = "-";
        return id + temp + brand + temp + model;
    }

}
