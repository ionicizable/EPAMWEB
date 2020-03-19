package by.epam.view;

import by.epam.dao.ShopController;
import by.epam.entities.Shop;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        ShopController sc = new ShopController();
        Shop shop = new Shop("TEst","shark","37525545436","10.00:22.00","dfgdddh");
        ArrayList<Shop> list = sc.readAll();
        list.add(shop);
        sc.writeAll(list);
        System.out.println("Program worked");

    }
}
