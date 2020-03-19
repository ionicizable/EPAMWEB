package by.epam;

import by.epam.dao.ShopDao;
import by.epam.entities.Shop;
import by.epam.service.ShopService;
import by.epam.view.ShopView;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        ShopView view = new ShopView();
        view.Start();
    }

}

