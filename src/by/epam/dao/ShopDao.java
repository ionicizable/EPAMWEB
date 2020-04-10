package by.epam.dao;

import by.epam.entities.Shop;
import by.epam.Utility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class ShopDao {
    //private ObjectOutputStream outputStream;
    Logger log = LogManager.getLogger();

    public void create(Shop shop) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Utility.shopData, true))) {
            writer.write(shop.toStringFile());
            writer.newLine();
            log.info("Shop Created");
        } catch (IOException ignored) {
            log.error("Creation error");
        }
    }

    public ArrayList<Shop> readAll() {
        try (BufferedReader reader = new BufferedReader(new FileReader(Utility.shopData))) {
            ArrayList<Shop> shops = new ArrayList<>();
            String buffer;
            while ((buffer = reader.readLine()) != null) {
                shops.add(shopTokenizer(buffer));
            }
            return shops;
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    public void writeAll(ArrayList<Shop> shops) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(Utility.shopData)))) {
            for (Shop shop : shops) {
                String temp = Utility.valueSeparator;
                writer.write(shop.toStringFile());
                writer.write("\n");
            }
        } catch (IOException e) {
            log.error(e.getMessage());
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

    public void update(int id, Shop newShop) {
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
        log.info("Update successful");
        writeAll(shops);
    }

    public Shop readShop(int id) {
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
        ArrayList<Shop> shops = readAll();
        for (Shop shop : shops) {
            if (id == shop.getId()) {
                shops.remove(shop);
                writeAll(shops);
                return;
            }
        }
        log.error("Error in delete");
        throw new IllegalArgumentException(String.format("Id %d не найден", id));
    }

    public Shop shopTokenizer(String buffer){
        StringTokenizer st = new StringTokenizer(buffer, Utility.valueSeparator);
        int id = Integer.parseInt(st.nextToken());
        String name = st.nextToken();
        String address = st.nextToken();
        String contact = st.nextToken();
        String worktime = st.nextToken();
        String description = st.nextToken();
        return new Shop(id, name, address, contact, worktime, description);
    }
}
