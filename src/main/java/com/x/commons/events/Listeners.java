package com.x.commons.events;

import com.x.commons.util.bean.New;
import com.x.commons.util.reflact.Clazzs;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;

/**
 * @Desc：
 * @Author：AD
 * @Date：2020/1/13 11:26
 */
class Listeners {

    private static final Comparator<ListenerInfo> COMPARATOR = new Comparator<ListenerInfo>() {
        @Override
        public int compare(ListenerInfo first, ListenerInfo second) {
            int firstPriority = first.getPriority();
            int secondPriority = second.getPriority();
            if (firstPriority == secondPriority) {
                return 0;
            } else {
                return firstPriority < secondPriority ? -1 : 1;
            }
        }
    };

    private Map<Class<?>, ListenerInfo> classMap = New.concurrentMap();

    private Map<IListener, ListenerInfo> infoMap = New.concurrentMap();

    private volatile boolean changed = false;

    private final Object lock = new Object();

    private ListenerInfo[] listeners;


    Listeners() {}

    boolean add(ListenerInfo info) {
        if (info == null) {return false;}
        IListener listener = info.getListener();
        if (listener == null) {
            Class<? extends IListener> listenerClass = info.getListenerClass();
            if (listenerClass == null) {
                return false;
            }
            try {
                listener = Clazzs.newInstance(listenerClass);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            synchronized (lock) {
                if (infoMap.containsKey(listener)) {
                    return true;
                }
                info.setListener(listener);
                classMap.put(listenerClass, info);
                infoMap.put(listener, info);
                if (!changed) {
                    changed = true;
                }
            }
            return true;
        } else {
            synchronized (lock) {
                if (infoMap.containsKey(listener)) {
                    return true;
                }
                infoMap.put(listener, info);
                if (!changed) {
                    changed = true;
                }
            }
            return true;
        }
    }


    int remove(IListener listener) {
        synchronized (lock) {
            ListenerInfo remove = infoMap.remove(listener);
            if (remove != null && !changed) {
                changed = true;
            }
        }
        return infoMap.size();
    }

    int remove(Class<? extends IListener> listenerClass) {
        synchronized (lock) {
            ListenerInfo remove = classMap.remove(listenerClass);
            if (remove != null) {
                infoMap.remove(remove.getListener());
                if (!changed) {
                    changed = true;
                }
            }
        }
        return infoMap.size();
    }

    boolean contain(IListener listener) {
        return infoMap.containsKey(listener);
    }

    boolean contain(Class<? extends IListener> listenerClass) {
        return classMap.containsKey(listenerClass);
    }

    ListenerInfo[] getListeners() {
        if (changed) {
            synchronized (lock) {
                if (changed) {
                    changed = false;
                    this.listeners = infoMap.values().toArray(new ListenerInfo[infoMap.size()]);
                    Arrays.sort(this.listeners, COMPARATOR);
                }
            }
        }
        return this.listeners;
    }
}
