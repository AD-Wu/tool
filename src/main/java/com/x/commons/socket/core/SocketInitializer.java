package com.x.commons.socket.core;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @Desc：
 * @Author：AD
 * @Date：2020/3/27 16:37
 */
public class SocketInitializer extends ChannelInitializer<SocketChannel> {

    private final SocketConfig config;

    private final ISocketListener listener;
    public SocketInitializer(SocketConfig config,ISocketListener listener) {
        this.config = config;
        this.listener = listener;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline p = ch.pipeline();
        p.addLast(new IdleStateHandler(
                config.getReadTimeout(),
                config.getWriteTimeout(),
                config.getIdleTimeout(),
                TimeUnit.MINUTES
        ));

        p.addLast(new SocketHandler(listener));
    }
}
