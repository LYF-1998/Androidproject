package com.example.androidproject;

import java.util.LinkedList;
import java.util.List;

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
    List<String> list = new LinkedList();
}
