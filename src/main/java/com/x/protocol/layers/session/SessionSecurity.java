package com.x.protocol.layers.session;

import com.x.commons.local.Locals;
import com.x.commons.util.bean.New;
import com.x.commons.util.collection.XArrays;
import com.x.commons.util.log.Logs;
import com.x.commons.util.string.Strings;
import com.x.protocol.core.*;
import com.x.protocol.layers.session.config.ConnectionConfig;
import com.x.protocol.layers.session.config.LoginConfig;
import com.x.protocol.layers.session.config.SessionConfig;
import com.x.protocol.network.core.NetworkConsentType;
import org.slf4j.Logger;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @Desc
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

    private final Object channelsLock = new Object();

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
                synchronized (channelsLock) {
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
                        synchronized (channelsLock) {
                            channels.remove(channel.getRemoteIP());
                            if (!isChanged) {
                                this.isChanged = true;
                            }
                        }
                    }
                    ++count;
                    if (count % 20 == 0) {
                        try {
                            TimeUnit.MILLISECONDS.sleep(100);
                        } catch (InterruptedException e) {
                            break;
                        }
                    }
                    if (protocol.isStopped()) {
                        break;
                    }
                }
            }
        }
    }

    public boolean checkSecurity(ChannelInfo channel) {
        if (minuteLimit <= 0) {
            return true;
        } else {
            String remoteHost = channel.getRemoteHost();
            if (Strings.isNotNull(remoteHost) && channel.getConsent() != null) {
                NetworkConsentType type = channel.getConsent().getType();
                if (type == NetworkConsentType.LOCAL_TO_REMOTE) {
                    return true;
                } else {
                    SessionChannelInfo session = channels.get(remoteHost);
                    if (session == null) {
                        synchronized (channelsLock) {
                            session = channels.get(remoteHost);
                            if (session == null) {
                                session = new SessionChannelInfo(remoteHost);
                                channels.put(remoteHost, session);
                                if (!isChanged) {
                                    this.isChanged = true;
                                }
                            }
                        }
                    }
                    int count = session.checkAccess(minuteLimit, limitTime);
                    switch (count) {
                        case 0:
                            return true;
                        case 1:
                            if (type == NetworkConsentType.REMOTE_TO_LOCAL) {
                                logger.warn(Locals.text("protocol.layer.security.remote", channel));
                            } else {
                                logger.warn(Locals.text("protocol.layer.security.client", channel));
                            }
                            this.notifyDefense(channel);
                        default:
                            return false;
                    }
                }
            } else {
                return false;
            }
        }
    }

    public void resetSecurity(ChannelInfo channel) {
        if (minuteLimit > 0 && channel != null) {
            String remoteHost = channel.getRemoteHost();
            if (Strings.isNotNull(remoteHost)) {
                if (channels.containsKey(remoteHost)) {
                    synchronized (channelsLock) {
                        if (channels.remove(remoteHost) != null && !isChanged) {
                            this.isChanged = true;
                        }
                    }
                }
            }
        }
    }

    public boolean isLoginRequest(ChannelData data, DataInfo info) {
        if (loginCommands.contains(data.getCommandKey())) {
            return true;
        } else {
            return info != null && info.isSkipLogin();
        }
    }

    public boolean isLoginCommand(String cmd, String ctrl) {
        if (Strings.isNotNull(cmd)) {
            return Strings.isNotNull(ctrl) ? loginCommands.contains(cmd + ":" + ctrl) :
                    loginCommands.contains(cmd);
        } else {
            return false;
        }
    }

    protected void notifyStart(Session session) {
        if (initializer != null) {
            try {
                initializer.onSessionCreated(protocol, session);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(
                        Locals.text("protocol.layer.method.err", initializer, "onSessionCreated",
                                    protocol.getName(), e.getMessage()), e);
            }
        }
    }

    protected void notifyStop(Session session){
        if(initializer!=null){
            try {
                initializer.onSessionDestroyed(protocol,session);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(Locals.text("protocol.layer.method.err",initializer,
                                         "onSessionDestroyed",protocol.getName(),e.getMessage()),e);
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

    private void notifyDefense(ChannelInfo channel) {
        if (initializer != null) {
            try {
                initializer.onSecurityDefense(this.protocol, channel);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(Locals.text("protocol.layer.method.err", initializer,
                                         "onSecurityDefense", protocol.getName(), e.getMessage()),
                             e);
            }
        }
    }
}
