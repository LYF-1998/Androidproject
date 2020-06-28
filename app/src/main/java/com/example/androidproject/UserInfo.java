package com.example.androidproject;

import android.content.Context;
import android.content.SharedPreferences;

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
    public String shopname;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getShopname(){return  shopname;}
    public void setShopname(String shopname){this.shopname=shopname;}
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

    public void clear(){
        SharedPreferences sp = MyApplication.getContext().getSharedPreferences("mysp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("username", null);
        editor.putString("passwd", null);
        editor.commit();
    }
}