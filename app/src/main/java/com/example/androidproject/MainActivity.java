package com.example.androidproject;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.ResultSet;
import java.sql.SQLException;


public  class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button2);

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
//                        String mu_username=u_username.getText().toString();
//                        String mu_password=u_password.getText().toString();
                        String sql1 = "INSERT INTO user VALUES('mu_username1','mu_password')";
                        JDBCUtils.update(sql1);
                        JDBCUtils.close();
                    }
                }).start();
            }
        });
//        button.setOnClickListener(new View.OnClickListener(){
//
//            @Override
//            public void onClick(View v) {
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        String sql1 = "SELECT id, name, url FROM websites";
//                        ResultSet resultSet = JDBCUtils.query(sql1);
//                        try{
//                            while(resultSet.next()){
//                                int id  = resultSet.getInt("id");
//                                String name = resultSet.getString("name");
//                                String url = resultSet.getString("url");
//                                String str = id + "******" + name + "*********" + url;
//                                // 输出数据
//                                //System.out.println(id + "******" + name + "*********" + url);
//                                Log.i("eeeeee",str);
//                            }
//
//                        } catch (SQLException e) {
//                            e.printStackTrace();
//                        }finally {
//                            JDBCUtils.close();
//                        }
//                    }
//                }).start();
//            }
//        });

    }
//    public void test1()  {
//        String sql1 = "SELECT id, name, url FROM websites";
//        ResultSet resultSet = JDBCUtils.query(sql1);
//        try{
//            while(resultSet.next()){
//                int id  = resultSet.getInt("id");
//                String name = resultSet.getString("name");
//                String url = resultSet.getString("url");
//                String str = id + "******" + name + "*********" + url;
//                // 输出数据
//                //System.out.println(id + "******" + name + "*********" + url);
//                Log.i("eeeeee",str);
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }finally {
//            JDBCUtils.close();
//        }
//
//    }


}
