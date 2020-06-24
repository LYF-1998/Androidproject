package com.example.androidproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class search extends AppCompatActivity {
    private Context mContext;
    private CommandAdapter mAdapter = null;
    private ListView listView;
    private String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Intent intent = getIntent();
        name = intent.getStringExtra("search_text");
        try {
            creatList();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Toast.makeText(this, name, Toast.LENGTH_SHORT).show();

    }
    private void creatList() throws ExecutionException, InterruptedException {
        mContext = search.this;
        listView = (ListView)findViewById(R.id.search_list);
        //int inis = 0;
        FutureTask<List<command_item>> futureTask = new FutureTask<>(new Callable<List<command_item>>() {
            @Override
            public List<command_item> call() throws Exception {
                List<command_item> mData = new LinkedList<>();
                int i = 1;
                String sql1 ="SELECT order1.order_name,order1.order_price,shop.shop_name,order1.order_picture "+
                        "FROM shop "+
                        "INNER JOIN class "+
                        "ON shop.shop_id=class.shop_id "+
                        "INNER JOIN order1 "+
                        "ON order1.order_id=class.order_id "+
                        "WHERE order_name like '%"+name+"%'";
                ResultSet resultSet = JDBCUtils.query(sql1);
                while (resultSet.next()) {
                    String p=resultSet.getString("order_picture");
                    int id = getResources().getIdentifier(p,"mipmap", "com.example.androidproject");
                    mData.add(new command_item(resultSet.getString(
                            "order_name"),"价格："+resultSet.getInt("order_price")+"￥",
                            "商家："+resultSet.getString("shop_name"),
                            id));
                    i++;
                }

                return mData;
            }
        });
        new Thread(futureTask).start();
        final List<command_item> mData = futureTask.get();
        mAdapter = new CommandAdapter((LinkedList<command_item>) mData, mContext);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final String shop_name = mData.get(i).getHot();
                int mshop_id=getId(shop_name);
                //Toast.makeText(Command.this,dishes_name,Toast.LENGTH_LONG).show();
                Bundle bundle = new Bundle();
                bundle.putString("shop_name", shop_name);
                bundle.putInt("shop_id",mshop_id);
                Intent intent = new Intent(search.this,Order.class);
                intent.putExtras(bundle);
                //finish();
                startActivity(intent);
            }
        });
    }
    private int getId(String name){
        FutureTask<Integer> futureTask = new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                int mshop_id = 0;
                final String name=null;
                String sql ="SELECT shop_id from shop where shop_name='"+name+"'";
                ResultSet resultSet = JDBCUtils.query(sql);
                while (resultSet.next()) {
                    mshop_id=resultSet.getInt("shop_id");
                }

                return mshop_id;
            }
        });
        new Thread(futureTask).start();
        int shop_id=0;
        try {
            shop_id= futureTask.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return shop_id;
    }
}
