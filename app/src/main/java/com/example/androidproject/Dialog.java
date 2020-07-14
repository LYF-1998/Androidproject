package com.example.androidproject;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.androidproject.Tencent_activitys.QQShareActivity;
import com.example.androidproject.Tencent_activitys.QZoneShareActivity;

public class Dialog extends DialogFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.dialog,container);
        ImageButton qq_share=view.findViewById(R.id.qq_share);
        ImageButton qzone_share=view.findViewById(R.id.qzone_share);
        ImageButton sina_share=view.findViewById(R.id.sina_share);
        //给按钮设置点击事件
        qq_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), QQShareActivity.class);
                startActivity(intent);
            }
        });
        qzone_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getActivity(), QZoneShareActivity.class);
                startActivity(intent1);
            }
        });
        sina_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getActivity(), Sina_share.class);
                startActivity(intent1);
            }
        });
        return view;
    }
}