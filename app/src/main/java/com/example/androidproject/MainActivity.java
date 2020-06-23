package com.example.androidproject;


import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public  class MainActivity extends AppCompatActivity {
    UserInfo instance = UserInfo.getInstance();
    public String name = "test1";
    Recommend recommend;
    Setting setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        instance.username = this.name;


        recommend = new Recommend();
        setting = new Setting();
        UserInfo instance = UserInfo.getInstance();
        //final ArrayList<String> list = new ArrayList<>();
        FutureTask<List<String>> future;


        future = new FutureTask<>(new Callable<List<String>>() {
            @Override
            public List<String> call() throws SQLException {
                LinkedList<String> list = new LinkedList();

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
        new Thread(future).start();

        try {
            instance.dishs.addAll(future.get());

        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }


        setContentView(R.layout.activity_main);
        RadioButton radioButton1 = findViewById(R.id.main_find);
        RadioButton radioButton2 = findViewById(R.id.main_message);
        RadioButton radioButton3 = findViewById(R.id.main_setting);
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
