package by.epam;

import by.epam.view.CarPartView;
import by.epam.view.OrderView;
import by.epam.view.ShopView;

import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        ShopView sview = new ShopView();
        CarPartView cview = new CarPartView();
        OrderView oview = new OrderView();

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
                case (3):
                    oview.Start();
                    break;
                default:
                System.out.println("Нет такого пункта");
                break;
            }
        }
    }

    public static int readerInt() {
        Scanner sc = new Scanner(System.in);
        int tmp = sc.nextInt();
        return tmp;
    }
}





