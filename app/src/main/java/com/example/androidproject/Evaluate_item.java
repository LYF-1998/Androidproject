package com.example.androidproject;

public class Evaluate_item {
    private String user;
    private String evaluate;

    public Evaluate_item(String user, String evaluate) {
        this.user = user;
        this.evaluate = evaluate;
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
}
