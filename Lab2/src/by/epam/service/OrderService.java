package by.epam.service;

import by.epam.dao.OrderDao;
import by.epam.entities.Order;
import by.epam.entities.OrderData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class OrderService {

    private OrderDao orderDao;
    private ShopService shopService;
    private CarPartService carPartService;
    private UserService userService;

    public OrderService(ShopService shopService, CarPartService carPartService, UserService userService) {
        orderDao = new OrderDao();
        this.shopService = shopService;
        this.carPartService = carPartService;
        this.userService = userService;
    }

    public ArrayList<Order> readAll() throws ParseException, SQLException {
        ArrayList<OrderData> orders = orderDao.readAll();
        ArrayList<Order> output = new ArrayList<>();
        for (OrderData o : orders
        ) {
            output.add(fill(o));
        }
        return output;
    }

    public void create(Order order) throws SQLException {
        int newId = orderDao.getMaxId() + 1;
        order.setId(newId);
        LocalDateTime now = LocalDateTime.now();
        order.setDate(java.sql.Timestamp.valueOf(now));
        orderDao.create(order);
    }

    public Order readOrder(int id) throws ParseException, SQLException {
        return fill(orderDao.readOrder(id));
    }

    public void delete(int id) throws ParseException, SQLException {
        Logger log = LogManager.getLogger();
        Order order = readOrder(id);
        orderDao.delete(id);
        log.info("Order deleted");
    }

    private Order fill(OrderData input) throws ParseException, SQLException {
        Order order = new Order(input.getId(), input.getCarPartId(), input.getShopId(), input.getUserId(), input.getDate());
        order.setShop(shopService.readShop(input.getShopId()));
        order.setCarPart(carPartService.readCarPart(input.getCarPartId()));
        order.setUser(userService.readUser(input.getUserId()));
        return order;
    }
}
