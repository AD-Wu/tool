package com.x.commons.socket.util;

import com.x.commons.socket.bean.SocketInfo;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

/**
 * @Desc
 * @Date 2020-03-31 23:20
 * @Author AD
 */
public final class SocketHelper {
    
    private SocketHelper() {}
    
    public static byte[] getData(ByteBuf buf) {
        byte[] data = new byte[buf.readableBytes()];
        buf.readBytes(data);
        buf.release();
        return data;
    }
    
    public static String getRemoteAddress(ChannelHandlerContext ctx) {
        return getSocketInfo(ctx).getRemoteAddress();
    }
    
    public static String getLocalAddress(ChannelHandlerContext ctx) {
        return getSocketInfo(ctx).getLocalAddress();
    }
    
    public static SocketInfo getSocketInfo(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        String remote = channel.remoteAddress().toString().substring(1);
        String local = channel.localAddress().toString().substring(1);
        return new SocketInfo(remote, local);
    }
    
}
