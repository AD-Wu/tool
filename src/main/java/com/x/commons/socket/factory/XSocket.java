package com.x.commons.socket.factory;

import com.x.commons.socket.bean.SocketConfig;
import com.x.commons.socket.core.ISocket;
import com.x.commons.socket.core.ISocketListener;
import com.x.commons.socket.core.ISocketSerializer;
import com.x.commons.socket.core.SocketInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.TimeUnit;

/**
 * @Desc
 * @Date 2020-03-30 22:52
 * @Author AD
 */
public class XSocket implements ISocket {
    
    private volatile boolean started = false;
    
    private volatile boolean connected = false;
    
    private Channel server;
    
    private Channel client;
    
    private ISocketListener listener;
    
    private ISocketSerializer serializer;
    
    public XSocket(ISocketListener listener) {
        this(listener, null);
    }
    
    public XSocket(ISocketListener listener, ISocketSerializer serializer) {
        this.listener = listener;
        this.serializer = serializer;
    }
    
    @Override
    public synchronized boolean connect(SocketConfig config) throws Exception {
        if (!connected && !config.isServerMode()) {
            new Thread(() -> {
                NioEventLoopGroup worker = new NioEventLoopGroup();
                Bootstrap boot = new Bootstrap();
                boot.group(worker);
                boot.channel(NioSocketChannel.class);
                boot.option(ChannelOption.SO_KEEPALIVE, true);
                boot.handler(new SocketInitializer(config, listener, serializer));
                try {
                    String ip = config.getIp();
                    int port = config.getPort();
                    ChannelFuture f = boot.connect(ip, port).sync();
                    connected = true;
                    client = f.channel();
                    client.closeFuture().sync();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    connected = false;
                    worker.shutdownGracefully();
                }
            }, "X-Socket-Client").start();
        }
        TimeUnit.SECONDS.sleep(5);
        return connected;
    }
    
    @Override
    public synchronized void disconnect() {
        if (connected && client != null) {
            client.close();
            connected = false;
        }
    }
    
    @Override
    public synchronized boolean start(SocketConfig config) throws Exception {
        if (!started && config.isServerMode()) {
            ServerBootstrap boot = new ServerBootstrap();
            boot.channel(NioServerSocketChannel.class);
            boot.option(ChannelOption.SO_KEEPALIVE, config.isKeepalive());
            boot.option(ChannelOption.SO_BACKLOG, config.getClientCount());
            boot.childHandler(new SocketInitializer(config, listener, serializer));
            
            EventLoopGroup boss = new NioEventLoopGroup();
            NioEventLoopGroup worker = new NioEventLoopGroup();
            new Thread(() -> {
                boot.group(boss, worker);
                try {
                    ChannelFuture future = boot.bind(config.getPort()).sync();
                    started = true;
                    server = future.channel();
                    server.closeFuture().sync();
                } catch (Exception e) {
                    e.printStackTrace();
                    started = false;
                } finally {
                    started = false;
                    worker.shutdownGracefully();
                    boss.shutdownGracefully();
                }
            }, "X-Socket-Server").start();
        }
        TimeUnit.SECONDS.sleep(5);
        return started;
    }
    
    @Override
    public synchronized void stop() {
        if (started && server != null) {
            server.close();
            started = false;
        }
    }
    
}
