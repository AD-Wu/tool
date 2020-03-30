package com.x.commons.socket.core;

import com.x.commons.util.bean.New;
import com.x.commons.util.collection.XArrays;
import com.x.commons.util.convert.Converts;
import com.x.commons.util.string.Strings;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.StringJoiner;

/**
 * @Desc
 * @Date 2020-03-27 23:34
 * @Author AD
 */
public final class XSocketChannel {
    
    private final String localHost;
    
    private final int localPort;
    
    private final String remoteHost;
    
    private final int remotePort;
    
    private final ChannelHandlerContext ctx;
    
    private final Channel channel;
    
    private final ISocketSerializer serializer;
    
    XSocketChannel(ChannelHandlerContext ctx, ISocketSerializer serializer) {
        this.ctx = ctx;
        this.channel = ctx.channel();
        this.serializer = serializer;
        String local = channel.localAddress().toString();
        String remote = channel.remoteAddress().toString();
        int end = local.indexOf(":");
        this.localHost = local.substring(1, end);
        this.localPort = Converts.toInt(local.substring(end + 1));
        end = remote.indexOf(":");
        this.remoteHost = remote.substring(1, end);
        this.remotePort = Converts.toInt(remote.substring(end + 1));
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
    
    public String getLocalHost() {
        return localHost;
    }
    
    public int getLocalPort() {
        return localPort;
    }
    
    public String getRemoteHost() {
        return remoteHost;
    }
    
    public int getRemotePort() {
        return remotePort;
    }
    
    @Override
    public String toString() {
        return new StringJoiner(", ", XSocketChannel.class.getSimpleName() + "[", "]")
                .add("localHost='" + localHost + "'")
                .add("localPort=" + localPort)
                .add("remoteHost='" + remoteHost + "'")
                .add("remotePort=" + remotePort)
                .toString();
    }
    
}
