package by.epam.service;

import by.epam.dao.OrderDao;
import by.epam.entities.Order;
import by.epam.entities.OrderData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class OrderService {

    private OrderDao orderDao;
    private ShopService shopService;
    private CarPartService carPartService;

    public OrderService(ShopService shopService, CarPartService carPartService) {
        orderDao = new OrderDao();
        this.shopService = shopService;
        this.carPartService = carPartService;
    }

    public ArrayList<Order> readAll() throws ParseException {
        ArrayList<OrderData> orders = orderDao.readAll();
        ArrayList<Order> output = new ArrayList<>();
        for (OrderData o : orders
        ) {
            output.add(fill(o));
        }
        return output;
    }

    public void create(Order order) {
        int newId = orderDao.getMaxId() + 1;
        order.setId(newId);
        LocalDateTime now = LocalDateTime.now();
        order.setDate(java.sql.Timestamp.valueOf(now));
        orderDao.create(order);
    }

    public Order readOrder(int id) throws ParseException {
        return fill(orderDao.readOrder(id));
    }

    public void delete(int id) throws ParseException {
        Logger log = LogManager.getLogger();
        Order order = readOrder(id);
        orderDao.delete(id);
        log.info("Order deleted");
    }

    private Order fill(OrderData input) throws ParseException {
        Order order = new Order(input.getId(), input.getCarPartId(), input.getShopId(), input.getDateString());
        order.setShop(shopService.readShop(input.getShopId()));
        order.setCarPart(carPartService.readCarPart(input.getCarPartId()));
        return order;
    }
}
