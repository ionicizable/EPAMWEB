package by.epam.dao;

import by.epam.entities.Shop;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class ShopController {
    private ObjectOutputStream outputStream;

    public void create(Shop shop) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/Shop.txt", true))) {
            writer.write(shop.toStringFile());
            writer.newLine();
        } catch (IOException ignored) {
        }
    }

    public ArrayList<Shop> readAll() {
        try (BufferedReader reader = new BufferedReader(new FileReader("data/Shop.txt"))) {
            ArrayList<Shop> shops = new ArrayList<>();
            String buffer;
            while ((buffer = reader.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(buffer, "-");
                String name = st.nextToken();
                String address = st.nextToken();
                String contact = st.nextToken();
                String worktime = st.nextToken();
                String description = st.nextToken();
                shops.add(new Shop(name, address, contact, worktime, description));
            }
            return shops;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteByName(String name){
        // TODO: 19.03.2020 create delete method
    }

    public void writeAll(ArrayList<Shop> shops) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File("data","Shop.txt")))) {
            for (Shop shop : shops) {
                String temp = "-";
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

}
