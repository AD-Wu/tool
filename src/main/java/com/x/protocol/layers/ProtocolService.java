package com.x.protocol.layers;

import com.x.commons.collection.DataSet;
import com.x.commons.events.Dispatcher;
import com.x.commons.events.Event;
import com.x.commons.local.Locals;
import com.x.commons.timming.Timer;
import com.x.commons.util.bean.New;
import com.x.commons.util.log.Logs;
import com.x.commons.util.string.Strings;
import com.x.protocol.config.ServiceConfig;
import com.x.protocol.core.*;
import com.x.protocol.enums.ProtocolStatus;
import com.x.protocol.layers.application.Application;
import com.x.protocol.layers.presentation.Presentation;
import com.x.protocol.layers.session.SessionManager;
import com.x.protocol.layers.transport.CallbackInfo;
import com.x.protocol.layers.transport.Transport;
import com.x.protocol.layers.transport.config.ClientConfig;
import com.x.protocol.layers.transport.interfaces.ITransportNotification;
import com.x.protocol.network.core.NetworkConfig;
import org.slf4j.Logger;

import java.util.List;
import java.util.Map;

/**
 * @Desc
 * @Date 2020-03-10 00:42
 * @Author AD
 */
public abstract class ProtocolService implements IProtocol, ITransportNotification, Runnable {
    
    // ------------------------ 变量定义 ------------------------
    
    protected Logger logger;
    
    protected boolean debug;
    
    protected Application application;
    
    protected Presentation presentation;
    
    protected SessionManager sessions;
    
    protected Transport transport;
    
    private Dispatcher dispatcher;
    
    private IProtocolInitializer initializer;
    
    private IStatusNotification status;
    
    private boolean stopped = true;
    
    private Map<String, ChannelInfo> clientsMap = New.concurrentMap();
    
    private final Object clientLock = new Object();
    
    private DataSet svcParams;
    
    private final String name;
    
    // ------------------------ 构造方法 ------------------------
    
    public ProtocolService(String name) {
        this.name = name;
        this.logger = Logs.get(name);
        logger.info("--------------------- {} ---------------------", name);
    }
    
    // ------------------------ 方法定义 ------------------------
    @Override
    public synchronized boolean start(ServiceConfig service, IProtocolInitializer init, DataSet serviceParams,
            IStatusNotification status)
            throws Exception {
        if (!this.stopped) {
            return true;
        } else {
            this.stopped = false;
            if (service == null) {
                return false;
            } else {
                this.initializer = init;
                this.status = status;
                this.svcParams = serviceParams;
                this.debug = service.isDebug();
                
                try {
                    this.application = new Application(this, service.getApplication());
                    this.presentation = new Presentation(service.getPresentations());
                    this.sessions = new SessionManager(this, service.getSession(), presentation);
                    this.transport = new Transport((Protocol) this);
                    boolean start = transport.start(service.getTransports());
                    if (transport.getChannelCount() > 0) {
                        new Thread(this, Strings.replace("Protocol[{0}]", this.name)).start();
                    } else {
                        this.stop();
                    }
                    return start;
                } catch (Exception e) {
                    logger.error(Locals.text("protocol.service.start.err", name));
                    this.stop();
                    throw e;
                }
            }
        }
    }
    
