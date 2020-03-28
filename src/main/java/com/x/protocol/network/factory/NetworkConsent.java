package com.x.protocol.network.factory;

import com.x.commons.local.Locals;
import com.x.commons.util.string.Strings;
import com.x.protocol.network.core.NetworkConsentType;
import com.x.protocol.network.interfaces.INetworkConsent;
import com.x.protocol.network.interfaces.INetworkIO;
import com.x.protocol.network.interfaces.INetworkService;

/**
 * @Desc 网络应答对象基类
 * @Date 2020-02-19 00:48
 * @Author AD
 */
public abstract class NetworkConsent implements INetworkConsent, Runnable {
    
    // ------------------------ 变量定义 ------------------------
    
    /**
     * 网络服务
     */
    protected NetworkService service;
    
    /**
     * 网络应答对象类型
     */
    private NetworkConsentType type;
    
    /**
     * 是否可超时
     */
    private boolean timeoutEnabled;
    
    /**
     * 自定侦查数据
     */
    private boolean autoDetectData;
    
    /**
     * 网络应答对象名
     */
    private String name;
    
    /**
     * 是否登录
     */
    private boolean login;
    
    /**
     * 连接信息
     */
    private String connInfo;
    
    /**
     * 网络应答对象编号
     */
    private long consentIndex;
    
    /**
     * 是否正在读取数据
     */
    private boolean readingData;
    
    /**
     * 更新时间
     */
    private long updateTime;
    
    /**
     * 是否启动
     */
    private boolean started = false;
    
    /**
     * 关闭锁
     */
    private Object closeLock = new Object();
    
    /**
     * 已关闭
     */
    private boolean closed = false;
    
    /**
     * 是否接受
     */
    private boolean accepted = false;
    
    /**
     * 语言key
     */
    private String langKey;
    
    /**
     * 本地host
     */
    protected String localHost;
    
    /**
     * 本地端口
     */
    protected int localPort;
    
    /**
     * 远端Host
     */
    protected String remoteHost;
    
    /**
     * 远端端口
     */
    protected int remotePort;
    
    // ------------------------ 构造方法 ------------------------
    
    /**
     * 网络应答对象构造方法
     *
     * @param name           网络应答对象名称
     * @param service        网络服务对象
     * @param type           网络应答对象类型
     * @param timeoutEnabled 是否允许超时
     * @param autoDetectData 是否自动侦查数据
     */
    public NetworkConsent(String name, NetworkService service, NetworkConsentType type, boolean timeoutEnabled,
            boolean autoDetectData) {
        this.service = service;
        this.type = type;
        this.timeoutEnabled = service.isTimeoutEnabled() && !timeoutEnabled;
        this.autoDetectData = autoDetectData;
        this.consentIndex = service.getNextConsentIndex();
        this.connInfo = service.getName() + "[" + consentIndex + "]";
        this.name = !Strings.isNull(name) ? name : connInfo;
        if (this.timeoutEnabled) {
            this.updateTime = System.currentTimeMillis();
        }
    }
    
    // ------------------------ 方法定义 ------------------------
    @Override
    public void run() {
        INetworkIO io = null;
        try {
            io = this.getNetworkIO();
        } catch (Exception e) {
        }
        if (io == null) {
            this.readingData = false;
        } else {
            if (!this.service.notifyConsentData(io)) {
                this.readingData = false;
            }
            if (this.timeoutEnabled) {
                this.updateTime = System.currentTimeMillis();
            }
        }
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public boolean isAccepted() {
        return accepted;
    }
    
    @Override
    public void setLoginMark(boolean login) {
        if (this.login != login) {
            this.login = login;
        }
    }
    
    @Override
    public boolean isLogin() {
        return login;
    }
    
    @Override
    public String getConnectionInfo() {
        return connInfo;
    }
    
    @Override
    public long getConsentIndex() {
        return consentIndex;
    }
    
    @Override
    public <T> T getInformation(String consentType) {
        try {
            return (T) this.getConsentInfo(consentType);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public INetworkService getService() {
        return service;
    }
    
    @Override
    public NetworkConsentType getType() {
        return type;
    }
    
    @Override
    public String getLangKey() {
        return langKey;
    }
    
    @Override
    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }
    
    @Override
    public void close() {
        if (!closed) {
            synchronized (closeLock) {
                if (closed) {
                    return;
                }
                this.closed = true;
            }
            this.service.notifyConsentStop(this);
            try {
                this.closeConsent();
            } catch (Exception e) {
                e.printStackTrace();
                this.service.notifyError(this, Locals.text("protocol.consent.err", e.getMessage()));
            }
        }
    }
    
    @Override
    public boolean isClosed() {
        return this.closed;
    }
    
    public synchronized boolean start() {
        if (this.started) {
            return true;
        } else {
            this.started = true;
            if (this.service.notifyConsentStart(this)) {
                this.accepted = true;
                return true;
            } else {
                return false;
            }
        }
    }
    
    public void notifyData(INetworkIO io) {
        if (this.started && io != null) {
            this.readingData = true;
            if (this.timeoutEnabled) {
                this.updateTime = System.currentTimeMillis();
            }
            if (!this.service.notifyConsentData(io)) {
                this.readingData = false;
            }
            if (this.timeoutEnabled) {
                this.updateTime = System.currentTimeMillis();
            }
        }
    }
    
    public void resetReading() {
        this.readingData = false;
    }
    
    // ------------------------  缺省方法 ------------------------
    boolean checkSyncConsentData(long updateTime, boolean timeChanged) {
        if (!started) {
            return true;
        } else if (!this.readingData && !this.closed) {
            if (timeChanged && this.timeoutEnabled) {
                this.updateTime = updateTime;
            }
            
            if (this.autoDetectData && this.checkDataAvailable()) {
                this.readingData = true;
                if (this.timeoutEnabled && !timeChanged) {
                    this.updateTime = updateTime;
                }
                if (!this.service.runSchedule(this)) {
                    this.readingData = false;
                    return false;
                }
            }
            
            return true;
        } else {
            if (this.timeoutEnabled) {
                this.updateTime = updateTime;
            }
            return true;
        }
    }
    
    void checkConsentTimeout(long nowTime, long readTimeout) {
        if (this.timeoutEnabled) {
            if (nowTime - this.updateTime > readTimeout) {
                this.service.notifyMessage(this, Locals.text("protocol.consent.timeout"));
                this.close();
            }
        }
    }
    
    // ------------------------  抽象方法 ------------------------
    
    protected abstract boolean checkDataAvailable();
    
    protected abstract Object getConsentInfo(String consentType);
    
    protected abstract void closeConsent();
    
}
