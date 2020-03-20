package com.x.protocol.layers.transport.channel.core;

import com.x.commons.collection.NameValue;
import com.x.commons.local.Locals;
import com.x.commons.timming.Timer;
import com.x.commons.util.Systems;
import com.x.commons.util.log.Logs;
import com.x.protocol.core.*;
import com.x.protocol.layers.Protocol;
import com.x.protocol.layers.transport.Transport;
import com.x.protocol.layers.transport.config.ChannelConfig;
import com.x.protocol.layers.transport.config.ProtocolConfig;
import com.x.protocol.layers.transport.config.ServerConfig;
import com.x.protocol.network.NetworkManager;
import com.x.protocol.network.core.NetworkConfig;
import com.x.protocol.network.core.NetworkConsentType;
import com.x.protocol.network.interfaces.*;
import org.slf4j.Logger;

import java.util.List;

/**
 * @Desc
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
        if (maxMappingIndex == 0) {
            return 0;
        } else {
            if (currentMappingIndex >= maxMappingIndex) {
                currentMappingIndex = 0;
            }
            return ++currentMappingIndex;
        }
    }
    
    @Override
    public boolean runSchedule(Runnable runnable) {
        return service == null ? false : service.runSchedule(runnable);
    }
    
    @Override
    public boolean onConsentData(INetworkIO networkIO) throws Exception {
        ChannelInfo info = getChannelInfo(null, networkIO);
        return false;
    }
    
    @Override
    public boolean onConsentStart(INetworkConsent consent) throws Exception {
        ChannelInfo info = this.getChannelInfo(consent, null);
        if (protocol.onConnected(info)) {
            if (consent.getType() == NetworkConsentType.REMOTE_TO_LOCAL) {
                if (maxConnection > 0 && remoteCount >= maxConnection) {
                    logger.warn(Locals.text("protocol.layer.consent.limit", remoteCount, maxConnection, info));
                    return false;
                } else if (protocol.notifyRemoteConnected(info)) {
                    this.changeRemoteCount(1);
                    logger.info(Locals.text("protocol.layer.remote.connect", info, remoteCount));
                    return true;
                } else {
                    return false;
                }
            } else if (protocol.notifyClientConnected(info)) {
                this.changeClientCount(1);
                logger.info(Locals.text("protocol.layer.client.connected", consent.getName(), info, clientCount));
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    
    @Override
    public void onConsentStop(INetworkConsent consent) throws Exception {
        if (consent.isAccepted()) {
            ChannelInfo info = this.getChannelInfo(consent, null);
            if (consent.getType() == NetworkConsentType.REMOTE_TO_LOCAL) {
                changeRemoteCount(-1);
                logger.info(Locals.text("protocol.layer.remote.disconnect", info, remoteCount));
                protocol.notifyRemoteDisconnected(info);
            } else {
                changeClientCount(-1);
                logger.info(Locals.text("protocol.layer.client.disconnect", consent.getName(), info, clientCount));
                protocol.notifyClientDisconnected(info);
            }
        }
    }
    
    @Override
    public void onError(INetworkService service, INetworkConsent consent, String errorMsg) throws Exception {
        if (consent == null) {
            logger.error(Locals.text("protocol.layer.channel.err", this.name, errorMsg));
            protocol.onError("", this.name, errorMsg);
        } else {
            ChannelInfo info = getChannelInfo(consent, null);
            if (consent.getType() == NetworkConsentType.REMOTE_TO_LOCAL) {
                logger.error(Locals.text("protocol.layer.remote.err", info, errorMsg));
            } else {
                logger.error(Locals.text("protocol.layer.client.err", consent.getName(), info, errorMsg));
            }
        }
    }
    
    @Override
    public void onMessage(INetworkService service, INetworkConsent consent, String msg) throws Exception {
        if (consent == null) {
            logger.warn(Locals.text("protocol.layer.channel.msg", service.getName(), msg));
        } else {
            ChannelInfo info = getChannelInfo(consent, null);
            if (consent.getType() == NetworkConsentType.REMOTE_TO_LOCAL) {
                logger.debug(Locals.text("protocol.layer.remote.msg", info, msg));
            } else {
                logger.debug(Locals.text("protocol.layer.client.msg", consent.getName(), info, msg));
            }
        }
    }
    
    @Override
    public void onServiceStart(INetworkService service) throws Exception {
        logger.info(Locals.text("protocol.layer.channel.started", service.getName(), service.getServiceInfo()));
        protocol.onChannelStart(this);
        synchronized (countLock) {
            ++totalSucceed;
            this.transport.addSucceedCount();
            if (totalSucceed == 1) {
                this.isFirstService = true;
            }
            if (transport.checkStarted()) {
                logger.info(Locals.text("protocol.layer.service.started", protocol.getName()));
                Timer.get().add(() -> {
                    ChannelService.this.protocol.onServiceStart();
                }, 10);
                if (!this.stopped && totalSucceed == totalService) {
                    Timer.get().add(() -> {
                        synchronized (ChannelService.countLock) {
                            if (!ChannelService.this.stopped && !ChannelService.hasStarted &&
                                ChannelService.totalSucceed == ChannelService.totalService) {
                                ChannelService.hasStarted = true;
                                logger.info(Locals.text("protocol.layer.service.allsatarted"));
                                ChannelService.this.protocol.onStart();
                            }
                        }
                    }, 1000);
                }
            }
        }
    }
    
    @Override
    public void onServiceStop(INetworkService service) throws Exception {
        logger.info(Locals.text("protocol.layer.channel.stopped", service.getName()));
        protocol.onChannelStop(this);
        synchronized (countLock) {
            ++totalStop;
            transport.addStopCount();
            if (transport.checkStopped()) {
                logger.info(Locals.text("protocol.layer.service.stopped", protocol.getName()));
                Timer.get().add(() -> {
                    ChannelService.this.protocol.onServiceStop();
                });
            }
            if (!hasStopped && totalStop == totalService) {
                hasStopped = true;
                Timer.get().add(() -> {
                    logger.info(Systems.runtimeInfo());
                    logger.info(Locals.text("protocol.layer.service.allstopped"));
                    ChannelService.this.protocol.onStop();
                }, 500);
            }
        }
    }
    
    protected IProtocolReader getReader(INetworkIO io) {
        INetworkInput in = io.getInput();
        if (in == null) {
            return null;
        } else {
            IProtocolReader reader = in.getProtocolReader();
            if (reader != null) {
                return reader;
            } else {
                synchronized (readerLock) {
                    reader = in.getProtocolReader();
                    if (reader != null) {
                        return reader;
                    } else {
                        try {
                            reader = (IProtocolReader) readerClass.newInstance();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        in.setProtocolReader(reader);
                        return reader;
                    }
                }
            }
        }
    }
    
    protected IProtocolSender getSender() {
        try {
            return (IProtocolSender) senderClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    protected void notifyDataReady(ChannelInfo info, ChannelData data) throws Exception {
        if (data.isRequest()) {
            protocol.onDataRequest(info, data);
        }
        transport.callbackResponse(info, data);
    }
    
    private synchronized void changeRemoteCount(int value) {
        this.remoteCount += value;
    }
    
    private synchronized void changeClientCount(int value) {
        this.clientCount += value;
    }
    
    protected abstract ChannelInfo getChannelInfo(INetworkConsent consent, INetworkIO io);
    
}
