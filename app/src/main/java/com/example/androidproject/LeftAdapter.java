package com.example.androidproject;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


    public class LeftAdapter  extends BaseAdapter {
        private Context context;
        private String[] strings;
        public static int mPosition;
        public LeftAdapter(Context context, String[] strings){
            this.context =context;
            this.strings = strings;
        }
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return strings.length;
        }
        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return strings[position];
        }
        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            convertView = LayoutInflater.from(context).inflate(R.layout.order_left_item, null);
            TextView tv = (TextView) convertView.findViewById(R.id.tv);
            mPosition = position;
            tv.setText(strings[position]);
            if (position == OrderFragment.mPosition) {
                convertView.setBackgroundResource(R.color.text_gray);
            } else {
                convertView.setBackgroundColor(Color.parseColor("#f4f4f4"));
            }
            return convertView;
        }
    }

