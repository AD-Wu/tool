package com.x.commons.socket.client;

import com.x.commons.socket.bean.XSocketProtocol;
import com.x.commons.socket.core.ISocketListener;
import com.x.commons.socket.core.XSocketChannel;
import com.x.commons.util.string.Strings;
import com.x.commons.util.thread.Runner;
import io.netty.buffer.ByteBuf;
import io.netty.handler.timeout.IdleStateEvent;

import java.util.concurrent.TimeUnit;

/**
 * @Desc
 * @Date 2020-03-28 00:34
 * @Author AD
 */
public class SocketClientListener implements ISocketListener<XSocketProtocol> {
    
    @Override
    public void active(XSocketChannel channel) throws Exception {
        System.out.println("client >>> 通道建立：" + channel.toString());
        channel.send("client:active");
    }
    
    @Override
    public void inActive(XSocketChannel channel) throws Exception {
        System.out.println("client >>> 通道关闭:" + channel.toString());
        channel.close();
        int activeCount = Runner.getRunner().getActiveCount();
        long taskCount = Runner.getRunner().getTaskCount();
        System.out.println("activeCount:" + activeCount);
        System.out.println("taskCount:" + taskCount);
        while (activeCount != 0 || taskCount != 0) {
            System.out.println("activeCount:" + activeCount);
            System.out.println("taskCount:" + taskCount);
            TimeUnit.SECONDS.sleep(1);
        }
        System.out.println("activeCount:" + activeCount);
        System.out.println("taskCount:" + taskCount);
    }
    
    @Override
    public void receive(XSocketChannel channel, ByteBuf buf) throws Exception {
        System.out.println("client >>> 接收数据：" + channel.toString());
        int i = buf.readableBytes();
        byte[] data = new byte[i];
        buf.readBytes(data);
        System.out.println("client >>> 接收数据：" + Strings.toHex(data));
        channel.send("client:receive >> " + Strings.toHex(data));
    }
    
    @Override
    public void receive(XSocketChannel channel, XSocketProtocol msg) throws Exception {
        System.out.println("client >>> 接收数据");
    }
    
    @Override
    public void timeout(XSocketChannel channel, IdleStateEvent event) throws Exception {
        System.out.println("client >>> 超时：" + channel.toString());
    }
    
    @Override
    public void error(XSocketChannel channel, Throwable cause) throws Exception {
        System.out.println("client >>> 错误：" + channel.toString() + ";error=" + cause.getMessage());
    }
    
}
