package com.sjtfreaks.taiwu.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sjtfreaks.taiwu.R;
import com.sjtfreaks.taiwu.bean.MyUser;
import com.sjtfreaks.taiwu.utils.StaticClass;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by jet on 2018-10-13.
 */

public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener {
    private Button bt_forget_email;
    private EditText et_email;
    private EditText et_old_pass;
    private EditText et_new_pass;
    private EditText et_new_password;
    private Button bt_update_pass;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);
        //Bmob初始化
        Bmob.initialize(this, StaticClass.BMOB_ID);
        init();
    }

    private void init() {
        et_email = (EditText) findViewById(R.id.et_email);
        et_old_pass = (EditText) findViewById(R.id.et_old_pass);
        et_new_pass = (EditText) findViewById(R.id.et_new_pass);
        et_new_password = (EditText) findViewById(R.id.et_new_password);

        bt_forget_email = (Button) findViewById(R.id.bt_forget_email);
        bt_forget_email.setOnClickListener(this);
        bt_update_pass = (Button) findViewById(R.id.bt_update_pass);
        bt_update_pass.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_update_pass:
                String old_pass= et_old_pass.getText().toString().trim();
                String new_pass= et_new_pass.getText().toString().trim();
                String new_password= et_new_password.getText().toString();
                if (!TextUtils.isEmpty(old_pass)&!TextUtils.isEmpty(new_pass)&!TextUtils.isEmpty(new_password)){
                    if (new_pass.equals(new_password)){
                        MyUser.updateCurrentUserPassword(old_pass,new_pass,new UpdateListener() {
                            @Override
                            public void done(BmobException e){
                                if(e==null){
                                    Toast.makeText(ForgetPasswordActivity.this,"密码重置成功",Toast.LENGTH_SHORT).show();
                                    finish();
                                }else{
                                    Toast.makeText(ForgetPasswordActivity.this,"密码重置失败，失败原因："+e.toString(),Toast.LENGTH_SHORT).show();
                                }
                            }

                        });
                    }else{
                        Toast.makeText(this,"两次密码输入不一致",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(this,"输入框不得为空",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bt_forget_email:
                //1.获取输入框邮箱
                final String email= et_email.getText().toString().trim();
                //2.判断
                if (!TextUtils.isEmpty(email)){
                    //3.发送邮箱
                    MyUser.resetPasswordByEmail(email, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                Toast.makeText(ForgetPasswordActivity.this,"邮箱发送至："+email,Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(ForgetPasswordActivity.this,"邮箱发送失败，原因"+e.toString(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else {
                    Toast.makeText(this,"输入框不得为空",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
