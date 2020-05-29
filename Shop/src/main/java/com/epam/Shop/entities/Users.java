package com.epam.Shop.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Users {
    @Id
    private int id;

    private String isAdmin;
    private String username;
    private String password;

    public Users(int id, String isAdmin, String username, String password) {
        this.id = id;
        this.isAdmin = isAdmin;
        this.username = username;
        this.password = password;
    }

    public Users() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getisAdmin() {
        return isAdmin;
    }

    public void setisAdmin(String isAdmin) {
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
        String temp = "-";
        return id + temp + isAdmin + temp + password + temp + username;
    }
}
