package by.epam.service;

import by.epam.dao.ShopDao;
import by.epam.entities.Shop;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class ShopService {

    private ShopDao shopDao;

    public ShopService() {
        shopDao = new ShopDao();
    }

    public ArrayList<Shop> readAll() {
        ArrayList<Shop> shops = shopDao.readAll();
        return shops;
    }

        public void create(Shop shop) {
        int newId = shopDao.getMaxId() + 1;
        shop.setId(newId);
        shopDao.create(shop);
    }

    public void update(int id, Shop newShop) {
        shopDao.update(id, newShop);
    }

    public Shop readShop(int id) {
        return shopDao.readShop(id);
    }

    public void delete(int id, String name) {
        try {
            Logger log = LogManager.getLogger();
            Shop shop = readShop(id);
            if (shop.getName().equals(name)) {
                shopDao.delete(id);
                log.info("Shop deleted");
            }
            else {
                System.out.println("Wrong control name");
            }
        }
        catch (Exception e){
            System.out.println("Error");
            throw new IllegalArgumentException("");
        }
    }
}
