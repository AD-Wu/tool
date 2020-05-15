package com.x.commons.util.manager;

import com.x.commons.util.bean.New;

import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * 策略模式管理者，继承该类的子类:<br/>
 * - 私有化构造方法<br/>
 * - 创建静态工厂get方法，获取实际策略对象<br/>
 * - 子类创建一个静态的自身对象<br/>
 *
 * @Author：AD
 * @Date：2020/5/13 18:11
 */
public abstract class Manager<T, KEY> implements IManager<T, KEY> {

    protected final Map<KEY, T> factory = New.concurrentMap();

    private volatile boolean inited = false;

    protected Manager() {}

    @Override
    public final T getWorker(KEY key) {
        if (!inited) {
            synchronized (this) {
                if (!inited) {
                    init();
                }
            }
        }
        return factory.get(key);
    }

    private void init() {
        Iterator<T> it = ServiceLoader.load(getClazz()).iterator();
        while (it.hasNext()) {
            T sub = it.next();
            KEY[] keys = getKeys(sub);
            for (KEY key : keys) {
                factory.put(key, sub);
            }
        }
        inited = true;
    }

    protected abstract Class<T> getClazz();

    protected abstract KEY[] getKeys(T sub);

}
