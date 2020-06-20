package com.example.androidproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedList;

public class CommandAdapter extends BaseAdapter {

        private LinkedList<command_item> mData;
        private Context mContext;

        public CommandAdapter(LinkedList<command_item> mData, Context mContext) {
            this.mData = mData;
            this.mContext = mContext;
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.command_list_item,parent,false);
            ImageView img_icon = (ImageView) convertView.findViewById(R.id.image);
            TextView txt_name = (TextView) convertView.findViewById(R.id.name);
            TextView txt_price = (TextView) convertView.findViewById(R.id.price);
            TextView txt_hot = (TextView) convertView.findViewById(R.id.hot);
            img_icon.setBackgroundResource(mData.get(position).getIcon());
            txt_name.setText(mData.get(position).getName());
            txt_price.setText(mData.get(position).getPrice());
            txt_hot.setText(mData.get(position).getHot());
            return convertView;
        }

}
