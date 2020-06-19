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
    private left_list_fragment myFragment;
    public static int mPosition;
    private int shop_id;
    private left_list_fragment fg1,fg2,fg3,fg4,fg5;
    @Override
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
        myFragment = new left_list_fragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, myFragment);
        Bundle bundle = new Bundle();
        bundle.putInt("shop_id", shop_id);
        bundle.putInt("class_id",mPosition);
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
        //FragmentTransaction fragmentTransaction = getSupportFragmentManager()
        //        .beginTransaction();
       // hideAllFragment(fragmentTransaction);
       for (int i = 0; i < strs.length; i++) {
            myFragment = new left_list_fragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                    .beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, myFragment);
            //fragmentTransaction.hide(fragmentTransaction);
            //fragmentTransaction.show(myFragment);
            Bundle bundle1 = new Bundle();
            bundle1.putString(left_list_fragment.TAG, strs[position]);
            //bundle.putInt("shop_id", shop_id);
            //bundle.putInt("class_id",mPosition);
            myFragment.setArguments(bundle1);
            fragmentTransaction.commit();

       /* switch (mPosition){
            case 0:
                if(fg1 == null){
                    fg1 = new left_list_fragment();
                    fragmentTransaction.add(R.id.ly_content,fg1);
                }else{
                    fragmentTransaction.show(fg1);
                }
                break;
            case 1:
                if(fg2 == null){
                    fg2 = new left_list_fragment();
                    fragmentTransaction.add(R.id.ly_content,fg2);
                }else{
                    fragmentTransaction.show(fg2);
                }
                break;
            case 2:
                if(fg3 == null){
                    fg3 = new left_list_fragment();
                    fragmentTransaction.add(R.id.ly_content,fg3);
                }else{
                    fragmentTransaction.show(fg3);
                }
                break;
            case 3:
                if(fg4 == null){
                    fg4 = new left_list_fragment();
                    fragmentTransaction.add(R.id.ly_content,fg4);
                }else{
                    fragmentTransaction.show(fg4);
                }
                break;
            case 4:
                if(fg5 == null){
                    fg5 = new left_list_fragment();
                    fragmentTransaction.add(R.id.ly_content,fg4);
                }else{
                    fragmentTransaction.show(fg4);
                }
                break;
        }
        Bundle bundle1 = new Bundle();
        bundle1.putString(left_list_fragment.TAG, strs[position]);
        myFragment.setArguments(bundle1);
        fragmentTransaction.commit();*/
    }
   /* private void hideAllFragment(FragmentTransaction fragmentTransaction){
        if(fg1 != null)fragmentTransaction.hide(fg1);
        if(fg2 != null)fragmentTransaction.hide(fg2);
        if(fg3 != null)fragmentTransaction.hide(fg3);
        if(fg4 != null)fragmentTransaction.hide(fg4);
        if(fg5 != null)fragmentTransaction.hide(fg5);
    }*/
}
