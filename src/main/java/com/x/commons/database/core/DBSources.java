package com.x.commons.database.core;

import com.x.commons.database.pool.DatabaseType;
import com.x.commons.database.pool.PoolConfig;
import com.x.commons.enums.Charset;
import com.x.commons.util.bean.New;
import com.x.commons.util.prop.Config;
import com.x.commons.util.prop.data.Part;
import com.x.framework.database.DaoManager;
import com.x.framework.database.IDaoManager;
import com.x.protocol.layers.application.config.DatabaseConfig;

import java.util.Map;

/**
 * @Desc
 * @Date 2020-03-23 22:15
 * @Author AD
 */
public final class DBSources {
    
    private static final Map<String, IDaoManager> managers = New.concurrentMap();
    
    private static IDaoManager FIRST;
    
    public static void load(String filename) throws Exception {
        Config config = new Config();
        Part[] parts = config.load(filename);
        for (Part part : parts) {
            String name = part.getName();
            DatabaseConfig dbConfig = config.toClass(DatabaseConfig.class, part);
            managers.put(name, new DaoManager(name, dbConfig));
        }
    }
    
    public static IDaoManager getDaoManager() {
        if (FIRST != null) {
            return FIRST;
        }
        synchronized (DBSources.class) {
            if (FIRST != null) {
                return FIRST;
            }
            if (managers.size() == 1) {
                FIRST = managers.values().toArray(new IDaoManager[0])[0];
                return FIRST;
            }
        }
        return null;
    }
    
    public static IDaoManager getDaoManager(String name) {
        return managers.get(name);
    }
    
    public static boolean generateDBConfigFile(String filename, DatabaseType db) {
        Config config = new Config();
        try {
            PoolConfig pool = new PoolConfig();
            pool.setType(db.type());
            pool.setUser("root");
            pool.setPassword("123456");
            pool.setDriver(db.driver());
            pool.setUrl(db.localURL());
            pool.setPoolName(db.type());
            config.toFile(filename, pool, Charset.UTF8);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
}
