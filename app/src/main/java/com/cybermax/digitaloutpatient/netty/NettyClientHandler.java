package com.cybermax.digitaloutpatient.netty;

import android.util.Log;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class NettyClientHandler extends SimpleChannelInboundHandler {
    private static  MessageLisener messageLisener;

    public   static void  registLisener(MessageLisener messageLiser){
        //
        System.out.println("注入新的监听");
        messageLisener = messageLiser;
    }

    public static ChannelHandlerContext context = null;

    public NettyClientHandler() {

    }

    //利用写空闲发送心跳检测消息
    public static Map<String, Class> messageMapping = new HashMap<>();



    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        //在此发送心跳消息
    }

    private void  handlerMessage(MessageDTO message){
        if(messageLisener==null) {
            System.out.println("no messageLisener registed");
            return;
        }
        messageLisener.onMessage(message);
    }


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object byteBuf) throws Exception {
        MessageDTO message = getMessageFromBuf(byteBuf);
        if(message!=null){
            System.out.println("【channelRead0】-----收到消息：");
            System.out.println(message.toString());
            handlerMessage(message);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object byteBuf) throws Exception {
        MessageDTO message = getMessageFromBuf(byteBuf);
        if(message!=null){
            System.out.println("【channelRead】收到消息"+message.toString());
            System.out.println(message.toString());
            handlerMessage(message);
        }
    }

    private MessageDTO getMessageFromBuf(Object byteBuf) {
        String message="";
        try {
            ByteBuf bf = (ByteBuf) byteBuf;
            byte[] byteArray = new byte[bf.capacity()];
            bf.readBytes(byteArray);
            message = new String(byteArray);
            return  JSON.parseObject(message, MessageDTO.class);
        } catch (Exception e) {
            Logger.getLogger(NettyClientHandler.class.getName()).log(Level.WARNING,"消息转换失败"+message);
        }
        return null;
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        /*  super.channelActive(ctx);*/


    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        // 主动断开还是被动断开，被动断开需要设置重连，主动断开不需要重连(比如重新设置IP)
        Log.e("NettyClientHandler", "网络已经断开....返回主页");
        NettyClient client = NettyClient.getInstance();
        if (null != client)
            client.setStatus(0);
    /*    if (BaseApplication.getContext() != null && BaseApplication.getContext().getClass().equals(IndexActivity.class)) {
            Intent intent1 = new Intent(BaseApplication.getContext(), IndexActivity.class);
            BaseApplication.getContext().startActivity(intent1);
            ActivityManager.finishAll();
        }*/
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        super.channelWritabilityChanged(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        super.exceptionCaught(ctx, cause);
    }
}
