package com.example.androidproject;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import interfaces.heweather.com.interfacesmodule.bean.weather.now.Now;
import interfaces.heweather.com.interfacesmodule.view.HeConfig;
import interfaces.heweather.com.interfacesmodule.view.HeWeather;


public class Command extends AppCompatActivity implements View.OnClickListener {
        private Button location;
        private Button scanner;
        private Button code;
        private TextView weather;
        private TextView command1;
        private TextView command2;
        private TextView command3;
        private TextView command4;
        private TextView tv_wether, tv_location, tv_temperature;
    private List<command_item> mData = null;
    private Context mContext;
    private CommandAdapter mAdapter = null;
    private ListView list_animal;
    private ListView listView;
    // private  String[] names={"白斩鸡","地三鲜","剁椒鱼头","干锅土豆","酸菜鱼","梅菜扣肉"};
   // private String[]  price={"23","22","18","27","14","15"};
   // private String[]  hot={"156","234","121","54","34","20"};
   // private int[] icons={R.mipmap.baizhanji,R.mipmap.disanxian,R.mipmap.duojiao,R.mipmap.ganguotudou,R.mipmap.suancaiyu,R.mipmap.meicaikourou};

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_command);
            bindViews();
            creatList();
            Intent intent = getIntent();
            String u_username = intent.getStringExtra("u_username");
            Toast.makeText(Command.this,"欢迎用户"+u_username+"！",Toast.LENGTH_LONG).show();
            HeConfig.init("HE2006012053341638", "9ba0db84d7f04b0abbcf5eabcba662b8");
            //个人开发者需要切换到免费服务域名，默认使用中国付费节点服务域名会报错
            HeConfig.switchToFreeServerNode();
        }

        private void bindViews() {
            location = (Button) findViewById(R.id.wlocation);
            location.setOnClickListener(this);
            scanner = (Button) findViewById(R.id.scanner);
            scanner.setOnClickListener(this);
            code = (Button) findViewById(R.id.code);
            code.setOnClickListener(this);
            location.setText(Content.Location);
            weather = (TextView) findViewById(R.id.weather);
        }
        /*private void creatList(){
            mContext = Command.this;
            list_animal = (ListView) findViewById(R.id.command_list);
            mData = new LinkedList<command_item>();
            mData.add(new command_item("狗说", "你是狗么?","234",R.mipmap.baizhanji));
            mData.add(new command_item("牛说", "你是牛么?","345", R.mipmap.disanxian));
            mData.add(new command_item("鸭说", "你是鸭么?","445", R.mipmap.duojiao));
            mData.add(new command_item("鱼说", "你是鱼么?","78", R.mipmap.ganguotudou));
            mData.add(new command_item("马说", "你是马么?", "34",R.mipmap.meicaikourou));
            mData.add(new command_item("马说", "你是马么?", "34",R.mipmap.yuxiangrousi));
            mData.add(new command_item("马说", "你是马么?", "34",R.mipmap.suancaiyu));
            mAdapter = new CommandAdapter((LinkedList<command_item>) mData, mContext);
            list_animal.setAdapter(mAdapter);
        }*/
        private void creatList(){
            mContext = Command.this;
            list_animal = (ListView) findViewById(R.id.command_list);
            mData = new LinkedList<command_item>();
            new Thread(new Runnable() {
                @Override
                public void run() {
                        String sql1 = "select * from order1";
                        ResultSet resultSet = JDBCUtils.query(sql1);
                       try {
                           while (resultSet.next()) {
                              // Log.i("name:", resultSet.getString("order_name"));
                               mData.add(new command_item(resultSet.getString("order_name"), resultSet.getString("order_price"), resultSet.getString("order_quantity"), R.mipmap.baizhanji));
                           }
                       }catch (SQLException e) {
                        e.printStackTrace();
                    }finally {
                           JDBCUtils.close();
                       }

                    Log.i("name:",mData.get(1).getName());

                }
            }).start();
if(mData!=null){
    Log.i("nnnnn","nnnnnnn");
}
            mAdapter = new CommandAdapter((LinkedList<command_item>) mData, mContext);
            list_animal.setAdapter(mAdapter);
           /* new Thread(new Runnable() {
                @Override
                public void run() {
                    String sql1 = "select * from order1 where order_id='1'";
                    ResultSet resultSet = JDBCUtils.query(sql1);
                    try {
                        resultSet.next();
                        //command1.setText(resultSet.getString("order_name"));
                        Log.i("name:",resultSet.getString("order_name"));
                    }catch (SQLException e) {
                        e.printStackTrace();
                    }
                    JDBCUtils.close();

                }
            }).start();*/


        }
        /*private void creatList(){
            mContext = Command.this;
            list_animal = (ListView) findViewById(R.id.command_list);
            mData = new LinkedList<command_item>();
            new Thread(new Runnable() {
                @Override
                public void run() {
                        String sql1 = "select * from order1";
                        ResultSet resultSet = JDBCUtils.query(sql1);
                    try {
                        while (resultSet.next()) {
                                mData.add(new command_item(resultSet.getString("order_name"), resultSet.getString("order_price"), resultSet.getString("order_quantity"), R.mipmap.baizhanji));
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    JDBCUtils.close();
                }
            }).start();
            mAdapter = new CommandAdapter((LinkedList<command_item>) mData, mContext);
            list_animal.setAdapter(mAdapter);

        }
*/
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
                            Content.Location = wcity;
                            Content.Weather = wweather;
                            Content.Temperature = wtemperature;
                            //Toast.makeText(Command.this, wcity + wweather, Toast.LENGTH_SHORT).show();

                            location.setText(Content.Location);
                            weather.setText(Content.Weather + " " + Content.Temperature + "℃");
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
                    Intent intent = new Intent(Command.this, Location.class);
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