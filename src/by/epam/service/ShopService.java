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

    public ArrayList<Shop> ReadAll() {
        ArrayList<Shop> shops = shopDao.readAll();
        return shops;
    }

    public void Create(Shop shop) {
        int newId = shopDao.getMaxId() + 1;
        shop.setId(newId);
        shopDao.create(shop);
    }

    public void Update(int id, Shop newShop) {
        shopDao.Update(id, newShop);
    }

    public Shop ReadShop(int id) {
        return shopDao.readShop(id);
    }

    public void Delete(int id, String name) {
        Logger log = LogManager.getLogger();
        Shop shop = ReadShop(id);
        if(shop.getName().equals(name)){
            shopDao.delete(id);
            log.info("Shop deleted");
        } else {
            log.error("Wrong control name");
            throw new IllegalArgumentException("");
        }
    }
}
