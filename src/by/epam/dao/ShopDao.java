package by.epam.dao;

import by.epam.entities.Shop;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class ShopDao {
    private ObjectOutputStream outputStream;

    public void create(Shop shop) {
        Logger log = LogManager.getLogger();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/Shop.txt", true))) {
            writer.write(shop.toStringFile());
            writer.newLine();
            log.info("Shop Created");
        } catch (IOException ignored) {
            log.error("Creation error");
        }
    }

    public ArrayList<Shop> readAll() {
        try (BufferedReader reader = new BufferedReader(new FileReader("data/Shop.txt"))) {
            ArrayList<Shop> shops = new ArrayList<>();
            String buffer;
            while ((buffer = reader.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(buffer, "-");
                int id = Integer.parseInt(st.nextToken());
                String name = st.nextToken();
                String address = st.nextToken();
                String contact = st.nextToken();
                String worktime = st.nextToken();
                String description = st.nextToken();
                shops.add(new Shop(id, name, address, contact, worktime, description));
            }
            return shops;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteByName(String name, ArrayList<Shop> shops) {
        // TODO: 19.03.2020 create delete method

    }

    public void writeAll(ArrayList<Shop> shops) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File("data", "Shop.txt")))) {
            for (Shop shop : shops) {
                String temp = "-";
                writer.write(Integer.toString(shop.getId()));
                writer.write(temp);
                writer.write(shop.getName());
                writer.write(temp);
                writer.write(shop.getAddress());
                writer.write(temp);
                writer.write(shop.getContact());
                writer.write(temp);
                writer.write(shop.getWorktime());
                writer.write(temp);
                writer.write(shop.getDescription());
                writer.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getMaxId() {
        ArrayList<Shop> shops = readAll();
        int result = 0;
        for (Shop shop : shops) {
            if (shop.getId() > result) {
                result = shop.getId();
            }
        }
        return result;
    }

    public void Update(int id, Shop newShop) {
        ArrayList<Shop> shops = readAll();
        for (Shop shop : shops) {
            if (id == shop.getId()) {
                shop.setName(newShop.getName());
                shop.setAddress(newShop.getAddress());
                shop.setContact(newShop.getContact());
                shop.setWorktime(newShop.getWorktime());
                shop.setDescription(newShop.getDescription());
                break;
            }
        }
        writeAll(shops);
    }

    public Shop readShop(int id) {
        ArrayList<Shop> shops = readAll();
        for (Shop shop : shops) {
            if (id == shop.getId()) {
                return shop;
            }
        }
        throw new IllegalArgumentException(String.format("Id %d не найден", id));
    }

    public void delete(int id) {
        ArrayList<Shop> shops = readAll();
        for (Shop shop : shops) {
            if (id == shop.getId()) {
                shops.remove(shop);
                writeAll(shops);
                return;
            }
        }
        throw new IllegalArgumentException(String.format("Id %d не найден", id));
    }
}
