package com.x.commons.socket.client;

import com.x.commons.socket.bean.SocketConfig;
import com.x.commons.socket.core.ISocket;
import com.x.commons.socket.core.ISocketListener;
import com.x.commons.socket.core.ISocketSerializer;
import com.x.commons.socket.core.SocketInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.TimeUnit;

/**
 * @Desc
 * @Date 2020-03-31 22:26
 * @Author AD
 */
public class SocketClient implements ISocket {
    
    private volatile boolean connected = false;
    
    private Channel client;
    
    private final ISocketListener listener;
    
    private final ISocketSerializer serializer;
    
    private final SocketConfig config;
    
    public SocketClient(SocketConfig config, ISocketListener listener) {
        this(config, listener, null);
    }
    
    public SocketClient(SocketConfig config, ISocketListener listener, ISocketSerializer serializer) {
        this.config = config;
        this.listener = listener;
        this.serializer = serializer;
    }
    
    @Override
    public synchronized boolean start() throws Exception {
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
        TimeUnit.SECONDS.sleep(3);
        return connected;
    }
    
    @Override
    public synchronized void stop() {
        if (connected && client != null) {
            client.close();
            connected = false;
        }
    }
    
}
