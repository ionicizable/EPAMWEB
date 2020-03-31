package by.epam.dao;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

import by.epam.entities.Admin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AdminDao {
    private ObjectOutputStream outputStream;

    public void create(Admin admin) {
        Logger log = LogManager.getLogger();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/Admin.txt", true))) {
            writer.write(admin.toStringFile());
            writer.newLine();
            log.info("Admin Created");
        } catch (IOException ignored) {
            log.error("Creation error");
        }
    }

    public ArrayList<Admin> readAll() {
        try (BufferedReader reader = new BufferedReader(new FileReader("data/Admin.txt"))) {
            ArrayList<Admin> admins = new ArrayList<>();
            String buffer;
            while ((buffer = reader.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(buffer, "-");
                int id = Integer.parseInt(st.nextToken());
                String email = st.nextToken();
                String password = st.nextToken();
                admins.add(new Admin(id, email, password));
            }
            return admins;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void writeAll(ArrayList<Admin> admins) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File("data", "Admin.txt")))) {
            for (Admin admin : admins) {
                String temp = "-";
                writer.write(Integer.toString(admin.getId()));
                writer.write(temp);
                writer.write(admin.getEmail());
                writer.write(temp);
                writer.write(admin.getPassword());
                writer.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getMaxId() {
        ArrayList<Admin> admins = readAll();
        int result = 0;
        for (Admin admin : admins) {
            if (admin.getId() > result) {
                result = admin.getId();
            }
        }
        return result;
    }

    public void Update(int id, Admin newAdmin) {
        ArrayList<Admin> admins = readAll();
        for (Admin admin : admins) {
            if (id == admin.getId()) {
                admin.setEmail(newAdmin.getEmail());
                admin.setPassword(newAdmin.getPassword());
                break;
            }
        }
        writeAll(admins);
    }

    public Admin readAdmin(int id) {
        ArrayList<Admin> admins = readAll();
        for (Admin admin : admins) {
            if (id == admin.getId()) {
                return admin;
            }
        }
        throw new IllegalArgumentException(String.format("Id %d не найден", id));
    }

    public void delete(int id) {
        ArrayList<Admin> admins = readAll();
        for (Admin admin : admins) {
            if (id == admin.getId()) {
                admins.remove(admin);
                writeAll(admins);
                return;
            }
        }
        throw new IllegalArgumentException(String.format("Id %d не найден", id));
    }
}
