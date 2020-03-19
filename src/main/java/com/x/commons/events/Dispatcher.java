package com.x.commons.events;

import com.x.commons.util.bean.New;
import com.x.commons.util.string.Strings;

import java.util.Map;

/**
 * @Desc：
 * @Author：AD
 * @Date：2020/1/13 13:11
 */
public class Dispatcher {
    private final Object lock = new Object();

    private final Map<String, Listeners> map = New.concurrentMap();

    public void addListener(String token, IListener listener) {
        if (Strings.isNotNull(token) && listener != null) {
            this.addListener(token, new ListenerInfo(listener));
        }
    }

    public void addListener(String token, IListener listener, int priority, Object... params) {
        if (Strings.isNotNull(token) && listener != null) {
            this.addListener(token, new ListenerInfo(priority, listener, params));
        }
    }

    public void addListener(String token, Class<?> listenerClass) {
        if (Strings.isNotNull(token) && listenerClass != null) {
            this.addListener(token, new ListenerInfo(listenerClass));
        }
    }

    public void addListener(String token, Class<?> listenerClass, int priority, Object... params) {
        if (Strings.isNotNull(token) && listenerClass != null) {
            this.addListener(token, new ListenerInfo(priority, listenerClass, params));
        }
    }

    public boolean hasListener(String token) {
        return map.containsKey(token);
    }

    public boolean hasListener(String token, IListener listener) {
        Listeners listeners = map.get(token);
        return listeners != null && listeners.contain(listener);
    }

    public boolean hasListener(String token, Class<? extends IListener> listenerClass) {
        Listeners listeners = map.get(token);
        return listeners != null && listeners.contain(listenerClass);
    }

    public void removeListener(String token) {
        synchronized (lock) {
            map.remove(token);
        }
    }

    public void removeListener(String token, IListener listener) {
        synchronized (lock) {
            Listeners listeners = map.get(token);
            if (listeners != null) {
                if (listeners.remove(listener) == 0) {
                    map.remove(token);
                }
            }
        }
    }

    public void removeListener(String token, Class<? extends IListener> listenerClass) {
        synchronized (lock) {
            Listeners listeners = map.get(token);
            if (listeners != null) {
                if (listeners.remove(listenerClass) == 0) {
                    map.remove(token);
                }
            }
        }
    }

    public void removeAllListeners() {
        synchronized (this.lock) {
            map.clear();
        }
    }

    public int dispatch(Event event) {
        if (event == null) {
            return 0;
        } else {
            Listeners var2 = map.get(event.getType());
            if (var2 == null) {
                return 0;
            } else {
                ListenerInfo[] var3 = var2.getListeners();
                ;
                int var4 = 0;
                ListenerInfo[] var5 = var3;
                int var6 = var3.length;

                for (int var7 = 0; var7 < var6; ++var7) {
                    ListenerInfo var8 = var5[var7];
                    if (event.isStopped()) {
                        break;
                    }

                    event.setParams(var8.getParams());

                    try {
                        var8.getListener().onEvent(event);
                        ++var4;
                    } catch (Exception var10) {
                        var10.printStackTrace();
                        event.setException(var10);
                    }
                }

                return var4;
            }
        }
    }

    private void addListener(String token, ListenerInfo info) {
        if (!Strings.isNull(token)) {
            Listeners listeners = map.get(token);
            if (listeners != null) {
                listeners.add(info);
            } else {
                synchronized (lock) {
                    listeners = map.get(token);
                    if (listeners == null) {
                        listeners = new Listeners();
                        map.put(token, listeners);
                    }
                }
                listeners.add(info);
            }
        }
    }
}
