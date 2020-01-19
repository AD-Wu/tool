package com.x.framework.database;


import com.x.commons.collection.KeyValue;
import com.x.commons.collection.Where;
import com.x.commons.events.IListener;

/**
 * @Desc TODO
 * @Date 2019-12-05 21:53
 * @Author AD
 */
public interface IDao<T> {
    String[] getPrimaryKeys();

    void refreshCache();

    void addCacheListener(String token, IListener listener);

    void addCacheListener(String token, IListener listener, int action);

    boolean hasCacheListener(String token);

    void removeCacheListener(String token);

    boolean hasCacheListener(String token, IListener listener);

    void removeAllCacheListeners();

    void removeCacheListener(String token, IListener listener);

    T add(T data) throws Exception;

    T add(String[] var1, Object[] var2) throws Exception;

    T[] addAll(T[] datas) throws Exception;

    T put(T data) throws Exception;

    T[] putAll(T[] datas) throws Exception;

    int delete(Where[] wheres) throws Exception;

    int delete(String[] var1, Object[] var2) throws Exception;

    int edit(T data) throws Exception;

    T getBean(Where[] wheres) throws Exception;

    T getBean(String[] var1, Object[] var2) throws Exception;

    T getByPrimary(Object... pks) throws Exception;

    boolean contains(Where[] wheres) throws Exception;

    boolean contains(String[] var1, Object[] var2) throws Exception;

    int getCount(Where[] wheres) throws Exception;

    int getCount(String[] var1, Object[] var2) throws Exception;

    T[] getList(Where[] wheres, KeyValue[] kvs) throws Exception;

    T[] getList(String[] var1, Object[] var2, KeyValue[] kvs) throws Exception;

    T[] getPage(int var1, int var2, Where[] wheres, KeyValue[] kvs) throws Exception;

    T[] getPage(int var1, int var2, String[] var3, Object[] var4, KeyValue[] kvs) throws Exception;

    int update(KeyValue[] kvs, Where[] wheres) throws Exception;

    int update(String[] var1, Object[] var2, String[] var3, Object[] var4) throws Exception;
}
