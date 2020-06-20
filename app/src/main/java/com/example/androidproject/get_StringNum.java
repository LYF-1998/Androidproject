package com.example.androidproject;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
    public class get_StringNum {
        String str;
       public get_StringNum(String str){
          this.str=str;
        }
        public int get(){
            String regEx="[^0-9]";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(str);
            int b=Integer.parseInt(m.replaceAll("").trim());
            Log.v("转换", String.valueOf(b));
            return b;
            //System.out.println( m.replaceAll("").trim());
        }
    }
