package by.epam.entities;

import by.epam.Utility;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderData {
    private int id;
    private int carPartId;
    private int shopId;
    private int userId;
    private Date date;
    private final String dateStringFormat = "ddMMyyyy";

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDateString() {
        DateFormat dateFormat = new SimpleDateFormat(dateStringFormat);
        return dateFormat.format(date);
    }

    public void setDateString(String value) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(dateStringFormat);
        date = dateFormat.parse(value);
    }

    public OrderData(int id, int carPartId, int shopId, int userId, Date date) {
        this.id = id;
        this.carPartId = carPartId;
        this.shopId = shopId;
        this.date = date;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCarPartId() {
        return carPartId;
    }

    public void setCarPartId(int carPartId) {
        this.carPartId = carPartId;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String toStringFile() {
        String temp = Utility.valueSeparator;
        return id + temp + carPartId + temp + shopId + temp + userId + temp + getDateString();
    }
}