    @Override
    public void stop() {
        if (!stopped && transport != null) {
            stopped = true;
            if (initializer != null) {
                try {
                    initializer.onServiceStopping(this);
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error(Locals.text("protocol.layer.method.err", initializer, "stop", name, e.getMessage()), e);
                }
            }
            transport.stop();
            notifyAll();
        }
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public boolean isDebug() {
        return this.debug;
    }
    
    @Override
    public void setDebug(boolean debug) {
        this.debug = debug;
    }
    
    @Override
    public boolean isStopped() {
        return this.stopped;
    }
    
    @Override
    public ISessionManager getSessionManager() {
        return this.sessions;
    }
    
    @Override
    public boolean addClient(String name, ClientConfig client) {
        IChannel channel = transport.getChannel(name);
        return channel == null ? false : channel.addClient(client);
    }
    
    @Override
    public void removeClient(String name, String key) {
        IChannel channel = transport.getChannel(name);
        if (channel != null) {
            channel.removeClient(name);
        }
        ChannelInfo info = clientsMap.get(key);
        if (info != null) {
            info.getConsent().close();
        }
    }
    
    @Override
    public ChannelInfo getClient(String key) {
        return clientsMap.get(key);
    }
    
    @Override
    public ChannelInfo[] getClients() {
        synchronized (clientLock) {
            return clientsMap.values().toArray(new ChannelInfo[0]);
        }
    }
    
    @Override
    public IChannel getChannel(String name) {
        return transport.getChannel(name);
    }
    
    @Override
    public Dispatcher getDispatcher() {
        return this.dispatcher;
    }
    
    @Override
    public int dispatch(Event event) {
        return dispatcher.dispatch(event);
    }
    
    @Override
    public IProtocolInitializer getInitializer() {
        return this.initializer;
    }
    
    @Override
    public Logger getLogger() {
        return this.logger;
    }
    
    @Override
    public boolean hasFromRemoteControlKey(String key) {
        return this.application.hasFromRemoteControlKey(key);
    }
    
    @Override
    public boolean hasToRemoteControlKey(String key) {
        return this.application.hasToRemoteControlKey(key);
    }
    
    @Override
    public String getActorCommand(Class<?> clazz) {
        return this.application.getActorCommand(clazz);
    }
    
    @Override
    public DataConfig getDataConfig(Class<?> clazz) {
        return this.application.getDataConfig(clazz);
    }
    
    @Override
    public void setTimeout(ChannelInfo info, ChannelData data, long timeout) {
        if (transport != null) {
            transport.setTimeout(info, data, timeout);
        }
    }
    
    @Override
    public void changeTimeout(ChannelInfo info, ChannelData data, int change) {
        if (transport != null) {
            transport.changeTimeout(info, data, change);
        }
    }
    
    @Override
    public boolean cancelTimeout(ChannelInfo info, ChannelData data, boolean callbackError) {
        CallbackInfo callback = transport.removeTimeout(info, data);
        if (callback != null) {
            if (callbackError) {
                transport.callbackErrorResponse(callback.getResponse(), info, data, ProtocolStatus.REQUEST_FAILURE);
            }
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public boolean onChannelPrepare(IChannel channel, NetworkConfig config) {
        if (initializer != null) {
            try {
                return initializer.onChannelPrepare(this, channel, config);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(Locals.text("protocol.layer.method.err", initializer, "onChannelPrepare", name, e.getMessage()), e);
            }
        }
        return true;
    }
    
    @Override
    public boolean onClientPrepare(IChannel channel, DataSet dataSet) {
        if (initializer != null) {
            try {
                return initializer.onClientPrepare(this, channel, dataSet);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(Locals.text("protocol.layer.method.err", initializer, "onClientPrepare", name, e.getMessage()), e);
            }
        }
        return true;
    }
    
    @Override
    public void onChannelStart(IChannel channel) {
        if (initializer != null) {
            try {
                initializer.onChannelStart(this, channel);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(Locals.text("protocol.layer.method.err", initializer, "onChannelStart", name, e.getMessage()), e);
            }
        }
    }
    
    @Override
    public void onChannelStop(IChannel channel) {
        if (initializer != null) {
            try {
                initializer.onChannelStop(this, channel);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(Locals.text("protocol.layer.method.err", initializer, "onChannelStop", name, e.getMessage()), e);
            }
        }
    }
    
    @Override
    public void onServiceStart() {
        if (initializer != null) {
            try {
                initializer.onServiceStart(this, this.svcParams);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(Locals.text("protocol.layer.method.err", initializer, "onServiceStart", name, e.getMessage()), e);
            }
        }
        this.svcParams = null;
        this.onStart(name);
    }
    
    @Override
    public void onServiceStop() {
        if (initializer != null) {
            try {
                initializer.onServiceStop(this);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(Locals.text("protocol.layer.method.err", initializer, "onServiceStop", name, e.getMessage()), e);
            }
        }
        this.onStop(name);
    }
    
    @Override
    public boolean onConnected(ChannelInfo info) throws Exception {
        return sessions.checkSecurity(info);
    }
    
    @Override
    public boolean notifyClientConnected(ChannelInfo info) {
        synchronized (clientLock) {
            clientsMap.put(info.getConsent().getName(), info);
        }
        if (initializer != null) {
            try {
                return initializer.onClientConnected(this, info);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(Locals.text("protocol.layer.method.err", initializer, "onClientConnected", name, e.getMessage()), e);
                return false;
            }
        } else {
            return true;
        }
    }
    
    @Override
    public void notifyClientDisconnected(ChannelInfo info) {
        synchronized (clientLock) {
            clientsMap.remove(info.getConsent().getName());
        }
        if (initializer != null) {
            try {
                initializer.onClientDisconnected(this, info);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(Locals.text("protocol.layer.method.err", initializer, "onClientDisconnected", name,
                        e.getMessage()), e);
            }
        }
        sessions.ChannelLogout(info, true, true);
    }
    
    @Override
    public boolean notifyRemoteConnected(ChannelInfo info) {
        if (initializer != null) {
            try {
                return initializer.onRemoteConnected(this, info);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(Locals.text("protocol.layer.method.err", initializer, "onRemoteConnected", name, e.getMessage()), e);
                return false;
            }
        } else {
            return true;
        }
    }
    
    @Override
    public void notifyRemoteDisconnected(ChannelInfo info) {
        if (initializer != null) {
            try {
                initializer.onRemoteDisconnected(this, info);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(Locals.text("protocol.layer.method.err", initializer, "onRemoteDisconnected", name,
                        e.getMessage()), e);
            }
        }
        sessions.ChannelLogout(info, true, true);
    }
    
    @Override
    public void onError(String s, String name, String error) {
        if (transport != null) {
            transport.stopChannel(name);
            if (transport.getChannelCount() == 0) {
                this.stop();
            }
        }
        logger.error(Locals.text("protocol.layer.service.err", name, error));
        if (status != null) {
            Timer.get().add(() -> {
                try {
                    ProtocolService.this.status.onError(ProtocolService.this.name, name, error);
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error(Locals.text("protocol.layer.method.err", ProtocolService.this.status, "onError",
                            ProtocolService.this.name, e
                                    .getMessage()), e);
                }
            });
        }
    }
    
    @Override
    public void onStart() {
        if (status != null) {
            try {
                status.onStart();
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(Locals.text("protocol.layer.method.err", status, "onStart", name, e.getMessage()), e);
            }
        }
    }
    
    @Override
    public void onStart(String protocolName) {
        if (status != null) {
            try {
                status.onStart(protocolName);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(Locals.text("protocol.layer.method.err", status, "onStart", name, e.getMessage()), e);
            }
        }
    }
    
    @Override
    public void onStop() {
        if (status != null) {
            try {
                status.onStop();
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(Locals.text("protocol.layer.method.err", status, "onStop", name, e.getMessage()), e);
            }
        }
    }
    
    @Override
    public void onStop(String protocolName) {
        if (status != null) {
            try {
                status.onStop(protocolName);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(Locals.text("protocol.layer.method.err", status, "onStop", name, e.getMessage()), e);
            }
        }
    }
    
    @Override
    public void onLoadConfig(List<ServiceConfig> configs) {}
    
    @Override
    public void run() {
        final Object lock = new Object();
        synchronized (lock) {
            while (!stopped) {
                long now = System.currentTimeMillis();
                try {
                    sessions.checkSessionTimeout(now);
                    if (stopped) {
                        break;
                    }
                    
                    sessions.checkSecurityTimeout(now);
                    if (stopped) {
                        break;
                    }
                    
                    transport.checkTransportTimeout(now);
                } catch (Exception e) {
                    this.logger.error(Locals.text("protocol.layer.monitor", e.getMessage()));
                }
                
                if (stopped) {
                    break;
                }
                
                try {
                    lock.wait(1000);
                } catch (Exception e) {
                }
            }
        }
    }
    
}
