package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Order extends AppCompatActivity {
private ListView listview;
    private Context mContext;
    private CommandAdapter mAdapter = null;
    private ListView listView1;
    private int[] icons={R.mipmap.baizhanji,R.mipmap.disanxian,R.mipmap.duojiao,
            R.mipmap.ganguotudou,R.mipmap.suancaiyu,R.mipmap.meicaikourou,
            R.mipmap.ganguotudou,R.mipmap.suancaiyu,R.mipmap.meicaikourou,
            R.mipmap.baizhanji,R.mipmap.disanxian,R.mipmap.suancaiyu,R.mipmap.meicaikourou,
            R.mipmap.baizhanji,R.mipmap.disanxian,R.mipmap.suancaiyu,R.mipmap.meicaikourou,
            R.mipmap.baizhanji,R.mipmap.disanxian,R.mipmap.suancaiyu,R.mipmap.meicaikourou,
            R.mipmap.baizhanji,R.mipmap.disanxian};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        /*TextView shop_name=(TextView)findViewById(R.id.shop_name);
        Bundle b=getIntent().getExtras();
        //获取Bundle的信息
        String name=b.getString("shop_name");
        shop_name.setText(name);*/
        creatList1();
    }

private void creatList1()  {
    listview = (ListView)findViewById(R.id.right_list);
    String[] str = {"热销", "优惠", "本店特色", "主食", "饮品酒水"};
       /* List<String> listdata = new ArrayList<String>();
        for(int i=0;i<5;i++) {
            listdata.add(str[i]);
        }*/
    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.order_left_item,str);//listdata和str均可
    listview.setAdapter(arrayAdapter);

}


}
