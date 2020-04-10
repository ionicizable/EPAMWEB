package by.epam.view;

import by.epam.entities.CarPart;
import by.epam.entities.Order;
import by.epam.entities.Shop;
import by.epam.service.CarPartService;
import by.epam.service.OrderService;
import by.epam.service.ShopService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class OrderView {
    private final int MENU_READ_ALL = 2;
    private final int MENU_CREATE = 1;
    private final int MENU_DELETE = 3;

    private OrderService orderService;
    private CarPartService carPartService;
    private ShopService shopService;
    //private ShopView shopView;
    //private CarPartView carPartView;

    public OrderView() {
        ShopService shopService = new ShopService();
        CarPartService carPartService = new CarPartService();
        orderService = new OrderService(shopService,carPartService);
    }

    public void Start(boolean isAdmin) throws ParseException {
        Logger log = LogManager.getLogger();
        while (true) {
            showMenu(isAdmin);
            int check = readerInt();
            if (check == MENU_READ_ALL && isAdmin) {
                readAllOrders();
                continue;
            }
            if (check == MENU_CREATE) {
                createOrder();
                continue;
            }
            if (check == MENU_DELETE && isAdmin) {
                deleteOrder();
                continue;
            }
            System.out.println("Нет такого пункта");
        }
    }

    private void deleteOrder() throws ParseException {
        System.out.println("Введите номер удаляемого заказа:");
        int id = readerInt();
        orderService.delete(id);
    }

    private void createOrder() throws ParseException {
        try {
            CarPartService carPartService = new CarPartService();
            ShopService shopService = new ShopService();
            System.out.println("Выберите, какую деталь вы хотите заказать:");
            ArrayList<CarPart> carParts = carPartService.readAll();
            for (CarPart carPart : carParts
            ) {
                System.out.println(carPart.toStringFile());
            }
            int carPartId = readerInt();
            System.out.println("Выберите,  из какого магазина вы хотите заказать:");
            ArrayList<Shop> shops = shopService.readAll();
            for (Shop shop : shops
            ) {
                System.out.println(shop.toStringFile());
            }

            int shopId = readerInt();
            orderService.create(new Order(0, carPartId, shopId, "00000000"));
        }catch (Exception e){
            System.out.println("Error");
            System.out.println(e.getMessage());
        }
    }

    private boolean readOrder(int id) {
        Logger log = LogManager.getLogger();
        try {
            Order order = orderService.readOrder(id);
            System.out.println(order.toStringFile());
            return true;
        } catch (Exception e) {
            log.error(String.format("Ошибка %s", e.getMessage()));
            return false;
        }
    }


    private void showMenu(boolean isAdmin) {
        System.out.println(String.format("Введите %d  чтобы создать новый заказ", MENU_CREATE));
        if (isAdmin){
            System.out.println(String.format("Введите %d  чтобы показать все заказы", MENU_READ_ALL));
            System.out.println(String.format("Введите %d  чтобы удалить заказ", MENU_DELETE));
        }

    }

    private void readAllOrders() throws ParseException {
        Logger log = LogManager.getLogger();
        ArrayList<Order> orders = orderService.readAll();

        for (Order order : orders
        ) {
            System.out.println(order.toStringFile()+"\n  "+order.getCarPart().toStringFile()+"\n  "+order.getShop().toStringFile());
        }
        log.info("Order list reviewed");
    }

    public int readerInt() {
        Scanner sc = new Scanner(System.in);
        int tmp = sc.nextInt();
        return tmp;
    }

    public String readerString() {
        Scanner sc = new Scanner(System.in);
        String tmp = sc.nextLine();
        return tmp;
    }
}
