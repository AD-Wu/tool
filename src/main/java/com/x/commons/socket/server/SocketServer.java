package com.x.commons.socket.server;

import com.x.commons.socket.config.ServerConfig;
import com.x.commons.socket.handler.server.ServerChannelInitializer;
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
class SocketServer implements ISocket {

    private final ServerConfig config;

    private Channel channel;

    private ServerBootstrap boot;

    private EventLoopGroup boss;

    private NioEventLoopGroup worker;

    private volatile boolean start = false;

    SocketServer(ServerConfig config) {

        this.config = config;
        boot = new ServerBootstrap();
        boot.channel(NioServerSocketChannel.class);
        boot.option(ChannelOption.SO_KEEPALIVE, config.isKeepalive());
        boot.option(ChannelOption.SO_BACKLOG, config.getClientCount());
        boot.childHandler(new ServerChannelInitializer(config));

    }

    @Override
    public synchronized void start() {
        if (!start) {
            boss = new NioEventLoopGroup();
            worker = new NioEventLoopGroup();
            int port = config.getPort();
            new Thread(() -> {
                boot.group(boss, worker);
                try {
                    ChannelFuture future = boot.bind(port).sync();
                    start = true;
                    channel = future.channel();
                    channel.closeFuture().sync();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    start = false;
                    worker.shutdownGracefully();
                    boss.shutdownGracefully();
                }
            }).start();
        }

    }

    @Override
    public synchronized void stop() {
        if (start && channel != null) {
            channel.close();
        }
    }

    @Override
    public void send(Object msg) {

    }

    // @Override
    public void send(byte[] msg) {
        channel.writeAndFlush(msg);
    }
    
    @Override
    public String toString() {

        return "SocketServer{config=" + config + "}";
    }

}
