package com.x.framework.caching.datas;

import com.x.commons.collection.KeyValue;
import com.x.commons.collection.Where;
import com.x.commons.database.pool.DatabaseType;
import com.x.commons.events.Dispatcher;
import com.x.commons.events.IListener;
import com.x.commons.util.bean.New;
import com.x.commons.util.collection.XArrays;
import com.x.framework.caching.methods.MethodData;
import com.x.framework.caching.methods.MethodManager;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Desc：cache=true的bean类信息对象
 * @Author：AD
 * @Date：2020/1/19 12:08
 */
public class CacheData<T> {

    private final ReentrantLock writeLock = new ReentrantLock();

    private final Class<T> dataClass;

    private final String[] pks;

    private final Set<String> pkSet = New.set();

    // key=hashCode(pk value),value=bean
    private final Map<String, T> datas = New.linkedMap();

    private Dispatcher dispatcher = null;

    private Object eventLock = new Object();

    private boolean dataChanged = false;

    private MethodData methods;

    private T[] dataAry;

    private HistoryCache<T> history = null;

    /**
     * 缓存数据必须要有主键
     *
     * @param dataClass    bean类
     * @param pks          主键属性名
     * @param cacheHistory 是否缓存历史
     * @param type         数据库类型
     */
    CacheData(Class<T> dataClass, String[] pks, boolean cacheHistory, DatabaseType type) {
        if (!XArrays.isEmpty(pks)) {
            this.dataClass = dataClass;
            this.pks = CacheHelper.upperCasePrimaryKeys(pks);
            this.pkSet.addAll(Arrays.asList(pks));
            this.dataAry = (T[]) Array.newInstance(dataClass, 0);
            this.methods = MethodManager.getMethodData(dataClass, type);
            this.history = cacheHistory ? new HistoryCache<>() : null;
        } else {
            throw new IllegalArgumentException(
                    "No primary key in data caching! Class: " + dataClass.getName());
        }
    }

    void lock() {
        writeLock.lock();
    }

    void unlock() {
        writeLock.unlock();
    }

    boolean hasData() {
        return this.datas.size() > 0;
    }

    int size() {
        return datas.size();
    }

    private T[] getArrayData() {
        if (!this.dataChanged) {
            return this.dataAry;
        } else {
            this.writeLock.lock();

            T[] results;
            try {
                if (this.dataChanged) {
                    results = (T[]) Array.newInstance(this.dataClass, this.datas.size());
                    this.datas.values().toArray(results);
                    this.dataAry = results;
                    this.dataChanged = false;
                    return results;
                }

                results = this.dataAry;
            } finally {
                this.writeLock.unlock();
            }

            return results;
        }
    }

    void clearHistory() {
        if (this.history != null && this.history.size() != 0) {
            this.history.clear();
        }
    }

    void clear() {
        T[] var1 = this.dataAry;
        this.datas.clear();
        this.dataAry = (T[]) Array.newInstance(this.dataClass, 0);
        this.dataChanged = false;
        this.clearHistory();
        if (this.dispatcher != null) {
            this.dispatchChanged(CacheEvent.ACTION_REMOVED, (T[]) var1);
        }

    }

    void put(T data) throws Exception {
        if (data != null) {
            String pkHashCode = CacheHelper.getPrimaryValueByKeys(methods.getMethodsGetMap(), pks,
                                                                  data);
            if (pkHashCode != null) {
                T result = this.datas.put(pkHashCode, data);
                if (!this.dataChanged) {
                    this.dataChanged = true;
                    this.clearHistory();
                }

                if (this.dispatcher != null) {
                    if (result == null) {
                        this.dispatchChanged(CacheEvent.ACTION_ADDED, data);
                    } else {
                        this.dispatchChanged(CacheEvent.ACTION_UPDATED, data);
                    }
                }

            }
        }
    }

    void putAll(T[] beans) throws Exception {
        for (T bean : beans) {
            if (bean != null) {
                String pkHashCode = CacheHelper.getPrimaryValueByKeys(
                        methods.getMethodsGetMap(),
                        pks, bean);
                if (pkHashCode != null) {
                    T result = this.datas.put(pkHashCode, bean);
                    if (this.dispatcher != null) {
                        if (result == null) {
                            this.dispatchChanged(CacheEvent.ACTION_ADDED, bean);
                        } else {
                            this.dispatchChanged(CacheEvent.ACTION_UPDATED, bean);
                        }
                    }
                }
            }
        }

        if (!this.dataChanged) {
            this.dataChanged = true;
            this.clearHistory();
        }

    }

