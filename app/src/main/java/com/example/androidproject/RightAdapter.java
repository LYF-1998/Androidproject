package com.example.androidproject;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class RightAdapter extends BaseAdapter {

    private List<command_item> data;
    private LayoutInflater layoutInflater;
    private Context context;
    public RightAdapter(Context context,List<command_item> data){
        this.context=context;
        this.data=data;
        this.layoutInflater=LayoutInflater.from(context);
    }
    /**
     * 组件集合，对应list.xml中的控件
     * @author Administrator
     */
    public final class Zujian{
        public ImageView image;
        public TextView name;
        public TextView price;
        public TextView hot;
    }
    @Override
    public int getCount() {
        return data.size();
    }
    /**
     * 获得某一位置的数据
     */
    @Override
    public Object getItem(int position) {
        return data.get(position);
    }
    /**
     * 获得唯一标识
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Zujian zujian=null;
        if(convertView==null){
            zujian=new Zujian();
            //获得组件，实例化组件
            convertView=layoutInflater.inflate(R.layout.command_list_item, null);
            zujian.image=(ImageView)convertView.findViewById(R.id.image);
            zujian.name=(TextView)convertView.findViewById(R.id.name);
            zujian.price=(TextView)convertView.findViewById(R.id.price);
            zujian.hot=(TextView)convertView.findViewById(R.id.hot);
            convertView.setTag(zujian);
        }else{
            zujian=(Zujian)convertView.getTag();
        }
        //绑定数据
        zujian.image.setBackgroundResource((Integer)data.get(position).getIcon());
        zujian.name.setText((String)data.get(position).getName());
        zujian.price.setText((String)data.get(position).getPrice());
        zujian.hot.setText((String)data.get(position).getHot());
        return convertView;
    }

}