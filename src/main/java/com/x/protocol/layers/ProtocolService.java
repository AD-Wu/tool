package com.x.protocol.layers;

import com.x.commons.collection.DataSet;
import com.x.commons.events.Dispatcher;
import com.x.commons.events.Event;
import com.x.commons.socket.config.ServerConfig;
import com.x.commons.util.bean.New;
import com.x.commons.util.log.Logs;
import com.x.protocol.config.ServiceConfig;
import com.x.protocol.core.*;
import com.x.protocol.layers.application.Application;
import com.x.protocol.layers.presentation.Presentation;
import com.x.protocol.layers.session.SessionManager;
import com.x.protocol.layers.transport.Transport;
import com.x.protocol.layers.transport.config.ClientConfig;
import com.x.protocol.layers.transport.interfaces.ITransportNotification;
import com.x.protocol.network.core.NetworkConfig;
import org.slf4j.Logger;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Desc TODO
 * @Date 2020-03-10 00:42
 * @Author AD
 */
public class ProtocolService implements IProtocol, ITransportNotification {
    
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
    public synchronized boolean start(ServiceConfig service, IProtocolInitializer init, DataSet serviceParams, IStatusNotification status)
            throws Exception {
        if(!this.stopped){
            return true;
        }else{
            this.stopped = false;
            if(service==null){
                return false;
            }else{
                this.initializer=init;
                this.status = status;
                this.svcParams=serviceParams;
                this.debug=service.isDebug();
                
                this.application=new Application(this,service.getApplication());
                this.presentation=new Presentation(service.getPresentations());
                this.sessions=new SessionManager(this, service.getSession(), presentation);
                this.transport=new Transport((Protocol) this);
                
            }
        }
        return false;
    }
    
    @Override
    public void stop() {
    
    }
    
    @Override
    public int request(ChannelInfo[] infos, ChannelData data, IDataResponse resp, long index, ResponseMode mode) {
        return 0;
    }
    
    @Override
    public boolean requestTry(ChannelInfo[] infos, ChannelData data, IDataResponse resp, long index, ResponseMode mode) {
        return false;
    }
    
    @Override
    public ResponseResult response(ChannelInfo info, ChannelData data, Serializable dataSerialized, DataSet dataSet, int status
            , String msg) {
        return null;
    }
    
    @Override
    public ResponseResult responseTry(ChannelInfo info, ChannelData data, Serializable dataSerialized, DataSet dataSet,
            int status, String msg) {
        return null;
    }
    
    @Override
    public String getName() {
        return null;
    }
    
    @Override
    public boolean isDebug() {
        return false;
    }
    
    @Override
    public void setDebug(boolean debug) {
    
    }
    
    @Override
    public boolean isStopped() {
        return false;
    }
    
    @Override
    public ISessionManager getSessionManager() {
        return null;
    }
    
    @Override
    public boolean addClient(String key, ClientConfig client) {
        return false;
    }
    
    @Override
    public void removeClient(String key, String msg) {
    
    }
    
    @Override
    public ChannelInfo getClient(String key) {
        return null;
    }
    
    @Override
    public ChannelInfo[] getClients() {
        return new ChannelInfo[0];
    }
    
    @Override
    public IChannel getChannel(String key) {
        return null;
    }
    
    @Override
    public Dispatcher getDispatcher() {
        return null;
    }
    
    @Override
    public int dispatch(Event event) {
        return 0;
    }
    
    @Override
    public IProtocolInitializer getInitializer() {
        return null;
    }
    
    @Override
    public Logger getLogger() {
        return null;
    }
    
    @Override
    public boolean hasFromRemoteControlKey(String key) {
        return false;
    }
    
    @Override
    public boolean hasToRemoteControlKey(String key) {
        return false;
    }
    
    @Override
    public String getActorCommand(Class<?> clazz) {
        return null;
    }
    
    @Override
    public DataConfig getDataConfig(Class<?> clazz) {
        return null;
    }
    
    @Override
    public void setTimeout(ChannelInfo info, ChannelData data, long timeout) {
    
    }
    
    @Override
    public void changeTimeout(ChannelInfo info, ChannelData data, int change) {
    
    }
    
    @Override
    public boolean cancelTimeout(ChannelInfo info, ChannelData data, boolean cancel) {
        return false;
    }
    
    @Override
    public boolean onChannelPrepare(IChannel channel, NetworkConfig config) {
        return false;
    }
    
    @Override
    public boolean onClientPrepare(IChannel channel, DataSet dataSet) {
        return false;
    }
    
    @Override
    public void onChannelStart(IChannel channel) {
    
    }
    
    @Override
    public void onChannelStop(IChannel channel) {
    
    }
    
    @Override
    public void onServiceStart() {
    
    }
    
    @Override
    public void onServiceStop() {
    
    }
    
    @Override
    public boolean onConnected(ChannelInfo info) throws Exception {
        return false;
    }
    
    @Override
    public void onDataRequest(ChannelInfo info, ChannelData data) throws Exception {
    
    }
    
    @Override
    public boolean notifyClientConnected(ChannelInfo info) {
        return false;
    }
    
    @Override
    public void notifyClientDisconnected(ChannelInfo info) {
    
    }
    
    @Override
    public boolean notifyRemoteConnected(ChannelInfo info) {
        return false;
    }
    
    @Override
    public void notifyRemoteDisconnected(ChannelInfo info) {
    
    }
    
    @Override
    public void onError(String s, String ss, String sss) {
    
    }
    
    @Override
    public void onStart() {
    
    }
    
    @Override
    public void onStart(String msg) {
    
    }
    
    @Override
    public void onStop() {
    
    }
    
    @Override
    public void onStop(String msg) {
    
    }
    
    @Override
    public void onLoadConfig(List<ServerConfig> configs) {
    
    }
    // ------------------------ 私有方法 ------------------------
    
}
