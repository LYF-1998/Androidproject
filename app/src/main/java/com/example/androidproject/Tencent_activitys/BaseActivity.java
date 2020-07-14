package com.example.androidproject.Tencent_activitys;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.androidproject.R;
import com.example.androidproject.Recommend;
import com.example.androidproject.Tencent_share.AppConstants;
import com.example.androidproject.Tencent_share.Util;
import com.tencent.tauth.Tencent;

public class BaseActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.setContentView(R.layout.base_activity_with_titlebar);
		setTitle(null);
	}

    protected void checkTencentInstance() {
        if (null == Recommend.mTencent) {
            Recommend.mTencent = Tencent.createInstance("101885901", BaseActivity.this, AppConstants.APP_AUTHORITIES);
        }
    }

	@Override
    protected void onDestroy() {
        super.onDestroy();
        Util.release();
    }

    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK) {
			Util.dismissDialog();
		}
		return super.onKeyDown(keyCode, event);
	}
	@Override
	public void setContentView(int layoutResID) {
		LayoutInflater inflater = LayoutInflater.from(this);
		((LinearLayout)findViewById(R.id.layout_content)).addView(inflater.inflate(layoutResID, null));
	}


	public void setBarTitle(String title){
		((TextView)findViewById(R.id.base_title)).setText(title);
	}
}
