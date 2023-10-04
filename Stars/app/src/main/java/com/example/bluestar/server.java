package com.example.bluestar;

import org.json.JSONObject;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
//只能当作一个服务端代码的备份，因为服务端代码已经被改的面目全非，怕出Bug解决不了，
// 只能实现一对一交流的服务端代码，
public class server
{
//    入口函数
    public static void main(String[] args){

        try{
            System.out.println("Socket服务器开始运行...");
//            定义ServerSocket端口
            ServerSocket serverSocket=new ServerSocket(9999);
            while(true){
//                接收客户请求
                Socket socket=serverSocket.accept();
//                创建监听和发送线程
                new Thread(new Server_listen(socket)).start();
                new Thread(new Server_send(socket)).start();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
//定义一个监听类
class Server_listen implements Runnable{
//    继承Runnable接口实现他的run方法
    private Socket socket;
    Server_listen(Socket socket){
        this.socket=socket;
    }

    @Override
    public void run() {
//        输入流
//        标红线，是因为可能会出现IO错误，所以要放入try中
        try{
//            读取客户端传入信息，对象输入流
            ObjectInputStream ois=new ObjectInputStream(socket.getInputStream());
            while (true){
                System.out.println(ois.readObject());
            }
        }catch(Exception e){
            e.printStackTrace();
        }
//        客户端断开连接后，就结束（待定）。
        finally {
            try {
                socket.close();
            }catch(Exception e){
                e.printStackTrace();
            }

        }

    }
}

//发送数据类
class Server_send implements Runnable{
    private Socket socket;
    Server_send(Socket socket){
        this.socket=socket;
    }
    @Override
    public void run() {
        try{
//            对象输出流
            ObjectOutputStream oos=new ObjectOutputStream(socket.getOutputStream());
//            获取键盘输入数据
            Scanner scanner=new Scanner(System.in);
            while (true){
                oos.writeObject("请输入要发送的内容：");
                String string=scanner.nextLine();
//                通过json传递内容
//                导入外部库"D:\Desktop\Stars\json-simple-1.1.1.jar"
                JSONObject object=new JSONObject();
//                自定义类型：chat聊天消息，msg内容string
                object.put("type","chat");
                object.put("msg",string);
//                通过输出流发送到客户端
                oos.writeObject(object);
//                刷新缓冲区
                oos.flush();

            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}