package com.example.bluestar;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.net.Socket;
//没有使用，本来打算将socket代码放入到服务中
public class MyclientService extends Service {
    private static Socket socket;
    public static boolean connection_state = false;
    private Log log;

    public MyclientService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        log.i("服务状态:","服务被创建");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {







        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        log.i("服务状态:","服务被销毁");
        super.onDestroy();

    }
}