package com.example.androidproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Register extends AppCompatActivity implements View.OnClickListener{
    private EditText u_username;
    private EditText u_password;
    private Button   u_register;
    private Button   u_login;
    private final int SUCCESS = 0x01;
    private final int FAILD = 0X00;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == FAILD){
                u_username.setText("");
                u_password.setText("");
                Toast.makeText(Register.this, "该用户名已存在！", Toast.LENGTH_SHORT).show();
            }else if (msg.what == SUCCESS){
                Toast.makeText(Register.this,"注册成功！快去登陆吧~~",Toast.LENGTH_SHORT).show();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
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
            Intent intent = new Intent(Register.this,Login.class);
            startActivity(intent);
                break;
            case R.id.u_register:
                new Thread(new Runnable() {
                    @Override
                    public synchronized void run() {
                        String sql = "select * from user where username='"
                                + u_username.getText().toString() + "'";
                        ResultSet resultSet = JDBCUtils.query(sql);
                        try {
                            boolean flag = resultSet.next();
                            Log.i("**************", String.valueOf(flag));
                            if(flag){
                                handler.sendEmptyMessage(FAILD);
//                                Looper.prepare();
//                                Toast.makeText(Register.this, "该用户名已存在！", Toast.LENGTH_SHORT).show();
//                                Looper.loop();
                            }else{
                                String sql1 = "INSERT INTO user(username,password) VALUES('"
                                        +u_username.getText().toString()+"','"
                                        +u_password.getText().toString()+"')";
                                handler.sendEmptyMessage(SUCCESS);
//                                Looper.prepare();
//                                Toast.makeText(Register.this,"注册成功！快去登陆吧~~",Toast.LENGTH_SHORT).show();
//                                Looper.loop();
                                JDBCUtils.update(sql1);
                                Intent intent1=new Intent(Register.this,Login.class);
                                startActivity(intent1);
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }finally {
                            //JDBCUtils.update(sql1);
                            JDBCUtils.close();
                        }
                    }
                }).start();
                break;
        }
    }
}
