package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class OrderActivity extends AppCompatActivity {
    private Button button1,button2,button3,button4;
    private int shop_id;

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
    }

    View.OnClickListener onClickListener =new View.OnClickListener() {
        //FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.order_button1:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment,new OrderFragment()).commit();
                    break;
                case R.id.order_button2:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment,new EvaluateFragment()).commit();

                    break;
                case R.id.order_button3:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment,new InformationFragment()).commit();
                    break;
                case  R.id.order_button4:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment,new Featurefragment()).commit();
                    break;
                default:
                    break;
            }
        }
    };
}