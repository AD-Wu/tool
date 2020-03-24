package com.x.framework.caching.datas;

import com.x.commons.collection.KeyValue;
import com.x.commons.collection.Where;
import com.x.commons.database.pool.DatabaseType;
import com.x.commons.util.bean.New;
import com.x.commons.util.collection.XArrays;

import java.util.Map;

/**
 * @Desc：
 * @Author：AD
 * @Date：2020/1/19 15:25
 */
public final class CacheManager {
    private static final Map<Class<?>, CacheData<?>> cacheMap = New.map();
    private static final Object cacheLock = new Object();

    private CacheManager() {
    }

    public static <T> void createCache(Class<T> dataClass, String[] pks, boolean cacheHistory, DatabaseType type) {
        if (!cacheMap.containsKey(dataClass)) {
            synchronized(cacheLock) {
                if (!cacheMap.containsKey(dataClass)) {
                    cacheMap.put(dataClass, new CacheData<>(dataClass, pks, cacheHistory, type));
                }
            }
        }
    }

    public static <T> boolean contains(Class<T> dataClass) {
        return dataClass != null && cacheMap.containsKey(dataClass);
    }

    public static void clear() {
        synchronized(cacheLock) {
            CacheData[] cacheDatas = cacheMap.values().toArray(new CacheData[0]);
            int L = cacheDatas.length;

            for(int i = 0; i < L; ++i) {
                CacheData data = cacheDatas[i];
                data.lock();
                data.clear();
                data.unlock();
            }

        }
    }

    public static void clear(Class<?> dataClass) {
        if (dataClass != null) {
            CacheData data = cacheMap.get(dataClass);
            if (data != null) {
                data.lock();
                data.clear();
                data.unlock();
            }

        }
    }

    public static void clear(CacheData<?> cacheData) {
        if (cacheData != null) {
            cacheData.clear();
        }

    }

    public static void clearHistory(Class<?> dataClass) {
        if (dataClass != null) {
            CacheData data = cacheMap.get(dataClass);
            if (data != null) {
                data.lock();
                data.clearHistory();
                data.unlock();
            }

        }
    }

    public static void clearHistory(CacheData<?> cacheData) {
        if (cacheData != null) {
            cacheData.clearHistory();
        }

    }

    public static <T> CacheData<T> lock(Class<T> dataClass) {
        if (dataClass == null) {
            return null;
        } else {
            CacheData<T> data = (CacheData<T>) cacheMap.get(dataClass);
            if (data != null) {
                data.lock();
            }

            return data;
        }
    }

    public static <T> void unlock(CacheData<T> cacheData) {
        if (cacheData != null) {
            cacheData.unlock();
        }

    }

    public static <T> void put(CacheData<T> cache, T data) throws Exception {
        if (cache != null && data != null) {
            cache.put(data);
        }
    }

    public static <T> void putAll(CacheData<T> cache, T[] datas, boolean isCache) throws Exception {
        if (cache != null && !XArrays.isEmpty(datas)) {
            if (isCache || !cache.hasData()) {
                cache.putAll(datas);
            }
        }
    }

    public static <T> void remove(CacheData<T> cache, Where[] wheres) throws Exception {
        if (cache != null) {
            cache.remove(wheres);
        }

    }

    public static <T> T getBean(Class<T> dataClass, Where[] wheres) throws Exception {
        if (dataClass == null) {
            return null;
        } else {
            CacheData cache = cacheMap.get(dataClass);
            return cache == null ? null : (T)cache.getOne(wheres);
        }
    }

    public static <T> T getByPrimary(Class<T> dataClass, Object[] pks) {
        if (dataClass != null && pks != null && pks.length != 0) {
            CacheData cache = cacheMap.get(dataClass);
            return cache == null ? null : (T)cache.getByPrimary(pks);
        } else {
            return null;
        }
    }

    public static <T> boolean contains(Class<T> dataClass, Where[] wheres) throws Exception {
        if (dataClass == null) {
            return false;
        } else {
            CacheData var2 = cacheMap.get(dataClass);
            return var2 == null ? false : var2.contains(wheres);
        }
    }

    public static <T> int getCount(Class<T> dataClass, Where[] wheres) throws Exception {
        if (dataClass == null) {
            return -1;
        } else {
            CacheData cache = cacheMap.get(dataClass);
            return cache == null ? -1 : cache.getCount(wheres);
        }
    }

    public static <T> T[] getList(Class<T> dataClass, Where[] wheres, KeyValue[] kvs) throws Exception {
        if (dataClass == null) {
            return null;
        } else {
            CacheData cache = cacheMap.get(dataClass);
            return cache == null ? null : (T[])cache.getList(wheres, kvs);
        }
    }

    public static <T> T[] getPage(Class<T> dataClass, int var1, int pageSize, Where[] wheres, KeyValue[] kvs) throws Exception {
        if (dataClass == null) {
            return null;
        } else {
            CacheData cache = cacheMap.get(dataClass);
            return cache == null ? null : (T[])cache.getPage(var1, pageSize, wheres, kvs);
        }
    }

    public static <T> void update(CacheData<T> cache, KeyValue[] kvs, Where[] wheres) throws Exception {
        if (cache != null && kvs != null && kvs.length != 0) {
            cache.update(kvs, wheres);
        }
    }
}
