package com.x.commons.socket.server;

import com.x.commons.socket.bean.SocketConfig;
import com.x.commons.socket.core.ISocket;
import com.x.commons.socket.core.ISocketListener;
import com.x.commons.socket.core.ISocketSerializer;
import com.x.commons.socket.core.SocketInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.concurrent.TimeUnit;

/**
 * @Desc
 * @Date 2020-03-31 22:36
 * @Author AD
 */
public class SocketServer implements ISocket {
    private volatile boolean started = false;
    
    private Channel server;
    
    private final ISocketListener listener;
    
    private final ISocketSerializer serializer;
    
    private final SocketConfig config;
    public SocketServer(SocketConfig config,ISocketListener listener) {
        this(config,listener, null);
    }
    
    public SocketServer(SocketConfig config,ISocketListener listener, ISocketSerializer serializer) {
        this.config = config;
        this.listener = listener;
        this.serializer = serializer;
    }
    @Override
    public boolean start() throws Exception {
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
    public void stop() {
        if (started && server != null) {
            server.close();
            started = false;
        }
    }
    
}
