package com.x.commons.util.redis;

import com.x.commons.local.Locals;
import com.x.commons.util.convert.Converts;
import com.x.commons.util.string.Strings;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.concurrent.TimeUnit;

/**
 * @Desc TODO
 * @Date 2020-01-08 22:15
 * @Author AD
 */
public class RedisPool {
    
    private final String name;
    
    private final String host;
    
    private final int port;
    
    private JedisPool pool;
    
    public RedisPool(RedisPoolConfig config) throws Exception {
        this.name = config.getName();
        this.host = config.getHost();
        this.port = config.getPort();
        if (!Strings.isNull(name)) {
            if (!Strings.isNull(host)) {
                if (port > 0) {
                    JedisPoolConfig poolConfig = new JedisPoolConfig();
                    poolConfig.setMaxTotal(config.getMaxTotal());
                    poolConfig.setMinIdle(config.getMinIdle());
                    poolConfig.setMaxWaitMillis(config.getMaxWaitMillis());
                    poolConfig.setTestOnBorrow(config.isTestOnBorrow());
                    String pwd = config.getPassword();
                    int timeout = Converts.toInt(TimeUnit.SECONDS.toMillis(10));
                    if (Strings.isNull(pwd)) {
                        this.pool = new JedisPool(poolConfig, host, port, timeout, pwd);
                    } else {
                        this.pool = new JedisPool(poolConfig, host, port, timeout);
                    }
                } else {
                    throw new IllegalArgumentException(Locals.text("commons.redis.host", port));
                }
            } else {
                throw new IllegalArgumentException(Locals.text("commons.redis.host"));
            }
        } else {
            throw new IllegalArgumentException(Locals.text("commons.redis.name"));
        }
    }
    
    public void stop() {
        if (pool != null && !pool.isClosed()) {
            pool.close();
        }
    }
    
    public Jedis getRedis() {
        return pool.getResource();
    }
    
    public String getName() {return this.name;}
    
    public String getHost() {return this.host;}
    
    public int getPort()    {return this.port;}
    
    public String getStatus() {
        return Locals.text("commons.redis.status", pool.getNumActive(), pool.getNumIdle(), pool.getNumWaiters());
    }
    
}
