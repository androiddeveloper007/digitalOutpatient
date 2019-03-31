package com.cybermax.digitaloutpatient.netty.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

import com.cybermax.digitaloutpatient.bean.share.ServerInfo;
import com.cybermax.digitaloutpatient.netty.NettyClient;
import com.cybermax.digitaloutpatient.tool.SharedPreferenceUtil;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by ZHANGZHIYU on 2018/8/7.
 */

public class NetService extends Service {

    public static NettyClient nettyClient;

    private Intent intentActivity  = new Intent("com.service.isConnection");
    // nettyClient
    public static Timer timer = null;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        Logger.getLogger(NetService.class.getName()).info("net服务销毁");
        try {
            // 销毁netty
            timer.cancel();
            timer.purge();
            if(null != nettyClient)
                nettyClient.close();
        }catch (Exception e) {

        }

        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
              Logger.getLogger(NetService.class.getName()).info("net服务启动");
            //获取监听不断监听服务端消息，只要客户端不退出的情况下，始终保持连接
            ServerInfo serverInfo = (ServerInfo) SharedPreferenceUtil.getInstance().getObject(ServerInfo.class);
            String port = serverInfo.getNettyPort();
            String ip =  serverInfo.getIp();
            if (TextUtils.isEmpty(ip) || TextUtils.isEmpty(port)) {
              //  intent = new Intent(this, IpConfigActivity.class);
                this.startActivity(intent);
            }
            nettyClient = NettyClient.initInstance(Integer.parseInt(port), serverInfo.getIp());
            reconnect();
            return super.onStartCommand(intent, flags, startId);
    }


    private void reconnect() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    NettyClient nettyClient = (NettyClient) NetService.nettyClient;
                    if(null != nettyClient && (nettyClient.getStatus() == 0 || nettyClient.getStatus() == 3)) { // 未连接
                        Log.i(this.getClass().getName(),"未连接或者连接失败......");
                        intentActivity.putExtra("isConnection",2);
                        sendBroadcast(intentActivity);
//                        if(nettyClient.getMode() == 0)
//                            return;
                        ServerInfo serverInfo = (ServerInfo) SharedPreferenceUtil.getInstance().getObject(ServerInfo.class);
                        String port = serverInfo.getNettyPort();
                        String ip =  serverInfo.getIp();
                        if (!TextUtils.isEmpty(ip) && !TextUtils.isEmpty(port)) {
                            nettyClient.setHost(ip);
                            nettyClient.setPort(Integer.parseInt(port));
                        }
                        nettyClient.connect();
                    }
                    if(null != nettyClient && nettyClient.getStatus() == 1){ //正在连接
                        Log.i(this.getClass().getName(),"正在连接......");
                        return;
                    }
                    if(null != nettyClient && nettyClient.getStatus() == 2) { // 已连接
                        // 去掉loading
                        intentActivity.putExtra("isConnection",1);
                        sendBroadcast(intentActivity);
                        // 大部分正常情况下是连接状态
                        Log.i(this.getClass().getName(),"连接保持正常......");
                        return;
                    }
                }catch (Exception e) {
                    Log.e(this.getClass().getName(),"断线重连失败......");
                }

            }
        },new Date(),3000);
    }

    @Override
    public void onCreate() {
        Logger.getLogger(NetService.class.getName()).log(Level.INFO, "net服务创建");
        super.onCreate();
    }

}
