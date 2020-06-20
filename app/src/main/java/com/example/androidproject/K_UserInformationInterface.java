package com.example.androidproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class K_UserInformationInterface extends AppCompatActivity {
    Button button1,button2,button3,button4;
    TextView username,number;
    ArrayList<String> list = new ArrayList<>();
    FutureTask<List<String>> future;
    public static String name = "test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinformationinterface);

        future = new FutureTask<>(new Callable<List<String>>() {
            @Override
            public List<String> call() throws SQLException {

                String sql = "select phone from user where username = '" + name + "';";
                ResultSet resultSet = JDBCUtils.query(sql);
                resultSet.next();
                String number = resultSet.getString("phone");
                list.add(name);
                list.add(number);
                JDBCUtils.close();
                return list;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        new Thread(future).start();
        button1 = findViewById(R.id.orderbutton);
        button2 = findViewById(R.id.addbutton);
        button3 = findViewById(R.id.collectionbutton);
        button4 = findViewById(R.id.serverbutton);
        username = findViewById(R.id.username);
        number = findViewById(R.id.number);
        List<String> list = null;
        try {
            list = future.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        //Toast.makeText(this,String.valueOf(list == null),Toast.LENGTH_LONG).show();
        username.setText(list.get(0));
        number.setText(list.get(1));
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(userInformationInterface.this,"button1",Toast.LENGTH_SHORT).show();
                //订单页面
                Intent intent = new Intent(K_UserInformationInterface.this, K_BusinessOrder.class);
                startActivity(intent);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(userInformationInterface.this,"button2",Toast.LENGTH_SHORT).show();
                //地址页面
                Intent intent = new Intent(K_UserInformationInterface.this, K_Address.class);
                startActivity(intent);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(UserInformationInterface.this,"button3",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(K_UserInformationInterface.this, K_Collection.class);
                startActivity(intent);
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(UserInformationInterface.this,"button4",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(K_UserInformationInterface.this, K_Server.class);
                startActivity(intent);
            }
        });
    }
}