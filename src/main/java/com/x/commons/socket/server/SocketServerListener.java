package com.x.commons.socket.server;

import com.x.commons.socket.core.ISocketListener;
import com.x.commons.socket.core.SocketChannel;
import com.x.commons.util.string.Strings;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @Desc
 * @Date 2020-03-28 00:29
 * @Author AD
 */
public class SocketServerListener implements ISocketListener {
    
    @Override
    public void active(SocketChannel channel) throws Exception {
        System.out.println("server >>> 通道建立：" + channel.toString());
        channel.send("server:channel active");
    }
    
    @Override
    public void inActive(SocketChannel channel) throws Exception {
        System.out.println("server >>> 通道关闭:" + channel.toString());
        channel.close();
    }
    
    @Override
    public void receive(SocketChannel channel, byte[] data) throws Exception {
        System.out.println("server >>> 接收数据：" + channel.toString() + "=" + Strings.toHex(data));
        channel.send("server:receive >> " + Strings.toHex(data));
    }
    
    @Override
    public void timeout(SocketChannel channel, IdleStateEvent event) throws Exception {
        System.out.println("server >>> 超时：" + channel.toString());
        channel.send("server:timeout close");
        channel.close();
    }
    
    @Override
    public void error(SocketChannel channel, Throwable cause) throws Exception {
        System.out.println("server >>> 错误：" + channel.toString() + ";error=" + cause.getMessage());
        channel.send("server:error");
        channel.close();
    }
    
}
