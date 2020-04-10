package by.epam.service;

import by.epam.dao.UserDao;
import by.epam.entities.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class UserService {

    private UserDao userDao;
    Logger log = LogManager.getLogger();

    public UserService() {
        userDao = new UserDao();
    }

    public ArrayList<User> readAll() {
        return userDao.readAll();
    }

    public void create(User user) {
        int newId = userDao.getMaxId() + 1;
        user.setId(newId);
        userDao.create(user);
    }

    public User readUser(int id) {
        return userDao.readUser(id);
    }

    public void update(int id, User newUser) {
        userDao.Update(id, newUser);
    }

    public void delete(int id, String Username, String Password) {
        User user = readUser(id);
        if (user.getUsername().equals(Username) && user.getPassword().equals(Password)) {
            userDao.delete(id);
            log.info("User deleted");
        } else {
            log.error("Wrong username or password");
            throw new IllegalArgumentException("");
        }
    }

    public User login(String username, String password) {
        ArrayList<User> users = userDao.readAll();
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        throw new IllegalArgumentException("Неверная комбинация логина и пароля.");
    }
}
