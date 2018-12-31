package com.sjtfreaks.taiwu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.sjtfreaks.taiwu.R;
import com.sjtfreaks.taiwu.utils.ShareUtils;
import com.sjtfreaks.taiwu.utils.StaticClass;


/**
 * Created by jet on 2018-10-15.
 */

public class SplashActivity extends AppCompatActivity {
    private TextView mSplash;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case StaticClass.HANDLER_TIEM:
                    //是否为第一次运行
                        startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                    finish();
                    break;
            }
        }
    };

    private boolean isFirst() {
        boolean isFirst = ShareUtils.getBoolean(this,StaticClass.SHARE_IS_FIRST,true);
        ShareUtils.putBoolean(this,StaticClass.SHARE_IS_FIRST,false);
        if(isFirst){
            return true;
        }else {
            return false;
        }
    }
    //j禁止返回
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initView();
    }
    //初始化
    private void initView() {
        //延迟
        handler.sendEmptyMessageDelayed(StaticClass.HANDLER_TIEM,2000);
        mSplash = (TextView) findViewById(R.id.mSplash);

    }
}
