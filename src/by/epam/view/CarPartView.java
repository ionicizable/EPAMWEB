package by.epam.view;

import by.epam.entities.CarPart;
import by.epam.service.CarPartService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class CarPartView {

    private final int menuReadAll = 1;
    private final int menuCreate = 2;
    private final int menuUpdate = 3;
    private final int menuDelete = 4;

    private CarPartService carPartService;

    public CarPartView() { carPartService = new CarPartService();
    }

    public void Start() {
        while (true) {
            try {
                showMenu();
                int check = readerInt();
                switch (check) {
                    case (menuReadAll):
                        readAllCarParts();
                        break;
                    case (menuCreate):
                        createCarPart();
                        break;
                    case (menuUpdate):
                        updateCarPart();
                        break;
                    case (menuDelete):
                        deleteCarPart();
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


    private void showMenu() {
        System.out.println(String.format("Введите %d  чтобы показать все запчасти", menuReadAll));
        System.out.println(String.format("Введите %d  чтобы создать новую запчасть", menuCreate));
        System.out.println(String.format("Введите %d  чтобы изменить данные запчасти", menuUpdate));
        System.out.println(String.format("Введите %d  чтобы удалить запчасть", menuDelete));
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


