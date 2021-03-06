package com.example.androidproject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class K_Server extends AppCompatActivity {
    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if(msg.what == 1){
                Toast.makeText(K_Server.this,"已提交给客服，感谢您的支持",Toast.LENGTH_SHORT).show();
            }else if(msg.what == 2){
                Toast.makeText(K_Server.this,"提交失败，请检查网络稍后再试",Toast.LENGTH_SHORT).show();
            }

        }
    };
    Button button;
    EditText editText;
    String message = "",name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        name = UserInfo.getInstance().username;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);
        button = findViewById(R.id.serverbutton);
        editText = findViewById(R.id.servertextView);
        message = editText.getText().toString();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FutureTask<Integer> task = new FutureTask<>(new Callable<Integer>() {
                    @Override
                    public Integer call() throws Exception {
                        String sql = "update user set suggestion = '" + message + "' where username = '" + name +"';";
                        int update = JDBCUtils.update(sql);
                        JDBCUtils.close();
                        return update;
                    }
                });
                new Thread(task).start();
                int get = 0;
                try {
                    get = task.get();
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
                if(get != 0){
                   handler.sendEmptyMessage(1);
                }else{
                    handler.sendEmptyMessage(2);
                }
                editText.setText("");
            }
        });
    }
}