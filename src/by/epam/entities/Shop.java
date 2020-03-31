package by.epam.entities;

import by.epam.Utility;

public class Shop {


    private int id;
    private String name;
    private String address;
    private String contact;
    private String worktime;
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int ID){
        this.id = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getWorktime() {
        return worktime;
    }

    public void setWorktime(String worktime) {
        this.worktime = worktime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String toStringFile(){
        String temp = Utility.valueSeparator;
        return Integer.toString(id) + temp + name + temp + address + temp + contact + temp + worktime + temp + description;
    }

    public Shop(int id,String name, String address, String contact, String worktime, String description) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.worktime = worktime;
        this.description = description;
    }
}