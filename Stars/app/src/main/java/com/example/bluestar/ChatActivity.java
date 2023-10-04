package com.example.bluestar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
//聊天界面及连接服务器
public class ChatActivity extends AppCompatActivity {

    private Socket socket;
    private ArrayList<ChatMsg> list;
    private ChatAdapter adapter;

    private RecyclerView rv;
    private EditText et;
    private Button bt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        rv = (RecyclerView) findViewById(R.id.chat_rv);
        et = (EditText) findViewById(R.id.chat_send_et);
        bt = (Button) findViewById(R.id.chat_send_bu);

        list = new ArrayList<>();
        adapter = new ChatAdapter(this);
//        传入上下文对象

        final Handler handler = new ChatHandler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
//                    socket = new Socket("127.0.0.1", 10000);
                    socket = new Socket("10.0.2.2", 10001);
//                    socket = new Socket("10.0.2.2", 9999);
//                    申请连接
                    Log.i("提示","连接成功");
                    InputStream inputStream = socket.getInputStream();
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = inputStream.read(buffer)) != -1) {
//                        循环不断从bufer中读取数据
//                        通过 inputStream.read(buffer) 方法返回实际读取的字节数。
                        String data = new String(buffer, 0, len);
                        // 发到主线程中 收到的数据
                        Message message = Message.obtain();
//                        实例化
                        message.what = 1;
                        message.obj = data;
                        handler.sendMessage(message);
//                        将消息发送给主线程进行处理和显示
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String data = et.getText().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            OutputStream outputStream = socket.getOutputStream();
                            SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");    //设置日期格式
                            outputStream.write((socket.getLocalPort() + "//" + data + "//" + df.format(new Date())).getBytes("utf-8"));
                            outputStream.flush();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });




    }

    private class ChatHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {

                int localPort = socket.getLocalPort();
//                获取socket本地端口号
                String[] split = ((String) msg.obj).split("//");

                if (split[0].equals(localPort + "")) {
                    ChatMsg chatMsg = new ChatMsg(split[1], 0, split[2], "我：");
                    list.add(chatMsg);
                } else {
                    ChatMsg chatMsg = new ChatMsg(split[1], 1, split[2], ("来自：" + split[0]));
                    list.add(chatMsg);
                }
                Log.i("提示","输出信息ChatHandler");

                // 向适配器set数据
                adapter.setData(list);
                rv.setAdapter(adapter);
                LinearLayoutManager manager = new LinearLayoutManager(ChatActivity.this, LinearLayoutManager.VERTICAL, false);
                rv.setLayoutManager(manager);
//                创建一个 LinearLayoutManager 对象，并将其设置为 RecyclerView 的布局管理器，用于控制列表项的显示方式。
            }
        }
    }



}