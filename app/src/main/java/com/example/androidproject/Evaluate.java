package com.example.androidproject;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Evaluate extends AppCompatActivity {
    private ListView listview;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate);
        listview = findViewById(R.id.s_list);
   //     List<List<String>> lists = getSong();
        MyListDateAdpter adapter = new MyListDateAdpter();
        listview.setAdapter(adapter);
        //   list.setAdapter(new Sadapter(Evaluate.this));
    }
    /*
  public List<List<String>> getSong() {
        FutureTask<List<List<String>>> futureTask = new FutureTask<>(new Callable<List<List<String>>>() {
            @Override
            public List call() throws Exception {
                List<List<String>> list = new ArrayList<>();
                List<String> list1 = new ArrayList<>();
                List<String> list2 = new ArrayList<>();
             //   List<String> list3 = new ArrayList<>();
                String sql = "select * from evaluate";
                ResultSet resultSet = JDBCUtils.query(sql);
                while (resultSet.next()) {
                    String user = resultSet.getString("user");
                    String evaluate = resultSet.getString("evaluate");
                    list1.add(user);
                    list2.add(evaluate);
                }
                list.add(list1);
                list.add(list2);
                return list;
            }
        });
        new Thread(futureTask).start();
        //   list = findViewById(R.id.s_list);
        List<List<String>> lists = null;
        try {
            lists = futureTask.get();

        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close();
        }
        return null;
    }
*/


    public  List<Evaluate_item> getSong(){
        FutureTask<List<Evaluate_item>> futureTask = new FutureTask<>(new Callable<List<Evaluate_item>>() {
            @Override
            public List<Evaluate_item> call() throws Exception {
                List<Evaluate_item> ldate = new LinkedList<>();
                int i = 1;
                String sql1 = "select * from evaluate";
                ResultSet resultSet = JDBCUtils.query(sql1);
                while (resultSet.next()) {
                    ldate.add(new Evaluate_item(resultSet.getString("user"),resultSet.getString("evaluate") ));
                    i++;
                }
                JDBCUtils.close();
                return ldate;
            }
        }
        );
        new Thread(futureTask).start();
        //new Thread(futureTask).start();
        List<Evaluate_item> date = null;
        try {
             date = futureTask.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return date;
    }
        // textview.setText(lists.get(1).get(0));
        class MyListDateAdpter extends BaseAdapter{
        //    private LinkedList<command_item> mData;
           List<Evaluate_item> lists = getSong();
            public int getCount() {
                return lists.size();
            }

            @Override
            public Object getItem(int position) {
                return lists.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                MyViewHolder holder;
                if(convertView==null){
                    convertView = View.inflate(Evaluate.this,R.layout.s_list_item,null);
                    holder = new MyViewHolder();
                    holder.tv1 = convertView.findViewById(R.id.textView);
                    holder.tv2 = convertView.findViewById(R.id.textView2);
                    holder.tv1.setText(lists.get(position).getUser());
                    holder.tv2.setText(lists.get(position).getEvaluate());
                    convertView.setTag(holder);
                }else{
                    holder = (MyViewHolder) convertView.getTag();
                }
               // List<List<String>> lists = getSong();
             //   holder.tv1.setText(lists.get(0).get(0));
               // holder.tv2.setText(lists.get(0).get(1));

                return convertView;
            }
            class MyViewHolder{
                public TextView tv1,tv2;
            }
        }
    }


