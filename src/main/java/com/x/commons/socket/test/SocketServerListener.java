package com.x.commons.socket.test;

import com.x.commons.socket.bean.XSocketProtocol;
import com.x.commons.socket.core.ISocketListener;
import com.x.commons.socket.core.XSocketChannel;
import com.x.commons.util.string.Strings;
import io.netty.buffer.ByteBuf;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @Desc
 * @Date 2020-03-28 00:29
 * @Author AD
 */
public class SocketServerListener implements ISocketListener<XSocketProtocol> {
    
    @Override
    public void active(XSocketChannel channel) throws Exception {
        System.out.println("server >>> 通道建立：" + channel.toString());
        XSocketProtocol p = new XSocketProtocol(new byte[]{1, 2, 3});
        channel.send(p);
    }
    
    @Override
    public void inActive(XSocketChannel channel) throws Exception {
        System.out.println("server >>> 通道关闭:" + channel.toString());
        channel.close();
    }
    
    @Override
    public void receive(XSocketChannel channel, ByteBuf buf, long seq) throws Exception {
        System.out.println("server >>> 接收byte数据：" + channel.toString());
        int i = buf.readableBytes();
        byte b = buf.readByte();
        buf.resetReaderIndex();
        System.out.println("readable=" + i + ",read=" + Strings.toHex(b));
        channel.send("server:receive >> " + Strings.toHex(b));
    }
    
    @Override
    public void receive(XSocketChannel channel, XSocketProtocol msg, long seq) throws Exception {
        System.out.println("server >>> 接收对象数据：" + channel.toString());
        System.out.println("server >>> 接收对象数据：" + msg);
        channel.send("server:receive >> " + msg);
    }
    
    @Override
    public void timeout(XSocketChannel channel, IdleStateEvent event) throws Exception {
        System.out.println("server >>> 超时：" + channel.toString());
        channel.send("server:timeout close");
        channel.close();
    }
    
    @Override
    public void error(XSocketChannel channel, Throwable cause) throws Exception {
        System.out.println("server >>> 错误：" + channel.toString() + ";error=" + cause.getMessage());
        channel.send("server:error");
        // channel.close();
    }
    
}
