package by.epam.entities;

import by.epam.Utility;

public class CarPart {
    private int id;
    private String name;
    private String description;
    private String CarId;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getCarId() { return CarId; }

    public void setCarId(String carId) { CarId = carId; }

    public String toStringFile(){
        String temp = Utility.valueSeparator;
        return id + temp + name + temp + description + temp + CarId;
    }

    public CarPart(int id, String name, String description, String carId) {
        this.id = id;
        this.name = name;
        this.description = description;
        CarId = carId;
    }
}
