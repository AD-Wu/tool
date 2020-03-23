package com.x.framework.database;

import com.x.commons.collection.KeyValue;
import com.x.commons.collection.Where;
import com.x.commons.events.IListener;
import com.x.commons.util.string.Strings;
import com.x.framework.caching.datas.CacheData;
import com.x.framework.caching.datas.CacheManager;
import com.x.framework.database.core.SQLInfo;
import com.x.framework.database.core.Sqls;
import com.x.protocol.core.IProtocol;

/**
 * @Desc：
 * @Author：AD
 * @Date：2020/1/19 17:31
 */
public class CacheDao<T> implements IDao<T> {
    
    private Class<T> dataClass;
    
    private SQLInfo<T> sqlInfo;
    
    private Dao<T> dao;
    
    CacheDao(String name, SQLInfo<T> sqlInfo) throws Exception {
        this.dataClass = sqlInfo.getDataClass();
        this.sqlInfo = sqlInfo;
        this.dao = !Strings.isNull(name) ? new Dao<>(name, sqlInfo) : null;
        CacheManager.createCache(dataClass, sqlInfo.getPrimaryKeys(),
                sqlInfo.isCachingHistory(), sqlInfo.getType());
        CacheData<T> cacheData = CacheManager.lock(this.dataClass);
        
        try {
            CacheManager.putAll(cacheData, dao.getList(null, null), false);
        } finally {
            CacheManager.unlock(cacheData);
        }
        
    }
    
    CacheDao(IProtocol protocol, SQLInfo<T> sqlInfo) throws Exception {
        this.dataClass = sqlInfo.getDataClass();
        this.sqlInfo = sqlInfo;
        this.dao = protocol != null ? new Dao<>(protocol, sqlInfo) : null;
        CacheManager.createCache(dataClass, sqlInfo.getPrimaryKeys(),
                sqlInfo.isCachingHistory(), sqlInfo.getType());
        CacheData<T> cacheData = CacheManager.lock(this.dataClass);
        
        try {
            CacheManager.putAll(cacheData, dao.getList(null, null), false);
        } finally {
            CacheManager.unlock(cacheData);
        }
    }
    
    @Override
    public String[] getPrimaryKeys() {
        return this.sqlInfo.getPrimaryKeys();
    }
    
    @Override
    public void refreshCache() {
        CacheData<T> cacheData = CacheManager.lock(this.dataClass);
        
        try {
            CacheManager.clear(cacheData);
            CacheManager.putAll(cacheData, this.dao.getList(null, null), false);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CacheManager.unlock(cacheData);
        }
        
    }
    
    @Override
    public void addCacheListener(String type, IListener listener) {
        CacheData<T> cacheData = CacheManager.lock(this.dataClass);
        
        try {
            cacheData.addListener(type, listener);
        } finally {
            CacheManager.unlock(cacheData);
        }
        
    }
    
    @Override
    public void addCacheListener(String type, IListener listener, int cacheEventAction) {
        CacheData<T> cache = CacheManager.lock(this.dataClass);
        
        try {
            cache.addListener(type, listener, cacheEventAction);
        } finally {
            CacheManager.unlock(cache);
        }
        
    }
    
    @Override
    public boolean hasCacheListener(String type) {
        CacheData<T> cacheData = CacheManager.lock(this.dataClass);
        
        boolean contain;
        try {
            contain = cacheData.hasListener(type);
        } finally {
            CacheManager.unlock(cacheData);
        }
        
        return contain;
    }
    
    @Override
    public boolean hasCacheListener(String type, IListener listener) {
        CacheData<T> cacheData = CacheManager.lock(this.dataClass);
        
        boolean contain;
        try {
            contain = cacheData.hasListener(type, listener);
        } finally {
            CacheManager.unlock(cacheData);
        }
        
        return contain;
    }
    
    @Override
    public void removeCacheListener(String type) {
        CacheData<T> cacheData = CacheManager.lock(this.dataClass);
        
        try {
            cacheData.removeListener(type);
        } finally {
            CacheManager.unlock(cacheData);
        }
        
    }
    
    @Override
    public void removeAllCacheListeners() {
        CacheData<T> cacheData = CacheManager.lock(this.dataClass);
        
        try {
            cacheData.removeAllListeners();
        } finally {
            CacheManager.unlock(cacheData);
        }
        
    }
    
    @Override
    public void removeCacheListener(String type, IListener listener) {
        CacheData<T> cacheData = CacheManager.lock(this.dataClass);
        
        try {
            cacheData.removeListener(type, listener);
        } finally {
            CacheManager.unlock(cacheData);
        }
        
    }
    
    @Override
    public T add(T bean) throws Exception {
        CacheData<T> cacheData = CacheManager.lock(this.dataClass);
        
        T t;
        try {
            t = this.dao.add(bean);
            CacheManager.put(cacheData, t);
        } finally {
            CacheManager.unlock(cacheData);
        }
        return t;
    }
    