    void remove(Where[] wheres) throws Exception {
        if (this.datas.size() != 0) {
            if (!XArrays.isEmpty(wheres)) {
                CacheHelper.upperCaseWhereKeys(wheres);
                String pkHashCode = CacheHelper.getPrimaryValueByWheres(this.pks, wheres);
                if (pkHashCode != null) {
                    T data = this.datas.remove(pkHashCode);
                    if (data != null && !this.dataChanged) {
                        this.dataChanged = true;
                        this.clearHistory();
                    }

                    if (this.dispatcher != null && data != null) {
                        this.dispatchChanged(CacheEvent.ACTION_REMOVED, data);
                    }
                } else {
                    ValueMatcher[] matchers = CacheHelper.getWhereMatchers(
                            this.methods.getMethodsGetMap(), wheres);
                    Iterator<Map.Entry<String, T>> it = this.datas.entrySet().iterator();
                    List<T> dataList = this.dispatcher == null ? null : New.list();
                    boolean find = false;

                    while (it.hasNext()) {
                        T data = it.next().getValue();
                        ;
                        if (CacheHelper.matchCondition(matchers, data)) {
                            it.remove();
                            if (dataList != null && data != null) {
                                dataList.add(data);
                            }

                            if (!find) {
                                find = true;
                            }
                        }
                    }

                    if (find && !this.dataChanged) {
                        this.dataChanged = true;
                        this.clearHistory();
                    }

                    if (dataList != null) {
                        T[] datas = (T[]) Array.newInstance(this.dataClass, dataList.size());
                        this.dispatchChanged(CacheEvent.ACTION_REMOVED, dataList.toArray(datas));
                    }
                }
            } else {
                this.clear();
            }
        }
    }

    T removeByPrimary(Object[] pkValues) {
        T data = this.datas.remove(CacheHelper.getPrimaryValueAsString(pkValues));
        if (data != null && !this.dataChanged) {
            this.dataChanged = true;
            this.clearHistory();
        }

        return data;
    }

    void update(KeyValue[] updates, Where[] wheres) throws Exception {
        T[] datas = this.getList(wheres, null);
        if (datas.length != 0) {
            ValueUpdater[] updaters = CacheHelper.getUpdaters(this.methods.getMethodsSetMap(),
                                                              updates);
            boolean containPK = false;

            for (ValueUpdater updater : updaters) {
                if (this.pkSet.contains(updater.getPropName())) {
                    containPK = true;
                    break;
                }
            }
            // 更新值有包含主键值，通知移除
            if (containPK && this.dispatcher != null) {
                this.dispatchChanged(CacheEvent.ACTION_REMOVED, datas);
            }

            for (T data : datas) {
                if (containPK) {
                    this.datas.remove(
                            CacheHelper.getPrimaryValueByKeys(this.methods.getMethodsGetMap(),
                                                              this.pks, data));
                }

                for (ValueUpdater updater : updaters) {
                    updater.setValue(data);
                }

                if (containPK) {
                    this.datas.put(
                            CacheHelper.getPrimaryValueByKeys(this.methods.getMethodsGetMap(),
                                                              this.pks, data), data);
                }
            }

            this.clearHistory();
            if (this.dispatcher != null) {
                if (containPK) {
                    this.dispatchChanged(CacheEvent.ACTION_ADDED, datas);
                } else {
                    this.dispatchChanged(CacheEvent.ACTION_UPDATED, datas);
                }
            }

        }
    }

    T getByPrimary(Object[] pks) {
        return this.datas.get(CacheHelper.getPrimaryValueAsString(pks));
    }

    /**
     * @param wheres
     * @return
     * @throws Exception
     */
    T getOne(Where[] wheres) throws Exception {
        int size = this.datas.size();
        if (size == 0) {
            return null;
        } else if (!XArrays.isEmpty(wheres)) {
            CacheHelper.upperCaseWhereKeys(wheres);
            String pkValue = CacheHelper.getPrimaryValueByWheres(this.pks, wheres);
            if (pkValue != null) {
                return this.datas.get(pkValue);
            } else {
                T[] datas = this.getArrayData();
                if (datas.length == 0) {
                    return null;
                } else {
                    ValueMatcher[] valueMatchers = CacheHelper.getWhereMatchers(
                            this.methods.getMethodsGetMap(), wheres);

                    for (T data : datas) {
                        if (CacheHelper.matchCondition(valueMatchers, data)) {
                            return data;
                        }
                    }

                    return null;
                }
            }
        } else {
            T[] datas = this.getArrayData();
            return datas.length == 0 ? null : datas[0];
        }
    }

