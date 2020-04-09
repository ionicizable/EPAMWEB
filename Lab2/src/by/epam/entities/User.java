package by.epam.entities;

import by.epam.Utility;

public class User {
    private int id;
    private boolean isAdmin;
    private String username;
    private String password;

    public User(int id, boolean isAdmin, String username, String password) {
        this.id = id;
        this.isAdmin = isAdmin;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean getisAdmin() {
        return isAdmin;
    }

    public void setisAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toStringFile(){
        String temp = Utility.valueSeparator;
        return id + temp + isAdmin + temp + password + temp + username;
    }
}