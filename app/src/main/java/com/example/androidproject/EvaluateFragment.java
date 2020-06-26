package com.example.androidproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EvaluateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EvaluateFragment extends Fragment {
    ListView listview;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EvaluateFragment() {

        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EvaluateFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EvaluateFragment newInstance(String param1, String param2) {
        EvaluateFragment fragment = new EvaluateFragment();
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
    public void onStart() {
        super.onStart();
        listview = getActivity().findViewById(R.id.s_list);
        //     List<List<String>> lists = getSong();
        MyListDateAdpter adapter = new MyListDateAdpter();
        listview.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_evaluate, container, false);

    }

    public List<Evaluate_item> getSong(){
        FutureTask<List<Evaluate_item>> futureTask = new FutureTask<>(new Callable<List<Evaluate_item>>() {
            @Override
            public List<Evaluate_item> call() throws Exception {
                List<Evaluate_item> ldate = new LinkedList<>();
                int i = 1;
                String sql1 = "select * from evaluate where business='鸡公煲' ";
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
    class MyListDateAdpter extends BaseAdapter {
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
                convertView = View.inflate(getActivity(),R.layout.s_list_item,null);
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