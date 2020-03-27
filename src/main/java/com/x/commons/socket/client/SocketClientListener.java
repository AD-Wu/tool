package com.x.commons.socket.client;

import com.x.commons.socket.core.ISocketListener;
import com.x.commons.socket.core.SocketChannel;
import com.x.commons.util.string.Strings;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @Desc
 * @Date 2020-03-28 00:34
 * @Author AD
 */
public class SocketClientListener implements ISocketListener {
    
    @Override
    public void active(SocketChannel channel) throws Exception {
        System.out.println("client >>> 通道建立：" + channel.toString());
        channel.send("client:active");
    }
    
    @Override
    public void inActive(SocketChannel channel) throws Exception {
        System.out.println("client >>> 通道关闭:" + channel.toString());
        channel.close();
    }
    
    @Override
    public void receive(SocketChannel channel, byte[] data) throws Exception {
        System.out.println("client >>> 接收数据：" + channel.toString() + "=" + Strings.toHex(data));
        channel.send("client:receive >> "+Strings.toHex(data));
    }
    
    @Override
    public void timeout(SocketChannel channel, IdleStateEvent event) throws Exception {
        System.out.println("client >>> 超时：" + channel.toString());
    }
    
    @Override
    public void error(SocketChannel channel, Throwable cause) throws Exception {
        System.out.println("client >>> 错误：" + channel.toString() + ";error=" + cause.getMessage());
    }
    
}
