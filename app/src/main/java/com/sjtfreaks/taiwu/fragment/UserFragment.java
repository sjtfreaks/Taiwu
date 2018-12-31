package com.sjtfreaks.taiwu.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sjtfreaks.taiwu.R;
import com.sjtfreaks.taiwu.activity.LoginActivity;
import com.sjtfreaks.taiwu.bean.MyUser;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment implements View.OnClickListener {
    private Button bt_exit;
    private Button bt_finsh_user;
    private TextView tv_user;
    private EditText et_username;
    private EditText et_year;
    private EditText et_sex;
    private EditText et_desc1;

    public static UserFragment newInstance(String name){
        Bundle args = new Bundle();
        args.putString("name", name);
        UserFragment fragment = new UserFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user,null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        bt_exit =(Button) view.findViewById(R.id.bt_exit);
        bt_exit.setOnClickListener(this);
        bt_finsh_user =(Button) view.findViewById(R.id.bt_finsh_user);
        bt_finsh_user.setOnClickListener(this);
        tv_user =(TextView) view.findViewById(R.id.tv_user);
        tv_user.setOnClickListener(this);
        et_username =(EditText) view.findViewById(R.id.et_username);
        et_sex=(EditText) view.findViewById(R.id.et_sex);
        et_year =(EditText) view.findViewById(R.id.et_year);
        et_desc1 =(EditText) view.findViewById(R.id.et_desc1);

        setEnabled(false);
        MyUser userInfo = BmobUser.getCurrentUser(MyUser.class);
        et_username.setText(userInfo.getUsername());
        et_year.setText(userInfo.getAge()+"岁");
        et_sex.setText(userInfo.isSex()?"男":"女");
        et_desc1.setText(userInfo.getDesc());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_exit:
                MyUser.logOut();   //清除缓存用户对象
                BmobUser currentUser = MyUser.getCurrentUser(); // 现在的currentUser是null了
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                break;
            //编辑资料
            case R.id.tv_user:
                setEnabled(true);
                tv_user.setVisibility(View.VISIBLE);
                break;
            case R.id.bt_finsh_user:
                String username = et_username.getText().toString();
                String year = et_year.getText().toString();
                String sex = et_sex.getText().toString();
                String desc1 = et_desc1.getText().toString();
                if (!TextUtils.isEmpty(username)&!TextUtils.isEmpty(year)&
                        !TextUtils.isEmpty(sex)&!TextUtils.isEmpty(desc1)){
                    MyUser user = new MyUser();
                    user.setUsername(username);
                    user.setAge(Integer.parseInt(year));
                    if (!TextUtils.isEmpty(desc1)){
                        user.setDesc(desc1);
                    }else {
                        user.setDesc("这个人很懒，什么都没留下……");
                    }
                    if (sex.equals("男")){
                        user.setSex(true);
                    }else{
                        user.setSex(false);
                    }
                    BmobUser bmobUser = BmobUser.getCurrentUser();
                    user.update(bmobUser.getObjectId(),new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                setEnabled(false);
                                bt_finsh_user.setVisibility(View.GONE);
                                Toast.makeText(getActivity(),"编辑成功",Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getActivity(),"编辑失败，失败原因："+e.toString(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(getActivity(),"输入框的值不得为空",Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }
    private void setEnabled(boolean isenabled){
        et_username.setEnabled(isenabled);
        et_year.setEnabled(isenabled);
        et_sex.setEnabled(isenabled);
        et_desc1.setEnabled(isenabled);
    }
}
