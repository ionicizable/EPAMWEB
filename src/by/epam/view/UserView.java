package by.epam.view;

import by.epam.entities.CarPart;
import by.epam.entities.User;
import by.epam.entities.Shop;
import by.epam.service.CarPartService;
import by.epam.service.ShopService;
import by.epam.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class UserView {

    private final int menuReadAllParts = 1;
    private final int menuReadAllShops = 2;
    private final int createUser = 3;
    private final int updateUser = 4;
    private final int deleteUser = 5;

    private CarPartService carPartService;
    private ShopService shopService;
    private UserService userService;

    public UserView() { userService = new UserService();
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
                    case (menuReadAllShops):
                        readAllShops();
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
                    default:
                        System.out.println("Нет такого пункта");
                        break;
                }
            }
            catch (Exception e) {
                System.out.println("Неверно введенные данные");
            }
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

    private void readAllCarParts() {
        Logger log = LogManager.getLogger();
        ArrayList<CarPart> carParts = carPartService.ReadAll();
        for (CarPart carPart : carParts
        ) {
            System.out.println(carPart.toStringFile());
        }
        log.info("CarPart list reviewed");
    }

    private void deleteUser() {
        System.out.println("Введите id удаляемого пользователя:");
        int id = readerInt();
        try { System.out.println("Введите username удаляемого пользователя:");
            String username = readerString();
            System.out.println("Введите пароль:");
            String password = readerString();
            userService.Delete(id,username,password);
        }
        catch (Exception e) {
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
            User user = new User(id,email,password,username);
            userService.Update(id,user);
        }
        catch (Exception e) {
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

    private void showMenu() {
        System.out.println(String.format("Введите %d  чтобы показать все запчасти", menuReadAllParts));
        System.out.println(String.format("Введите %d  чтобы показать все магазины", menuReadAllShops));
        System.out.println(String.format("Введите %d  создать нового пользователя", createUser));
        System.out.println(String.format("Введите %d  чтобы изменить пользователя", updateUser));
        System.out.println(String.format("Введите %d  чтобы удалить пользователя", deleteUser));
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