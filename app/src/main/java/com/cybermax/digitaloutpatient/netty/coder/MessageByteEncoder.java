package com.cybermax.digitaloutpatient.netty.coder;

import android.text.TextUtils;

import com.cybermax.digitaloutpatient.util.FileUtils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MessageByteEncoder extends MessageToByteEncoder<CharSequence> {


    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, CharSequence msg, ByteBuf byteBuf) throws Exception {
        if (TextUtils.isEmpty(msg))
            return;
        // 报文长度
        byte[] bytes = msg.toString().getBytes("UTF-8");
        int dataLength = bytes.length;
        dataLength = FileUtils.beToLe(bytes.length);
        byteBuf.writeInt(dataLength);
        byteBuf.writeBytes(bytes);
    }

}
