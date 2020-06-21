package com.example.androidproject;

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
    public List<String> dishs;
    public String dishes;

}
