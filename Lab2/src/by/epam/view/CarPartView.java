package by.epam.view;

import by.epam.entities.CarPart;
import by.epam.service.CarPartService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class CarPartView {

    private final int MENU_READ_ALL = 1;
    private final int MENU_CREATE = 2;
    private final int MENU_UPDATE = 3;
    private final int MENU_DELETE = 4;
    private final int MENU_RETURN = 0;

    private CarPartService carPartService;

    public CarPartView() { carPartService = new CarPartService();
    }

    public void Start(boolean isAdmin) {
        while (true) {
            showMenu(isAdmin);
            int check = readerInt();
            if (check == MENU_RETURN){
                return;
            }
            if (check == MENU_READ_ALL) {
                readAllCarParts();
                continue;
            }
            if (check == MENU_CREATE && isAdmin) {
                createCarPart();
                continue;
            }
            if (check == MENU_UPDATE && isAdmin) {
                updateCarPart();
                continue;
            }
            if (check == MENU_DELETE && isAdmin) {
                deleteCarPart();
                continue;
            }
            System.out.println("Нет такого пункта");
        }
    }

    private void deleteCarPart() {
        System.out.println("Введите номер удаляемой запчасти:");
        int id = readerInt();
        System.out.println("Введите название удаляемой запчасти:");
        String name = readerString();
        carPartService.delete(id,name);
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
            carPartService.update(id, carPart);
        } catch (Exception e) {
            System.out.println("Неверные данные");
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
            carPartService.create(new CarPart(0, name, description, CarId));
        } catch (Exception e){
            System.out.println("Неправильный ввод данных");
        }
    }

    private boolean readCarPart(int id) {
        try {
            CarPart carPart = carPartService.readCarPart(id);
            System.out.println(carPart.toStringFile());
            return true;
        } catch (Exception e) {
            System.out.println(String.format("Ошибка %s", e.getMessage()));
            return false;
        }
    }


    private void showMenu(boolean isAdmin) {
        System.out.println(String.format("Введите %d чтобы выйти", MENU_RETURN));
        System.out.println(String.format("Введите %d  чтобы показать все запчасти", MENU_READ_ALL));
        if (isAdmin){
            System.out.println(String.format("Введите %d  чтобы создать новую запчасть", MENU_CREATE));
            System.out.println(String.format("Введите %d  чтобы изменить данные запчасти", MENU_UPDATE));
            System.out.println(String.format("Введите %d  чтобы удалить запчасть", MENU_DELETE));
        }
    }

    private void readAllCarParts() {
        try {
            ArrayList<CarPart> carParts = carPartService.readAll();
            for (CarPart carPart : carParts
            ) {
                System.out.println(carPart.toStringFile());
            }
        } catch (SQLException e){
            System.out.println(String.format("Ошибка %s", e.getMessage()));
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


