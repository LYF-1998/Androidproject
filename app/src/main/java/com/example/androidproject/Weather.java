package com.example.androidproject;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import interfaces.heweather.com.interfacesmodule.bean.weather.now.Now;
import interfaces.heweather.com.interfacesmodule.view.HeConfig;
import interfaces.heweather.com.interfacesmodule.view.HeWeather;

/**
 * @Description:
 * @Author: chenciu
 * @Time: 2019/10/24
 */
public class Weather extends AppCompatActivity {
    TextView tv_wether, tv_location, tv_temperature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        //此处分别输入你申请的Username与Key
        HeConfig.init("HE2006012053341638", "9ba0db84d7f04b0abbcf5eabcba662b8");
        //个人开发者需要切换到免费服务域名，默认使用中国付费节点服务域名会报错
        HeConfig.switchToFreeServerNode();
        tv_wether = (TextView) findViewById(R.id.wwether);
        tv_location = (TextView) findViewById(R.id.wlocation);
        tv_temperature = (TextView) findViewById(R.id.wtemperature);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getWether();
    }

    private void getWether() {
        HeWeather.getWeatherNow(Weather.this, new HeWeather.OnResultWeatherNowBeanListener() {
            public static final String TAG = "HeWeather_getWeatherNow";

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "onError:", e);
                System.out.println("Weather Now Error:" + new Gson());
            }

            @Override
            public void onSuccess(Now dataObject) {
                Log.i(TAG, " Weather Now onSuccess:" + new Gson().toJson(dataObject));
                String weather = null, temperature = null, city = null, district = null, cid = null;
                if (dataObject.getStatus().equals("ok")) {
                    String JsonNow = new Gson().toJson(dataObject.getNow());
                    String JsonBasic = new Gson().toJson(dataObject.getBasic());
                    JSONObject jsonObject = null;
                    JSONObject jsonObject1 = null;
                    try {
                        jsonObject = new JSONObject(JsonNow);
                        jsonObject1 = new JSONObject(JsonBasic);
                        city = jsonObject1.getString("parent_city");
                        district = jsonObject1.getString("location");
                        cid = jsonObject1.getString("cid");
                        weather = jsonObject.getString("cond_txt");
                        temperature = jsonObject.getString("tmp");
                        Toast.makeText(Weather.this, city+weather, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(Weather.this, "Mistakes...", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }

}
