package by.epam.view;

import by.epam.Utility;
import by.epam.entities.Car;
import by.epam.service.CarService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class CarView {

    private final int MENU_READ_ALL = 1;
    private final int MENU_CREATE = 2;
    private final int MENU_UPDATE = 3;
    private final int MENU_DELETE = 4;

    private CarService carService;

    public CarView() {
        carService = new CarService();
    }

    public void Start(boolean isAdmin) {
        Logger log = LogManager.getLogger();
        while (true) {
            showMenu(isAdmin);
            int check = readerInt();
            if (check == MENU_READ_ALL) {
                readAllCars();
                break;
            }
            if (check == MENU_CREATE && isAdmin) {
                createCar();
                break;
            }
            if (check == MENU_UPDATE && isAdmin) {
                updateCar();
                break;
            }
            if (check == MENU_DELETE && isAdmin) {
                deleteCar();
                break;
            }
            System.out.println("Нет такого пункта");
        }
    }

    private void deleteCar() {
        System.out.println("Введите номер удаляемого автомобиля:");
        int id = readerInt();
        System.out.println("Введите название удаляемого автомобиля:");
        String brand = readerString();
        carService.delete(id, brand);
    }

    private void updateCar() {
        System.out.println("Введите номер изменяемого автомобиля:");
        int id = readerInt();
        if (!readCar(id)) {
            return;
        }
        try {
            System.out.println("Введите новые данные автомобиля в формате Марка-Модель:");
            String buffer = readerString();
            StringTokenizer st = new StringTokenizer(buffer, Utility.valueSeparator);
            String brand = st.nextToken();
            String model = st.nextToken();
            Car car = new Car(id, brand, model);
            carService.update(id, car);
        } catch (Exception e) {
            System.out.println("Неверные данные");
        }

    }

    private void createCar() {
        try {
            System.out.println("Введите данные нового автомобиля в формате Марка-Модель:");
            String buffer = readerString();
            StringTokenizer st = new StringTokenizer(buffer, "-");
            String brand = st.nextToken();
            String model = st.nextToken();
            carService.create(new Car(0, brand,model));
        }
        catch (Exception e){
            System.out.println("Неправильный ввод данных");
        }
    }

    private boolean readCar(int id) {
        Logger log = LogManager.getLogger();
        try {
            Car car = carService.readCar(id);
            System.out.println(car.toStringFile());
            return true;
        } catch (Exception e) {
            log.error(String.format("Ошибка %s", e.getMessage()));
            return false;
        }
    }


    private void showMenu(boolean isAdmin) {
        System.out.println(String.format("Введите %d  чтобы показать все автомобили", MENU_READ_ALL));
        if (isAdmin) {
            System.out.println(String.format("Введите %d  чтобы создать новый автомобиль", MENU_CREATE));
            System.out.println(String.format("Введите %d  чтобы изменить данные автомобиля", MENU_UPDATE));
            System.out.println(String.format("Введите %d  чтобы удалить автомобиль", MENU_DELETE));
        }
    }

    private void readAllCars() {
        Logger log = LogManager.getLogger();
        ArrayList<Car> cars = carService.readAll();
        for (Car car : cars
        ) {
            System.out.println(car.toStringFile());
        }
        log.info("Car list reviewed");
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
