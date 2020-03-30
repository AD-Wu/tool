package com.x.commons.socket.client;

import com.x.commons.socket.core.ISocket;
import com.x.commons.socket.core.ISocketListener;
import com.x.commons.socket.core.ISocketSerializer;
import com.x.commons.socket.core.SocketInitializer;
import com.x.commons.timming.Timer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Desc
 * @Date 2019-11-01 23:08
 * @Author AD
 */
public class SocketClient implements ISocket {
    
    private Bootstrap boot;
    
    private Channel channel;
    
    private NioEventLoopGroup worker;
    
    private final SocketClientConfig config;
    
    private volatile boolean connected;
    
    private Runnable task;
    
    public SocketClient(SocketClientConfig config, ISocketListener listener) {
        this(config, listener, null);
    }
    
    public SocketClient(SocketClientConfig config, ISocketListener listener, ISocketSerializer serializer) {
        this.config = config;
        this.connected = false;
        this.boot = new Bootstrap();
        boot.channel(NioSocketChannel.class);
        boot.option(ChannelOption.SO_KEEPALIVE, true);
        boot.handler(new SocketInitializer(config, listener, serializer));
    }
    
    @Override
    public synchronized void start() throws Exception {
        if (!connected) {
            new Thread(() -> {
                worker = new NioEventLoopGroup();
                boot.group(worker);
                try {
                    ChannelFuture f = boot.connect(config.getIp(), config.getPort()).sync();
                    connected = true;
                    channel = f.channel();
                    channel.closeFuture().sync();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    connected = false;
                    Timer.get().remove(task);
                    worker.shutdownGracefully();
                }
            }, "X-Socket-Client").start();
            
        }
    }
    
    @Override
    public synchronized void stop() {
        if (connected && channel != null) {
            channel.close();
        }
    }
    
}
