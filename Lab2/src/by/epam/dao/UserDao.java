package by.epam.dao;

import by.epam.Connections;
import by.epam.entities.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;

public class UserDao {
    Logger log = LogManager.getLogger();

    public UserDao() {
    }

    public void create(User user) {
        String insert_new = "INSERT INTO USERS VALUES(?,?,?,?)";
        try {
            Connection connection = Connections.get();
            PreparedStatement preparedStatement = connection.prepareStatement(insert_new);
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setBoolean(2, user.getisAdmin());
            preparedStatement.setString(3, user.getUsername());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.execute();
            log.info("User created succesfully");
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    public ArrayList<User> readAll() throws SQLException {
        try {
            Connection connection = Connections.get();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT ID, ADMIN, USERNAME, PASSWORD FROM USERS");
            ArrayList<User> users = new ArrayList<>();
            while (resultSet.next()) {
                users.add(userFromResultSet(resultSet));
            }
            return users;
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public int getMaxId() throws SQLException {
        ArrayList<User> users = readAll();
        int result = 0;
        for (User user : users) {
            if (user.getId() > result) {
                result = user.getId();
            }
        }
        return result;
    }

    public void Update(int id, User user) throws SQLException {
        String update = "UPDATE USERS SET ADMIN = ?, USERNAME = ?, PASSWORD = ? WHERE ID = ?";
        try {
            Connection connection = Connections.get();
            PreparedStatement preparedStatement = connection.prepareStatement(update);
            preparedStatement.setBoolean(1, user.getisAdmin());
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setInt(4, id);
            preparedStatement.execute();
            log.info("User updated succesfully");
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    public User readUser(int id) throws SQLException {
        ArrayList<User> users = readAll();
        for (User user : users) {
            if (id == user.getId()) {
                return user;
            }
        }
        throw new IllegalArgumentException(String.format("Id %d не найден", id));
    }

    public void delete(int id) {
        String delete = "DELETE FROM USERS WHERE ID = ?";
        try {
            Connection connection = Connections.get();
            PreparedStatement preparedStatement = connection.prepareStatement(delete);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            log.info("User deleted succesfully");
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    private User userFromResultSet(ResultSet input) throws SQLException {
        return new User(
                input.getInt(1),
                Boolean.parseBoolean(input.getString(2)),
                input.getString(3),
                input.getString(4));
    }
}