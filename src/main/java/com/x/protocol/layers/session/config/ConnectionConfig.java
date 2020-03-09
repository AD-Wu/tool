package com.x.protocol.layers.session.config;

import com.x.commons.util.string.Strings;

import java.io.Serializable;

/**
 * @Desc
 * @Date 2020-03-08 22:14
 * @Author AD
 */
public class ConnectionConfig implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    // ------------------------ 变量定义 ------------------------
    private int maxSize = 1000;
    
    private int minuteLimit = 10;
    
    private long limitTime = 300000L;
    
    private long sessionTimeout = 180000L;
    
    // ------------------------ 构造方法 ------------------------
    public ConnectionConfig() {}
    // ------------------------ 方法定义 ------------------------
    
    public int getMaxSize() {
        return maxSize;
    }
    
    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }
    
    public int getMinuteLimit() {
        return minuteLimit;
    }
    
    public void setMinuteLimit(int minuteLimit) {
        this.minuteLimit = minuteLimit;
    }
    
    public long getLimitTime() {
        return limitTime;
    }
    
    public void setLimitTime(long limitTime) {
        this.limitTime = limitTime;
    }
    
    public long getSessionTimeout() {
        return sessionTimeout;
    }
    
    public void setSessionTimeout(long sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }
    
    @Override
    public String toString() {
        return Strings.defaultToString(this);
    }
    
}
