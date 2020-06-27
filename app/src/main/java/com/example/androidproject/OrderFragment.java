package com.example.androidproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderFragment extends Fragment implements AdapterView.OnItemClickListener{

    private String[] strs = { "热销", "优惠", "本店特色", "主食", "饮品酒水" };
    private ListView listView;
    private LeftAdapter adapter;
    private right_list_fragment myFragment;
    public static int mPosition;
    private int shop_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order, null);
        TextView name=(TextView)getActivity().findViewById(R.id.shop_name);
        //获取Bundle的信息
       String info=getArguments().getString("shop_name");
        shop_id=getArguments().getInt("shop_id");
        name.setHint(info);
        listView = (ListView) view.findViewById(R.id.left_list);
        adapter = new LeftAdapter(getActivity(), strs);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((AdapterView.OnItemClickListener) this);
        //创建MyFragment对象
        myFragment = new right_list_fragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager()
                .beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, myFragment);
        Bundle bundle = new Bundle();
        bundle.putInt("shop_id", shop_id);
        myFragment.setArguments(bundle);
        fragmentTransaction.commit();
        //initView();
        return view;
    }
    /*private void initView() {
        // TODO Auto-generated method stub
        TextView name=(TextView)getActivity().findViewById(R.id.shop_name);
        //获取Bundle的信息
        String info=getArguments().getString("shop_name");
        shop_id=getArguments().getInt("shop_id");
        name.setText(info);
        listView = (ListView) getView().findViewById(R.id.left_list);
        adapter = new LeftAdapter(getActivity(), strs);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((AdapterView.OnItemClickListener) this);
        //创建MyFragment对象
        myFragment = new right_list_fragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager()
                .beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, myFragment);
        Bundle bundle = new Bundle();
        bundle.putInt("shop_id", shop_id);
        myFragment.setArguments(bundle);
        fragmentTransaction.commit();
    }*/
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // TODO Auto-generated method stub
        //拿到当前位置
        mPosition = position;
        //即使刷新adapter
        adapter.notifyDataSetChanged();
        for (int i = 0; i < strs.length; i++) {
            myFragment = new right_list_fragment();
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager()
                    .beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, myFragment);
            Bundle bundle1 = new Bundle();
            bundle1.putString(right_list_fragment.TAG, strs[position]);
            //bundle.putInt("shop_id", shop_id);
            //bundle.putInt("class_id",mPosition);
            bundle1.putInt("class_id",mPosition);
            myFragment.setArguments(bundle1);
            fragmentTransaction.commit();
        }
    }
}