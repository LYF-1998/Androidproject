package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Myorder extends AppCompatActivity {
    private ListView listview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myorder);
        listview = findViewById(R.id.s_orderlist);
        MyListDateAdpter adapter = new MyListDateAdpter();
        listview.setAdapter(adapter);
    }

    public List<Myorder_item> getSong(){
        FutureTask<List<Myorder_item>> futureTask = new FutureTask<>(new Callable<List<Myorder_item>>() {
            @Override
            public List<Myorder_item> call() throws Exception {
                List<Myorder_item> ldate = new LinkedList<>();
                int i = 1;
                String sql1 = "select * from dishes where username='lyf' ";
                ResultSet resultSet = JDBCUtils.query(sql1);
                while (resultSet.next()) {
                    ldate.add(new Myorder_item(resultSet.getString("ds"),resultSet.getString("price") ,resultSet.getString("time")));
                    i++;
                }
                JDBCUtils.close();
                return ldate;
            }
        }
        );
        new Thread(futureTask).start();
        //new Thread(futureTask).start();
        List<Myorder_item> dishes = null;
        try {
            dishes = futureTask.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return dishes;
    }
    // textview.setText(lists.get(1).get(0));
    class MyListDateAdpter extends BaseAdapter {
        //    private LinkedList<command_item> mData;
        List<Myorder_item> dish = getSong();
        public int getCount() {
            return dish.size();
        }

        @Override
        public Object getItem(int position) {
            return dish.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MyViewHolder holder;
            if(convertView==null){
                convertView = View.inflate(Myorder.this,R.layout.s_oredr_list,null);
                holder = new MyViewHolder();
                holder.tv1 = convertView.findViewById(R.id.textView19);
                holder.tv2 = convertView.findViewById(R.id.textView20);
                holder.tv3 = convertView.findViewById(R.id.textView21);
                holder.tv1.setText(dish.get(position).getPrice());
                holder.tv2.setText(dish.get(position).getTime());
                holder.tv3.setText(dish.get(position).getDs());
                convertView.setTag(holder);
            }else{
                holder = (MyViewHolder) convertView.getTag();
            }

            return convertView;
        }
        class MyViewHolder{
            public TextView tv1,tv2,tv3;
        }
    }

}

