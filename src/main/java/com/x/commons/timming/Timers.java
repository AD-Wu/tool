package com.x.commons.timming;

/**
 * @Desc TODO
 * @Date 2019-11-26 23:39
 * @Author AD
 */
public final class Timers {

    private static Timer timer;

    private Timers() {}

    public static Timer getTimer() {
        return TimerHolder.get();
    }

    /**
     * 静态内部类：不会随着外部类的加载而加载；
     * 静态成员被访问时，如果需要构造，有JDK保证线程安全进行构造
     */
    private static class TimerHolder {
        private static Timer timer;
        private static final Object LOCK = new Object();
        private static Timer get() {
            if (timer != null) {
                return timer;
            } else {
                synchronized (LOCK) {
                    if (timer != null) {
                        return timer;
                    } else {
                        timer = new Timer("system", 0, 100, 60L, 0);
                        timer.start();
                    }
                }
            }
            return timer;
        }
    }

    /**
     * 定时器是重量级资源，建议采用单例方法getTimer();
     *
     * @return
     */
    public static Timer newTimer() {
        return null;
    }
}
