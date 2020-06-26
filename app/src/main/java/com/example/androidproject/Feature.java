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

public class Feature extends AppCompatActivity {

//    private ImageView iv;
//    private TextView tv1,tv2,tv3;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feature);
//        iv = findViewById(R.id.imageView3);
//        tv1 = findViewById(R.id.textView3);
//        tv2 = findViewById(R.id.textView4);
//        tv3 = findViewById(R.id.textView5);
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
//                    String feature = resultSet.getString("feature");
//                    String feature_price = resultSet.getString("feature_price");
//                    String feature_information = resultSet.getString("feature_information");
//                    String feature_name = resultSet.getString("feature_name");
//                    list1.add(business);
//                    list2.add(feature);
//                    list3.add(feature_name);
//                    list4.add(feature_price);
//                    list5.add(feature_information);
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
//        tv1.setText(lists.get(2).get(0));
//        tv2.setText(lists.get(3).get(0));
//        tv3.setText(lists.get(4).get(0));
//        String url= lists.get(1).get(0);
//        switch (url){
//            case "口水鸡":
//                iv.setImageResource(R.drawable.koushuiji);
//               // iv.setImageResource(R.drawable.address);
//                break;
//            case"蒸羊羔":
//                iv.setImageResource(R.drawable.zhengyanggao);
//                break;
//        }
    }
}
