package by.epam.view;

import by.epam.entities.User;
import by.epam.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class UserView {

    private final int menuReadAll = 1;
    private final int menuCreate = 2;
    private final int menuUpdate = 3;
    private final int menuDelete = 4;

    private UserService userService;

    public UserView() {
        userService = new UserService();
    }

    public void Start() {
        while (true) {
                showMenu();
                int check = readerInt();
                switch (check) {
                    case (menuReadAll):
                        readAll();
                        break;
                    case (menuCreate):
                        createUser();
                        break;
                    case (menuUpdate):
                        updateUser();
                        break;
                    case (menuDelete):
                        deleteUser();
                        break;
                    default:
                        System.out.println("Нет такого пункта");
                        break;
                }
        }
    }

    private void readAll() {
        Logger log = LogManager.getLogger();
        ArrayList<User> users = userService.readAll();
        for (User user : users) {
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
            userService.delete(id, username, password);
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
            Boolean isAdmin = Boolean.parseBoolean(st.nextToken());
            String password = st.nextToken();
            String username = st.nextToken();
            User user = new User(id, isAdmin, password, username);
            userService.update(id, user);
        } catch (Exception e) {
            System.out.println("Неверно введенные данные");
        }
    }

    private void createUser() {
        try {
            System.out.println("Введите данные нового пользователя в формате Почта-Пароль-ИмяПользователя:");
            String buffer = readerString();
            StringTokenizer st = new StringTokenizer(buffer, "-");
            Boolean isAdmin = Boolean.parseBoolean(st.nextToken());
            String password = st.nextToken();
            String username = st.nextToken();
            userService.create(new User(0, isAdmin, password, username));
        } catch (Exception e) {
            System.out.println("Неверно введенные данные");
        }
    }

    private boolean readUser(int id) {
        try {
            User user = userService.readUser(id);
            System.out.println(user.toStringFile());
            return true;
        } catch (Exception e) {
            System.out.println(String.format("Ошибка %s", e.getMessage()));
            return false;
        }
    }

    private void showMenu() {
        System.out.println(String.format("Введите %d  чтобы показать всех пользователей", menuReadAll));
        System.out.println(String.format("Введите %d  создать нового пользователя", menuCreate));
        System.out.println(String.format("Введите %d  чтобы изменить пользователя", menuUpdate));
        System.out.println(String.format("Введите %d  чтобы удалить пользователя", menuDelete));
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

    public User login() {
        while (true) {
            System.out.println("Введите логин:");
            String username = readerString();
            System.out.println("Введите пароль:");
            String password = readerString();
            try {
                return userService.login(username, password);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
