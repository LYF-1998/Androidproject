package com.example.androidproject;

import android.content.Context;
import android.content.SharedPreferences;
//import android.widget.Toast;
import java.util.HashMap;
import java.util.Map;

public class SharedHelper {

    public static SharedHelper instance;

//    private Context mContext;

    public static SharedHelper getInstance(){
        if (instance == null){
            instance = new SharedHelper();
            return instance;
        }
        return instance;
    }

    private SharedHelper() {
    }

//    public SharedHelper(Context mContext) {
//        this.mContext = mContext;
//    }


    //定义一个保存数据的方法
    public void save(String username, String passwd) {
        SharedPreferences sp = MyApplication.getContext().getSharedPreferences("mysp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("username", username);
        editor.putString("passwd", passwd);
        editor.commit();
        //Toast.makeText(mContext, "信息已写入SharedPreference中", Toast.LENGTH_SHORT).show();
    }
    public void clear(){
        SharedPreferences sp = MyApplication.getContext().getSharedPreferences("mysp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("username", null);
        editor.putString("passwd", null);
        editor.commit();
    }

    //定义一个读取SP文件的方法
    public Map<String, String> read() {
        Map<String, String> data = new HashMap<String, String>();
        SharedPreferences sp = MyApplication.getContext().getSharedPreferences("mysp", Context.MODE_PRIVATE);
        data.put("username", sp.getString("username", ""));
        data.put("passwd", sp.getString("passwd", ""));
        return data;
    }
    public boolean check_f(String ISCHECK){
        SharedPreferences sp = MyApplication.getContext().getSharedPreferences("mysp", Context.MODE_PRIVATE);
        return sp.getBoolean(ISCHECK,false);
    }
    public boolean check_t(String ISCHECK){
        SharedPreferences sp = MyApplication.getContext().getSharedPreferences("mysp", Context.MODE_PRIVATE);
        return sp.getBoolean(ISCHECK,true);
    }
    public void put_true(String ISCHECK){
        SharedPreferences sp = MyApplication.getContext().getSharedPreferences("mysp", Context.MODE_PRIVATE);
        sp.edit().putBoolean(ISCHECK, true).commit();
    }
    public void put_false(String ISCHECK){
        SharedPreferences sp = MyApplication.getContext().getSharedPreferences("mysp", Context.MODE_PRIVATE);
        sp.edit().putBoolean(ISCHECK, false).commit();
    }
}