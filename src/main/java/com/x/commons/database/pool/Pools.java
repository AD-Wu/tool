package com.x.commons.database.pool;

import com.x.commons.local.Locals;
import com.x.commons.util.bean.New;

import java.util.Map;

/**
 * @Desc
 * @Date 2019-11-08 17:31
 * @Author AD
 */
public final class Pools {
    
    private static final Map<String, Pool> POOLS = New.concurrentMap();
    
    private Pools() {}
    
    public static Pool start(PoolConfig config) throws Exception {
        String name = config.getPoolName();
        if (POOLS.containsKey(name)) {
            throw new Exception(Locals.text("commons.pool.started", name));
        } else {
            Pool pool = new Pool(config);
            POOLS.put(name, pool);
            return pool;
        }
    }
    
    public static void stop(String name) throws Exception {
        Pool pool = (Pool) POOLS.remove(name);
        if (pool != null) {
            pool.stop();
        }
    }
    
    public static void stopAll() {
        Pool[] pools = POOLS.values().toArray(new Pool[POOLS.size()]);
        Pools.POOLS.clear();
        
        for (int i = 0, L = pools.length; i < L; ++i) {
            Pool pool = pools[i];
            try {
                pool.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
    }
    
    public static Pool getPool(String poolName) {
        return POOLS.get(poolName);
    }
    
}
