package com.example.androidproject;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {
    private static MyApplication newInstance;
    public static Context getInstance() {
        if (newInstance == null) {
            newInstance = new MyApplication();
        }
        return newInstance;
    }
}
