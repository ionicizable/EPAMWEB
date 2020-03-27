package by.epam;

import by.epam.view.CarPartView;
import by.epam.view.ShopView;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        ShopView sview = new ShopView();

        CarPartView cview = new CarPartView();

        try {while (true) {

            System.out.println("1.Магазины 2.Запчасти");
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
        catch (Exception e)
        {
            System.out.println("Неверные данные");
        }
    }

    public static int readerInt() {
        Scanner sc = new Scanner(System.in);
        int tmp = sc.nextInt();
        return tmp;
    }
}





