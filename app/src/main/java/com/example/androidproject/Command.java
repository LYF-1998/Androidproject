package com.example.androidproject;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import interfaces.heweather.com.interfacesmodule.bean.weather.now.Now;
import interfaces.heweather.com.interfacesmodule.view.HeConfig;
import interfaces.heweather.com.interfacesmodule.view.HeWeather;


public class Command extends AppCompatActivity implements View.OnClickListener{
    private Button location;
    private Button scanner;
    private Button code;
    private TextView weather;
    private TextView command1;
    private TextView command2;
    private TextView command3;
    private TextView command4;
    private TextView tv_wether, tv_location, tv_temperature;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_command);
        bindViews();

        HeConfig.init("HE2006012053341638", "9ba0db84d7f04b0abbcf5eabcba662b8");
        //个人开发者需要切换到免费服务域名，默认使用中国付费节点服务域名会报错
        HeConfig.switchToFreeServerNode();
    }
   private void bindViews(){
       location = (Button) findViewById(R.id.wlocation);
       location.setOnClickListener(this);
       scanner = (Button) findViewById(R.id.scanner);
       scanner.setOnClickListener(this);
       code = (Button) findViewById(R.id.code);
       code.setOnClickListener(this);
       location.setText(Content.Location);
       weather = (TextView) findViewById(R.id.weather);
   }

    @Override
    protected void onStart() {
        super.onStart();
        getWether();
    }

    private void getWether() {
        HeWeather.getWeatherNow(Command.this, new HeWeather.OnResultWeatherNowBeanListener() {


            @Override
            public void onError(Throwable e) {
                Toast.makeText(Command.this, "weather error", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(Now dataObject) {
                String wweather = null, wtemperature = null, wcity = null, wdistrict = null, wcid = null;
                if (dataObject.getStatus().equals("ok")) {
                    String JsonNow = new Gson().toJson(dataObject.getNow());
                    String JsonBasic = new Gson().toJson(dataObject.getBasic());
                    JSONObject jsonObject = null;
                    JSONObject jsonObject1 = null;
                    try {
                        jsonObject = new JSONObject(JsonNow);
                        jsonObject1 = new JSONObject(JsonBasic);
                        wcity = jsonObject1.getString("parent_city");
                        wdistrict = jsonObject1.getString("location");
                        wcid = jsonObject1.getString("cid");
                        wweather = jsonObject.getString("cond_txt");
                        wtemperature = jsonObject.getString("tmp");
                        Content.Location=wcity;
                        Content.Weather=wweather;
                        Content.Temperature=wtemperature;
                        Toast.makeText(Command.this, wcity+wweather, Toast.LENGTH_SHORT).show();

                        location.setText(Content.Location);
                        weather.setText(Content.Weather+" "+Content.Temperature+"℃");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(Command.this, "weather mistake", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.wlocation:
                Intent intent = new Intent(Command.this,Location.class);
                startActivity(intent);
                break;
            case R.id.scanner:
                break;
            case R.id.code:

                break;
            case R.id.command1:

                break;
            case R.id.command2:

                break;
            case R.id.command3:

                break;
        }
    }


}
