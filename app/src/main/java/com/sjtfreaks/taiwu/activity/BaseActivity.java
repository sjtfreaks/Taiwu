package com.sjtfreaks.taiwu.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;


import com.sjtfreaks.taiwu.utils.StaticClass;

import cn.bmob.v3.Bmob;


/**
 * Created by jet on 2018-10-17.
 */

public class BaseActivity extends AppCompatActivity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //返回键
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //bmob
        Bmob.initialize(this, StaticClass.BMOB_ID);
    }
    //菜单栏
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}


