package by.epam.service;

import by.epam.dao.ShopDao;
import by.epam.entities.Shop;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;

public class ShopService {
    private ShopDao shopDao;
    Logger log = LogManager.getLogger();

    public ShopService() {
        shopDao = new ShopDao();
    }

    public ArrayList<Shop> readAll() throws SQLException {
        ArrayList<Shop> shops = shopDao.readAll();
        return shops;
    }

    public void create(Shop shop) {
        try {
            int newId = shopDao.getMaxId() + 1;
            shop.setId(newId);
            shopDao.create(shop);
        } catch (SQLException e){
            log.error(e.getMessage());
        }
    }

    public void update(int id, Shop newShop) {
        shopDao.update(id, newShop);
    }

    public Shop readShop(int id) throws SQLException {
        return shopDao.readShop(id);
    }

    public void delete(int id, String name) {
        try {
            Shop shop = readShop(id);
            if (shop.getName().equals(name)) {
                shopDao.delete(id);
                log.info("Shop deleted");
            } else {
                System.out.println("Wrong control name");
            }
        } catch (IllegalArgumentException | SQLException ignored) {
        }
    }
}
