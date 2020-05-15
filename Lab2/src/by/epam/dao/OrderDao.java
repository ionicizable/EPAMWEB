package by.epam.dao;

import by.epam.Connections;
import by.epam.entities.Order;
import by.epam.entities.OrderData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;

public class OrderDao {
    Logger log = LogManager.getLogger();

    public OrderDao() {
    }

    public void create(Order order) {
        String insert_new = "INSERT INTO ORDERS VALUES(?,?,?,?,?)";
        try {
            Connection connection = Connections.get();
            java.sql.Date sqlDate = new java.sql.Date(order.getDate().getTime());
            PreparedStatement preparedStatement = connection.prepareStatement(insert_new);
            preparedStatement.setInt(1, order.getId());
            preparedStatement.setDate(2, sqlDate);
            preparedStatement.setInt(3, order.getCarPartId());
            preparedStatement.setInt(4, order.getShopId());
            preparedStatement.setInt(5, order.getUserId());
            preparedStatement.execute();
            log.info("Order created succesfully");
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    public ArrayList<OrderData> readAll() throws SQLException {
        try {
            Connection connection = Connections.get();
            Statement statement = connection.createStatement();
            ResultSet resultSet =
                    statement.executeQuery("SELECT ID, ORDERDATE, CARPARTID, SHOPID, USERID FROM ORDERS");
            ArrayList<OrderData> orders = new ArrayList<>();
            while (resultSet.next()) {
                orders.add(orderFromResultSet(resultSet));
            }
            return orders;
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public int getMaxId() throws SQLException {
        ArrayList<OrderData> orders = readAll();
        int result = 0;
        for (OrderData order : orders) {
            if (order.getId() > result) {
                result = order.getId();
            }
        }
        return result;
    }

    public OrderData readOrder(int id) throws SQLException {
        ArrayList<OrderData> orders = readAll();
        for (OrderData order : orders) {
            if (id == order.getId()) {
                return order;
            }
        }
        throw new IllegalArgumentException(String.format("Id %d не найден", id));
    }

    public void delete(int id) {
        String update = "DELETE FROM ORDERS WHERE ID = ?";
        try {
            Connection connection = Connections.get();
            PreparedStatement preparedStatement = connection.prepareStatement(update);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            log.info("Order deleted succesfully");
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    private OrderData orderFromResultSet(ResultSet input) throws SQLException {
        return new OrderData(
                input.getInt(1),
                input.getInt(3),
                input.getInt(4),
                input.getInt(5),
                input.getDate(2));
    }
}
