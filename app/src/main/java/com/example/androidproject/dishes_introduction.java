package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class dishes_introduction extends AppCompatActivity {
private TextView name,price,introduction;
private ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dishes_introduction);
        Bundle a=getIntent().getExtras();
        String dishes_name=a.getString("dishes_name");
        String dishes_price=a.getString("dishes_price");
        bindViews();
        image.setImageResource(getResources().getIdentifier(create(dishes_name).get(0).get(0),"drawable", "com.example.androidproject"));
        introduction.setText(create(dishes_name).get(1).get(0));
        name.setText(dishes_name);
        price.setText(dishes_price);
    }
    private void bindViews() {
        name=findViewById(R.id.name);
        price=findViewById(R.id.price);
        image=findViewById(R.id.image);
        introduction=findViewById(R.id.introduction);
    }
    private List<List<String>>  create(String dishes_name){
        FutureTask<List<List<String>>> futureTask = new FutureTask<>(new Callable<List<List<String>>>() {
            @Override
            public List call() throws Exception {
                List<List<String>> list = new ArrayList<>();
                List<String> list1 = new ArrayList<>();
                List<String> list2 = new ArrayList<>();
                String sql1 = "select order_introduction ,order_picture from order1 where order_name='"+dishes_name+"'";
                ResultSet resultSet = JDBCUtils.query(sql1);
                try {
                    if(resultSet.next()) {
                        list1.add(resultSet.getString("order_picture"));
                        list2.add(resultSet.getString("order_introduction"));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                list.add(list1);
                list.add(list2);
                JDBCUtils.close();
                return list;
            }
        });
        new Thread(futureTask).start();
        List<List<String>> list=null;
        try {
            list= futureTask.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            return list;
        }

    }
}
