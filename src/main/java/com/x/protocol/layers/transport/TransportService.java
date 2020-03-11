package com.x.protocol.layers.transport;

import com.x.commons.local.Locals;
import com.x.commons.util.bean.New;
import com.x.commons.util.collection.XArrays;
import com.x.commons.util.log.Logs;
import com.x.commons.util.string.Strings;
import com.x.protocol.core.IChannel;
import com.x.protocol.layers.Protocol;
import com.x.protocol.layers.transport.channel.HttpChannel;
import com.x.protocol.layers.transport.channel.SocketChannel;
import com.x.protocol.layers.transport.config.ChannelConfig;
import org.slf4j.Logger;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Desc
 * @Date 2020-03-11 00:00
 * @Author AD
 */
public abstract class TransportService {
    
    // ------------------------ 变量定义 ------------------------
    protected final Protocol protocol;
    
    protected final Logger logger;
    
    private Map<String, IChannel> channels = New.concurrentMap();
    
    private final Object countLock = new Object();
    
    private final Object channelLock = new Object();
    
    private int serviceCount = 0;
    
    private int succeedCount = 0;
    
    private int stopCount = 0;
    
    private IChannel[] channelArray = new IChannel[0];
    
    private boolean starting = true;
    
    // ------------------------ 构造方法 ------------------------
    public TransportService(Protocol protocol) {
        this.protocol = protocol;
        this.logger = Logs.get(protocol.getName());
    }
    // ------------------------ 方法定义 ------------------------
    
    public boolean start(List<ChannelConfig> channelsConfig) throws Exception {
        if (XArrays.isEmpty(channelsConfig)) {
            return false;
        } else {
            boolean started = true;
            Transport transport = (Transport) this;
            Iterator<ChannelConfig> it = channelsConfig.iterator();
            while (it.hasNext()) {
                ChannelConfig config = it.next();
                if (config != null && config.isEnabled()) {
                    String name = config.getName();
                    String type = config.getType();
                    Object chn = null;
                    if ("socket".equals(type)) {
                        chn = new SocketChannel(protocol, transport);
                    } else if ("http".equals(type)) {
                        chn = new HttpChannel(protocol, transport);
                    }
                    if (chn == null) {
                        logger.warn(Locals.text("protocol.layer.channel.start", name));
                        return false;
                    } else {
                        try {
                            if (((IChannel) chn).start(config)) {
                                synchronized (channelLock) {
                                    channels.put(name, (IChannel) chn);
                                }
                            } else {
                                started = false;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            logger.error(Locals.text("protocol.layer.channel.service", name, e));
                            started = false;
                            throw e;
                        }
                    }
                }
            }
            this.starting = false;
            this.channelArray = channels.values().toArray(new IChannel[0]);
            return started;
        }
    }
    
    public void stop() {
        synchronized (channelLock) {
            Iterator<IChannel> it = channels.values().iterator();
            while (true) {
                if (!it.hasNext()) {
                    channels.clear();
                    break;
                }
                it.next().stop();
            }
        }
        this.channelArray = new IChannel[0];
    }
    
    public void stopChannel(String name) {
        if (Strings.isNotNull(name)) {
            synchronized (channelLock) {
                IChannel channel = channels.remove(name);
                if (channel != null) {
                    channel.stop();
                }
                channelArray = channels.values().toArray(new IChannel[0]);
            }
        }
    }
    
    public IChannel getChannel(String name) {
        return channels.get(name);
    }
    
    public void addServiceCount() {
        synchronized (countLock) {
            ++serviceCount;
        }
    }
    
    public void addSucceedCount() {
        synchronized (countLock) {
            ++succeedCount;
        }
    }
    
    public void addStopCount() {
        synchronized (countLock) {
            ++stopCount;
        }
    }
    
    public boolean checkStopped() {
        synchronized (countLock) {
            return stopCount == succeedCount;
        }
    }
    
    public int getChannelCount() {
        return channels.size();
    }
    
    protected void checkClientConnections(long now) {
        if (!XArrays.isEmpty(channelArray)) {
            Arrays.stream(channelArray).forEach(channel -> {
                channel.checkClientConnections(now);
                if (protocol.isStopped()) {
                    return;
                }
            });
        }
    }
    

}
