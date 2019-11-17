package com.x.commons.socket.handler.client;

import com.x.commons.socket.config.ClientConfig;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @Desc TODO
 * @Date 2019-11-01 23:36
 * @Author AD
 */
public class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {

    private final ClientConfig config;

    public ClientChannelInitializer(ClientConfig config) {

        this.config = config;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline p = ch.pipeline();

        p.addLast(new IdleStateHandler(config.getReadTimeout(), config.getWriteTimeout(), config.getIdleTimeout(),
                                       TimeUnit.MINUTES));

    }

}
