package com.example.androidproject;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class K_Address extends AppCompatActivity {
    ListView listView;
    FutureTask<ArrayList<String>> futureTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        listView = findViewById(R.id.listview1);
        futureTask = new FutureTask<ArrayList<String>>(new Callable() {
            @Override
            public ArrayList<String> call() throws SQLException {
                ArrayList<String> arrayList = new ArrayList<>();
                String sql = "select address from user where username = 'test'";
                ResultSet query = JDBCUtils.query(sql);
                query.next();
                String address = query.getString("address");
                JDBCUtils.close();
                arrayList.add(address);
                return arrayList;
            }
        });
        new Thread(futureTask).start();
    }

    @Override
    protected void onStart() {
        super.onStart();
        String address = "";
        ArrayList<String> arr = new ArrayList<>();
        try {
            arr = futureTask.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        arr.add(address);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, arr);
        listView.setAdapter(arrayAdapter);
    }
}