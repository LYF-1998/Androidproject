package com.example.androidproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.ResultSet;
import java.sql.SQLException;


public class Login extends AppCompatActivity implements View.OnClickListener{
    private final int SUCCESS = 0x01;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == SUCCESS){

            }
        }
    };

    private EditText u_username;
    private EditText u_password;
    private Button   u_login;
    private Button   u_register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bindViews();
    }
    private void bindViews() {
         u_username= (EditText) findViewById(R.id.u_usename);
         u_password= (EditText) findViewById(R.id.u_password);
         u_login = (Button) findViewById(R.id.u_login);
         u_register= (Button) findViewById(R.id.u_register);
         u_username=(EditText) findViewById(R.id.u_usename);
         u_password=(EditText) findViewById(R.id.u_password);
         u_login.setOnClickListener(this);
         u_register.setOnClickListener(this);
    }
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.u_login:
        new Thread(new Runnable() {
            @Override
            public void run() {
                String sql1 = "select * from user where username='"+u_username.getText().toString()
                        +"' and password='"+u_password.getText().toString()+"'";
                ResultSet resultSet = JDBCUtils.query(sql1);
                try {
                    if(resultSet.next()) {
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        //intent.putExtra("u_username",u_username.getText().toString());
                        UserInfo instance = UserInfo.getInstance();
                        instance.setUsername(u_username.getText().toString());
                        startActivity(intent);

                        // Toast.makeText(getApplicationContext(),"欢迎用户"+u_username+"！",Toast.LENGTH_LONG).show();
                    }
                    else {
                        Looper.prepare();
                        Toast.makeText(getApplicationContext(), "用户名或密码错误！", Toast.LENGTH_LONG).show();
                        Looper.loop();
                        u_username.setText("");
                        u_password.setText("");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                JDBCUtils.close();
            }
            }).start();
        break;
        case R.id.u_register:
         Intent intent = new Intent(Login.this,Register.class);
         startActivity(intent);
        break;
    }
    }
}