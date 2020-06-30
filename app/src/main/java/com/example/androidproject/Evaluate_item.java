package com.example.androidproject;

public class Evaluate_item {
    private String user;
    private String evaluate;
    private String  time;

    public Evaluate_item(String user, String evaluate,String time) {
        this.user = user;
        this.evaluate = evaluate;
        this.time = time;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
