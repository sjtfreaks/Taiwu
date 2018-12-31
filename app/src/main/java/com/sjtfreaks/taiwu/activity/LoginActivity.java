package com.sjtfreaks.taiwu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sjtfreaks.taiwu.MainActivity;
import com.sjtfreaks.taiwu.R;
import com.sjtfreaks.taiwu.bean.MyUser;
import com.sjtfreaks.taiwu.utils.ShareUtils;
import com.sjtfreaks.taiwu.utils.StaticClass;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView tv_zhuce;
    private LinearLayout lay_login;
    private EditText et_account;
    private EditText et_psw;
    private TextView tv_wjmm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        //bmob
        Bmob.initialize(this, StaticClass.BMOB_ID);
    }

    private void initView() {
        tv_wjmm = (TextView) findViewById(R.id.tv_wjmm);
        tv_wjmm.setOnClickListener(this);
        et_account = (EditText) findViewById(R.id.et_account);
        et_psw = (EditText) findViewById(R.id.et_psw);
        lay_login = (LinearLayout) findViewById(R.id.lay_login);
        lay_login.setOnClickListener(this);
        //keep_password = (CheckBox) findViewById(R.id.keep_password);
        tv_zhuce = (TextView) findViewById(R.id.tv_zhuce);
        tv_zhuce.setOnClickListener(this);
        //选中状态
        boolean isCheck = ShareUtils.getBoolean(this,"keeppass",false);
        //keep_password.setChecked(isCheck);
        if (isCheck){
            //设置密码
            et_account.setText(ShareUtils.getString(this,"name",""));
            et_psw.setText(ShareUtils.getString(this,"password",""));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_zhuce:
                startActivity(new Intent(this,RegisteredActivity.class));
                break;
            case R.id.tv_wjmm:
                startActivity(new Intent(this,ForgetPasswordActivity.class));
                break;
            case R.id.lay_login:
                //获取输入值
                String name = et_account.getText().toString().trim();
                String password = et_psw.getText().toString().trim();
                //判断是否为空
                if(!TextUtils.isEmpty(name)&!TextUtils.isEmpty(password)){
                    //登录
                    final MyUser user = new MyUser();
                    user.setUsername(name);
                    user.setPassword(password);
                    user.login(new SaveListener<MyUser>() {
                        @Override
                        public void done(MyUser myUser, BmobException e) {
                            //判断user和pass
                            if (e== null){
                                //邮箱是否验证
//                                if (user.getEmailVerified()){
                                //跳转
                                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                finish();
//                                }else{
//                                    Toast.makeText(LoginActivity.this,"登陆失败,失败原因：邮箱未验证",Toast.LENGTH_SHORT).show();
//                                }
                            }else {
                                Toast.makeText(LoginActivity.this,"登陆失败,失败原因："+e.toString(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(this,"输入框不能为空",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}