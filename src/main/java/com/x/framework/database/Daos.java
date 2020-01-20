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
import com.x.protocol.anno.info.DataInfo;
import com.x.protocol.config.DatabaseConfig;
import com.x.protocol.core.IProtocol;

import java.util.Map;

/**
 * @Desc：
 * @Author：AD
 * @Date：2020/1/19 17:56
 */
public class Daos implements IDaos {
    private final Map<Class, IDao> daosMap = New.map();
    private final Object daosLock = new Object();
    private final String name;
    private IProtocol protocol;
    private boolean isStopped = false;
    private DatabaseType type;

    public Daos(String name, DatabaseConfig config) throws Exception {
        this.name = name;
        PoolConfig poolConfig = new PoolConfig();
        poolConfig.setPoolName(name);
        poolConfig.setType(config.getType());
        poolConfig.setUrl(config.getURL());
        poolConfig.setDriver(config.getDriverClass());
        poolConfig.setUser(config.getUser());
        poolConfig.setPassword(config.getPassword());
        poolConfig.setInitialSize(Converts.toInt(config.getInitialSize(), 0));
        poolConfig.setMaxActive(Converts.toInt(config.getMaxActive(), 0));
        poolConfig.setMaxWait(Converts.toLong(config.getMaxWait(), 60000L));
        poolConfig.setMinEvictableIdleTimeMillis(Converts.toLong(config.getMinEvictableIdleTimeMillis(), 1800000L));
        poolConfig.setMinIdle(Converts.toInt(config.getMinIdle(), 5));
        poolConfig.setTestOnBorrow(Converts.toBoolean(config.getTestOnBorrow(), false));
        poolConfig.setTestOnReturn(Converts.toBoolean(config.getTestOnReturn(), false));
        poolConfig.setTestWhileIdle(Converts.toBoolean(config.getTestWhileIdle(), true));
        poolConfig.setTimeBetweenEvictionRunsMillis(Converts.toLong(config.getTimeBetweenEvictionRunsMillis(), 60000L));
        poolConfig.setValidationQuery(config.getValidateQuery());

        try {
            Pool pool = Pools.start(poolConfig);
            this.type = pool.getType();
        } catch (Exception e) {
            Logs.get(name).error(Locals.text("framework.db.start.err",name));
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

    public <T> IDao<T> getDao(Class<T> clazz, ITableInfoGetter<T> tableInfoGetter) {
        if (this.isStopped) {
            return null;
        } else {
            IDao<T> dao = daosMap.get(clazz);
            if (dao == null) {
                synchronized(this.daosLock) {
                    dao = daosMap.get(clazz);
                    if (dao != null) {
                        return dao;
                    }

                    SQLInfo<T> info = this.getDaoMethods(clazz, tableInfoGetter);

                    try {
                        if (info.isCaching()) {
                            dao = new CacheDao(this.protocol, info);
                        } else {
                            dao = new Dao(this.protocol, info);
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

    private <T> SQLInfo<T> getDaoMethods(Class<T> clazz, ITableInfoGetter<T> tableInfoGetter) {
        if (tableInfoGetter == null) {
            tableInfoGetter = new ITableInfoGetter<T>() {
                public TableInfo getTableInfo(Class<T> clazz) {
                    DataInfo info = Daos.this.protocol.getDataConfig(clazz);
                    return info == null ? null : new TableInfo(info.getTable(), info.getPks(), info.isCache(), info.isHistory());
                }
            };
        }

        return new SQLInfo(clazz, tableInfoGetter.getTableInfo(clazz), this.type);
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