    boolean contains(Where[] wheres) throws Exception {
        int size = this.datas.size();
        if (!XArrays.isEmpty(wheres) && size != 0) {
            CacheHelper.upperCaseWhereKeys(wheres);
            String pkValue = CacheHelper.getPrimaryValueByWheres(this.pks, wheres);
            if (pkValue != null) {
                return this.datas.get(pkValue) != null;
            } else {
                T[] datas;
                if (this.history != null && this.history.size() > 0) {
                    int cacheKey = CacheHelper.getHistoryCacheKey("list", wheres, null);
                    datas = this.history.get(cacheKey);
                    if (datas != null) {
                        return datas.length > 0;
                    }
                }

                datas = this.getArrayData();
                if (datas.length == 0) {
                    return false;
                } else {
                    ValueMatcher[] valueMatchers = CacheHelper.getWhereMatchers(
                            this.methods.getMethodsGetMap(), wheres);

                    for (T data : datas) {
                        if (CacheHelper.matchCondition(valueMatchers, data)) {
                            return true;
                        }
                    }

                    return false;
                }
            }
        } else {
            return size > 0;
        }
    }

    int getCount(Where[] wheres) throws Exception {
        int size = this.datas.size();
        if (!XArrays.isEmpty(wheres) && size != 0) {
            int historyCacheKey = 0;
            if (this.history != null && this.history.size() > 0) {
                historyCacheKey = CacheHelper.getHistoryCacheKey("list", wheres, (KeyValue[]) null);
                T[] datas = this.history.get(historyCacheKey);
                if (datas != null) {
                    return datas.length;
                }
            }

            CacheHelper.upperCaseWhereKeys(wheres);
            String pkValue = CacheHelper.getPrimaryValueByWheres(this.pks, wheres);
            if (pkValue != null) {
                return this.datas.containsKey(pkValue) ? 1 : 0;
            } else {
                T[] datas = this.getArrayData();
                List<T> dataList = new ArrayList();
                ValueMatcher[] whereMatchers = CacheHelper.getWhereMatchers(
                        this.methods.getMethodsGetMap(),
                        wheres);
                for (int i = 0, L = datas.length; i < L; ++i) {
                    T data = datas[i];
                    if (CacheHelper.matchCondition(whereMatchers, data)) {
                        dataList.add(data);
                    }
                }

                datas = (T[]) Array.newInstance(this.dataClass, dataList.size());
                datas = dataList.toArray(datas);
                if (this.history != null) {
                    this.history.put(historyCacheKey, datas);
                }

                return datas.length;
            }
        } else {
            return size;
        }
    }

    T[] getList(Where[] wheres, KeyValue[] kvs) throws Exception {
        if (this.datas.size() == 0) {
            return (T[]) Array.newInstance(this.dataClass, 0);
        } else {
            int historyCacheKey = 0;
            T[] historyDatas;
            if (this.history != null && this.history.size() > 0) {
                historyCacheKey = CacheHelper.getHistoryCacheKey("list", wheres, kvs);
                historyDatas = this.history.get(historyCacheKey);
                if (historyDatas != null) {
                    return historyDatas;
                }
            }

            T[] datas;
            if (wheres != null && wheres.length != 0) {
                CacheHelper.upperCaseWhereKeys(wheres);
                String primaryValue = CacheHelper.getPrimaryValueByWheres(this.pks, wheres);
                if (primaryValue != null) {
                    T data = this.datas.get(primaryValue);
                    if (data == null) {
                        return (T[]) Array.newInstance(this.dataClass, 0);
                    } else {
                        T[] results = (T[]) Array.newInstance(this.dataClass, 1);
                        results[0] = data;
                        return results;
                    }
                } else {
                    datas = this.getArrayData();
                    List<T> dataList = New.list();
                    ValueMatcher[] valueMatchers = CacheHelper.getWhereMatchers(
                            this.methods.getMethodsGetMap(), wheres);
                    for (int i = 0, L = datas.length; i < L; ++i) {
                        T data = datas[i];
                        if (CacheHelper.matchCondition(valueMatchers, data)) {
                            dataList.add(data);
                        }
                    }

                    datas = (T[]) Array.newInstance(this.dataClass, dataList.size());
                    datas = dataList.toArray(datas);
                    CacheHelper.sortArray(this.methods.getMethodsGetMap(), kvs, datas);
                    if (this.history != null) {
                        this.history.put(historyCacheKey, datas);
                    }

                    return datas;
                }
            } else {
                historyDatas = this.getArrayData();
                datas = (T[]) ((T[]) Array.newInstance(this.dataClass, historyDatas.length));
                System.arraycopy(historyDatas, 0, datas, 0, historyDatas.length);
                CacheHelper.sortArray(this.methods.getMethodsGetMap(), kvs, datas);
                if (this.history != null) {
                    this.history.put(historyCacheKey, datas);
                }

                return datas;
            }
        }
    }