    @Override
    public T[] addAll(T[] beans) throws Exception {
        CacheData<T> cacheData = CacheManager.lock(this.dataClass);
        
        T[] results;
        try {
            results = this.dao.addAll(beans);
            CacheManager.putAll(cacheData, results, true);
        } finally {
            CacheManager.unlock(cacheData);
        }
        
        return results;
    }
    
    @Override
    public T put(T bean) throws Exception {
        CacheData<T> cacheData = CacheManager.lock(this.dataClass);
        
        T t;
        try {
            t = this.dao.put(bean);
            CacheManager.put(cacheData, t);
        } finally {
            CacheManager.unlock(cacheData);
        }
        
        return t;
    }
    
    @Override
    public T[] putAll(T[] beans) throws Exception {
        CacheData<T> cacheData = CacheManager.lock(this.dataClass);
        
        T[] results;
        try {
            results = this.dao.putAll(beans);
            CacheManager.putAll(cacheData, results, true);
        } finally {
            CacheManager.unlock(cacheData);
        }
        
        return results;
    }
    
    @Override
    public int delete(Where[] wheres) throws Exception {
        CacheData<T> cacheData = CacheManager.lock(this.dataClass);
        
        int count = -1;
        try {
            count = this.dao.delete(wheres);
            if (count != -1) {
                CacheManager.remove(cacheData, wheres);
            }
            
        } finally {
            CacheManager.unlock(cacheData);
        }
        return count;
    }
    
    @Override
    public int edit(T bean) throws Exception {
        CacheData<T> cacheData = CacheManager.lock(this.dataClass);
        
        int count = -1;
        try {
            count = this.dao.edit(bean);
            if (count != -1) {
                CacheManager.put(cacheData, bean);
            }
        } finally {
            CacheManager.unlock(cacheData);
        }
        
        return count;
    }
    
    @Override
    public T getBean(Where[] wheres) throws Exception {
        return CacheManager.getBean(this.dataClass, wheres);
    }
    
    @Override
    public T getByPrimary(Object... pks) throws Exception {
        return CacheManager.getByPrimary(this.dataClass, pks);
    }
    
    @Override
    public boolean contains(Where[] wheres) throws Exception {
        return CacheManager.contains(this.dataClass, wheres);
    }
    
    @Override
    public int getCount(Where[] wheres) throws Exception {
        return CacheManager.getCount(this.dataClass, wheres);
    }
    
    @Override
    public T[] getList(Where[] wheres, KeyValue[] orders) throws Exception {
        return CacheManager.getList(this.dataClass, wheres, orders);
    }
    
    @Override
    public T[] getPage(int page, int pageSize, Where[] wheres, KeyValue[] orders) throws Exception {
        return CacheManager.getPage(this.dataClass, page, pageSize, wheres, orders);
    }
    
    @Override
    public int update(KeyValue[] updates, Where[] wheres) throws Exception {
        CacheData<T> cacheData = CacheManager.lock(this.dataClass);
        
        int count = -1;
        try {
            count = this.dao.update(updates, wheres);
            if (count != -1) {
                CacheManager.update(cacheData, updates, wheres);
            }
            
        } finally {
            CacheManager.unlock(cacheData);
        }
        
        return count;
    }
    
    @Override
    public T add(String[] columns, Object[] values) throws Exception {
        T data = this.sqlInfo.createBean(columns, values);
        return data == null ? null : this.add(data);
    }
    
    public int delete(String[] columns, Object[] values) throws Exception {
        return this.delete(Sqls.getWheres(columns, values));
    }
    
    @Override
    public T getBean(String[] columns, Object[] values) throws Exception {
        return this.getBean(Sqls.getWheres(columns, values));
    }
    
    @Override
    public int getCount(String[] columns, Object[] values) throws Exception {
        return this.getCount(Sqls.getWheres(columns, values));
    }
    
    @Override
    public boolean contains(String[] columns, Object[] values) throws Exception {
        return CacheManager.contains(this.dataClass, Sqls.getWheres(columns, values));
    }
    
    @Override
    public T[] getList(String[] columns, Object[] values, KeyValue[] orders) throws Exception {
        return this.getList(Sqls.getWheres(columns, values), orders);
    }
    
    @Override
    public T[] getPage(int page, int pageSize, String[] columns, Object[] values, KeyValue[] orders) throws Exception {
        return this.getPage(page, pageSize, Sqls.getWheres(columns, values), orders);
    }
    
    @Override
    public int update(String[] updateColumns, Object[] updateValues, String[] whereColumns, Object[] whereValues)
            throws Exception {
        return this.update(Sqls.getUpdates(updateColumns, updateValues),
                Sqls.getWheres(whereColumns, whereValues));
    }
    
}
