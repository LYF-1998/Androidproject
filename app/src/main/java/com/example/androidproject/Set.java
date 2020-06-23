package com.example.androidproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Set extends AppCompatActivity {
    private EditText phone,address;
    private Button commit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        final UserInfo instance = UserInfo.getInstance();
        phone = findViewById(R.id.set_numberedit);
        address = findViewById(R.id.set_addressedit);
        commit = findViewById(R.id.set_commit);
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instance.phone = Set.this.phone.getText().toString();
                instance.address = Set.this.address.getText().toString();
                FutureTask<Integer> futureTask =  new FutureTask<Integer>(new Callable() {
                    @Override
                    public Integer call() {
                        String updateSQL = "update user set phone = '"+ instance.phone +"',"
                                + "address = '" + instance.address +"' where username = '"
                                + instance.username +"'";
                        int update = JDBCUtils.update(updateSQL);
                        return update;
                    }
                });
                new Thread(futureTask).start();
                int status = 0;
                try {
                    status = futureTask.get();
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
                if (status == 0){
                    Toast.makeText(Set.this,"更新失败，请检查网络并稍后再试",Toast.LENGTH_SHORT);
                }else{
                    Toast.makeText(Set.this,"更新个人信息成功！！！",Toast.LENGTH_SHORT);
                }
                phone.setText("");
                address.setText("");
            }
        });
    }
}