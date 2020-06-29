package com.example.androidproject;

public class Myorder_item {
    private String price,time,ds,shop;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDs() {
        return ds;
    }

    public void setDs(String ds) {
        this.ds = ds;
    }

    public Myorder_item(String shop,String price, String time, String ds) {
        this.price = price;
        this.time = time;
        this.ds = ds;
        this.shop=shop;
    }
}
