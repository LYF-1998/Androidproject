package com.example.androidproject;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public  class MainActivity extends AppCompatActivity {
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private RadioButton radioButton3;
    public static String name = "";
    Recommend recommend;
    Setting setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext

        recommend = new Recommend();
        setting = new Setting();
        UserInfo instance = UserInfo.getInstance();
        //final ArrayList<String> list = new ArrayList<>();



        setContentView(R.layout.activity_main);
        radioButton1 = findViewById(R.id.main_find);
        radioButton2 = findViewById(R.id.main_message);
        radioButton3 = findViewById(R.id.main_setting);
        radioButton1.setOnClickListener(onClickListener);
        radioButton2.setOnClickListener(onClickListener);
        radioButton3.setOnClickListener(onClickListener);

    }
    View.OnClickListener onClickListener =new View.OnClickListener() {
        //FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.main_find:
//                    fragmentTransaction.replace(R.id.fragment,new Recommend());
//                    fragmentTransaction.commit();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment,recommend).commit();

                    break;
                case R.id.main_message:

                    break;
                case R.id.main_setting:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment,new Setting()).commit();
//                    fragmentTransaction.commit();
//                    fragmentTransaction.add(recommend,null).hide(recommend).show(setting).commit();
                    break;
                default:
                    break;
            }

        }
    };
}
