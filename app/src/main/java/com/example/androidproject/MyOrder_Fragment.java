package com.example.androidproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyOrder_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyOrder_Fragment extends Fragment {
    private ListView listview;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MyOrder_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyOrder_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyOrder_Fragment newInstance(String param1, String param2) {
        MyOrder_Fragment fragment = new MyOrder_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_my_order_, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        listview = getActivity().findViewById(R.id.s_orderlist);
        MyListDateAdpter adapter = new MyListDateAdpter();
        listview.setAdapter(adapter);
    }

    public List<Myorder_item> getSong(){
        FutureTask<List<Myorder_item>> futureTask = new FutureTask<>(new Callable<List<Myorder_item>>() {
            @Override
            public List<Myorder_item> call() throws Exception {
                List<Myorder_item> ldate = new LinkedList<>();
                int i = 1;
                String sql1 = "select * from dishes where username='"
                        + UserInfo.getInstance().username + "' ";
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
                convertView = View.inflate(getActivity(),R.layout.s_oredr_list,null);
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