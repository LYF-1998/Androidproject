package com.example.androidproject;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;


public class Login extends AppCompatActivity implements View.OnClickListener{
    private final int SUCCESS = 0x01;
    private CheckBox auto_login,remember;
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
//    private SharedPreferences sp;
    private  SharedHelper sp;
//    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bindViews();
//        mContext = getApplicationContext();
        sp = SharedHelper.getInstance();

           /* if (sp.getBoolean("ISCHECK", false)) {
                remember.setChecked(true);
                    u_username.setText(sp.getString("USER_NAME", ""));
                    u_password.setText(sp.getString("PASSWORD", ""));
                    if (sp.getBoolean("AUTO_ISCHECK", false)) {
                        auto_login.setChecked(true);
                        //Intent intent = new Intent(Login.this, MainActivity.class);
                        //startActivity(intent);
                    }
            }*/
        if(sp.check_f("ISCHECK")){
            remember.setChecked(true);
            Map<String,String> data = sp.read();
            u_username.setText(data.get("username"));
            u_password.setText(data.get("passwd"));
            if(sp.check_f("AUTO_ISCHECK")){
                auto_login.setChecked(true);
                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
            }
        }
        remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if (remember.isChecked()) {
                    Toast.makeText(Login.this, "记住密码已选中", Toast.LENGTH_SHORT).show();
                    sp.put_true("ISCHECK");
                    //sp.edit().putBoolean("ISCHECK", true).commit();
                }else {
                    //sp.edit().putBoolean("ISCHECK", false).commit();
                    sp.put_false("ISCHECK");
                }
            }
        });
        //监听自动登录多选框事件
        auto_login.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if (auto_login.isChecked()) {
                    Toast.makeText(Login.this, "自动登陆已选中", Toast.LENGTH_SHORT).show();
                    //sp.edit().putBoolean("AUTO_ISCHECK", true).commit();
                    sp.put_true("AUTO_ISCHECK");
                } else {
                    // sp.edit().putBoolean("AUTO_ISCHECK", false).commit();
                    sp.put_false("AUTO_ISCHECK");
                }
            }
        });
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
        auto_login=(CheckBox) findViewById(R.id.auto_login);
        remember=(CheckBox)findViewById(R.id.remember);
        // sp = getSharedPreferences("userInfo", MODE_WORLD_READABLE);
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
                                /*if(remember.isChecked())
                                {
                                    //记住用户名、密码、
                                    SharedPreferences.Editor editor = sp.edit();
                                    editor.putString("USER_NAME", u_username.getText().toString());
                                    editor.putString("PASSWORD",u_password.getText().toString());
                                    editor.commit();
                                }*/
                                if(remember.isChecked()) {
                                    sp.save(u_username.getText().toString(), u_password.getText().toString());
                                }
                                //Map<String,String> data = sp.read();
                                //Log.v("@@@@@@",data.get("username")+data.get("passwd"));
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