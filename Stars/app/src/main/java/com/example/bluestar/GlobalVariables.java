package com.example.bluestar;

import java.net.Socket;

//使用单例模式：创建一个单例类，其中包含要共享的变量，并提供公共的访问方法。
public class GlobalVariables {
    private static GlobalVariables instance;
    private int counter = 0;
    private String[] Chatobject=null;
    private String SelfAddress=null;

    private Client client=null;

    private Socket self_socket=null;

    private GlobalVariables() {
        // 私有构造函数
    }

    public static synchronized GlobalVariables getInstance() {
        if (instance == null) {
            instance = new GlobalVariables();
        }
        return instance;
    }

    public int getCounter() {
        return counter;
    }
    public void setCounter(int value) {
        counter = value;
    }

    public String[] getChatobject() {
        return Chatobject;
    }
    public void setChatobject(String[] list) {
        Chatobject = list;
    }

    public String getSelfAddress() {
        return SelfAddress;
    }
    public void setSelfAddress(String address) {
        SelfAddress = address;
    }

    public Socket getSelf_socket() {
        return this.self_socket;
    }
    public void setSelf_socket(Socket self_socket) {
        this.self_socket = self_socket;
    }

    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        client = client;
    }



}

