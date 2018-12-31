package com.sjtfreaks.taiwu.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.sjtfreaks.taiwu.R;
import com.sjtfreaks.taiwu.bean.MyUser;
import com.sjtfreaks.taiwu.utils.StaticClass;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by jet on 2018-10-13.
 */

public class RegisteredActivity extends BaseActivity implements View.OnClickListener{
    private EditText et_user;
    private EditText et_age;
    private EditText et_pass;
    private EditText et_password;
    private EditText et_mail;
    private EditText et_desc;
    private RadioGroup mRadioGroup;
    private Button btnRegistened;
    private boolean isMan = true;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered);
        //Bmob初始化
        Bmob.initialize(this, StaticClass.BMOB_ID);
        init();
    }

    private void init() {
        et_age = (EditText) findViewById(R.id.et_age);
        et_user = (EditText) findViewById(R.id.et_user);
        et_pass = (EditText) findViewById(R.id.et_pass);
        et_password = (EditText) findViewById(R.id.et_password);
        et_desc = (EditText) findViewById(R.id.et_desc);
        et_mail = (EditText) findViewById(R.id.et_mail);
        mRadioGroup = (RadioGroup) findViewById(R.id.mRadioGroup);
        btnRegistened = (Button) findViewById(R.id.btnRegistened);
        btnRegistened.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnRegistened:
                //get值
                String name = et_user.getText().toString().trim();
                String pass = et_pass.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                String age = et_age.getText().toString().trim();
                String desc = et_desc.getText().toString().trim();
                String mail = et_mail.getText().toString().trim();
                //判断是否为null
                if (!TextUtils.isEmpty(name)&!TextUtils.isEmpty(pass)&!TextUtils.isEmpty(password)&!TextUtils.isEmpty(age)&!TextUtils.isEmpty(mail)
                        ){
                    //密码是否一致
                    if (pass.equals(password)){
                        //判断性别
                        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                                if (checkedId == R.id.rb_boy){
                                    isMan = true;
                                }else if (checkedId == R.id.rb_girl){
                                    isMan = false;
                                }
                            }
                        });
                        //简介是否为空
                        if(!TextUtils.isEmpty(desc)){
                            desc = "这个人很懒，什么都没留下……";
                        }
                        //注册
                        MyUser user = new MyUser();
                        user.setUsername(name);
                        user.setPassword(password);
                        user.setEmail(mail);
                        user.setAge(Integer.parseInt(age));
                        user.setSex(isMan);
                        user.signUp(new SaveListener<MyUser>() {

                            @Override
                            public void done(MyUser myUser, BmobException e) {
                                if(e==null){
                                    Toast.makeText(RegisteredActivity.this,"注册成功!",Toast.LENGTH_SHORT).show();
                                    finish();
                                }else{
                                    Toast.makeText(RegisteredActivity.this,"注册失败!失败原因："+e.toString(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else {
                        Toast.makeText(this,"两次输入的密码不一致",Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(this,"输入框不能为空",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
