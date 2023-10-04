package com.example.bluestar;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;

import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
//被淘汰，只能实现客户端和服务端一对一通信
///重写
public class Client {
    private Socket socket;

    private Handler handler;

    public Client(Handler handler) {
        this.handler = handler;
    }
//    private PrintWriter out;
//    private BufferedReader in;

//    连接服务器同时不断接收信息
//    final Handler all_handler = new MyFragment.AllChatHandler();
//
    public void connect() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
//                    socket = new Socket("127.0.0.1", 10000);
                    socket = new Socket("10.0.2.2", 10001);
//                    担心测试出问题
                    GlobalVariables.getInstance().setSelf_socket(socket);

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
//                        将消息发送给主线程进行处理和显示
//                        all_handler.sendMessage(message);
                        handler.sendMessage(message);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public void send(int type,String data){
        //                                0群发1私聊2查询
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
//                    GlobalVariables.getInstance().setSelf_socket(socket);
//                    socket=GlobalVariables.getInstance().getSelf_socket();
                    if(type==0){
//                        群发
                        OutputStream outputStream = socket.getOutputStream();
//                                0群发1私聊2查询
                        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");    //设置日期格式
                        outputStream.write((socket.getLocalPort() +"//" + data + "//" + df.format(new Date()) +"//" + "0").getBytes("utf-8"));
                        outputStream.flush();
                    } else if (type==1) {
//                        私聊

                    }else {
//                        查询
                        socket=GlobalVariables.getInstance().getSelf_socket();







                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }


//    private class AllChatHandler extends Handler {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            if (msg.what == 1) {
//                int localPort = socket.getLocalPort();
////                获取socket本地端口号
//                String[] split = ((String) msg.obj).split("//");
//                if(split[3]=="2"){
////                    查询
//                    String[] onlineAddress =split[1].split("_");
//                    GlobalVariables.getInstance().setSelfAddress(split[2]);
//                    GlobalVariables.getInstance().setChatobject(onlineAddress);
//                    Log.i("提示","查询开始");
//;
//
//                } else if (split[3]=="1"){
////                    私聊
//                    String[] onlineAddress =split[1].split("_");
//
//
//                }
//                else{
////                    聊天室
//                    if (split[0].equals(localPort + "")) {
//                        ChatMsg chatMsg = new ChatMsg(split[1], 0, split[2], "我：");
//
//                    } else {
//                        ChatMsg chatMsg = new ChatMsg(split[1], 1, split[2], ("来自：" + split[0]));
//
//                    }
//                    Log.i("提示","输出信息ChatHandler");
//
//                }
//
//
//
//            }
//        }
//    }
}


