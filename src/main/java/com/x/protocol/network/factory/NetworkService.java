package com.x.protocol.network.factory;

import com.x.commons.local.Locals;
import com.x.commons.timming.Timer;
import com.x.commons.util.date.DateTimes;
import com.x.protocol.network.core.NetworkConfig;
import com.x.protocol.network.interfaces.INetworkConsent;
import com.x.protocol.network.interfaces.INetworkIO;
import com.x.protocol.network.interfaces.INetworkNotification;
import com.x.protocol.network.interfaces.INetworkService;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Desc 网络服务基类
 * @Date 2020-02-19 00:50
 * @Author AD
 */
public abstract class NetworkService implements INetworkService {
    
    // ------------------------ 变量定义 ------------------------
    
    /**
     * 网络配置
     */
    protected NetworkConfig config = new NetworkConfig();
    
    /**
     * 网络通知器
     */
    private final INetworkNotification notification;
    
    /**
     * 数据线程池
     */
    private ThreadPoolExecutor threadPool;
    
    /**
     * 已启动
     */
    private boolean started = false;
    
    /**
     * 已停止
     */
    private boolean stopped = true;
    
    /**
     * 超时时间
     */
    private long readTimeout = 0L;
    
    private Thread svcThread;
    
    private Thread dataThread;
    
    /**
     * 当前网络应答对象下标
     */
    private long currentConsentIndex;
    
    /**
     * 网络应答对象缓存
     */
    private Map<Long, NetworkConsent> consents = new ConcurrentHashMap<>();
    
    /**
     * 网络应答对象锁
     */
    private Object consentsLock = new Object();
    
    /**
     * 网络应答对象已改变
     */
    private volatile boolean consentChanged = true;
    
    /**
     * 状态计数
     */
    private long statCount = 0L;
    
    /**
     * 没有应答对象
     */
    private volatile boolean noConsents = true;
    
    // ------------------------ 构造方法 ------------------------
    
    public NetworkService(INetworkNotification notification, boolean noConsents) {
        this.notification = notification;
        this.noConsents = noConsents;
    }
    
    // ------------------------ 方法定义 ------------------------
    
    @Override
    public NetworkConfig getConfig() {
        return config;
    }
    
    @Override
    public String getName() {
        return config.getName();
    }
    
    @Override
    public String getType() {
        return config.getType();
    }
    
    @Override
    public boolean isServerMode() {
        return config.isServerMode();
    }
    
    @Override
    public boolean isStarted() {
        return started;
    }
    
    @Override
    public boolean isStopped() {
        return stopped;
    }
    
