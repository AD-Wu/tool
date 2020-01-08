package com.x.commons.util.redis;

import java.util.concurrent.TimeUnit;

/**
 * @Desc：
 * @Author：AD
 * @Date：2020/1/8 18:09
 */
public class RedisPoolConfig {
    
    // ------------------------ 变量定义 ------------------------
    
    // redis连接池名
    private final String name;
    
    // redis IP
    private final String host;
    
    // redis 端口
    private final int port;
    
    // redis密码
    private String password;
    
    // 最大连接数
    private int maxTotal = 100;
    
    // 最小空闲数
    private int minIdle = 0;
    
    // 最大等待时长
    private long maxWaitMillis = TimeUnit.SECONDS.toMillis(30);
    
    // 获取连接时检测有效性
    private boolean testOnBorrow = true;
    
    // ------------------------ 构造方法 ------------------------
    
    public RedisPoolConfig(String name, String host, int port) {
        this.name = name;
        this.host = host;
        this.port = port;
    }
    
    // ------------------------ 方法定义 ------------------------
    
    /**
     * 获取 redis连接池名
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * 获取 redis IP
     */
    public String getHost() {
        return this.host;
    }
    
    /**
     * 获取 redis 端口
     */
    public int getPort() {
        return this.port;
    }
    
    /**
     * 获取 redis密码
     */
    public String getPassword() {
        return this.password;
    }
    
    /**
     * 设置 redis密码
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * 获取 最大连接数
     */
    public int getMaxTotal() {
        return this.maxTotal;
    }
    
    /**
     * 设置 最大连接数
     */
    public void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }
    
    /**
     * 获取 最小空闲数
     */
    public int getMinIdle() {
        return this.minIdle;
    }
    
    /**
     * 设置 最小空闲数
     */
    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }
    
    /**
     * 获取 最大等待时长
     */
    public long getMaxWaitMillis() {
        return this.maxWaitMillis;
    }
    
    /**
     * 设置 最大等待时长
     */
    public void setMaxWaitMillis(long maxWaitMillis) {
        this.maxWaitMillis = maxWaitMillis;
    }
    
    /**
     * 获取 获取连接时检测有效性
     */
    public boolean isTestOnBorrow() {
        return this.testOnBorrow;
    }
    
    /**
     * 设置 获取连接时检测有效性
     */
    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }
    
}
