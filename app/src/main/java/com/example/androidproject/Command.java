package com.example.androidproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

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
   // private List<command_item> mData = null;
    private Context mContext;
    private CommandAdapter mAdapter = null;
    private ListView listView;
    // private  String[] names={"白斩鸡","地三鲜","剁椒鱼头","干锅土豆","酸菜鱼","梅菜扣肉"};
   // private String[]  price={"23","22","18","27","14","15"};
   // private String[]  hot={"156","234","121","54","34","20"};
    private int[] icons={R.mipmap.baizhanji,R.mipmap.disanxian,R.mipmap.duojiao,R.mipmap.ganguotudou,R.mipmap.suancaiyu,R.mipmap.meicaikourou};

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_command);
            bindViews();
            try {
                creatList();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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

        private void creatList() throws ExecutionException, InterruptedException {
            mContext = Command.this;
            listView = (ListView) findViewById(R.id.command_list);
            int inis = 0;
            FutureTask<List<command_item>> futureTask = new FutureTask<>(new Callable<List<command_item>>() {
                @Override
                public List<command_item> call() throws Exception {
                    List<command_item> mData = new LinkedList<>();
                    int i = 1;
                    String sql1 = "select * from shop";
                    ResultSet resultSet = JDBCUtils.query(sql1);
                    while (resultSet.next()) {
                        mData.add(new command_item(resultSet.getString(
                                "shop_name"),"配送费5￥",
                                resultSet.getString("shop_quantity"),
                                icons[i]));
                        i++;
                    }
                    return mData;
                }
            });
            new Thread(futureTask).start();
            final List<command_item> mData = futureTask.get();
            mAdapter = new CommandAdapter((LinkedList<command_item>) mData, mContext);
            listView.setAdapter(mAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String shop_name = mData.get(i).getName();
                    //Toast.makeText(Command.this,dishes_name,Toast.LENGTH_LONG).show();
                    //Bundle bundle = new Bundle();
                   // bundle.putString("shop_name", shop_name);

                    Intent intent = new Intent(Command.this,Order.class);
                    //intent.putExtras(bundle);
                    //finish();
                    startActivity(intent);
                }
            });
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