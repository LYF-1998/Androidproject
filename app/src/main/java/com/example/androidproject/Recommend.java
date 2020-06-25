package com.example.androidproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import interfaces.heweather.com.interfacesmodule.bean.weather.now.Now;
import interfaces.heweather.com.interfacesmodule.view.HeConfig;
import interfaces.heweather.com.interfacesmodule.view.HeWeather;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Recommend#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Recommend extends Fragment implements View.OnClickListener{
    private Button location;
    private Button scanner;
    private Button code;
    private Button search_confirm;
    private EditText search;
    private TextView weather;
    private TextView command1;
    private TextView command2;
    private TextView command3;
    private TextView command4;
    private TextView command5;
    private TextView tv_wether, tv_location, tv_temperature;
    // private List<command_item> mData = null;
    private Context mContext;
    private CommandAdapter mAdapter = null;
    private ListView listView;
    // private  String[] names={"白斩鸡","地三鲜","剁椒鱼头","干锅土豆","酸菜鱼","梅菜扣肉"};
    // private String[]  price={"23","22","18","27","14","15"};
    // private String[]  hot={"156","234","121","54","34","20"};
    private int[] icons={R.mipmap.baizhanji,R.mipmap.disanxian,R.mipmap.duojiao,
            R.mipmap.ganguotudou,R.mipmap.ganguotudou,R.mipmap.suancaiyu,R.mipmap.meicaikourou,
            R.mipmap.suancaiyu,R.mipmap.meicaikourou,R.mipmap.baizhanji,R.mipmap.disanxian,R.mipmap.duojiao};

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Recommend() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Recommend.
     */
    // TODO: Rename and change types and number of parameters
    public static Recommend newInstance(String param1, String param2) {
        Recommend fragment = new Recommend();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        bindViews();
        try {
            creatList();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<String> command_list = select();
        command1.setText(command_list.get(0));
        command2.setText(command_list.get(1));
        command3.setText(command_list.get(2));
        command4.setText(command_list.get(3));
        command5.setText(command_list.get(4));
        HeConfig.init("HE2006012053341638", "9ba0db84d7f04b0abbcf5eabcba662b8");
        //个人开发者需要切换到免费服务域名，默认使用中国付费节点服务域名会报错
        HeConfig.switchToFreeServerNode();
        getWether();
        Intent intent = getActivity().getIntent();
        String u_username = intent.getStringExtra("u_username");
        Toast.makeText(getActivity(),"欢迎用户"+u_username+"！",Toast.LENGTH_LONG).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_recommend, container, false);
    }
    private void bindViews() {
        location = getActivity().findViewById(R.id.wlocation);
        location.setOnClickListener(this);
        scanner = getActivity().findViewById(R.id.scanner);
        scanner.setOnClickListener(this);
        code = getActivity().findViewById(R.id.code);
        code.setOnClickListener(this);
        search_confirm=getActivity().findViewById(R.id.search_confirm);
        search_confirm.setOnClickListener(this);
        search=getActivity().findViewById(R.id.search);
        location.setText(Content.Location);
        weather = getActivity().findViewById(R.id.weather);
        command1=getActivity().findViewById(R.id.command1);
        command2=getActivity().findViewById(R.id.command2);
        command3=getActivity().findViewById(R.id.command3);
        command4=getActivity().findViewById(R.id.command4);
        command5=getActivity().findViewById(R.id.command5);
    }

    private void creatList() throws ExecutionException, InterruptedException {
        mContext = getActivity();
        listView = (ListView) getView().findViewById(R.id.command_list);
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
                            "shop_name"),"配送费："+resultSet.getInt("shop_price")+"￥",
                            "月销量"+resultSet.getInt("shop_quantity")+"份",
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
                Bundle bundle = new Bundle();
                bundle.putString("shop_name", shop_name);
                bundle.putInt("shop_id",i);
                Intent intent = new Intent(getActivity(),Order.class);
                intent.putExtras(bundle);
                //finish();
                startActivity(intent);
            }
        });
    }
    private List<String> select(){
        final FutureTask<List<String>> futureTask = new FutureTask<>(new Callable<List<String>>() {
            @Override
            public List<String> call() throws Exception {
                List<String> mData = new LinkedList<>();
                String sql = "select * from (select * from order1 order by order_quantity DESC )a limit 5";
                ResultSet resultSet = JDBCUtils.query(sql);
                while (resultSet.next()) {
                    mData.add(resultSet.getString("order_name"));
                }
                return mData;

            }
        });
        new Thread(futureTask).start();
        List<String> Data = null;
        try {
            Data = futureTask.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            return Data;
        }
    }
    private void getWether() {
        HeWeather.getWeatherNow(getActivity(), new HeWeather.OnResultWeatherNowBeanListener() {


            @Override
            public void onError(Throwable e) {
                Toast.makeText(getActivity(), "weather error", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getActivity(), "weather mistake", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.wlocation:
                Intent intent = new Intent(getActivity(), Location.class);
                startActivity(intent);
                break;
            case R.id.scanner:
                Intent intent_scanner = new Intent(getActivity(),scanner.class);
                startActivity(intent_scanner);
                break;
            case R.id.code:

                break;
            case R.id.search_confirm:
                 Intent intent1=new Intent(getActivity(),search.class);
                 intent1.putExtra("search_text",search.getText().toString());
                 startActivity(intent1);
                break;
        }
    }
}