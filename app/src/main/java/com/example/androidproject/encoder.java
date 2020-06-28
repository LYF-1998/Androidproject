package com.example.androidproject;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidproject.zxing.encoding.EncodingUtils;

public class encoder extends AppCompatActivity {
    //private EditText mEditText;
    private TextView mName;
    private ImageView mImageView;
    private CheckBox mCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encoder);

        initView();

    }

    private void initView() {
        mName= (TextView) this.findViewById(R.id.et_text);
        mImageView= (ImageView) this.findViewById(R.id.img_shouw);
        mCheckBox= (CheckBox) this.findViewById(R.id.cb_logo);
    }



    //生成二维码 可以设置Logo
    public void make(View view) {
         UserInfo info = UserInfo.getInstance();
         String name = info.getUsername();
         mName.setText(name);
        Bitmap qrCode = EncodingUtils.createQRCode(name, 500, 500,
                mCheckBox.isChecked()? BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher):null);
        mImageView.setImageBitmap(qrCode);
       /* String input = mEditText.getText().toString();
        if (input.equals("")){
            Toast.makeText(this,"输入不能为空",Toast.LENGTH_SHORT).show();
        }else{
            Bitmap qrCode = EncodingUtils.createQRCode(input, 500, 500,
                    mCheckBox.isChecked()? BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher):null);
            mImageView.setImageBitmap(qrCode);
        }*/
    }
}