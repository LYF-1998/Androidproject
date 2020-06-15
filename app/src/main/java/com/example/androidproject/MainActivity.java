package com.example.androidproject;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.ResultSet;
import java.sql.SQLException;


public  class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        setContentView(R.layout.activity_main);
        ListView listView = findViewById(R.id.listview1);
        String[] arr = {"qq", "ww", "ee"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, arr);
        listView.setAdapter(adapter);

        String sql = "select address from user where username = 'test';";
        String address = null;
        ResultSet query = JDBCUtils.query(sql);
        try {
            query.next();
            address = query.getString("address");
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.close();
        }
        System.out.println(address);
    }


}
