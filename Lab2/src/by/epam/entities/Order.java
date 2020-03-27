package by.epam.entities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Order extends OrderData {
    private CarPart carPart;
    private Shop shop;

    public Order(int id, int carPartId, int shopId, String dateString) throws ParseException {
        super(id, carPartId, shopId, dateString);
    }

    public CarPart getCarPart() {
        return carPart;
    }

    public void setCarPart(CarPart carPart) {
        this.carPart = carPart;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
}
