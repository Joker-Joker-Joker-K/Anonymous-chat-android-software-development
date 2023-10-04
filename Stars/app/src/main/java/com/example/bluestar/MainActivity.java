package com.example.bluestar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.InetAddress;
import java.net.Socket;
import java.sql.Connection;

//主函数（登入注册界面移到这里）
public class MainActivity extends AppCompatActivity {
    private Client client;
    private newClient client2;
    private Button button;
    private Button button2;

    private Button loginButton,registerButton;
    private EditText username,password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        button=(Button) findViewById(R.id.nothing);
//        button2=(Button) findViewById(R.id.anything);
//
////        client=new Client();
////        GlobalVariables.getInstance().setClient(client);
////        client.connect();
//
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent =null;
//                intent=new Intent(MainActivity.this,ChatActivity.class);
//                startActivity(intent);
//            }
//        });
//
//
//        button2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent =null;
//                intent=new Intent(MainActivity.this,FragmentActivity.class);
//                startActivity(intent);
//            }
//        });

        //找到控件
        find();
        //注册新用户
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
//        loginButton.setOnClickListener((View.OnClickListener) this);
        loginButton.setOnClickListener(this::onClick);


//        用服务去实例化客户端

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Log.i("提示","连接1开始");
////                    InetAddress  address = InetAddress.getLocalHost();//获取本地地址
//                    Socket socket = new Socket(" 10.0.2.2", 10001);
//                    Log.i("提示","连接1成功");
////                    申请连接
//                }catch (Exception e){
//                    e.printStackTrace();}
//            }
//        }).start();;
////        172.26.198.88




//        new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//                Log.i("提示：","连接2开始...");
//                client2 = new newClient();
//                client2.connect();
//                Log.i("提示：","连接2成功...");
//
//            }
//        }).start();

//        Log.i("提示：","连接3...");
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Log.i("提示：","进入线程...");
//                client2 = new newClient();
//                client2.connect();
//                Log.i("提示：","客户端线程已启动...");
//
//            }
//        }).start();



//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Log.i("提示：","进入线程...");
//                client = new Client();
////                client.run();
//                Log.i("提示：","客户端线程已启动...");
//
//            }
//        }).start();
    }

    public void LoginTest(String name, String password){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent intent = null;
                Looper.prepare();
                User user;
                String ok = "登陆成功!";
                String fail = "密码或账号错误，请重新登录！";
                Connection con = DBUtil.getConnection();
                if(con != null){
                    System.out.println(1);
                }
                user = UserDao.login(name, password);
                if(user != null){
                    if(user.getName().equals(name) && user.getPassword().equals(password)){
                        intent = new Intent(MainActivity.this, FragmentActivity.class);
                        startActivity(intent);
                        //Toast弹出消息
                        Toast.makeText(getApplicationContext(),ok,Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    //Toast弹出不正确
                    Toast.makeText(getApplicationContext(),fail,Toast.LENGTH_SHORT).show();
                }
                try {
                    DBUtil.release(null,null,null,con);
                } catch (Exception e){
                    e.printStackTrace();
                }

            }
        }).start();
    }

    //点击事件
    public void onClick(View view){
        String account = username.getText().toString();
        String password_ed = password.getText().toString();
        //设置Toast弹出的内容
        String ok = "登陆成功!";
        String fail = "密码或账号错误，请重新登录！";

        LoginTest(account,password_ed);

    }
    private void find(){
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);
        username = findViewById(R.id.usernameField);
        password = findViewById(R.id.passwordField);
    }

}