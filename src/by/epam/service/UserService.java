package by.epam.service;

import by.epam.dao.UserDao;
import by.epam.entities.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class UserService {

    private UserDao userDao;

    public UserService() {
        userDao = new UserDao();
    }

    public ArrayList<User> ReadAll() {
        ArrayList<User> users = userDao.readAll();
        return users;
    }

    public void Create(User user) {
        int newId = userDao.getMaxId() + 1;
        user.setId(newId);
        userDao.create(user);
    }

    public void Update(int id, User newUser) {
        userDao.Update(id, newUser);
    }

    public User ReadUser(int id) {
        return userDao.readUser(id);
    }

    public void Delete(int id, String Username, String Password ) {
        Logger log = LogManager.getLogger();
        User user = ReadUser(id);
        if(user.getUsername().equals(Username) && user.getPassword().equals(Password)){
            userDao.delete(id);
            log.info("User deleted");
        } else {
            log.error("Wrong username or password");
            throw new IllegalArgumentException("");
        }
    }
}

