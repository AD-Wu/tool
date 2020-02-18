package com.x.commons.socket.client;

import com.x.commons.socket.config.ClientConfig;
import com.x.commons.socket.handler.client.ClientChannelInitializer;
import com.x.commons.socket.server.ISocket;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Desc TODO
 * @Date 2019-11-01 23:08
 * @Author AD
 */
public class SocketClient implements ISocket {

    private Bootstrap boot;

    private Channel channel;

    private NioEventLoopGroup worker;

    private final ClientConfig config;

    private volatile boolean connected;

    public SocketClient(ClientConfig config) {
        this.config = config;
        connected = false;
        boot = new Bootstrap();
        boot.channel(NioSocketChannel.class);
        boot.option(ChannelOption.SO_KEEPALIVE, true);
        boot.handler(new ClientChannelInitializer(config));
        
    }

    @Override
    public synchronized void start() {
        if (!connected) {
            String ip = config.getIp();
            int port = config.getPort();
            new Thread(() -> {
                worker = new NioEventLoopGroup();
                this.boot.group(worker);
                try {
                    ChannelFuture f = this.boot.connect(ip, port).sync();
                    connected = true;
                    channel = f.channel();
                    channel.closeFuture().sync();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    connected = false;
                    worker.shutdownGracefully();
                }
            }).start();
        }

    }

    @Override
    public synchronized void stop() {
        if (connected && channel != null) {
            channel.close();
        }
    }
    
    @Override
    public void send(Object msg) {
        if(channel!=null){
            ChannelFuture future = channel.writeAndFlush(msg);
            System.out.println(future);
        }
    }
    
}
