package com.x.protocol.layers.transport.channel.core;

import com.x.commons.collection.DataSet;
import com.x.commons.collection.NameValue;
import com.x.commons.local.Locals;
import com.x.commons.util.Systems;
import com.x.commons.util.bean.New;
import com.x.commons.util.collection.XArrays;
import com.x.commons.util.string.Strings;
import com.x.protocol.layers.Protocol;
import com.x.protocol.layers.transport.Transport;
import com.x.protocol.layers.transport.config.ChannelConfig;
import com.x.protocol.layers.transport.config.ClientConfig;
import com.x.protocol.network.interfaces.INetworkConsent;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.x.protocol.network.core.NetworkConsentType.LOCAL_TO_REMOTE;

/**
 * @Desc：
 * @Author：AD
 * @Date：2020/3/20 13:44
 */
public  abstract class ChannelClients extends ChannelService {
    
    // ------------------------ 变量定义 ------------------------
    private long waitTimes = 5;
    
    private final Object connectLock = new Object();
    
    private List<ClientParameter> readyParams = New.list();
    
    private Map<String, ClientParameter> connectedParams = New.concurrentMap();
    
    private volatile boolean isChanged = true;
    
    private ClientParameter[] clientArray;
    
    // ------------------------ 构造方法 ------------------------
    public ChannelClients(Transport transport, Protocol protocol) {
        super(transport, protocol);
    }
    
    // ------------------------ 方法定义 ------------------------
    
    @Override
    public synchronized boolean start(ChannelConfig channel) throws Exception {
        if (!super.start(channel)) {
            this.isChanged = true;
            return false;
        } else {
            this.waitTimes = 5;
            this.readyParams.clear();
            this.connectedParams.clear();
            List<ClientConfig> clients = channel.getClients();
            if (!XArrays.isEmpty(clients)) {
                Iterator<ClientConfig> it = clients.iterator();
out:
                while (true) {
                    ClientConfig config;
                    String name;
                    do {
                        do {
                            do {
                                if (!it.hasNext()) {
                                    break out;
                                }
                                config = it.next();
                                name = config.getName();
                            } while (!config.isEnabled());
                        } while (name == null);
                    } while (name.length() == 0);
                    ClientParameter client = getClientParameter(config, name);
                    this.readyParams.add(client);
                }
            }
            this.isChanged = true;
            return true;
        }
    }
    
    @Override
    public boolean addClient(ClientConfig config) {
        String name = config.getName();
        if (config.isEnabled() && Strings.isNotNull(name)) {
            ClientParameter client = getClientParameter(config, name);
            synchronized (connectLock) {
                if (connectedParams.containsKey(name)) {
                    return false;
                } else {
                    Iterator<ClientParameter> it = readyParams.iterator();
                    
                    ClientParameter parameter;
                    do {
                        if (!it.hasNext()) {
                            readyParams.add(client);
                            this.isChanged = true;
                            return this.tryConnectClient(client);
                        }
                        
                        parameter = it.next();
                    } while (!name.equals(parameter.getName()));
                    
                    return false;
                }
            }
        } else {
            return false;
        }
    }
    
    @Override
    public void removeClient(String name) {
        synchronized (connectLock) {
            ClientParameter client = connectedParams.remove(name);
            if (client != null) {
                this.isChanged = true;
            } else {
                
                for (int i = 0, L = readyParams.size(); i < L; i++) {
                    ClientParameter c = readyParams.get(i);
                    if (name.equals(c.getName())) {
                        this.readyParams.remove(i);
                        this.isChanged = true;
                        return;
                    }
                }
            }
        }
    }
    
    @Override
    public void onConsentStop(INetworkConsent consent) throws Exception {
        super.onConsentStop(consent);
        if (consent.getType() == LOCAL_TO_REMOTE && connectedParams.containsKey(consent.getName())) {
            synchronized(connectLock) {
                ClientParameter client = connectedParams.remove(consent.getName());
                if (client != null) {
                    this.readyParams.add(client);
                    this.isChanged = true;
                }
            }
        }
    }
    
    @Override
    public void checkClientConnections(long index) {
        if (!this.isStopped()) {
            if (this.waitTimes < Long.MAX_VALUE) {
                ++this.waitTimes;
            } else {
                this.waitTimes = 5L;
            }
        
            if (this.waitTimes % 5L == 0L) {
                if (this.isChanged) {
                    synchronized(connectLock) {
                        this.isChanged = false;
                        this.clientArray = readyParams.toArray(new ClientParameter[0]);
                    }
                }
            
                if (this.clientArray.length > 0) {
                    for (ClientParameter client : clientArray) {
                        this.tryConnectClient(client);
                    }
                }
            }
        
            if (this.waitTimes == 20L || this.waitTimes % 3500L == 0L) {
                logger.info(Locals.text("protocol.layer.statistics", service.getName(), getClientCount(), getRemoteCount(), service.getConsentSize(), protocol.getSessionManager().getSessionSize()));
                if (this.isFirstService) {
                    logger.info(Systems.runtimeInfo());
                }
            }
        
            // if (this.waitTimes == 21L && this.isFirstService) {
            //     AxFileAppender.flushAll();
            // }
        
        }
    }
    
    // ------------------------ 私有方法 ------------------------
    private ClientParameter getClientParameter(ClientConfig config, String name) {
        DataSet data = new DataSet();
        List<NameValue> nvs = config.getParameters();
        if (!XArrays.isEmpty(nvs)) {
            Iterator<NameValue> it = nvs.iterator();
            while (it.hasNext()) {
                NameValue nv = it.next();
                data.add(nv.getName(), nv.getValue());
            }
        }
        
        ClientParameter client = new ClientParameter();
        client.setName(name);
        client.setParameters(data);
        return client;
    }
    
    private synchronized boolean tryConnectClient(ClientParameter client) {
        String name = client.getName();
        if (connectedParams.containsKey(name)) {
            return true;
        } else {
            INetworkConsent consent = null;
            DataSet data = client.getParameters();
            
            try {
                if (super.protocol.onClientPrepare(this, data)) {
                    if (!client.isLoggedError()) {
                        logger.info(Locals.text("protocol.layer.client.connect", name));
                    }
                    
                    consent = super.service.connect(name, client.getParameters());
                    if (consent == null && !client.isLoggedError()) {
                        client.setLoggedError(true);
                        logger.warn(Locals.text("protocol.layer.client.failed", name, "UNKNOWN"));
                    }
                }
            } catch (Exception e) {
                if (!client.isLoggedError()) {
                    client.setLoggedError(true);
                    logger.warn(Locals.text("protocol.layer.client.failed", name, e.getMessage()
                    ));
                }
            }
            
            if (consent != null) {
                synchronized (connectLock) {
                    if (consent.isClosed()) {
                        return false;
                    }
                    
                    this.readyParams.remove(client);
                    this.connectedParams.put(client.getName(), client);
                    if (!this.isChanged) {
                        this.isChanged = true;
                    }
                }
                
                client.setLoggedError(false);
                return true;
            } else {
                return false;
            }
        }
        
    }
    
}
