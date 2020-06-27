package com.example.androidproject;

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

}