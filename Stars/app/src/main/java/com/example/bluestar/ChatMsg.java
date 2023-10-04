package com.example.bluestar;
//聊天消息的数据定义
public class ChatMsg {
    private String msg;
//    消息内容
    private int number;
//    消息类型0，1（发，收）2(查询)
    private String time;
    //    消息时间
    private String name;
//    id名称

    public ChatMsg() {
        this.msg = null;
        this.number = 0;
        this.name = null;
        this.time = null;
    }

    public ChatMsg(String msg, int number,String time,String name) {
        this.msg = msg;
        this.number = number;
        this.name = name;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

}
