package by.epam.entities;

public class Shop {
    private String name;
    private String address;
    private String contact;
    private String worktime;
    private String description;

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
        String temp = "-";
        return name + temp + address + temp + contact + temp + worktime + temp + description;
    }

    public Shop(String name, String address, String contact, String worktime, String description) {
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.worktime = worktime;
        this.description = description;
    }
}