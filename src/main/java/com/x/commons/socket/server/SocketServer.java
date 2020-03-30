package com.x.commons.socket.server;

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

/**
 * @Date 2019-10-18 21:14
 * @Author AD
 */
public class SocketServer implements ISocket {
    
    // ------------------------ 变量定义 ------------------------
    private final SocketServerConfig config;
    
    private Channel channel;
    
    private ServerBootstrap boot;
    
    private EventLoopGroup boss;
    
    private NioEventLoopGroup worker;
    
    private volatile boolean started = false;
    
    // ------------------------ 构造方法 ------------------------
    
    /**
     * 构造不需要序列化/反序列化的socket server
     *
     * @param config   服务配置
     * @param listener 消息监听器，不能为null
     */
    public SocketServer(SocketServerConfig config, ISocketListener listener) {
        this(config, listener, null);
    }
    
    /**
     * 构造需要序列化/反序列化的socket server
     *
     * @param config     服务配置
     * @param listener   消息监听器，不能为null
     * @param serializer 序列化者
     */
    public SocketServer(SocketServerConfig config,
            ISocketListener listener, ISocketSerializer serializer) {
        this.config = config;
        boot = new ServerBootstrap();
        boot.channel(NioServerSocketChannel.class);
        boot.option(ChannelOption.SO_KEEPALIVE, config.isKeepalive());
        boot.option(ChannelOption.SO_BACKLOG, config.getClientCount());
        boot.childHandler(new SocketInitializer(config, listener, serializer));
    }
    
    // ------------------------ 方法定义 ------------------------
    @Override
    public synchronized void start() throws Exception {
        if (!started) {
            boss = new NioEventLoopGroup();
            worker = new NioEventLoopGroup();
            new Thread(() -> {
                boot.group(boss, worker);
                try {
                    ChannelFuture future = boot.bind(config.getPort()).sync();
                    started = true;
                    channel = future.channel();
                    channel.closeFuture().sync();
                } catch (Exception e) {
                    e.printStackTrace();
                    started = false;
                } finally {
                    started = false;
                    worker.shutdownGracefully();
                    boss.shutdownGracefully();
                }
            }).start();
        }
    }
    
    @Override
    public synchronized void stop() throws Exception {
        if (started) {
            if (channel == null) {
                wait();
            } else {
                channel.close();
            }
        }
    }
    
    @Override
    public String toString() {
        return "SocketServer{config=" + config + "}";
    }
    
}
