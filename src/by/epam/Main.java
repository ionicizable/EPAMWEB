package by.epam;

import by.epam.view.CarPartView;
import by.epam.view.ShopView;
import by.epam.view.AdminView;
import by.epam.view.UserView;

import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        ShopView sview = new ShopView();
        CarPartView cview = new CarPartView();
        UserView uview = new UserView();
        AdminView aview = new AdminView();
        while (true) {
            System.out.println("1.Пользователь 2.Администратор");
            int validation = readerInt();
            switch (validation) {
                case (1):
                    uview.Start();
                    break;
                case (2):
                    aview.Start();
                    break;
                default:
                    System.out.println("Нет такого пункта");
                    break;
            }
            while (true) {
                System.out.println("1.Магазины 2.Запчасти 3.Заказы");
                int check = readerInt();
                switch (check) {
                    case (1):
                        sview.Start();
                        break;
                    case (2):
                        cview.Start();
                        break;
                    default:
                        System.out.println("Нет такого пункта");
                        break;
                }
            }
        }

    }
    public static int readerInt() {
        Scanner sc = new Scanner(System.in);
        int tmp = sc.nextInt();
        return tmp;
    }

    public static class Utility { public static String valueSeparator = "-";
    }
}