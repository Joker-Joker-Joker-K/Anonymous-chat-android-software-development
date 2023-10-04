package com.example.bluestar;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
//只用来测试能否连接服务端
public class newClient {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public void connect() {
        try {
            // 连接服务器
//            Log.i("提示","kaishi");
            socket = new Socket("10.0.2.2", 9999);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // 发送消息到服务器
            out.println("Hello, server!");

            // 从服务器接收消息
            String response = in.readLine();
            System.out.println("Server response: " + response);


            // 关闭连接
            out.close();
            in.close();
//            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
