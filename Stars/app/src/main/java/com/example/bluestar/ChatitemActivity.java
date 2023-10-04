package com.example.bluestar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import java.net.Socket;
import java.util.ArrayList;

public class ChatitemActivity extends AppCompatActivity {
    private Socket socket;
    private ArrayList<ChatMsg> list;
    private ChatAdapter adapter;

    private RecyclerView rv;
    private EditText et;
    private Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatitem);
        Intent intent = getIntent();
        String selectedItem = intent.getStringExtra("selectedItem");


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
                LinearLayoutManager manager = new LinearLayoutManager(ChatitemActivity.this, LinearLayoutManager.VERTICAL, false);
                rv.setLayoutManager(manager);
//                创建一个 LinearLayoutManager 对象，并将其设置为 RecyclerView 的布局管理器，用于控制列表项的显示方式。
            }
        }
    }
}