    @Override
    public <T> T getInformation(String s) {
        try {
            return (T) this.getServiceInfo(s);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public int getConsentSize() {
        return this.consents.size();
    }
    
    @Override
    public INetworkConsent[] getConsents() {
        synchronized (consentsLock) {
            return consents.values().toArray(new INetworkConsent[0]);
        }
    }
    
    @Override
    public INetworkConsent getConsentByIndex(long index) {
        return this.consents.get(index);
    }
    
    @Override
    public boolean containConsent(long index) {
        return this.consents.containsKey(index);
    }
    
    @Override
    public synchronized boolean runSchedule(Runnable runnable) {
        if (runnable == null) {
            return false;
        }
        try {
            threadPool.execute(runnable);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public void notifyMessage(INetworkConsent consent, String msg) {
        try {
            notification.onMessage(this, consent, msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public void notifyError(INetworkConsent consent, String msg) {
        try {
            notification.onError(this, consent, msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public synchronized boolean start(NetworkConfig config) {
        if (config != null && this.stopped) {
            this.stopped = false;
            this.config = config;
            this.readTimeout = config.getReadTimeout();
            int corePoolSize = config.getCorePoolSize();
            if (corePoolSize < 1) {
                corePoolSize = 1;
            }
            int maxPoolSize = config.getMaxPoolSize();
            if (maxPoolSize < 1) {
                corePoolSize = 1;
            }
            this.threadPool = new ThreadPoolExecutor(corePoolSize, maxPoolSize, 60, TimeUnit.SECONDS, new SynchronousQueue<>()
                    , new ThreadPoolExecutor.AbortPolicy());
            if (!this.noConsents) {
                this.dataThread = new Thread("NetworkMonitor[" + config.getName() + "]") {
                    
                    @Override
                    public void run() {
                        try {
                            NetworkService.this.checkRecvDatas();
                        } catch (Exception e) {
                            e.printStackTrace();
                            NetworkService.this.notifyError(null, Locals.text("protocol.network.err", e.getMessage()));
                        }
                    }
                };
                this.dataThread.start();
            }
            this.svcThread = new Thread("NetworkService[" + config.getName() + "]") {
                
                @Override
                public void run() {
                    try {
                        NetworkService.this.serviceStart();
                    } catch (Exception e) {
                        e.printStackTrace();
                        NetworkService.this.notifyError(null, Locals.text("protocol.service.err", e.getMessage()));
                        NetworkService.this.callStop();
                    }
                }
            };
            svcThread.start();
            return true;
        }else{
            return false;
        }
        
    }
    
    @Override
    public synchronized void stop() {
        if (!this.stopped) {
            this.stopped = true;
            
            try {
                this.serviceStop();
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            this.notifyServiceStop();
            
            try {
                this.threadPool.shutdown();
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            notifyAll();
            
        }
    }
    
    // ------------------------ 包级方法定义 ------------------------
    protected void notifyServiceStart() {
        if (!this.started) {
            this.started = true;
            Timer.get().add(() -> {
                try {
                    NetworkService.this.notification.onServiceStart(this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, 100);
        }
    }
    
    // ------------------------ 缺省方法定义 ------------------------
    
    boolean isTimeoutEnabled() {
        return readTimeout > 0L;
    }
    
    synchronized long getNextConsentIndex() {
        if (currentConsentIndex == Long.MAX_VALUE) {
            currentConsentIndex = 1L;
        } else {
            ++currentConsentIndex;
        }
        return currentConsentIndex;
    }
    
    boolean notifyConsentData(INetworkIO io) {
        
        try {
            return this.notification.onConsentData(io);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                this.notification.onError(this, io.getConsent(), Locals.text("protocol.consent.read.err", e.getMessage()));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return false;
        }
    }
    
    boolean notifyConsentStart(NetworkConsent consent) {
        if (this.stopped) {
            consent.close();
            return false;
        } else {
            boolean start;
            try {
                start = this.notification.onConsentStart(consent);
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    this.notification.onError(this, consent, Locals.text("protocol.consent.start.err", e.getMessage()));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                start = false;
            }
            if (!start) {
                consent.close();
            }
            if (consent.isClosed()) {
                return false;
            } else if (this.noConsents) {
                return true;
            } else {
                long consentIndex = consent.getConsentIndex();
                synchronized (consentsLock) {
                    this.consents.put(consentIndex, consent);
                    this.consentChanged = true;
                    return true;
                }
            }
        }
    }
    
    void notifyConsentStop(NetworkConsent consent) {
        if (this.noConsents) {
            synchronized (consentsLock) {
                if ((this.consents.remove(consent.getConsentIndex()) == null)) {
                    return;
                }
            }
            
            this.consentChanged = true;
            
            try {
                this.notification.onConsentStop(consent);
            } catch (Exception e) {
                e.printStackTrace();
                
                try {
                    this.notification.onError(this, consent, Locals.text("protocol.consent.stop.err", e.getMessage()));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    
    // ------------------------ 抽象方法 ------------------------
    
    protected abstract Object getServiceInfo(String serviceProperty);
    
    protected abstract void serviceStart();
    
    protected abstract void serviceStop();
    
    // ------------------------ 私有方法 ------------------------
    
    /**
     * 网络监听
     */
    private void checkRecvDatas() {
        NetworkConsent[] consents = new NetworkConsent[0];
        
        long oldTime = System.currentTimeMillis();
        long nowTime = 0L;
        long takeTime = 0L;
        boolean timeChanged = false;
        boolean isReadTimeout = readTimeout > 0L;
        Object lock = new Object();
        synchronized (lock) {
            for (; !stopped; addStatCount()) {
                if (consentChanged) {
                    synchronized (consentsLock) {
                        consentChanged = false;
                        consents = this.consents.values().toArray(new NetworkConsent[0]);
                    }
                    takeTime = (long) (30000 + consents.length * 20);
                }
                
                if (isReadTimeout) {
                    nowTime = System.currentTimeMillis();
                    if (nowTime >= oldTime && nowTime <= oldTime + takeTime) {
                        if (timeChanged) {
                            timeChanged = false;
                        }
                    } else {
                        timeChanged = true;
                        String old = DateTimes.format(oldTime);
                        String now = DateTimes.format(nowTime);
                        notifyMessage(null, Locals.text("protocol.time.changed", old, now));
                    }
                    oldTime = nowTime;
                }
                
                int consentsLength = consents.length;
                // 无网络应答对象
                if (consentsLength <= 0) {
                    try {
                        lock.wait(100);
                    } catch (Exception e) {
                    }
                } else {
                    // 遍历网络应答对象，查询是否有数据、超时
                    for (int i = 0; i < consentsLength && !stopped; ++i) {
                        NetworkConsent consent = consents[i];
                        consent.checkSyncConsentData(nowTime, timeChanged);
                        if (isReadTimeout) {
                            consent.checkConsentTimeout(nowTime, this.readTimeout);
                        }
                        
                        if (i % 50 == 0) {
                            this.addStatCount();
                            try {
                                lock.wait(1);
                            } catch (Exception e) {
                            }
                            if (this.stopped) {
                                break;
                            }
                        }
                    }
                    try {
                        lock.wait(1);
                    } catch (Exception e) {
                    }
                }
            }
            
        }
    }
    
    private void notifyServiceStop() {
        if (!this.noConsents) {
            INetworkConsent[] consents = this.getConsents();
            synchronized (consentsLock) {
                this.consents.clear();
                this.consentChanged = true;
            }
            
            Arrays.stream(consents).forEach(consent -> {
                consent.close();
            });
            
            if (this.started) {
                Runnable task = new Runnable() {
                    
                    @Override
                    public void run() {
                        try {
                            NetworkService.this.notification.onServiceStop(NetworkService.this);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                if (!this.runSchedule(task)) {
                    task.run();
                }
            }
        }
    }
    
    private void callStop() {
        this.stop();
    }
    
    private void addStatCount() {
        if (this.statCount < Long.MAX_VALUE) {
            ++statCount;
        } else {
            statCount = 0L;
        }
        if (statCount % 20000000L == 0L) {
            // 正在进行网络监听，应答对象总数
            notifyMessage(null, Locals.text("protocol.network.running", consents.size()));
        }
        
    }
    
}
