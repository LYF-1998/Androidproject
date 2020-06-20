package com.example.androidproject;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class Order extends FragmentActivity implements AdapterView.OnItemClickListener {
    private String[] strs = { "热销", "优惠", "本店特色", "主食", "饮品酒水" };
    private ListView listView;
    private LeftAdapter adapter;
    private right_list_fragment myFragment;
    public static int mPosition;
    private int shop_id;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        initView();
    }
    /**
     * 初始化view
     */
    private void initView() {
        // TODO Auto-generated method stub
        TextView name=(TextView)findViewById(R.id.shop_name);
        Bundle b=getIntent().getExtras();
        //获取Bundle的信息
        String info=b.getString("shop_name");
        shop_id=b.getInt("shop_id");
        name.setText(info);
        listView = (ListView) findViewById(R.id.left_list);
        adapter = new LeftAdapter(this, strs);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        //创建MyFragment对象
        myFragment = new right_list_fragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, myFragment);
        Bundle bundle = new Bundle();
        bundle.putInt("shop_id", shop_id);

        myFragment.setArguments(bundle);
        fragmentTransaction.commit();
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        // TODO Auto-generated method stub
        //拿到当前位置
        mPosition = position;
        //即使刷新adapter
        adapter.notifyDataSetChanged();
       for (int i = 0; i < strs.length; i++) {
            myFragment = new right_list_fragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager()
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
