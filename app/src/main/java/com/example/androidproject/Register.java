package com.example.androidproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.transform.Result;

public class Register extends AppCompatActivity implements View.OnClickListener{
    private EditText u_username;
    private EditText u_password;
    private Button   u_register;
    private Button   u_login;
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
                    public void run() {
                        String sql = "select * where username='"+u_username+"'";
                        String sql1 = "INSERT INTO user(username,password) VALUES('"+u_username.getText().toString()+"','"+u_password.getText().toString()+"')";
                        ResultSet resultSet=JDBCUtils.query(sql);
                        /*try {
                            if(resultSet.next()){
                                Looper.prepare();
                                Toast.makeText(Register.this, "该用户名已存在！", Toast.LENGTH_SHORT).show();
                                Looper.loop();
                            }else{
                                JDBCUtils.update(sql1);
                                Intent intent1=new Intent(Register.this,Login.class);
                                startActivity(intent1);
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }*/
                        JDBCUtils.update(sql1);
                        JDBCUtils.close();
                    }
                }).start();

                break;
        }
    }
}
