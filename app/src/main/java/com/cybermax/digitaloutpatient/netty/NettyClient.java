package com.cybermax.digitaloutpatient.netty;

import android.content.Context;
import android.os.Message;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.cybermax.digitaloutpatient.netty.coder.MessageByteDecoder;
import com.cybermax.digitaloutpatient.netty.coder.MessageByteEncoder;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

public class NettyClient  {


    private static int port;
    private static String host;
    private static   SocketChannel socketChannel;
    private static NettyClient nettyClient;
    private int reConnLimit = 0;
    private static Context mContext;
    public int mode = 1;// 0.手动模式（只执行一次）/1.自动模式（断线重连）
    // 0=未连接,1=正在连接,2=已连接,3=连接失败
    private volatile int status = 0;
    private ChannelFuture future;
    private EventLoopGroup eventLoopGroup;

    private NettyClient(int port, String host) {
        this.port = port;
        this.host = host;
    }

    public static NettyClient getInstance() {
        return nettyClient;
    }

    public static NettyClient initInstance(Integer port, String host) {
        if(null != nettyClient)
            return nettyClient;
        synchronized (NettyClient.class) {
            // 判断当前channel是否处于连接状态,关闭channel重新初始化
            if(null == nettyClient)
               nettyClient = new NettyClient(port,host);
        }
        return nettyClient;
    }

    public boolean connect(){
        reset();
        status = 1;
        try {
            eventLoopGroup=new NioEventLoopGroup();
            Bootstrap bootstrap=new Bootstrap();
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.option(ChannelOption.SO_KEEPALIVE,true);
            bootstrap.group(eventLoopGroup);
            bootstrap.remoteAddress(host,port);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    ChannelPipeline p = socketChannel.pipeline();
                    p.addLast(new IdleStateHandler(20,10,0));
                    p.addLast(new MessageByteDecoder());
                    p.addLast(new MessageByteEncoder());
                    p.addLast(new NettyClientHandler());
                }
            });
            Log.i(NettyClient.class.getName(), String.format("开始连接服务端%s:%s....",host,port));
            future =bootstrap.connect(new InetSocketAddress(host,port)).sync();
//                future.channel().closeFuture().sync();
            if (future.isSuccess()) {
                mode = 1;
                status = 2;
                socketChannel = (SocketChannel)future.channel();
                Log.i(NettyClient.class.getName(), String.format("成功连接到服务端%s:%s....",host,port));
            }else{
                throw new RuntimeException("连接失败");
            }
        } catch (Exception e) {
            status = 3;
            Log.e(NettyClient.class.getName(), String.format("无法连接到服务端%s:%s,请检查",host,port));
        }finally{
        }
        return true;
    }

    private void reset() {
        try {
            if(null != socketChannel)
                socketChannel.close();
            if(null != eventLoopGroup)
                eventLoopGroup.shutdownGracefully();
        }catch (Exception e) {
            Log.e(this.getClass().getName(),"连接异常");
        }
    }

    public boolean close() {
        try {
            mode = 0;
            status = 0;
            if (null != socketChannel)
                socketChannel.close();
            return true;
        }catch (Exception e) {
            return false;
        }
    }

    public boolean sendMessage(Message message){
        try {
            if(socketChannel !=null && socketChannel.isActive()){
                socketChannel.writeAndFlush(JSON.toJSON(message).toString());
                return true;
            }
        } catch (Exception e) {
            Log.e(NettyClient.class.getName(), String.format("无法连接到服务端%s,请检查",host));
        }
        return false;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        NettyClient.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        NettyClient.host = host;
    }
}






