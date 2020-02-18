package com.x.protocol.network.core;

import com.x.commons.collection.DataSet;
import com.x.commons.util.string.Strings;

/**
 * @Desc 网络服务配置
 * @Date 2020-02-18 23:54
 * @Author AD
 */
public class NetworkConfig extends DataSet {
    
    // ------------------------ 变量定义 ------------------------
    
    /**
     * 网络服务名字
     */
    private String name;
    
    /**
     * 网络服务类型
     */
    private String type;
    
    /**
     * 是否服务器模式
     */
    private boolean serverMode;
    
    /**
     * 核心线程池大小
     */
    private int corePoolSize = 0;
    
    /**
     * 最大线程数
     */
    private int maxPoolSize = 10;
    
    /**
     * 队列模式
     */
    private int queue = 1;
    
    /**
     * 读取超时时间
     */
    private long readTimeout = 1L;
    
    // ------------------------ 构造方法 ------------------------
    
    /**
     * 构造方法
     */
    public NetworkConfig() {}
    
    // ------------------------ 方法定义 ------------------------
    
    /**
     * 获取网络服务名字
     *
     * @return String 网络服务名字
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * 设置网络服务名字
     *
     * @param name 网络服务名字
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * 获取网络服务类型
     *
     * @return String 网络服务类型
     */
    public String getType() {
        return this.type;
    }
    
    /**
     * 设置网络服务类型
     *
     * @param type 网络服务类型
     */
    public void setType(String type) {
        this.type = type;
    }
    
    /**
     * 获取是否服务器模式
     *
     * @return boolean 是否服务器模式
     */
    public boolean isServerMode() {
        return this.serverMode;
    }
    
    /**
     * 设置是否服务器模式
     *
     * @param serverMode 是否服务器模式
     */
    public void setServerMode(boolean serverMode) {
        this.serverMode = serverMode;
    }
    
    /**
     * 获取核心线程池大小
     *
     * @return int 核心线程池大小
     */
    public int getCorePoolSize() {
        return this.corePoolSize;
    }
    
    /**
     * 设置核心线程池大小
     *
     * @param corePoolSize 核心线程池大小
     */
    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }
    
    /**
     * 获取最大线程数
     *
     * @return int 最大线程数
     */
    public int getMaxPoolSize() {
        return this.maxPoolSize;
    }
    
    /**
     * 设置最大线程数
     *
     * @param maxPoolSize 最大线程数
     */
    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }
    
    /**
     * 获取队列模式
     *
     * @return int 队列模式
     */
    public int getQueue() {
        return this.queue;
    }
    
    /**
     * 设置队列模式
     *
     * @param queue 队列模式
     */
    public void setQueue(int queue) {
        this.queue = queue;
    }
    
    /**
     * 获取读取超时时间
     *
     * @return long 读取超时时间
     */
    public long getReadTimeout() {
        return this.readTimeout;
    }
    
    /**
     * 设置读取超时时间
     *
     * @param readTimeout 读取超时时间
     */
    public void setReadTimeout(long readTimeout) {
        this.readTimeout = readTimeout;
    }
    
    @Override
    public String toString() {
        return Strings.defaultToString(this);
    }
    
}
