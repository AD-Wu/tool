package com.x.protocol.layers.transport.channel.core;

import com.x.commons.collection.NameValue;
import com.x.commons.local.Locals;
import com.x.commons.util.log.Logs;
import com.x.protocol.core.ChannelData;
import com.x.protocol.core.ChannelInfo;
import com.x.protocol.core.IChannel;
import com.x.protocol.core.ISerializer;
import com.x.protocol.layers.Protocol;
import com.x.protocol.layers.transport.Transport;
import com.x.protocol.layers.transport.config.ChannelConfig;
import com.x.protocol.layers.transport.config.ClientConfig;
import com.x.protocol.layers.transport.config.ProtocolConfig;
import com.x.protocol.layers.transport.config.ServerConfig;
import com.x.protocol.network.NetworkManager;
import com.x.protocol.network.core.NetworkConfig;
import com.x.protocol.network.interfaces.INetworkConsent;
import com.x.protocol.network.interfaces.INetworkIO;
import com.x.protocol.network.interfaces.INetworkNotification;
import com.x.protocol.network.interfaces.INetworkService;
import org.slf4j.Logger;

import java.util.List;

/**
 * @Desc TODO
 * @Date 2020-03-10 00:31
 * @Author AD
 */
public abstract class ChannelService implements IChannel, INetworkNotification {
    
    // ------------------------ 变量定义 ------------------------
    private static final Object countLock = new Object();
    
    private static int totalService = 0;
    
    private static int totalSucceed = 0;
    
    private static int totalStop = 0;
    
    private static boolean hasStarted = false;
    
    private static boolean hasStopped = false;
    
    protected boolean isFirstService = false;
    
    protected Logger logger;
    
    protected Protocol protocol;
    
    protected Transport transport;
    
    protected INetworkService service;
    
    private boolean stopped = true;
    
    private String name;
    
    private final Object readerLock = new Object();
    
    private int currentMappingIndex = -1;
    
    private int maxMappingIndex = 0;
    
    private int remoteCount = 0;
    
    private int clientCount = 0;
    
    private int maxConnection = 0;
    
    private long sendTimeout = 120000L;
    
    private Class<?> senderClass;
    
    private Class<?> readerClass;
    
    private ISerializer serializer;
    
    // ------------------------ 构造方法 ------------------------
    public ChannelService(Transport transport, Protocol protocol) {
        this.transport = transport;
        this.protocol = protocol;
        this.logger = Logs.get(protocol.getName());
    }
    // ------------------------ 方法定义 ------------------------
    
    @Override
    public synchronized boolean start(ChannelConfig channel) throws Exception {
        if (stopped && channel != null && channel.isEnabled()) {
            this.stopped = false;
            // 服务器配置
            boolean serviceMode = false;
            ServerConfig server = channel.getServer();
            if (server != null) {
                serviceMode = true;
                this.maxConnection = server.getMaxConnection();
            }
            this.name = channel.getName();
            this.sendTimeout = channel.getSendTimeout();
            if (sendTimeout < 30000L) {
                sendTimeout = 30000L;
            }
            // 协议配置
            ProtocolConfig prtcConfig = channel.getProtocol();
            this.maxMappingIndex = prtcConfig.getMaxMappingIndex();
            if (maxMappingIndex < 0) {
                maxMappingIndex = 0;
            }
            this.senderClass = Class.forName(prtcConfig.getSendClass());
            this.readerClass = Class.forName(prtcConfig.getReaderClass());
            // 网络配置
            NetworkConfig network = new NetworkConfig();
            network.setName(this.name);
            network.setType(channel.getType());
            network.setServerMode(serviceMode);
            network.setMaxPoolSize(channel.getMaxPoolSize());
            network.setCorePoolSize(channel.getCorePoolSize());
            network.setQueue(channel.getQueue());
            network.setReadTimeout(channel.getReadTimeout());
            // 服务器模式
            if (serviceMode) {
                List<NameValue> params = server.getParameters();
                if (params != null) {
                    params.forEach(param -> {
                        network.add(param.getName(), param.getValue());
                    });
                }
            }
            if (!this.protocol.onChannelPrepare(this, network)) {
                return false;
            } else {
                ++totalService;
                // this.transport.addServiceCount();
                this.service = NetworkManager.start(network, this);
                if (service != null) {
                    return true;
                } else {
                    protocol.onError("", name, Locals.text("protocol.layer.channel.start", name));
                    return false;
                }
            }
        }
        return false;
    }
    
    @Override
    public void stop() {
        if (!stopped && service != null) {
            stopped = true;
            service.stop();
            service = null;
        }
    }
    
    @Override
    public boolean isStopped() {
        return this.stopped;
    }
    
    @Override
    public boolean send(ChannelInfo info, ChannelData data) {
        return false;
    }
    
    @Override
    public INetworkService getService() {
        return this.service;
    }
    
    @Override
    public ISerializer getSerializer() {
        return this.serializer;
    }
    
    @Override
    public void setSerializer(ISerializer serializer) {
        this.serializer = serializer;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public boolean addClient(ClientConfig config) {
        return false;
    }
    
    @Override
    public void removeClient(String key) {
    
    }
    
    @Override
    public int getRemoteCount() {
        return this.remoteCount;
    }
    
    @Override
    public int getClientCount() {
        return this.clientCount;
    }
    
    @Override
    public long getSendTimeout() {
        return this.sendTimeout;
    }
    
    @Override
    public synchronized int getNextMappingIndex() {
        if(maxMappingIndex==0){
            return 0;
        }else{
            if(currentMappingIndex>=maxMappingIndex){
                currentMappingIndex=0;
            }
            return ++currentMappingIndex;
        }
    }
    
    @Override
    public void checkClientConnections(long index) {
    
    }
    
    @Override
    public boolean runSchedule(Runnable runnable) {
        return service==null?false:service.runSchedule(runnable);
    }
    
    @Override
    public boolean onConsentData(INetworkIO networkIO) throws Exception {
        ChannelInfo info = getChannelInfo(null, networkIO);
        return false;
    }
    
    @Override
    public boolean onConsentStart(INetworkConsent Consent) throws Exception {
        return false;
    }
    
    @Override
    public void onConsentStop(INetworkConsent Consent) throws Exception {
    
    }
    
    @Override
    public void onError(INetworkService service, INetworkConsent Consent, String errorMsg) throws Exception {
    
    }
    
    @Override
    public void onMessage(INetworkService service, INetworkConsent Consent, String msg) throws Exception {
    
    }
    
    @Override
    public void onServiceStart(INetworkService service) throws Exception {
    
    }
    
    @Override
    public void onServiceStop(INetworkService service) throws Exception {
    
    }
    
    protected abstract ChannelInfo getChannelInfo(INetworkConsent consent,INetworkIO io);
}
