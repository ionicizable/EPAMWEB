package by.epam.view;

import by.epam.entities.CarPart;
import by.epam.entities.User;
import by.epam.entities.Shop;
import by.epam.service.AdminService;
import by.epam.service.CarPartService;
import by.epam.service.ShopService;
import by.epam.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class AdminView {

    private final int menuReadAllParts = 1;
    private final int menuCreateCarPart = 2;
    private final int menuUpdateCarPart = 3;
    private final int menuDeleteCarPart = 4;
    private final int menuReadAllUsers = 5;
    private final int createUser = 6;
    private final int updateUser = 7;
    private final int deleteUser = 8;
    private final int menuReadAllShops = 9;
    private final int createShop = 10;
    private final int updateShop = 11;
    private final int deleteShop = 12;

    private CarPartService carPartService;
    private ShopService shopService;
    private UserService userService;

    public AdminView() {
        userService = new UserService();
        shopService = new ShopService();
        carPartService = new CarPartService();
    }

    public void Start() {
        while (true) {
            try {
                showMenu();
                int check = readerInt();
                switch (check) {
                    case (menuReadAllParts):
                        readAllCarParts();
                        break;
                    case (menuCreateCarPart):
                        createCarPart();
                        break;
                    case (menuUpdateCarPart):
                        updateCarPart();
                        break;
                    case (menuDeleteCarPart):
                        deleteCarPart();
                        break;
                    case (menuReadAllUsers):
                        readAllUsers();
                        break;
                    case (createUser):
                        createUser();
                        break;
                    case (updateUser):
                        updateUser();
                        break;
                    case (deleteUser):
                        deleteUser();
                        break;
                    case (menuReadAllShops):
                        readAllShops();
                        break;
                    case (createShop):
                        createShop();
                        break;
                    case (updateShop):
                        updateShop();
                        break;
                    case (deleteShop):
                        deleteShop();
                        break;
                    default:
                        System.out.println("Нет такого пункта");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Неверно введенные данные");
            }
        }
    }
    private void deleteCarPart() {
        System.out.println("Введите номер удаляемой запчасти:");
        int id = readerInt();
        try { System.out.println("Введите название удаляемой запчасти:");
            String name = readerString();
            carPartService.Delete(id,name);
        }
        catch (Exception e) {
            System.out.println("Неверно введенные данные");
        }
    }

    private void updateCarPart() {
        System.out.println("Введите номер изменяемой запчасти:");
        int id = readerInt();
        if (!readCarPart(id)) {
            return;
        }
        try {
            System.out.println("Введите новые данные запчасти в формате Имя-Описание-Автомобиль:");
            String buffer = readerString();
            StringTokenizer st = new StringTokenizer(buffer, "-");
            String name = st.nextToken();
            String description = st.nextToken();
            String CarId = st.nextToken();
            CarPart carPart = new CarPart(id, name, description,CarId);
            carPartService.Update(id, carPart);
        }
        catch (Exception e) {
            System.out.println("Неверно введенные данные");
        }
    }

    private void createCarPart() {
        try {
            System.out.println("Введите данные новой запчасти в формате Имя-Описание-Автомобиль:");
            String buffer = readerString();
            StringTokenizer st = new StringTokenizer(buffer, "-");
            String name = st.nextToken();
            String description = st.nextToken();
            String CarId = st.nextToken();
            carPartService.Create(new CarPart(0, name, description, CarId));
        } catch (Exception e) {
            System.out.println("Неверно введенные данные");
        }
    }

    private boolean readCarPart(int id) {
        try {
            CarPart carPart = carPartService.ReadCarPart(id);
            System.out.println(carPart.toStringFile());
            return true;
        } catch (Exception e) {
            System.out.println(String.format("Ошибка %s", e.getMessage()));
            return false;
        }
    }
    private void readAllCarParts() {
        Logger log = LogManager.getLogger();
        ArrayList<CarPart> carParts = carPartService.ReadAll();
        for (CarPart carPart : carParts
        ) {
            System.out.println(carPart.toStringFile());
        }
        log.info("CarPart list reviewed");
    }

    private void readAllUsers() {
        Logger log = LogManager.getLogger();
        ArrayList<User> users = userService.ReadAll();
        for (User user : users
        ) {
            System.out.println(user.toStringFile());
        }
        log.info("User list reviewed");
    }

    private void deleteUser() {
        System.out.println("Введите id удаляемого пользователя:");
        int id = readerInt();
        try {
            System.out.println("Введите username удаляемого пользователя:");
            String username = readerString();
            System.out.println("Введите пароль:");
            String password = readerString();
            userService.Delete(id, username, password);
        } catch (Exception e) {
            System.out.println("Неверно введенные данные");
        }
    }

    private void updateUser() {
        System.out.println("Введите id изменяемого пользователя:");
        int id = readerInt();
        if (!readUser(id)) {
            return;
        }
        try {
            System.out.println("Введите новые данные запчасти в формате Почта-Пароль-ИмяПользователя:");
            String buffer = readerString();
            StringTokenizer st = new StringTokenizer(buffer, "-");
            String email = st.nextToken();
            String password = st.nextToken();
            String username = st.nextToken();
            User user = new User(id, email, password, username);
            userService.Update(id, user);
        } catch (Exception e) {
            System.out.println("Неверно введенные данные");
        }
    }

    private void createUser() {
        try {
            System.out.println("Введите данные нового пользователя в формате Почта-Пароль-ИмяПользователя:");
            String buffer = readerString();
            StringTokenizer st = new StringTokenizer(buffer, "-");
            String email = st.nextToken();
            String password = st.nextToken();
            String username = st.nextToken();
            userService.Create(new User(0, email, password, username));
        } catch (Exception e) {
            System.out.println("Неверно введенные данные");
        }
    }

    private boolean readUser(int id) {
        try {
            User user = userService.ReadUser(id);
            System.out.println(user.toStringFile());
            return true;
        } catch (Exception e) {
            System.out.println(String.format("Ошибка %s", e.getMessage()));
            return false;
        }
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
    private void deleteShop() {
        System.out.println("Введите номер удаляемого магазина:");
        int id = readerInt();
        if (readShop(id)) { return; }
        try {System.out.println("Введите название удаляемого магазина:");
            String name = readerString();
            shopService.Delete(id, name);
        }
        catch (Exception e){
            System.out.println("Неверно введенные данные");
        }
    }

    private void updateShop() {
        System.out.println("Введите номер изменяемого магазина:");
        int id = readerInt();
        if (readShop(id)) { return; }
        try { System.out.println("Введите новые данные магазина в формате Имя-Адрес-Контакты-ВремяРаботы-Описание:");
            String buffer = readerString();
            StringTokenizer st = new StringTokenizer(buffer, "-");
            String name = st.nextToken();
            String address = st.nextToken();
            String contact = st.nextToken();
            String worktime = st.nextToken();
            String description = st.nextToken();
            Shop shop = new Shop(id, name, address, contact, worktime, description);
            shopService.Update(id, shop);
        }
        catch (Exception e)
        {
            System.out.println("Неверно введенные данные");
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
            return false;
        } catch (Exception e) {
            System.out.println("Неверно введенные данные");
            log.error(String.format("Ошибка %s", e.getMessage()));
            return true;
        }
    }

    private void showMenu() {
        System.out.println(String.format("Введите %d  чтобы показать все запчасти", menuReadAllParts));
        System.out.println(String.format("Введите %d  чтобы создать запчасть", menuCreateCarPart));
        System.out.println(String.format("Введите %d  чтобы обновить запчасть", menuUpdateCarPart));
        System.out.println(String.format("Введите %d  чтобы удалить запчасть", menuDeleteCarPart));
        System.out.println(String.format("Введите %d  чтобы показать всех пользователей", menuReadAllUsers));
        System.out.println(String.format("Введите %d  создать нового пользователя", createUser));
        System.out.println(String.format("Введите %d  чтобы изменить пользователя", updateUser));
        System.out.println(String.format("Введите %d  чтобы удалить пользователя", deleteUser));
        System.out.println(String.format("Введите %d  чтобы показать все магазины", menuReadAllShops));
        System.out.println(String.format("Введите %d  чтобы создать магазин", createShop));
        System.out.println(String.format("Введите %d  чтобы обновить магазин", updateShop));
        System.out.println(String.format("Введите %d  чтобы удалить магазин", deleteShop));
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
