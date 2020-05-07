package by.epam.service;

import by.epam.dao.UserDao;
import by.epam.entities.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserService {

    private UserDao userDao;
    Logger log = LogManager.getLogger();

    public UserService() {
        userDao = new UserDao();
    }

    public ArrayList<User> readAll() throws SQLException {
        return userDao.readAll();
    }

    public void create(User user) {
        try {
            int newId = userDao.getMaxId() + 1;
            user.setId(newId);
            userDao.create(user);
        }catch (SQLException e){
            log.error(e.getMessage());
        }
    }

    public User readUser(int id) throws SQLException {
        return userDao.readUser(id);
    }

    public void update(int id, User newUser) {
        try {
            userDao.Update(id, newUser);
        } catch (SQLException e){
            log.error(e.getMessage());
        }
    }

    public void delete(int id, String Username, String Password) {
        try {
            User user = readUser(id);
            if (user.getUsername().equals(Username) && user.getPassword().equals(Password)) {
                userDao.delete(id);
                log.info("User deleted");
            } else {
                log.error("Wrong username or password");
                throw new IllegalArgumentException("");
            }
        }catch (SQLException e){
            log.error(e.getMessage());
        }
    }

    public User login(String username, String password) throws SQLException {
        ArrayList<User> users = userDao.readAll();
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        throw new IllegalArgumentException("Неверная комбинация логина и пароля.");
    }
}
