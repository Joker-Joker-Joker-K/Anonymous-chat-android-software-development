//实现多客户端即时聊天
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

//首先创建客户端线程管理类，保存连接的客户端信息：存储客户端的地址和对应的Socket对象。
//创建聊天服务线程类，创建ServerSocket监听客户端请求，保存到客户端线程管理类中，并监听指定端口

//提供 startServer() 方法，用于启动服务器。该方法会创建一个新的 ServerThread 对象并启动它的线程。
//
//提供 shutdown() 方法，用于关闭服务器和清空 clientList 中的客户端连接。
//
//提供 sendMsgAll() 方法，用于将消息群发给所有客户端。

public class Server2 {

    private static Map<String,Socket> clientList = new HashMap<>();
    private static ServerThread serverThread = null;
//    这一行定义的不太懂
//    创建一个共享的锁对象
    private static final Object lock = new Object();

    private static class ServerThread implements Runnable {

        private int port = 10001;
        private boolean isExit = false;
        private ServerSocket server;

        public ServerThread() {
            try {
                server = new ServerSocket(port);
                System.out.println("启动服务成功" + "port:" + port);
            } catch (IOException e) {
                System.out.println("启动server失败，错误原因：" + e.getMessage());
            }
        }

        @Override
        public void run() {
            try {
                while (!isExit) {
                    // 进入等待环节
                    System.out.println("等待手机的连接... ... ");
                    final Socket socket = server.accept();
                    // 获取手机连接的地址及端口号
                    final String address = socket.getRemoteSocketAddress().toString();
                    System.out.println("连接成功，连接的手机为：" + address);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                // 单线程索锁
                                synchronized (this){
                                    // 放进到Map中保存
                                    clientList.put(address,socket);
                                }
                                // 定义输入流
                                InputStream inputStream = socket.getInputStream();
                                byte[] buffer = new byte[1024];
                                int len;
                                while ((len = inputStream.read(buffer)) != -1){
                                    String text = new String(buffer,0,len);
                                    System.out.println("收到的数据为：" + text);
                                    //判断是否进行消息查询
                                    String[] split = text.split("//");
//                                    甚至可以写一个自动更新函数，当连接中断或者有连接加入就自动发送一个代码自动更新
//                                    对啊，我完全没必要用按钮连接
//                                    算了有点麻烦，需要在安卓前端设置监听事件，之后有时间再说吧
//                                    不过很有可能没时间再去搞了
//                                    随便写写算了
                                    if(Objects.equals(split[3], "2")){
                                        StringBuilder addressStringBuilder = new StringBuilder();
                                        for (String address : clientList.keySet()) {
                                            addressStringBuilder.append(address).append("_");
                                        }
                                        String allAddresses = addressStringBuilder.toString();
                                        // 移除最后一个分隔符 "_"
                                        if (allAddresses.endsWith("_")) {
                                            allAddresses = allAddresses.substring(0, allAddresses.length() - 1);
                                        }

                                        split[1]=allAddresses;
                                        split[2]=address;
                                        System.out.println(address+"发起查询："+allAddresses);

//                                        拼接回去
                                        StringBuilder sbText = new StringBuilder();
                                        sbText.append(split[0]);//端口
                                        sbText.append("//");
                                        sbText.append(split[1]);//alladdress/data
                                        sbText.append("//");
                                        sbText.append(split[2]);//时间/selfaddress
                                        sbText.append("//");
                                        sbText.append(split[3]);//选项



                                        text = sbText.toString();
                                        // 在这里群发消息
                                        sendMsgAll(text);
//                                        可以选择私聊的
//                                        sendMsg(text,clientList.get(split[2]));
                                    }
//                                    增加私聊功能
                                    else if (Objects.equals(split[3], "1")){
//                                        msg都不够用需要增加一个项来判断执行什么操作
//                                        在时间那里改成id项
//                                        拼接回去
                                        StringBuilder sbText = new StringBuilder();
                                        sbText.append(split[0]);
                                        sbText.append("//");
                                        sbText.append(split[1]);
                                        sbText.append("//");
                                        sbText.append(split[2]);
                                        sbText.append("//");
                                        sbText.append(split[3]);//选项

                                        text = sbText.toString();

                                        sendMsg(text,clientList.get(split[2]));
                                        System.out.println(address+"和"+clientList.get(split[2])+"聊天");
                                    }
                                    else {
                                        // 在这里群发消息
                                        sendMsgAll(text);
                                        System.out.println("群发了一条消息");
                                    }






                                }

                            }catch (Exception e){
                                System.out.println("错误信息为：" + e.getMessage());
                            }finally {
                                synchronized (this){
                                    System.out.println("关闭链接：" + address);
                                    clientList.remove(address);
                                }
                            }
                        }
                    }).start();

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void Stop(){
            isExit = true;
            if (server != null){
                try {
                    server.close();
                    System.out.println("已关闭server");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static ServerThread startServer(){
        System.out.println("开启服务");
        if (serverThread != null){
            showDown();
        }
        serverThread = new ServerThread();
        new Thread(serverThread).start();
        System.out.println("开启服务成功");
        return serverThread;
    }

    // 关闭所有server socket 和 清空Map
    public static void showDown(){
        for (Socket socket : clientList.values()) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        serverThread.Stop();
        clientList.clear();
    }

    // 群发的方法
    public static boolean sendMsgAll(String msg){
        try {
            for (Socket socket : clientList.values()) {
//                OutputStream outputStream = socket.getOutputStream();
//                outputStream.write(msg.getBytes("utf-8"));
                synchronized (lock){
                    OutputStream outputStream = socket.getOutputStream();
                    outputStream.write(msg.getBytes("utf-8"));
                }
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    // 私聊方法
    public static boolean sendMsg(String msg,Socket socket){
        try {
            synchronized (lock){
                OutputStream outputStream = socket.getOutputStream();
                outputStream.write(msg.getBytes("utf-8"));
            }

            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

}



