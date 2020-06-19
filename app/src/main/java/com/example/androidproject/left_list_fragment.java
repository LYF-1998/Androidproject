package com.example.androidproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;
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
        FutureTask<List<command_item>> futureTask = new FutureTask<>(new Callable<List<command_item>>() {
            @Override
            public List<command_item> call() throws Exception {
                List<command_item> mData = new LinkedList<>();
                int i = 1;
                /*String sql1 = "select order_name,order_price,order_quantity " +
                        "from( table class left join table order1" +
                        "on class.order_id=order1.order_id" +
                        "where class_id="+mclass_id+"and shop_id="+mshop_id+"";*/
                /*String sql1="select shop_name,shop_quantity from shop where" +
                        "shop_id="+mshop_id+"";*/
                String sql1="select * from shop where shop_id='"+mshop_id+"'";
                ResultSet resultSet = JDBCUtils.query(sql1);
                while (resultSet.next()) {
                    mData.add(new command_item(resultSet.getString(
                            "shop_name"),"配送费5￥",
                            resultSet.getString("shop_quantity"),
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
}
