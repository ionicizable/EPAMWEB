package by.epam.dao;

import by.epam.Connections;
import by.epam.entities.CarPart;
import by.epam.entities.Shop;
import by.epam.Utility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class ShopDao {
    Logger log = LogManager.getLogger();

    public ShopDao() {
    }

    public void create(Shop shop) {
        String insert_new = "INSERT INTO SHOPS VALUES(?,?,?,?,?,?)";
        try {
            Connection connection = Connections.get();
            PreparedStatement preparedStatement = connection.prepareStatement(insert_new);
            preparedStatement.setInt(1, shop.getId());
            preparedStatement.setString(2, shop.getName());
            preparedStatement.setString(3, shop.getAddress());
            preparedStatement.setString(4, shop.getContact());
            preparedStatement.setString(5, shop.getWorktime());
            preparedStatement.setString(6, shop.getDescription());
            preparedStatement.execute();
            log.info("Shop created succesfully");
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    public ArrayList<Shop> readAll() throws SQLException {
        try {
            Connection connection = Connections.get();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT ID, NAME, ADDRESS, CONTACT, WORKTIME, DESCRIPTION FROM SHOPS");
            ArrayList<Shop> shops = new ArrayList<>();
            while (resultSet.next()) {
                shops.add(shopFromResultSet(resultSet));
            }
            return shops;
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public int getMaxId() throws SQLException {
        ArrayList<Shop> shops = readAll();
        int result = 0;
        for (Shop shop : shops) {
            if (shop.getId() > result) {
                result = shop.getId();
            }
        }
        return result;
    }

    public void update(int id, Shop shop) {
        String update = "UPDATE SHOPS SET NAME = ?, ADDRESS = ?, CONTACT = ?, WORKTIME = ?, DESCRIPTION = ? WHERE ID = ?";
        try {
            Connection connection = Connections.get();
            PreparedStatement preparedStatement = connection.prepareStatement(update);
            preparedStatement.setString(1, shop.getName());
            preparedStatement.setString(2, shop.getAddress());
            preparedStatement.setString(3, shop.getContact());
            preparedStatement.setString(4, shop.getWorktime());
            preparedStatement.setString(5, shop.getDescription());
            preparedStatement.setInt(6, id);
            preparedStatement.execute();
            log.info("Shop updated succesfully");
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    public Shop readShop(int id) throws SQLException {
        ArrayList<Shop> shops = readAll();
        for (Shop shop : shops) {
            if (id == shop.getId()) {
                return shop;
            }
        }
        log.error("Error in readShop");
        throw new IllegalArgumentException(String.format("Id %d не найден", id));
    }

    public void delete(int id) {
        String delete = "DELETE FROM SHOPS WHERE ID = ?";
        try {
            Connection connection = Connections.get();
            PreparedStatement preparedStatement = connection.prepareStatement(delete);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            log.info("Shop deleted succesfully");
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    private Shop shopFromResultSet(ResultSet input) throws SQLException {
        return new Shop(
                input.getInt(1),
                input.getString(2),
                input.getString(3),
                input.getString(4),
                input.getString(5),
                input.getString(6));
    }
}
