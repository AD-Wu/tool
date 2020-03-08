package com.x.protocol.layers.session;

import com.x.commons.util.bean.New;
import com.x.commons.util.collection.XArrays;
import com.x.commons.util.log.Logs;
import com.x.commons.util.string.Strings;
import com.x.protocol.core.IProtocol;
import com.x.protocol.core.IProtocolInitializer;
import com.x.protocol.core.ISessionManager;
import com.x.protocol.layers.session.config.ConnectionConfig;
import com.x.protocol.layers.session.config.LoginConfig;
import com.x.protocol.layers.session.config.SessionConfig;
import org.slf4j.Logger;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @Desc TODO
 * @Date 2020-03-09 00:46
 * @Author AD
 */
public abstract class SessionSecurity implements ISessionManager {
    
    // ------------------------ 变量定义 ------------------------
    
    protected Logger logger;
    
    protected IProtocol protocol;
    
    private IProtocolInitializer initializer;
    
    private boolean loginMode = false;
    
    private boolean multiSerializer = false;
    
    private int maxSize = 1000;
    
    private int minuteLimit = 10;
    
    private long limitTime = 300000L;
    
    private long sessionTimeout = 180000L;
    
    private Set<String> loginCommands = New.concurrentSet();
    
    private Map<String, SessionChannelInfo> channels = New.concurrentMap();
    
    private Object channelLock = new Object();
    
    private boolean isChanged = true;
    
    private SessionChannelInfo[] channelArray;
    
    // ------------------------ 构造方法 ------------------------
    public SessionSecurity(IProtocol protocol, SessionConfig config) {
        this.protocol = protocol;
        this.initializer = protocol.getInitializer();
        this.logger = Logs.get(protocol.getName());
        if (config == null) {
            this.minuteLimit = 0;
        } else {
            LoginConfig login = config.getLogin();
            if (login != null) {
                this.loginMode = login.isLoginMode();
                this.multiSerializer = login.isMultiSerializer();
                String command = login.getCommands();
                if (Strings.isNotNull(command)) {
                    Set<String> cmds = Arrays.stream(command.split(",")).filter(cmd -> {
                        return Strings.isNotNull(cmd);
                    }).collect(Collectors.toSet());
                    this.loginCommands.addAll(cmds);
                }
                ConnectionConfig connection = config.getConnection();
                if (connection != null) {
                    this.maxSize = connection.getMaxSize();
                    this.minuteLimit = connection.getMinuteLimit();
                    this.limitTime = connection.getLimitTime();
                    this.sessionTimeout = connection.getSessionTimeout();
                }
            }
        }
    }
    
    // ------------------------ 方法定义 ------------------------
    public void checkSecurityTimeout(long now) {
        if (minuteLimit > 0) {
            if (isChanged) {
                synchronized (channelLock) {
                    this.isChanged = false;
                    this.channelArray = channels.values().toArray(new SessionChannelInfo[0]);
                }
            }
            if (!XArrays.isEmpty(channelArray)) {
                int count = 0;
                long expired = now - limitTime - 60000L;
                for (SessionChannelInfo channel : channelArray) {
                    long checkTime = channel.getCheckTime();
                    if (checkTime < expired || now < checkTime - 60000L) {
                        synchronized (channelLock) {
                            channels.remove(channel.getRemoteIP());
                            if (!isChanged) {
                                this.isChanged = true;
                            }
                        }
                    }
                    ++count;
                    if(count%20==0){
                        try {
                            TimeUnit.MILLISECONDS.sleep(100);
                        } catch (InterruptedException e) {
                            break;
                        }
                    }
                    if(protocol.isStopped()){
                        break;
                    }
                }
            }
        }
    }
    
    public boolean isLoginMode() {
        return this.loginMode;
    }
    
    public boolean isMultiSerializer() {
        return this.multiSerializer;
    }
    
    public int getMaxSize() {
        return this.maxSize;
    }
    
    public int getMinuteLimit() {
        return this.minuteLimit;
    }
    
    public long getLimitTime() {
        return this.limitTime;
    }
    
    public long getSessionTimeout() {
        return this.sessionTimeout;
    }
    
    // ------------------------ 私有方法 ------------------------
    
}
