package by.epam.dao;

import by.epam.entities.Order;
import by.epam.entities.OrderData;
import by.epam.utility;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class OrderDao {
    private ObjectOutputStream outputStream;

    public void create(Order order) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/Order.txt", true))) {
            writer.write(order.toStringFile());
            writer.newLine();
        } catch (IOException ignored) {
        }
    }

    public ArrayList<OrderData> readAll() {
        try (BufferedReader reader = new BufferedReader(new FileReader("data/Order.txt"))) {
            ArrayList<OrderData> orders = new ArrayList<>();
            String buffer;
            while ((buffer = reader.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(buffer, utility.valueSeparator);
                int id = Integer.parseInt(st.nextToken());
                int carPartId = Integer.parseInt(st.nextToken());
                int shopId = Integer.parseInt(st.nextToken());
                String date = st.nextToken();
                orders.add(new OrderData(id, carPartId, shopId, date));
            }
            return orders;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteByName(String name, ArrayList<Order> orders) {
        // TODO: 19.03.2020 create delete method

    }

    public void writeAll(ArrayList<OrderData> orders) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File("data", "Order.txt")))) {
            for (OrderData order : orders) {
                writer.write(order.toStringFile());
                writer.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getMaxId() {
        ArrayList<OrderData> orders = readAll();
        int result = 0;
        for (OrderData order : orders) {
            if (order.getId() > result) {
                result = order.getId();
            }
        }
        return result;
    }

    public OrderData readOrder(int id) {
        ArrayList<OrderData> orders = readAll();
        for (OrderData order : orders) {
            if (id == order.getId()) {
                return order;
            }
        }
        throw new IllegalArgumentException(String.format("Id %d не найден", id));
    }

    public void delete(int id) {
        ArrayList<OrderData> orders = readAll();
        for (OrderData order : orders) {
            if (id == order.getId()) {
                orders.remove(order);
                writeAll(orders);
                return;
            }
        }
        throw new IllegalArgumentException(String.format("Id %d не найден", id));
    }
}
