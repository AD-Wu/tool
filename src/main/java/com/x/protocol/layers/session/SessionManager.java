package com.x.protocol.layers.session;

import com.x.commons.collection.KeyValue;
import com.x.commons.local.Locals;
import com.x.commons.util.bean.New;
import com.x.commons.util.collection.XArrays;
import com.x.commons.util.string.Strings;
import com.x.protocol.core.ChannelInfo;
import com.x.protocol.core.IChannel;
import com.x.protocol.core.IProtocol;
import com.x.protocol.core.ISession;
import com.x.protocol.layers.presentation.Presentation;
import com.x.protocol.layers.session.config.SessionConfig;
import com.x.protocol.network.interfaces.INetworkConsent;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Desc：
 * @Author：AD
 * @Date：2020/3/9 15:37
 */
public class SessionManager extends SessionSecurity {
    private final Presentation presentation;

    private final Object sessionLock = new Object();

    private Map<String, Session> sessions = New.concurrentMap();

    private Map<String, Session> mappings = New.concurrentMap();

    private volatile boolean isChanged = true;

    private Session[] sessionArray;

    public SessionManager(IProtocol protocol, SessionConfig config, Presentation presentation) {
        super(protocol, config);
        this.presentation = presentation;
    }

    @Override
    public ISession createSession(ChannelInfo info, KeyValue... kvs) {
        return this.createSession(Strings.UUID(), info, kvs);
    }

    @Override
    public ISession createSession(String key, KeyValue... kvs) {
        Session session = createNewSession(key, kvs);
        if (session != null) {
            super.notifyStart(session);
        }
        return session;
    }

    @Override
    public ISession createSession(String key, ChannelInfo info, KeyValue... kvs) {
        Session session = createNewSession(key, kvs);
        if (session != null) {
            this.ChannelLogin(session, info);
            super.notifyStart(session);
        }
        return session;
    }

    @Override
    public ISession createSessionWithSerializer(ChannelInfo info, KeyValue... kvs) {
        return this.createSessionWithSerializer(Strings.UUID(), info, kvs);
    }

    @Override
    public ISession createSessionWithSerializer(String key, ChannelInfo info, KeyValue... kvs) {
        if (!isMultiSerializer()) {
            throw new IllegalArgumentException(Locals.text("protocol.layer.serializer.multi"));
        } else {
            Session session = createNewSession(key, kvs);
            if (session != null) {
                IChannel ch = info.getChannel();
                session.setSerializer(presentation.createSerializer(ch.getName()));
                this.ChannelLogin(session, info);
                super.notifyStart(session);
            }
            return session;
        }
    }

    @Override
    public boolean isLogin(String key) {
        return sessions.containsKey(key);
    }

    @Override
    public ISession getSession(ChannelInfo info) {
        return mappings.get(info.getChannelInfoKey());
    }

    @Override
    public ISession getSession(String key) {
        return sessions.get(key);
    }

    @Override
    public int getSessionSize() {
        return sessions.size();
    }

    @Override
    public ISession[] getSessions() {
        if (isChanged) {
            synchronized (sessionLock) {
                if (!isChanged) {
                    return sessionArray;
                }
                this.isChanged = false;
                this.sessionArray = sessions.values().toArray(new Session[0]);
            }
        }
        return sessionArray;
    }

    @Override
    public boolean ChannelLogin(ISession session, ChannelInfo info) {
        Session sen = (Session) session;
        synchronized (sessionLock) {
            if (!sessions.containsKey(sen.getID())) {
                return false;
            } else if (mappings.containsKey(info.getChannelInfoKey())) {
                return false;
            } else {
                INetworkConsent consent = info.getConsent();
                if (consent != null) {
                    consent.setLoginMark(true);
                }
                sen.addChannelInfo(info);
                mappings.put(info.getChannelInfoKey(), sen);
                return true;
            }
        }
    }

    @Override
    public boolean isChannelLogin(ChannelInfo info) {
        return mappings.containsKey(info.getChannelInfoKey());
    }

    @Override
    public void ChannelLogout(ChannelInfo info, boolean closeChannel, boolean removeSession) {
        if (isLoginMode() && mappings.containsKey(info.getChannelInfoKey())) {
            Session logout = null;
            synchronized (sessionLock) {
                Session session = mappings.remove(info.getChannelInfoKey());
                if (session == null) {
                    return;
                }
                session.removeChannelInfo(info);
                if (removeSession && session.getChannelInfoSize() == 0) {
                    logout = sessions.remove(session.getID());
                    this.isChanged = true;
                }
            }
            if (logout != null) {
                super.notifyStop(logout);
            }
            if (closeChannel) {
                this.closeChannelInfos(Locals.text("protocol.layer.channel.logout"), info);
            }
        }
    }

    @Override
    public void endSession(String key) {
        this.endSession(key, Locals.text("protocol.layer.session.logout"));
    }

    @Override
    public void endSession(String key, String msg) {
        if (isLoginMode() && sessions.containsKey(key)) {
            Session session = null;
            ChannelInfo[] infos;
            synchronized (sessionLock) {
                session = sessions.remove(key);
                if (session == null) return;
                this.isChanged = true;
                infos = session.getChannelInfos();
                for (ChannelInfo info : infos) {
                    mappings.remove(info.getChannelInfoKey());
                }
            }
            super.notifyStop(session);
            this.closeChannelInfos(msg, infos);
        }
    }

    public void checkSessionTimeout(long now) {
        if (isLoginMode()) {
            if (isChanged) {
                synchronized (sessionLock) {
                    if (isChanged) {
                        this.isChanged = false;
                        this.sessionArray = sessions.values().toArray(new Session[0]);
                    }
                }
            }
            int count = 0;
            long timeout = this.getSessionTimeout();
            Session[] sessions = sessionArray;
            for (Session session : sessions) {
                long sessionTime = session.getSessionTime();
                if (now - sessionTime > timeout || now < sessionTime - timeout) {
                    this.endSession(session.getID(), Locals.text("protocol.layer.session.timeout"));
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

    private Session createNewSession(String key, KeyValue... kvs) {
        if (!isLoginMode()) {
            return null;
        } else {
            Session session = sessions.get(key);
            if (session == null) {
                int maxSize = this.getMaxSize();
                synchronized (sessionLock) {
                    session = sessions.get(key);
                    if (session == null) {
                        if (maxSize > 0 && sessions.size() >= maxSize) {
                            return null;
                        }
                        session = new Session(key);
                        sessions.put(key, session);
                        this.isChanged = true;
                    }
                }
            }
            if (!XArrays.isEmpty(kvs)) {
                for (KeyValue kv : kvs) {
                    session.setData(kv.getK(), kv.getV());
                }
            }
            return session;
        }
    }

    private void closeChannelInfos(String msg, ChannelInfo... infos) {
        for (ChannelInfo info : infos) {
            INetworkConsent consent = info.getConsent();
            if (consent != null) {
                consent.setLoginMark(false);
                if (!consent.isClosed()) {
                    logger.debug(Locals.text("protocol.layer.channel.close", info, msg));
                    consent.close();
                }
            }
        }
    }

}
