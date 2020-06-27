package com.example.androidproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Featurefragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Featurefragment extends Fragment {
    private ImageView iv;
    private TextView tv1,tv2,tv3;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Featurefragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Featurefragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Featurefragment newInstance(String param1, String param2) {
        Featurefragment fragment = new Featurefragment();
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
        return inflater.inflate(R.layout.fragment_featurefragment, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        iv = getActivity().findViewById(R.id.imageView3);
        tv1 = getActivity().findViewById(R.id.textView3);
        tv2 = getActivity().findViewById(R.id.textView4);
        tv3 = getActivity().findViewById(R.id.textView5);
        FutureTask<List<List<String>>> futureTask = new FutureTask<>(new Callable<List<List<String>>>() {
            @Override
            public List call() throws Exception {
                List<List<String>> list = new ArrayList<>();
                List<String> list1 = new ArrayList<>();
                List<String> list2 = new ArrayList<>();
                List<String> list3 = new ArrayList<>();
                List<String> list4 = new ArrayList<>();
                List<String> list5 = new ArrayList<>();
                String sql = "select * from commodity where business='"
                        + UserInfo.getInstance().shopname + "'";
                ResultSet resultSet = JDBCUtils.query(sql);
                while (resultSet.next()) {
                    String business = resultSet.getString("business");
                    String feature = resultSet.getString("feature");
                    String feature_price = resultSet.getString("feature_price");
                    String feature_information = resultSet.getString("feature_information");
                    String feature_name = resultSet.getString("feature_name");
                    list1.add(business);
                    list2.add(feature);
                    list3.add(feature_name);
                    list4.add(feature_price);
                    list5.add(feature_information);
                }
                list.add(list1);
                list.add(list2);
                list.add(list3);
                list.add(list4);
                list.add(list5);
                JDBCUtils.close();
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
        }
        tv1.setText(lists.get(2).get(0));
        tv2.setText(lists.get(3).get(0));
        tv3.setText(lists.get(4).get(0));
        String url= lists.get(1).get(0);
        switch (url){
            case "口水鸡":
                iv.setImageResource(R.drawable.koushuiji);
                // iv.setImageResource(R.drawable.address);
                break;
            case"蒸羊羔":
                iv.setImageResource(R.drawable.zhengyanggao);
                break;
        }
    }
}