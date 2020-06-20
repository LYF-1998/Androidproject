package com.example.androidproject;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

public class RightAdapter extends BaseAdapter {

    private List<command_item> data;
    private LayoutInflater layoutInflater;
    private Context context;
    //private  static  int i=0;
    public RightAdapter(Context context,List<command_item> data){
        this.context=context;
        this.data=data;
        this.layoutInflater=LayoutInflater.from(context);
    }
    /**
     * 组件集合，对应list.xml中的控件
     * @author Administrator
     */
    public final class Zujian{
        public ImageView image;
        public TextView name;
        public TextView price;
        public TextView hot;
        public EditText quantity;
        public Button  add,subtract;

    }
    @Override
    public int getCount() {
        return data.size();
    }
    /**
     * 获得某一位置的数据
     */
    @Override
    public Object getItem(int position) {
        return data.get(position);
    }
    /**
     * 获得唯一标识
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Zujian zujian=null;
        if(convertView==null){
            zujian=new Zujian();
            //获得组件，实例化组件
            convertView=layoutInflater.inflate(R.layout.order_right_item, null);
            zujian.image=(ImageView)convertView.findViewById(R.id.r_image);
            zujian.name=(TextView)convertView.findViewById(R.id.r_name);
            zujian.price=(TextView)convertView.findViewById(R.id.r_price);
            zujian.hot=(TextView)convertView.findViewById(R.id.r_hot);
            zujian.quantity=(EditText) convertView.findViewById(R.id.quantity);
            zujian.add=(Button)convertView.findViewById(R.id.add);
            zujian.subtract=(Button)convertView.findViewById((R.id.subtract));
            convertView.setTag(zujian);
        }else{
            zujian=(Zujian)convertView.getTag();
        }
        //绑定数据
        zujian.image.setBackgroundResource((Integer)data.get(position).getIcon());
        zujian.name.setText((String)data.get(position).getName());
        zujian.price.setText((String)data.get(position).getPrice());
        zujian.hot.setText((String)data.get(position).getHot());
        final Zujian finalZujian = zujian;
        final Zujian finalZujian1 = zujian;

        zujian.add.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String order_name = data.get(position).getName();
                String order_price = data.get(position).getPrice();
                //String order_quantity = data.get(position).getHot();
                int price = new get_StringNum(order_price).get();
                String quantity1= finalZujian.quantity.getText().toString();
                int quantity2=Integer.parseInt(quantity1);
                finalZujian1.quantity.setText(""+(quantity2+1));
               // Content.order_dishes[i][position]=order_name;
               // Content.order_quantity=Content.order_quantity+order_name+"1份 ";
                Content.Allorder_price=Content.Allorder_price+price;
                //i--;
                //Toast.makeText(context,Content.order_quantity+"总计"+String.valueOf(Content.Allorder_price)
                 //       +"元",Toast.LENGTH_LONG).show();
                //Toast.makeText(ListViewTestActivity.this,"listview button click",Toast.LENGTH_LONG).show();
            }
        });
        final Zujian finalZujian2 = zujian;
        zujian.subtract.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String order_name = data.get(position).getName();
                String order_price = data.get(position).getPrice();
                //String order_quantity = data.get(position).getHot();
                int price = new get_StringNum(order_price).get();
                if(Content.Allorder_price==0) {
                    Toast.makeText(context, "当前购物车为空！", Toast.LENGTH_LONG).show();
                }else if(finalZujian2.quantity.getText().toString().equals("0")){
                    Toast.makeText(context, "您并未选择当前菜品！", Toast.LENGTH_LONG).show();
                }
                 else{
                    //Content.order_dishes[i][position]="";
                    String quantity1= finalZujian.quantity.getText().toString();
                    int quantity2=Integer.parseInt(quantity1);
                    finalZujian1.quantity.setText(""+(quantity2-1));
                    Content.Allorder_price=Content.Allorder_price-price;
                   // Toast.makeText(context,Content.order_quantity+"总计"+String.valueOf(Content.Allorder_price)
                     //       +"元",Toast.LENGTH_LONG).show();
                }

                //Toast.makeText(ListViewTestActivity.this,"listview button click",Toast.LENGTH_LONG).show();
            }
        });
        return convertView;
    }

}