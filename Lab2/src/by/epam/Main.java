package by.epam;

import by.epam.entities.User;
import by.epam.view.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        ShopView sview = new ShopView();
        CarPartView cview = new CarPartView();
        OrderView oview = new OrderView();
        UserView uview = new UserView();
        CarView carview = new CarView();

        User user = uview.login();

        while (true) {
            if (user.getisAdmin()){
                System.out.println("1.Магазины 2.Запчасти 3.Заказы 4.Машины 5.Пользователи");
            }
            else{
                System.out.println("1.Магазины 2.Запчасти 3.Заказы 4.Машины");
            }

            int check = readerInt();
            if (check == 1) {
                sview.Start(user.getisAdmin());
                continue;
            }
            if (check == 2) {
                cview.Start(user.getisAdmin());
                continue;
            }
            if (check == 3) {
                oview.Start(user);
                continue;
            }
            if (check == 4) {
                carview.Start(user.getisAdmin());
                continue;
            }
            if (check == 5 && user.getisAdmin()) {
                uview.Start();
                continue;
            }
            System.out.println("Нет такого пункта");
        }
    }

    public static int readerInt() {
        Scanner sc = new Scanner(System.in);
        int tmp = sc.nextInt();
        return tmp;
    }
}





