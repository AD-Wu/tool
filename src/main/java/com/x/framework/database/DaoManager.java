package com.x.framework.database;

import com.x.commons.database.DatabaseAccess;
import com.x.commons.database.core.IDatabase;
import com.x.commons.database.pool.DatabaseType;
import com.x.commons.database.pool.Pool;
import com.x.commons.database.pool.PoolConfig;
import com.x.commons.database.pool.Pools;
import com.x.commons.local.Locals;
import com.x.commons.util.bean.New;
import com.x.commons.util.convert.Converts;
import com.x.commons.util.log.Logs;
import com.x.framework.caching.datas.CacheManager;
import com.x.framework.database.core.ITableInfoGetter;
import com.x.framework.database.core.SQLInfo;
import com.x.framework.database.core.TableInfo;
import com.x.framework.database.core.XTableInfoGetter;
import com.x.protocol.core.DataConfig;
import com.x.protocol.core.IProtocol;
import com.x.protocol.layers.application.config.DatabaseConfig;

import java.util.Map;

/**
 * @Desc：
 * @Author：AD
 * @Date：2020/1/19 17:56
 */
public class DaoManager implements IDaoManager {
    
    private final Map<Class<?>, IDao<?>> daosMap = New.concurrentMap();
    
    private final Object daosLock = new Object();
    
    private final String name;
    
    private IProtocol protocol;
    
    private boolean isStopped = false;
    
    private DatabaseType type;
    
    public DaoManager(String name, DatabaseConfig config) throws Exception {
        this.name = name;
        PoolConfig cfg = new PoolConfig();
        cfg.setName(name);
        cfg.setType(config.getType());
        cfg.setUrl(config.getUrl());
        cfg.setDriver(config.getDriver());
        cfg.setUser(config.getUser());
        cfg.setPassword(config.getPassword());
        cfg.setDruid(Converts.toBoolean(config.getDruid(),cfg.isDruid()));
        cfg.setInitialSize(Converts.toInt(config.getInitialSize(), cfg.getInitialSize()));
        cfg.setMaxActive(Converts.toInt(config.getMaxActive(), cfg.getMaxActive()));
        cfg.setMaxWait(Converts.toLong(config.getMaxWait(), cfg.getMaxWait()));
        cfg.setMinEvictableIdleTimeMillis(Converts.toLong(config.getMinEvictableIdleTimeMillis(),
                cfg.getMinEvictableIdleTimeMillis()));
        cfg.setMinIdle(Converts.toInt(config.getMinIdle(), cfg.getMinIdle()));
        cfg.setTestOnBorrow(Converts.toBoolean(config.getTestOnBorrow(), cfg.isTestOnBorrow()));
        cfg.setTestOnReturn(Converts.toBoolean(config.getTestOnReturn(), cfg.isTestOnReturn()));
        cfg.setTestWhileIdle(Converts.toBoolean(config.getTestWhileIdle(), cfg.isTestWhileIdle()));
        cfg.setTimeBetweenEvictionRunsMillis(
                Converts.toLong(config.getTimeBetweenEvictionRunsMillis(),
                        cfg.getTimeBetweenEvictionRunsMillis()));
        cfg.setValidationQuery(config.getValidateQuery());
        
        try {
            Pool pool = Pools.start(cfg);
            this.type = pool.getDBType();
        } catch (Exception e) {
            Logs.get(name).error(Locals.text("framework.db.start.err", name));
            throw e;
        }
    }
    
    public void setProtocol(IProtocol protocol) {
        this.protocol = protocol;
    }
    
    public IDatabase getDatabaseAccess() {
        try {
            return new DatabaseAccess(this.name);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public <T> IDao<T> getDao(Class<T> clazz) {
        return this.getDao(clazz, null);
    }
    
    public <T> IDao<T> getDao(Class<T> clazz, ITableInfoGetter<T> getter) {
        if (this.isStopped) {
            return null;
        } else {
            IDao<T> dao = (IDao<T>) daosMap.get(clazz);
            if (dao == null) {
                synchronized (this.daosLock) {
                    dao = (IDao<T>) daosMap.get(clazz);
                    if (dao != null) {
                        return dao;
                    }
                    
                    SQLInfo<T> info = this.getDaoMethods(clazz, getter);
                    
                    try {
                        if (info.isCaching()) {
                            if (protocol == null) {
                                dao = new CacheDao<>(this.name, info);
                            } else {
                                dao = new CacheDao<>(this.protocol, info);
                            }
                        } else {
                            if (protocol == null) {
                                dao = new Dao<>(this.name, info);
                            } else {
                                dao = new Dao<>(this.protocol, info);
                            }
                        }
                        
                        this.daosMap.put(clazz, dao);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            return dao;
        }
    }
    
    private <T> SQLInfo<T> getDaoMethods(Class<T> clazz, ITableInfoGetter<T> getter) {
        if (getter == null) {
            getter = new ITableInfoGetter<T>() {
                
                public TableInfo getTableInfo(Class<T> clazz) {
                    if(DaoManager.this.protocol!=null){
                        DataConfig cfg = DaoManager.this.protocol.getDataConfig(clazz);
                        return cfg == null ? null : new TableInfo(cfg.getTable(), cfg.getPks(),
                                cfg.isCache(), cfg.isHistory());
                    }
                    return null;
                }
            };
        }
        TableInfo tableInfo = getter.getTableInfo(clazz);
        if (tableInfo == null) {
            getter = new XTableInfoGetter<>();
            tableInfo = getter.getTableInfo(clazz);
        }
        return new SQLInfo<>(clazz, tableInfo, this.type);
    }
    
    public synchronized void stop() {
        if (!this.isStopped) {
            this.isStopped = true;
            
            try {
                Pools.stop(this.name);
            } catch (Exception e) {
                e.printStackTrace();
            }
            CacheManager.clear();
        }
    }
    
}
