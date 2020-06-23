package com.example.androidproject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserInfo {
    private static UserInfo instance;
    private UserInfo() {

    }

    public static UserInfo getInstance(){
        if (instance == null){
            instance = new UserInfo();
            return instance;
        }
        return instance;
    }

    public String username;
    public String phone;
    public String address;
    public Map<String,String> dishesname;
    public List<String> dishs = new ArrayList<>();
    public String dishes;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Map<String, String> getDishesname() {
        return dishesname;
    }

    public void setDishesname(Map<String, String> dishesname) {
        this.dishesname = dishesname;
    }

    public List<String> getDishs() {
        return dishs;
    }

    public void setDishs(List<String> dishs) {
        this.dishs = dishs;
    }

    public String getDishes() {
        return dishes;
    }

    public void setDishes(String dishes) {
        this.dishes = dishes;
    }
}