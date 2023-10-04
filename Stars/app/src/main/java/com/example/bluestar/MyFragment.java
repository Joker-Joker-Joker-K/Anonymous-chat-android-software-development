package com.example.bluestar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

//相当于活动，具体的页面操作,核心界面的核心函数
public class MyFragment extends Fragment {


    private String content;
    private Socket socket;
    private Socket socket2;

    private OutputStream os;
    private InputStream is;
    //    创建一个共享的锁对象
    private static final Object lock = new Object();

//    聊天室变量
    private ArrayList<ChatMsg> all_list;
    private AllAdapter all_adapter;
    private RecyclerView all_rv;
    private EditText all_et;
    private Button all_bt;


//    通讯录
    private Button chatlist_bu;
    private ListView list_lv;

//    私聊
    private Button list_bu;
    private RecyclerView list_rv;
    private listAdapter list_adapter;
    private listAdapter1 list_adapter1;

//    private  Client client;
//    private final Handler all_handler;


    public MyFragment(String content) {
        this.content = content;
//      this.Handler all_handler= new MyFragment.AllChatHandler() ;
//        all_handler= new MyFragment.AllChatHandler();
//        client = new Client(all_handler);
//        GlobalVariables.getInstance().setClient(client);
////        直接在构造函数中执行它
//        client.connect();

    }
//    定义要传输的数据

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = null;

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    socket = new Socket("10.0.2.2", 10001);
//////                    申请连接
//                        Log.i("提示","连接成功");
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//
//            }
//        }).start();

        if(content.equals("第一个Fragment")){
//            在线聊天：所有人一起聊天
            view = inflater.inflate(R.layout.all_chat,container,false);
//            直接挪过来吧，不研究函数了，时间不够
            all_rv = (RecyclerView) view.findViewById(R.id.allchat_rv);
            all_et = (EditText) view.findViewById(R.id.allchat_send_et);
            all_bt = (Button)view. findViewById(R.id.allchat_send_bu);

            all_list = new ArrayList<>();
            all_adapter = new AllAdapter(getActivity());
//        传入上下文对象把this改掉

//            适配器消息处理显示
            final  AllChatHandler all_handler=new MyFragment.AllChatHandler() ;

//            连接服务器
//              移动到构造函数里了

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
//                    socket = new Socket("127.0.0.1", 10000);
//                    socket = new Socket("10.0.2.2", 9999);
                        socket = new Socket("10.0.2.2", 10001);
//////                    申请连接
                        Log.i("提示","连接成功");
//                        把这个连接移动到共享部分
//                        socket = new Socket("10.0.2.2", 10001);
////                    申请连接
//                        Log.i("提示","连接成功");

//                        GlobalVariables.getInstance().setSelf_socket(socket);
//                        Socket s=GlobalVariables.getInstance().getSelf_socket();
//                        起不到任何用处

//                        如果我在建立连接时，就定义输出流，是不是可以解决问题
                        os=socket.getOutputStream();

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

                            all_handler.sendMessage(message);
//                        将消息发送给主线程进行处理和显示
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            all_bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String data = all_et.getText().toString();
//                    client.send(0,data);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {


                                Socket s=GlobalVariables.getInstance().getSelf_socket();

                                OutputStream outputStream=os;
//                                OutputStream outputStream = socket.getOutputStream();
//                                0群发1私聊2查询
                                SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");    //设置日期格式
                                outputStream.write((socket.getLocalPort() +"//" + data + "//" + df.format(new Date()) +"//" + "0").getBytes("utf-8"));
                                outputStream.flush();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            });





