package com.example.androidproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link left_list_fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link left_list_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class right_list_fragment extends Fragment {
    public static final String TAG = "MyFragment";
    private String str;
    private ListView listView;
    private  int mshop_id;
    private  int mclass_id;
    private int[] icons={R.mipmap.baizhanji,R.mipmap.disanxian,
            R.mipmap.duojiao,R.mipmap.ganguotudou,R.mipmap.suancaiyu,R.mipmap.meicaikourou};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)  {
        View view = inflater.inflate(R.layout.fragment_right_list_fragment, null);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        str = getArguments().getString(TAG);
        tv_title.setText(str);
        mshop_id=getArguments().getInt("shop_id");
        mclass_id=getArguments().getInt("class_id");

        listView = (ListView)view.findViewById(R.id.right_list);
        List<command_item> Data=crealist();
        listView.setAdapter(new RightAdapter(getActivity(), Data));

        return view;
    }
    private List<command_item> crealist(){
        final FutureTask<List<command_item>> futureTask = new FutureTask<>(new Callable<List<command_item>>() {
            @Override
            public List<command_item> call() throws Exception {
                List<command_item> mData = new LinkedList<>();
                //Random r=new Random();
                //int i = r.nextInt(5);
                int i=mclass_id;
                String sql1 = "SELECT order1.order_name,order1.order_price,order1.order_quantity,order1.order_picture "+
                "FROM shop "+
                "INNER JOIN class "+
                "ON shop.shop_id=class.shop_id "+
                "INNER JOIN order1 "+
                "ON order1.order_id=class.order_id "+
                "WHERE class_id='"+mclass_id+"' and shop.shop_id='"+mshop_id+"'";
                ResultSet resultSet = JDBCUtils.query(sql1);
                while (resultSet.next()) {
                    String p=resultSet.getString("order_picture");
                    int id = getResources().getIdentifier(p,"mipmap", "com.example.androidproject");
                    mData.add(new command_item(resultSet.getString(
                            "order_name"),"单价"+resultSet.getString("order_price")+"元",
                            "月销量"+resultSet.getInt("order_quantity")+"份",
                            id));
                    //i++;
                }
                return mData;

            }
        });
        new Thread(futureTask).start();
        List<command_item> Data = null;
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
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button   order=(Button)getActivity().findViewById(R.id.order);
        Button   confirm=(Button)getActivity().findViewById(R.id.confirm);
        order.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                        Date date = new Date(System.currentTimeMillis());
                        String time=simpleDateFormat.format(date);
                        UserInfo userinfo =UserInfo.getInstance();
                        String sql = "INSERT INTO dishes(username,shopname,time,ds,price) VALUES('"+userinfo.getUsername()+"','"+userinfo.getShopname()+"','"+time+"','"+Content.order_dishes+"','"+Content.Allorder_price+"')";
                        JDBCUtils.update(sql);
                        //Intent intent=new Intent(getActivity(),);
                        //startActivity(intent);
                        JDBCUtils.close();
                    }
                }).start();
                Toast.makeText(getActivity(), "下单成功！", Toast.LENGTH_SHORT).show();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                int i =listView.getLastVisiblePosition()-listView.getFirstVisiblePosition();
                for(int j=0;j<=i;j++) {
                    EditText ed = listView.getChildAt(j).findViewById(R.id.quantity);
                    String a = ed.getText().toString();
                    final int b=Integer.parseInt(a);
                    final String order_name = crealist().get(j).getName();
                    String order_price = crealist().get(j).getPrice();
                    int price = new get_StringNum(order_price).get();
                    Content.order_dishes=Content.order_dishes+order_name+a;
                    Content.Allorder_price=Content.Allorder_price+price;

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            int num = get_order_quantity(order_name);
                            Log.v("num:",String.valueOf(num));
                            int sum=num+b;
                            String sql1 = "update order1 set order_quantity='"+sum+"' where order_name='"+order_name+"'";
                            JDBCUtils.update(sql1);

                            JDBCUtils.close();
                        }
                    }).start();
                }
                Toast.makeText(getActivity(),Content.order_dishes+"总计"+String.valueOf(Content.Allorder_price)
                                +"元",Toast.LENGTH_LONG).show();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //String order_name = crealist().get(i).getName();
                //String order_price = crealist().get(i).getPrice();
                //String order_quantity = crealist().get(i).getHot();
                //int quantity = new get_StringNum(order_quantity).get();
                //int price = new get_StringNum(order_price).get();
                //Content.order_dishes=Content.order_dishes+order_name;
                //Content.order_dishes[i]=order_name;
                //Content.order_quantity=Content.order_quantity+order_name+"1份 ";
               // Content.Allorder_price=Content.Allorder_price+price;
               // Toast.makeText(getActivity(),Content.order_quantity+"总计"+String.valueOf(Content.Allorder_price)
                //        +"元",Toast.LENGTH_LONG).show();
                //Log.v("价格", String.valueOf(Content.Allorder_price));
            }
        });
    }
    private int get_order_quantity(String order_name){
        int num=0;
        String sql  = "select order1.order_quantity from order1 where order_name='"+order_name+"'";
        ResultSet resultSet=JDBCUtils.query(sql);
        try {
            resultSet.next();
            num=resultSet.getInt("order_quantity");
            //Log.v("num1:",String.valueOf(num));
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            return num;
        }

    }

}
