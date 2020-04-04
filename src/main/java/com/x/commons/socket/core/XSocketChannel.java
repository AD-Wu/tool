package com.x.commons.socket.core;

import com.x.commons.socket.bean.SocketInfo;
import com.x.commons.socket.util.SocketHelper;
import com.x.commons.util.bean.New;
import com.x.commons.util.collection.XArrays;
import com.x.commons.util.string.Strings;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Desc
 * @Date 2020-04-01 00:13
 * @Author AD
 */
public final class XSocketChannel {
    
    private final SocketInfo socketInfo;
    
    private final ChannelHandlerContext ctx;
    
    private final Channel channel;
    
    private final ISocketSerializer serializer;
    
    private final AtomicLong seq;
    
    XSocketChannel(ChannelHandlerContext ctx, ISocketSerializer serializer) {
        this.ctx = ctx;
        this.channel = ctx.channel();
        this.serializer = serializer;
        this.socketInfo = SocketHelper.getSocketInfo(ctx);
        this.seq = new AtomicLong(-1);
    }
    
    public boolean send(Serializable bean) throws Exception {
        if (bean == null) {
            return true;
        } else {
            if (this.serializer == null) {
                throw new Exception("序列化对象为空");
            } else {
                if (channel.isActive() && channel.isOpen() && channel.isActive()) {
                    ByteBuf buf = serializer.encode(bean, New.buf());
                    byte[] data = new byte[buf.readableBytes()];
                    buf.readBytes(data);
                    send(data);
                    return true;
                }
                return false;
            }
        }
    }
    
    public boolean send(byte[] bytes) {
        if (XArrays.isEmpty(bytes)) {
            return true;
        }
        if (channel.isActive() && channel.isOpen() && channel.isActive()) {
            ByteBuf buf = New.buf();
            buf.writeBytes(bytes);
            ctx.writeAndFlush(buf);
            return true;
        }
        return false;
    }
    
    public boolean send(String msg) {
        return send(msg, "UTF-8");
    }
    
    public boolean send(String msg, String charset) {
        if (Strings.isNull(msg, false)) {
            return true;
        }
        if (channel.isActive() && channel.isOpen() && channel.isActive()) {
            ByteBuf buf = New.buf();
            buf.writeCharSequence(msg, Charset.forName(charset));
            ctx.writeAndFlush(buf);
            return true;
        }
        return false;
    }
    
    public void close() {
        channel.flush().close();
    }
    
    public SocketInfo getSocketInfo() {
        return socketInfo;
    }
    
    long getSeq() {
        return seq.incrementAndGet();
    }
    
}