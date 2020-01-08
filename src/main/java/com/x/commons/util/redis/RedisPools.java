package com.x.commons.util.redis;

import com.x.commons.local.Locals;
import com.x.commons.util.bean.New;

import java.util.Iterator;
import java.util.Map;

/**
 * @Desc TODO
 * @Date 2020-01-08 23:05
 * @Author AD
 */
public final class RedisPools {
    
    private static final Map<String, RedisPool> POOLS = New.concurrentMap();
    
    private RedisPools() {}
    
    public static RedisPool start(RedisPoolConfig config) throws Exception {
        String name = config.getName();
        if (POOLS.containsKey(name)) {
            throw new Exception(Locals.text("commons.pool.started", name));
        } else {
            RedisPool pool = new RedisPool(config);
            POOLS.put(name, pool);
            return pool;
        }
    }
    
    public static void stop(String name) {
        RedisPool pool = POOLS.remove(name);
        if (pool != null) {
            pool.stop();
        }
    }
    
    public static RedisPool getPool(String name) {
        return POOLS.get(name);
    }
    
    public static void stopAll() {
        Iterator<RedisPool> it = POOLS.values().iterator();
        while (it.hasNext()) {
            it.next().stop();
        }
        POOLS.clear();
    }
    
}
