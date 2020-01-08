package com.x.commons.util.redis;

/**
 * @Desc：
 * @Author：AD
 * @Date：2020/1/8 18:09
 */
public class RedisConfig {
    private final String host;

    private final int port;

    private String password;

    private int maxTotal;

    private int minIdle;

    private long maxWaitMillis;

    private boolean testOnBorrow;

    public RedisConfig(String host, int port) {
        this.host = host;
        this.port = port;
    }
}
