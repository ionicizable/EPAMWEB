package by.epam.dao;

import by.epam.entities.Car;
import by.epam.Utility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class CarDao {
    //private ObjectOutputStream outputStream;
    Logger log = LogManager.getLogger();

    public void create(Car car) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Utility.carData, true))) {
            writer.write(car.toStringFile());
            writer.newLine();
            log.info("Car Created");
        } catch (IOException ignored) {
            log.error("Creation error");
        }
    }

    public ArrayList<Car> readAll() {
        try (BufferedReader reader = new BufferedReader(new FileReader(Utility.carData))) {
            ArrayList<Car> cars = new ArrayList<>();
            String buffer;
            while ((buffer = reader.readLine()) != null) {
                cars.add(carTokenizer(buffer));
            }
            return cars;
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    public void writeAll(ArrayList<Car> cars) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(Utility.carData)))) {
            for (Car car : cars) {
                String temp = Utility.valueSeparator;
                writer.write(car.toStringFile());
                writer.write("\n");
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public int getMaxId() {
        ArrayList<Car> cars = readAll();
        int result = 0;
        for (Car car : cars) {
            if (car.getId() > result) {
                result = car.getId();
            }
        }
        return result;
    }

    public void update(int id, Car newCar) {
        ArrayList<Car> cars = readAll();
        for (Car car : cars) {
            if (id == car.getId()) {
                car.setBrand(newCar.getBrand());
                car.setModel(newCar.getModel());
                break;
            }
        }
        log.info("Update successful");
        writeAll(cars);
    }

    public Car readCar(int id) {
        ArrayList<Car> cars = readAll();
        for (Car car : cars) {
            if (id == car.getId()) {
                return car;
            }
        }
        log.error("Error in readCar");
        throw new IllegalArgumentException(String.format("Id %d не найден", id));
    }

    public void delete(int id) {
        ArrayList<Car> cars = readAll();
        for (Car car : cars) {
            if (id == car.getId()) {
                cars.remove(car);
                writeAll(cars);
                return;
            }
        }
        log.error("Error in delete");
        throw new IllegalArgumentException(String.format("Id %d не найден", id));
    }

    public Car carTokenizer(String buffer){
        StringTokenizer st = new StringTokenizer(buffer, Utility.valueSeparator);
        int id = Integer.parseInt(st.nextToken());
        String brand = st.nextToken();
        String model = st.nextToken();
        return new Car(id, brand, model);
    }
}
