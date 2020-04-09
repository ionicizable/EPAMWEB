package by.epam.dao;

import by.epam.Utility;
import by.epam.entities.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class UserDao {
    private ObjectOutputStream outputStream;
    Logger log = LogManager.getLogger();

    public void create(User user) {
        Logger log = LogManager.getLogger();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Utility.userData, true))) {
            writer.write(user.toStringFile());
            writer.newLine();
            log.info("User Created");
        } catch (IOException ignored) {
            log.error("Creation error");
        }
    }

    public ArrayList<User> readAll() {
        try (BufferedReader reader = new BufferedReader(new FileReader(Utility.userData))) {
            ArrayList<User> users = new ArrayList<>();
            String buffer;
            while ((buffer = reader.readLine()) != null) {
                users.add(userTokenizer(buffer));
            }
            return users;
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return null;
    }


    public void writeAll(ArrayList<User> users) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(Utility.userData)))) {
            for (User user : users) {
                writer.write(user.toStringFile());
                writer.write("\n");
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public int getMaxId() {
        ArrayList<User> users = readAll();
        int result = 0;
        for (User user : users) {
            if (user.getId() > result) {
                result = user.getId();
            }
        }
        return result;
    }

    public void Update(int id, User newUser) {
        ArrayList<User> users = readAll();
        for (User user : users) {
            if (id == user.getId()) {
                user.setisAdmin(newUser.getisAdmin());
                user.setPassword(newUser.getPassword());
                user.setUsername(newUser.getUsername());
                break;
            }
        }
        writeAll(users);
    }

    public User readUser(int id) {
        ArrayList<User> users = readAll();
        for (User user : users) {
            if (id == user.getId()) {
                return user;
            }
        }
        throw new IllegalArgumentException(String.format("Id %d не найден", id));
    }

    public void delete(int id) {
        ArrayList<User> users = readAll();
        for (User user : users) {
            if (id == user.getId()) {
                users.remove(user);
                writeAll(users);
                return;
            }
        }
        throw new IllegalArgumentException(String.format("Id %d не найден", id));
    }

    public User userTokenizer(String buffer){
        StringTokenizer st = new StringTokenizer(buffer, "-");
        int id = Integer.parseInt(st.nextToken());
        boolean isAdmin = Boolean.parseBoolean(st.nextToken());
        String username = st.nextToken();
        String password = st.nextToken();
        return new User(id, isAdmin,username,password);
    }
}