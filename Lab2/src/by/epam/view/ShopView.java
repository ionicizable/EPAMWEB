package by.epam.view;

import by.epam.Utility;
import by.epam.entities.Shop;
import by.epam.service.ShopService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class ShopView {

    private final int MENU_READ_ALL = 1;
    private final int MENU_CREATE = 2;
    private final int MENU_UPDATE = 3;
    private final int MENU_DELETE = 4;
    private final int MENU_RETURN = 0;

    private ShopService shopService;
    Logger log = LogManager.getLogger();

    public ShopView() {
        shopService = new ShopService();
    }

    public void Start(boolean isAdmin) {
        Logger log = LogManager.getLogger();
        while (true) {
            showMenu(isAdmin);
            int check = readerInt();
            if (check == MENU_READ_ALL) {
                readAllShops();
                continue;
            }
            if (check == MENU_CREATE && isAdmin) {
                createShop();
                continue;
            }
            if (check == MENU_UPDATE && isAdmin) {
                updateShop();
                continue;
            }
            if (check == MENU_DELETE && isAdmin) {
                deleteShop();
                continue;
            }
            if (check == MENU_RETURN){
                return;
            }
            System.out.println("Нет такого пункта");
        }
    }

    private void deleteShop() {
        System.out.println("Введите номер удаляемого магазина:");
        int id = readerInt();
        System.out.println("Введите название удаляемого магазина:");
        String name = readerString();
        shopService.delete(id, name);
    }

    private void updateShop() {
        System.out.println("Введите номер изменяемого магазина:");
        int id = readerInt();
        if (!readShop(id)) {
            return;
        }
        try {
            System.out.println("Введите новые данные магазина в формате Имя-Адрес-Контакты-ВремяРаботы-Описание:");
            String buffer = readerString();
            StringTokenizer st = new StringTokenizer(buffer, Utility.valueSeparator);
            String name = st.nextToken();
            String address = st.nextToken();
            String contact = st.nextToken();
            String worktime = st.nextToken();
            String description = st.nextToken();
            Shop shop = new Shop(id, name, address, contact, worktime, description);
            shopService.update(id, shop);
        } catch (Exception e) {
            System.out.println("Неверные данные");
        }

    }

    private void createShop() {
        try {
            System.out.println("Введите данные нового магазина в формате Имя-Адрес-Контакты-ВремяРаботы-Описание:");
            String buffer = readerString();
            StringTokenizer st = new StringTokenizer(buffer, "-");
            String name = st.nextToken();
            String address = st.nextToken();
            String contact = st.nextToken();
            String worktime = st.nextToken();
            String description = st.nextToken();
            shopService.create(new Shop(0, name, address, contact, worktime, description));
        }
        catch (Exception e){
            System.out.println("Неправильный ввод данных");
        }
    }

    private boolean readShop(int id) {
        try {
            Shop shop = shopService.readShop(id);
            System.out.println(shop.toStringFile());
            return true;
        } catch (SQLException e) {
            log.error(String.format("Ошибка %s", e.getMessage()));
            return false;
        }
    }


    private void showMenu(boolean isAdmin) {
        System.out.println(String.format("Введите %d чтобы выйти", MENU_RETURN));
        System.out.println(String.format("Введите %d  чтобы показать все магазины", MENU_READ_ALL));
        if (isAdmin) {
            System.out.println(String.format("Введите %d  чтобы создать новый магазин", MENU_CREATE));
            System.out.println(String.format("Введите %d  чтобы изменить данные магазина", MENU_UPDATE));
            System.out.println(String.format("Введите %d  чтобы удалить магазин", MENU_DELETE));
        }
    }

    private void readAllShops() {
        try {
            Logger log = LogManager.getLogger();
            ArrayList<Shop> shops = shopService.readAll();
            for (Shop shop : shops
            ) {
                System.out.println(shop.toStringFile());
            }
            log.info("Shop list reviewed");
        } catch (SQLException e){
            log.error(e.getMessage());
        }
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
