package by.epam.service;

import by.epam.dao.ShopDao;
import by.epam.entities.Shop;

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
        Shop shop = ReadShop(id);
        if(shop.getName().equals(name)){
            shopDao.delete(id);
        } else {
            throw new IllegalArgumentException("Контрольное имя неверно");
        }
    }
}
