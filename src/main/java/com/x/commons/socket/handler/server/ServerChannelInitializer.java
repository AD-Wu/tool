package com.x.commons.socket.handler.server;

import com.x.commons.socket.config.ServerConfig;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @Desc TODO
 * @Date 2019-10-28 23:01
 * @Author AD
 */
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    private final ServerConfig config;

    public ServerChannelInitializer(ServerConfig config) {
        this.config = config;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline p = ch.pipeline();

        p.addLast(new IdleStateHandler(config.getReadTimeout(), config.getWriteTimeout(), config.getIdleTimeout(),
                                       TimeUnit.MINUTES));

        p.addLast(new ServerHandler());
    }

}
