package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class OrderActivity extends AppCompatActivity  {
    private Button button1,button2,button3,button4;
    private int shop_id;
    private OrderFragment myFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order2);
        button1 = findViewById(R.id.order_button1);
        button2 = findViewById(R.id.order_button2);
        button3 = findViewById(R.id.order_button3);
        button4 = findViewById(R.id.order_button4);
        button1.setOnClickListener(onClickListener);
        button2.setOnClickListener(onClickListener);
        button3.setOnClickListener(onClickListener);
        button4.setOnClickListener(onClickListener);

        Bundle b=getIntent().getExtras();
        shop_id=b.getInt("shop_id");
        TextView name= findViewById(R.id.shop_name);

        String info=b.getString("shop_name");
        name.setText(info);
        //name.setTextColor(030303);
        myFragment = new OrderFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction();
        fragmentTransaction.replace(R.id.order_fragment,myFragment);
        Bundle bundle = new Bundle();
        bundle.putInt("shop_id", shop_id);
        myFragment.setArguments(bundle);
        fragmentTransaction.commit();
    }


    View.OnClickListener onClickListener =new View.OnClickListener() {
        //FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.order_button1:
                    myFragment = new OrderFragment();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                            .beginTransaction();
                    fragmentTransaction.replace(R.id.order_fragment,myFragment);
                    Bundle bundle = new Bundle();
                    bundle.putInt("shop_id", shop_id);
                    myFragment.setArguments(bundle);
                    fragmentTransaction.commit();
                    break;
                case R.id.order_button2:
                    getSupportFragmentManager().beginTransaction().replace(R.id.order_fragment,new EvaluateFragment()).commit();

                    break;
                case R.id.order_button3:
                    getSupportFragmentManager().beginTransaction().replace(R.id.order_fragment,new InformationFragment()).commit();
                    break;
                case  R.id.order_button4:
                    getSupportFragmentManager().beginTransaction().replace(R.id.order_fragment,new Featurefragment()).commit();
                    break;
                default:
                    break;
            }
        }
    };
}