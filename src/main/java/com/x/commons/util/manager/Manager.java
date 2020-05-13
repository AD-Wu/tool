package com.x.commons.util.manager;

import com.x.commons.util.bean.New;

import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * @Desc：
 * @Author：AD
 * @Date：2020/5/13 18:11
 */
public abstract class Manager<T, KEY> implements IManager<T, KEY> {

    protected final Map<KEY, T> factory = New.concurrentMap();

    private static volatile boolean inited = false;

    private static final Object LOCK = new Object();

    protected final Class<T> clazz;

    protected Manager(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T get(KEY key) {
        if (!inited) {
            synchronized (LOCK) {
                if (!inited) {
                    init();
                    inited = true;
                }
            }
        }
        return factory.get(key);
    }

    private void init() {
        Iterator<T> it = ServiceLoader.load(clazz).iterator();
        while (it.hasNext()) {
            T sub = it.next();
            initFactory(sub);
        }
    }

    protected abstract void initFactory(T sub);
}
