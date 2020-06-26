package com.example.androidproject;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Information extends AppCompatActivity {

//    private ImageView iv;
//    private TextView tv1,tv2,tv3,tv4;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
//        iv = findViewById(R.id.imageView4);
//        tv1 = findViewById(R.id.textView7);
//        tv2 = findViewById(R.id.textView8);
//        tv3 = findViewById(R.id.textView11);
//        tv4 = findViewById(R.id.textView13);
//        FutureTask<List<List<String>>> futureTask = new FutureTask<>(new Callable<List<List<String>>>() {
//            @Override
//            public List call() throws Exception {
//                List<List<String>> list = new ArrayList<>();
//                List<String> list1 = new ArrayList<>();
//                List<String> list2 = new ArrayList<>();
//                List<String> list3 = new ArrayList<>();
//                List<String> list4 = new ArrayList<>();
//                List<String> list5 = new ArrayList<>();
//                String sql = "select * from commodity where business='鸡公煲'";
//                ResultSet resultSet = JDBCUtils.query(sql);
//                while (resultSet.next()) {
//                    String business = resultSet.getString("business");
//                    String name = resultSet.getString("name");
//                    String lei = resultSet.getString("lei");
//                    String address = resultSet.getString("address");
//                    String time = resultSet.getString("time");
//                    list1.add(business);
//                    list2.add(name);
//                    list3.add(lei);
//                    list4.add(address);
//                    list5.add(time);
//                }
//                list.add(list1);
//                list.add(list2);
//                list.add(list3);
//                list.add(list4);
//                list.add(list5);
//                JDBCUtils.close();
//                return list;
//            }
//        });
//        new Thread(futureTask).start();
//        //   list = findViewById(R.id.s_list);
//        List<List<String>> lists = null;
//        try {
//            lists = futureTask.get();
//        } catch (ExecutionException | InterruptedException e) {
//            e.printStackTrace();
//        }
//       tv1.setText(lists.get(0).get(0));
//        tv2.setText(lists.get(3).get(0));
//        tv3.setText(lists.get(2).get(0));
//        tv4.setText(lists.get(4).get(0));
//        String url= lists.get(1).get(0);
//        switch (url){
//            case "鸡公煲":
//                iv.setImageResource(R.drawable.jigongbao);
//                // iv.setImageResource(R.drawable.address);
//                break;
//            case"排骨米饭":
//                iv.setImageResource(R.drawable.paigumifan);
//                break;
//        }
    }
}
