package by.epam.view;

import by.epam.dao.ShopDao;
import by.epam.entities.Shop;
import by.epam.service.ShopService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class ShopView {

    private final int menuReadAll = 1;
    private final int menuCreate = 2;
    private final int menuUpdate = 3;
    private final int menuDelete = 4;

    private ShopService shopService;

    public ShopView() {
        shopService = new ShopService();
    }


    public void Start() {
        Logger log = LogManager.getLogger();
        while (true) {
            showMenu();
            int check = readerInt();
            switch (check) {
                case (menuReadAll):
                    readAllShops();
                    break;
                case (menuCreate):
                    createShop();
                    break;
                case (menuUpdate):
                    updateShop();
                    log.info("Shop updated");
                    break;
                case (menuDelete):
                    deleteShop();
                    log.info("Shop deleted");
                default:
                    System.out.println("Нет такого пункта");
                    break;
            }
        }
    }

    private void deleteShop() {
        System.out.println("Введите номер удаляемого магазина:");
        int id = readerInt();
        if (!readShop(id)) { return; }
        try {
            System.out.println("Введите название удаляемого магазина:");
            String name = readerString();
            shopService.Delete(id, name);
        }
        catch (Exception e){
            System.out.println("Неверные данные");
        }
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
            StringTokenizer st = new StringTokenizer(buffer, "-");
            String name = st.nextToken();
            String address = st.nextToken();
            String contact = st.nextToken();
            String worktime = st.nextToken();
            String description = st.nextToken();
            Shop shop = new Shop(id, name, address, contact, worktime, description);
            shopService.Update(id, shop);
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
            shopService.Create(new Shop(0, name, address, contact, worktime, description));
        }
        catch (Exception e){
            System.out.println("Неверно введенные данные");
        }
    }

    private boolean readShop(int id) {
        Logger log = LogManager.getLogger();
        try {
            Shop shop = shopService.ReadShop(id);
            System.out.println(shop.toStringFile());
            return true;
        } catch (Exception e) {
            log.error(String.format("Ошибка %s", e.getMessage()));
            return false;
        }
    }


    private void showMenu() {
        System.out.println(String.format("Введите %d  чтобы показать все магазины,", menuReadAll));
        System.out.println(String.format("Введите %d  чтобы создать новый магазин", menuCreate));
        System.out.println(String.format("Введите %d  чтобы изменить данные магазина", menuUpdate));
        System.out.println(String.format("Введите %d  чтобы удалить магазин", menuDelete));
    }

    private void readAllShops() {
        Logger log = LogManager.getLogger();
        ArrayList<Shop> shops = shopService.ReadAll();
        for (Shop shop : shops
        ) {
            System.out.println(shop.toStringFile());
        }
        log.info("Shop list reviewed");
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
