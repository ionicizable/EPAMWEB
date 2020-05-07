package by.epam.dao;

import by.epam.entities.Car;
import by.epam.Utility;
import by.epam.entities.CarPart;
import by.epam.entities.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class CarDao {
    //private ObjectOutputStream outputStream;
    Logger log = LogManager.getLogger();
    Connection connection = null;

    public CarDao() {
        try {
            connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "PARTSHOP", "oracle");
            log.info("Connection succesfull");
        } catch (SQLException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }

    public void create(Car car) {
        String insert_new = "INSERT INTO CARS VALUES(?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insert_new);
            preparedStatement.setInt(1, car.getId());
            preparedStatement.setString(2, car.getBrand());
            preparedStatement.setString(3, car.getModel());
            preparedStatement.execute();
            log.info("Car created succesfully");
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    public ArrayList<Car> readAll() throws SQLException {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT ID, BRAND, MODEL FROM CARS");
            ArrayList<Car> cars = new ArrayList<>();
            while (resultSet.next()) {
                cars.add(carFromResultSet(resultSet));
            }
            return cars;
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public int getMaxId() throws SQLException {
        ArrayList<Car> cars = readAll();
        int result = 0;
        for (Car car : cars) {
            if (car.getId() > result) {
                result = car.getId();
            }
        }
        return result;
    }

    /*public void update(int id, Car newCar) throws SQLException {
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
    }*/

    public void update(int id, Car car) {
        String update = "UPDATE CARS SET BRAND = ?, MODEL = ? WHERE ID = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(update);
            preparedStatement.setString(1, car.getBrand());
            preparedStatement.setString(2, car.getModel());
            preparedStatement.setInt(3, car.getId());
            preparedStatement.execute();
            log.info("Car updated succesfully");
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    public Car readCar(int id) throws SQLException {
        ArrayList<Car> cars = readAll();
        for (Car car : cars) {
            if (id == car.getId()) {
                return car;
            }
        }
        log.error("Error in readCar");
        throw new IllegalArgumentException(String.format("Id %d не найден", id));
    }


    /*public void delete(int id) throws SQLException {
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
    }*/

    public void delete(int id) {
        String delete = "DELETE FROM CARS WHERE ID = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(delete);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            log.info("Car deleted succesfully");
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    /*public Car carTokenizer(String buffer){
        StringTokenizer st = new StringTokenizer(buffer, Utility.valueSeparator);
        int id = Integer.parseInt(st.nextToken());
        String brand = st.nextToken();
        String model = st.nextToken();
        return new Car(id, brand, model);
    }*/

    private Car carFromResultSet(ResultSet input) throws SQLException {
        return new Car(
                input.getInt(1),
                input.getString(2),
                input.getString(3));
    }
}
