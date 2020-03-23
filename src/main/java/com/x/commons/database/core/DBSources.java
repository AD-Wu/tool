package com.x.commons.database.core;

import com.x.commons.database.pool.DatabaseType;
import com.x.commons.database.pool.PoolConfig;
import com.x.commons.enums.Charset;
import com.x.commons.util.bean.New;
import com.x.commons.util.prop.Config;
import com.x.commons.util.prop.data.Part;
import com.x.framework.database.DaoManager;
import com.x.protocol.layers.application.config.DatabaseConfig;

import java.util.Map;

/**
 * @Desc
 * @Date 2020-03-23 22:15
 * @Author AD
 */
public final class DBSources {
    
    private static final Map<String, DaoManager> managers = New.concurrentMap();
    
    private static DaoManager FIRST;
    
    public static void load(String filename) throws Exception {
        Config config = new Config();
        Part[] parts = config.load(filename);
        for (Part part : parts) {
            String name = part.getName();
            DatabaseConfig dbConfig = config.toClass(DatabaseConfig.class, part);
            managers.put(name, new DaoManager(name, dbConfig));
        }
    }
    
    public static DaoManager getDaoManager() {
        if (managers.size() == 1) {
            return managers.values().toArray(new DaoManager[0])[0];
        }
        return null;
    }
    
    public static DaoManager getDaoManager(String name) {
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