//            TextView txt_content = (TextView) view.findViewById(R.id.fg_content_tv);
//            txt_content.setText(content);
        }
        else if (content.equals("第二个Fragment")) {
//            通讯录：获取目前在线连接服务器的socket
            view = inflater.inflate(R.layout.activity_list,container,false);

//            list_rv = (RecyclerView) view.findViewById(R.id.list_rv);
            list_bu=(Button)view.findViewById(R.id.list_object_bu);
            list_adapter = new listAdapter(getActivity());
            list_adapter1 = new listAdapter1(getActivity(),GlobalVariables.getInstance().getChatobject());
            final Handler all_handler2 = new MyFragment.AllChatHandler();
//            all_handler2
//                        将消息发送给主线程进行处理和显示


//            TextView txt_content = (TextView) view.findViewById(R.id.fg_content_tv);
//            txt_content.setText(content);
//            实在不行，重新再创立一个socket连接，顶多也就麻烦一点
//            那这种情况下，第一个socket连接会中断吗，因为socket好像是最开始定义的一个值
//            而且有一个问题没有考虑过，我切换到其它导航栏，聊天室socket好像是在一直接收信息吗？
//            还有一个问题InputStream这个东西是一个代码里只能有一个吗
//            还有一个问题我这样再次建立一个连接那服务器那里不就重复又保存了一个连接吗
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
//                    socket = new Socket("127.0.0.1", 10000);
//                    socket = new Socket("10.0.2.2", 9999);

//                        把这个连接移动到共享部分
                        socket2 = new Socket("10.0.2.2", 10001);
//                    申请连接
                        Log.i("提示","通讯录-连接成功");


                        InputStream inputStream2 = socket2.getInputStream();
                        byte[] buffer = new byte[1024];
                        int len;
                        while ((len = inputStream2.read(buffer)) != -1) {
//                        循环不断从bufer中读取数据
//                        通过 inputStream.read(buffer) 方法返回实际读取的字节数。
                            String data = new String(buffer, 0, len);
                            // 发到主线程中 收到的数据
                            Message message = Message.obtain();
//                        实例化
                            message.what = 1;
                            message.obj = data;

                            all_handler2.sendMessage(message);
//                        将消息发送给主线程进行处理和显示
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();




//            查询现在在线用户
            list_bu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("提示","刷新通讯录列表");

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Log.i("提示","准备发出");
//                                发送服务器查询请求2
//                                全局变量存储socket竟然没有用

//                                socket=GlobalVariables.getInstance().getSelf_socket();
                                OutputStream outputStream2 = socket2.getOutputStream();
//                                OutputStream outputStream=os;
//                                解决了os的问题
//                                好像并没有解决这样这个东西是空的
                                Log.i("提示","重新定义输出流");
                                SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");    //设置日期格式
//                                可以选择在日期的地方改为ip
                                outputStream2.write((socket2.getLocalPort() + "//" + "查询" + "//" + df.format(new Date()) + "//" + "2").getBytes("utf-8"));
                                outputStream2.flush();
                                Log.i("提示","发出了查询");

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();




                }
            });


        }
        else if (content.equals("第三个Fragment")) {
            view = inflater.inflate(R.layout.fg_content,container,false);
//              蓝牙通信/发帖评论
//            放弃 改成了通讯录
            TextView txt_content = (TextView) view.findViewById(R.id.fg_content_tv);
            txt_content.setText(content);

            list_lv = (ListView) view.findViewById(R.id.list_lv);
            chatlist_bu=(Button)view.findViewById(R.id.list_object_bu);



            chatlist_bu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("提示","刷新通讯录列表");

//                     android.R.layout.simple_list_item_1
//                    通讯录数据
                    List<String> chatlist=null;
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item_layout,chatlist);

                    list_lv.setAdapter(adapter);
                }
            });
        }
        else if (content.equals("第四个Fragment")) {
//            个人信息修改
//            view = inflater.inflate(R.layout.fg_content,container,false);
//
//            TextView txt_content = (TextView) view.findViewById(R.id.fg_content_tv);
//            txt_content.setText(content);
            view = inflater.inflate(R.layout.activity_userinfo,container,false);
            TextView nameTextView = (TextView)view.findViewById(R.id.name_text_view);
            TextView bioTextView = (TextView)view.findViewById(R.id.bio_text_view);
            Button editButton = (Button)view.findViewById(R.id.edit_button);

            // 设置初始的个人信息文本
            String name = "名字:admin";
            String user_say = "个人简介:管理员";

            // 用UserDao中写好的函数获取名字和个人简介append到name和usersay中

            nameTextView.setText(name);
            bioTextView.setText(user_say);

            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 跳转到修改信息界面
                    Intent intent = new Intent(getActivity(), EditActivity.class);
                    startActivity(intent);
                }
            });





        }

        return view;
    }


// 处理列表项点击事件的方法
    private void onListItemClick(int position) {
        String selectedItem = GlobalVariables.getInstance().getChatobject()[position];

        // 执行跳转逻辑，启动列表详情页面并传递数据
        Intent intent = new Intent(getActivity(), ChatitemActivity.class);
        intent.putExtra("selectedItem", selectedItem);
        startActivity(intent);
    }

    private class AllChatHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                int localPort = socket.getLocalPort();
//                int localPort=GlobalVariables.getInstance().getSelf_socket().getLocalPort();
//                获取socket本地端口号
                String[] split = ((String) msg.obj).split("//");
                if(Objects.equals(split[3], "2")){
//                    查询
                    String[] onlineAddress =split[1].split("_");
                    GlobalVariables.getInstance().setSelfAddress(split[2]);
                    GlobalVariables.getInstance().setChatobject(onlineAddress);
                    Log.i("提示","查询开始");

//                    将适配器绑定

//                    突然发现我可以很轻松的实现自动刷新页面啊,等测试如果没有bug就用这个
//                    list_adapter.setData(list);
//                    list_rv.setAdapter(list);
//                    LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//                    list_rv.setLayoutManager(manager);
                    // 向适配器set数据
//                    all_adapter.setData(all_list);
                    list_adapter1.setData(GlobalVariables.getInstance().getChatobject());
                    list_rv.setAdapter(list_adapter1);
                    LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                    list_rv.setLayoutManager(manager);
//                创建一个 LinearLayoutManager 对象，并将其设置为 RecyclerView 的布局管理器，用于控制列表项的显示方式。
//                    点击事件方法
                    list_adapter1.setOnItemClickListener(new listAdapter1.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            onListItemClick(position);
                        }
                    });

                }
                else if (Objects.equals(split[3], "1")){
//                    私聊
                    Log.i("提示","私聊开始");
                    String[] onlineAddress =split[1].split("_");


                }
                else{
//                    聊天室
                    Log.i("提示","聊天室开始");
                    if (split[0].equals(localPort + "")) {
                        ChatMsg chatMsg = new ChatMsg(split[1], 0, split[2], "我：");
                        all_list.add(chatMsg);
                    } else {
                        ChatMsg chatMsg = new ChatMsg(split[1], 1, split[2], ("来自：" + split[0]));
                        all_list.add(chatMsg);
                    }
                    Log.i("提示","输出信息ChatHandler:"+all_list.get(0).getMsg());

                    // 向适配器set数据
                    all_adapter.setData(all_list);
                    all_rv.setAdapter(all_adapter);
                    LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                    all_rv.setLayoutManager(manager);
//                创建一个 LinearLayoutManager 对象，并将其设置为 RecyclerView 的布局管理器，用于控制列表项的显示方式。
                }
            }
        }
    }















}