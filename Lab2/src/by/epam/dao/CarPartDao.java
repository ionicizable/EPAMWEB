package by.epam.dao;

import by.epam.Connections;
import by.epam.entities.CarPart;
import by.epam.Utility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class CarPartDao {
    //private ObjectOutputStream outputStream;
    Logger log = LogManager.getLogger();

    public CarPartDao() {
    }

    public void create(CarPart carPart) {
        String insert_new = "INSERT INTO CARPARTS VALUES(?,?,?,?)";
        try {
            Connection connection = Connections.get();
            PreparedStatement preparedStatement = connection.prepareStatement(insert_new);
            preparedStatement.setInt(1, carPart.getId());
            preparedStatement.setString(2, carPart.getName());
            preparedStatement.setString(3, carPart.getDescription());
            preparedStatement.setString(4, carPart.getCarId());
            preparedStatement.execute();
            log.info("Part created succesfully");
            Connections.putBack(connection);
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    public ArrayList<CarPart> readAll() throws SQLException {
        try {
            Connection connection = Connections.get();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT ID, NAME, DESCRIPTION, CARID FROM CARPARTS");
            ArrayList<CarPart> carParts = new ArrayList<>();
            while (resultSet.next()) {
                carParts.add(carPartFromResultSet(resultSet));
            }
            Connections.putBack(connection);
            return carParts;
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public int getMaxId() throws SQLException {
        ArrayList<CarPart> carParts = readAll();
        int result = 0;
        for (CarPart carPart : carParts) {
            if (carPart.getId() > result) {
                result = carPart.getId();
            }
        }
        return result;
    }

    public void update(int id, CarPart carPart) {
        String update = "UPDATE CARPARTS SET NAME = ?, DESCRIPTION = ?, CARID=? WHERE ID = ?";
        try {
            Connection connection = Connections.get();
            PreparedStatement preparedStatement = connection.prepareStatement(update);
            preparedStatement.setString(1, carPart.getName());
            preparedStatement.setString(2, carPart.getDescription());
            preparedStatement.setString(3, carPart.getCarId());
            preparedStatement.setInt(4, id);
            preparedStatement.execute();
            log.info("Part updated succesfully");
            Connections.putBack(connection);
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    public CarPart readCarPart(int id) throws SQLException {
        ArrayList<CarPart> carParts = readAll();
        for (CarPart carPart : carParts) {
            if (id == carPart.getId()) {
                return carPart;
            }
        }
        log.error("Wrong id");
        throw new IllegalArgumentException(String.format("Id %d не найден", id));
    }

    public void delete(int id) {
        String update = "DELETE FROM CARPARTS WHERE ID = ?";
        try {
            Connection connection = Connections.get();
            PreparedStatement preparedStatement = connection.prepareStatement(update);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            log.info("Part deleted succesfully");
            Connections.putBack(connection);
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    public CarPart carPartTokenizer(String buffer) {
        StringTokenizer st = new StringTokenizer(buffer, Utility.valueSeparator);
        int id = Integer.parseInt(st.nextToken());
        String name = st.nextToken();
        String description = st.nextToken();
        String CarId = st.nextToken();
        return new CarPart(id, name, description, CarId);
    }

    private CarPart carPartFromResultSet(ResultSet input) throws SQLException {
        return new CarPart(
                input.getInt(1),
                input.getString(2),
                input.getString(3),
                input.getString(4));
    }

}
