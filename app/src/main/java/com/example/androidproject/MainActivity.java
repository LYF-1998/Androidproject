package com.example.androidproject;


import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

public  class MainActivity extends AppCompatActivity {
    //public static String name = "";
    Recommend recommend;
    Setting setting;
    MyOrder_Fragment myOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext

        recommend = new Recommend();
        setting = new Setting();
        myOrder = new MyOrder_Fragment();
        UserInfo instance = UserInfo.getInstance();
        instance.username = "test";
        //final ArrayList<String> list = new ArrayList<>();

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
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment,myOrder).commit();

                    break;
                case R.id.main_setting:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment,setting).commit();
//                    fragmentTransaction.commit();
//                    fragmentTransaction.add(recommend,null).hide(recommend).show(setting).commit();
                    break;
                default:
                    break;
            }
        }
    };
}
