package com.x.framework.caching.datas;


import com.x.commons.collection.KeyValue;
import com.x.commons.collection.Where;
import com.x.commons.database.pool.DatabaseType;
import com.x.framework.database.core.Sqls;

/**
 * @Desc：
 * @Author：AD
 * @Date：2020/1/19 15:00
 */
public class DataCaching<T> extends CacheData<T> {
    public DataCaching(Class<T> dataClass, String[] pks, boolean cache) {
        super(dataClass, pks, cache, null);
    }

    public DataCaching(Class<T> dataClass, String[] pks, boolean cache, DatabaseType databaseType) {
        super(dataClass, pks, cache, databaseType);
    }

    public boolean hasData() {
        return super.hasData();
    }

    public int size() {
        return super.size();
    }

    public void clear() {
        super.lock();

        try {
            super.clear();
        } finally {
            super.unlock();
        }

    }

    public void clearHistory() {
        super.lock();

        try {
            super.clearHistory();
        } finally {
            super.unlock();
        }

    }

    public void put(T data) throws Exception {
        super.lock();

        try {
            super.put(data);
        } finally {
            super.unlock();
        }

    }

    public void putAll(T[] datas) throws Exception {
        super.lock();

        try {
            super.putAll(datas);
        } finally {
            super.unlock();
        }

    }

    public T removeByPrimaryKey(Object... pks) {
        super.lock();

        T data;
        try {
            data = super.removeByPrimary(pks);
        } finally {
            super.unlock();
        }

        return data;
    }

    public void remove(Where[] wheres) throws Exception {
        super.lock();

        try {
            super.remove(wheres);
        } finally {
            super.unlock();
        }

    }

    public void remove(String[] props, Object[] values) throws Exception {
        super.lock();

        try {
            super.remove(Sqls.getWheres(props, values));
        } finally {
            super.unlock();
        }

    }

    public void update(KeyValue[] kvs, Where[] wheres) throws Exception {
        super.lock();

        try {
            super.update(kvs, wheres);
        } finally {
            super.unlock();
        }

    }

    public void update(String[] keys, Object[] values, String[] props, Object[] propValues) throws Exception {
        super.lock();

        try {
            super.update(Sqls.getUpdates(keys, values), Sqls.getWheres(props, propValues));
        } finally {
            super.unlock();
        }

    }

    public T getByPrimaryKey(Object... pks) {
        return super.getByPrimary(pks);
    }

    public T getOne(Where[] wheres) throws Exception {
        return super.getOne(wheres);
    }

    public T getOne(String[] props, Object[] values) throws Exception {
        return super.getOne(Sqls.getWheres(props, values));
    }

    public boolean contains(Where[] var1) throws Exception {
        return super.contains(var1);
    }

    public boolean contains(String[] props, Object[] values) throws Exception {
        return super.contains(Sqls.getWheres(props, values));
    }

    public int getCount(Where[] wheres) throws Exception {
        return super.getCount(wheres);
    }

    public int getCount(String[] props, Object[] values) throws Exception {
        return super.getCount(Sqls.getWheres(props, values));
    }

    public T[] getList(Where[] wheres, KeyValue[] kvs) throws Exception {
        return super.getList(wheres, kvs);
    }

    public T[] getList(String[] props, Object[] values, KeyValue[] kvs) throws Exception {
        return super.getList(Sqls.getWheres(props, values), kvs);
    }

    public T[] getPage(int var1, int var2, Where[] wheres, KeyValue[] kvs) throws Exception {
        return super.getPage(var1, var2, wheres, kvs);
    }

    public T[] getPage(int var1, int var2, String[] props, Object[] values, KeyValue[] kvs) throws Exception {
        return super.getPage(var1, var2, Sqls.getWheres(props, values), kvs);
    }
}