    T[] getPage(int var1, int pageSize, Where[] wheres, KeyValue[] kvs) throws Exception {
        if (var1 <= 0) {
            var1 = 1;
        }

        if (pageSize <= 0) {
            pageSize = 1;
        }

        int var5 = (var1 - 1) * pageSize;
        int dataSize = this.datas.size();
        if (var5 < dataSize && dataSize != 0) {
            int cacheKey = 0;
            T[] historyDatas;
            if (this.history != null && this.history.size() > 0) {
                cacheKey = CacheHelper.getHistoryCacheKey("page|" + var1 + "|" + pageSize, wheres,
                                                          kvs);
                historyDatas = this.history.get(cacheKey);
                if (historyDatas != null) {
                    return historyDatas;
                }
            }

            T[] datas = this.getArrayData();
            if (wheres != null && wheres.length != 0) {
                CacheHelper.upperCaseWhereKeys(wheres);
                ValueMatcher[] valueMatchers = CacheHelper.getWhereMatchers(
                        this.methods.getMethodsGetMap(), wheres);
                List<T> dataList = New.list();

                for (int i = 0, L = datas.length; i < L; ++i) {
                    T data = datas[i];
                    if (CacheHelper.matchCondition(valueMatchers, data)) {
                        dataList.add(data);
                    }
                }

                dataSize = dataList.size();
                if (dataSize == 0) {
                    return (T[]) Array.newInstance(this.dataClass, 0);
                }

                if (var5 + pageSize > dataSize) {
                    pageSize = dataSize - var5;
                }

                if (pageSize < 1) {
                    return (T[]) Array.newInstance(this.dataClass, 0);
                }

                historyDatas = (T[]) Array.newInstance(this.dataClass, dataSize);
                historyDatas = dataList.toArray(historyDatas);
            } else {
                if (var5 + pageSize > datas.length) {
                    pageSize = datas.length - var5;
                }

                if (pageSize < 1) {
                    return (T[]) Array.newInstance(this.dataClass, 0);
                }

                if (kvs == null || kvs.length == 0) {
                    historyDatas = (T[]) Array.newInstance(this.dataClass, pageSize);
                    System.arraycopy(datas, var5, historyDatas, 0, pageSize);
                    if (this.history != null) {
                        this.history.put(cacheKey, historyDatas);
                    }

                    return historyDatas;
                }

                historyDatas = (T[]) Array.newInstance(this.dataClass, datas.length);
                System.arraycopy(datas, 0, historyDatas, 0, datas.length);
            }

            CacheHelper.sortArray(this.methods.getMethodsGetMap(), kvs, historyDatas);
            T[] resultDatas = (T[]) Array.newInstance(this.dataClass, pageSize);
            System.arraycopy(historyDatas, var5, resultDatas, 0, pageSize);
            if (this.history != null) {
                this.history.put(cacheKey, resultDatas);
            }

            return resultDatas;
        } else {
            return (T[]) Array.newInstance(this.dataClass, 0);
        }
    }

    public void addListener(String type, IListener listener) {
        if (dispatcher == null) {
            synchronized (eventLock) {
                if (dispatcher == null) {
                    dispatcher = new Dispatcher();
                }
            }
        }

        dispatcher.addListener(type, listener);
    }

    public void addListener(String type, IListener listener, int priority) {
        if (dispatcher == null) {
            synchronized (eventLock) {
                if (dispatcher == null) {
                    dispatcher = new Dispatcher();
                }
            }
        }

        dispatcher.addListener(type, listener, priority, new Object[0]);
    }

    public boolean hasListener(String type) {
        return this.dispatcher != null && this.dispatcher.hasListener(type);
    }

    public void removeListener(String type) {
        if (this.dispatcher != null) {
            this.dispatcher.removeListener(type);
        }

    }

    public boolean hasListener(String type, IListener var2) {
        return this.dispatcher != null && this.dispatcher.hasListener(type, var2);
    }

    public void removeAllListeners() {
        if (this.dispatcher != null) {
            this.dispatcher.removeAllListeners();
        }

    }

    public void removeListener(String type, IListener listener) {
        if (dispatcher != null) {
            dispatcher.removeListener(type, listener);
        }

    }

    private void dispatchChanged(int action, T data) {
        T[] datas = (T[]) Array.newInstance(this.dataClass, 1);
        datas[0] = data;
        dispatcher.dispatch(new CacheEvent(CacheEvent.DATA_CHANGED, action, datas));
    }

    private void dispatchChanged(int action, T[] datas) {
        dispatcher.dispatch(new CacheEvent(CacheEvent.DATA_CHANGED, action, datas));
    }

}
