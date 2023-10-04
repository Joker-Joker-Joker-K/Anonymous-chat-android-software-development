package com.example.bluestar;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import java.sql.Connection;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    //声明控件
    private Button mBtnConfirm;
    private Button mBtnCancel;
    private EditText mEtUsername;
    private EditText mEtPassword;
    private EditText mEtConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //找到控件
        mBtnConfirm = findViewById(R.id.btn_confirm);
        mBtnCancel = findViewById(R.id.btn_cancel);
        mEtUsername = findViewById(R.id.re_username);
        mEtPassword = findViewById(R.id.re_password1);
        mEtConfirmPassword = findViewById(R.id.re_password2);
        //实现跳转，方法1
        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                ToastUtil.ShowMsg(getApplicationContext(), "取消成功！");
            }
        });
        mBtnConfirm.setOnClickListener(this);
    }

    public void RegisterTest(String name, String password){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                Connection con = DBUtil.getConnection();
                Message message = handler.obtainMessage();
                User ExistsUser = UserDao.findname(name);
                boolean IsSuccess;
                if(ExistsUser != null){
                    message.what = 1;
                    message.obj = "用户已经存在，请更换用户名重试！";
                }else{
                    message.what = 2;
                    IsSuccess = UserDao.register(name, password);
                    if(IsSuccess) message.obj = "注册成功！";
                    else message.obj = "注册失败！";
                }
                handler.sendMessage(message);
                try {
                    DBUtil.release(null,null,null,con);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
    @SuppressLint("HandlerLeak")
    private final Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            switch (msg.what){
                case 1:
                    String tips1 = (String) msg.obj;
                    ToastUtil.ShowMsg(RegisterActivity.this,tips1);
                    break;
                case 2:
                    String tips2 = (String) msg.obj;
                    ToastUtil.ShowMsg(RegisterActivity.this, tips2);
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };

    public void onClick(View v)
    {
        //需要获取用户名和密码
        String username = mEtUsername.getText().toString();
        String userpassword = mEtPassword.getText().toString();
        String userconfirmpassword = mEtConfirmPassword.getText().toString();
        //弹出内容设置
        String fail = "两次密码输入不相同，请重试";
        String Emptry = "某项输入为空或包含字符空格，请检查！";
        boolean user = username.length() == 0 || username.contains(" ");
        boolean pas = userpassword.length() == 0 || userpassword.contains(" ");
        boolean cpas = userconfirmpassword.length() == 0 || userconfirmpassword.contains(" ");
        if(user || pas || cpas) {
            ToastUtil.ShowMsg(RegisterActivity.this,Emptry);
        }else if(userpassword.length() < 6){
            ToastUtil.ShowMsg(RegisterActivity.this,"密码长度必须大于 6 个字符！");
        }else if(!userpassword.equals(userconfirmpassword)){
            ToastUtil.ShowMsg(RegisterActivity.this,fail);
        }else{
            RegisterTest(username, userpassword);
        }
    }
}