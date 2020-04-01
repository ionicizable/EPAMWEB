package by.epam;

import by.epam.view.AdminView;
import by.epam.view.UserView;

import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        UserView uview = new UserView();
        AdminView aview = new AdminView();
        while (true)
        try{
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

        }
catch (Exception e){System.out.println("Неверно введенные данные");}
    }
    public static int readerInt() {
        Scanner sc = new Scanner(System.in);
        int tmp = sc.nextInt();
        return tmp;
    }

    public static class Utility { public static String valueSeparator = "-";
    }
}