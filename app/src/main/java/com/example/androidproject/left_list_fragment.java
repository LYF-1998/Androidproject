package com.example.androidproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link left_list_fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link left_list_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class left_list_fragment extends Fragment {
    public static final String TAG = "MyFragment";
    private String str;
    private ListView listView;
    private  int mshop_id;
    private  int mclass_id;
    private int[] icons={R.mipmap.baizhanji,R.mipmap.disanxian,
            R.mipmap.duojiao,R.mipmap.ganguotudou,R.mipmap.suancaiyu,R.mipmap.meicaikourou};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)  {
        View view = inflater.inflate(R.layout.fragment_left_list_fragment, null);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        str = getArguments().getString(TAG);
        tv_title.setText(str);
        mshop_id=getArguments().getInt("shop_id");
        mclass_id=getArguments().getInt("class_id");
        listView = (ListView)view.findViewById(R.id.right_list);
        List<command_item> Data=crealist();
        listView.setAdapter(new RightAdapter(getActivity(), Data));
        return view;
    }
    private List<command_item> crealist(){
        final FutureTask<List<command_item>> futureTask = new FutureTask<>(new Callable<List<command_item>>() {
            @Override
            public List<command_item> call() throws Exception {
                List<command_item> mData = new LinkedList<>();
                //Random r=new Random();
                //int i = r.nextInt(5);
                int i=mclass_id;
                String sql1 = "SELECT order1.order_name,order1.order_price,order1.order_quantity "+
                "FROM shop "+
                "INNER JOIN class "+
                "ON shop.shop_id=class.shop_id "+
                "INNER JOIN order1 "+
                "ON order1.order_id=class.order_id "+
                "WHERE class_id='"+mclass_id+"' and shop.shop_id='"+mshop_id+"'";
                ResultSet resultSet = JDBCUtils.query(sql1);
                while (resultSet.next()) {
                    mData.add(new command_item(resultSet.getString(
                            "order_name"),"单价"+resultSet.getString("order_price")+"元",
                            "月销量"+resultSet.getString("order_quantity")+"份",
                            icons[i]));
                    i++;
                }
                return mData;

            }
        });
        new Thread(futureTask).start();
        List<command_item> Data = null;
        try {
            Data = futureTask.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            return Data;
        }

    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String order_name = crealist().get(i).getName();
                String order_price = crealist().get(i).getPrice();
                String order_quantity = crealist().get(i).getHot();
                //int quantity = new get_StringNum(order_quantity).get();
                int price = new get_StringNum(order_price).get();
                Content.order_dishes=Content.order_dishes+order_name;
                Content.order_quantity=Content.order_quantity+order_name+"1份 ";
                Content.Allorder_price=Content.Allorder_price+price;
                Toast.makeText(getActivity(),Content.order_quantity+"总计"+String.valueOf(Content.Allorder_price)
                        +"元",Toast.LENGTH_LONG).show();
                //Log.v("价格", String.valueOf(Content.Allorder_price));
            }
        });
    }
}
