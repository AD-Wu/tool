package com.x.commons.util.thread;

import com.x.commons.util.bean.New;
import com.x.commons.util.collection.XArrays;

import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Desc
 * @Date 2019-11-02 11:45
 * @Author AD
 */
public final class Runner {
    
    private static final int MAX = 100;
    
    private static ThreadPoolExecutor runner;
    
    private Runner() {}
    
    public static ThreadPoolExecutor getRunner() {
        return runner;
    }
    
    public static boolean remove(Runnable runnable) {
        return runner.remove(runnable);
    }
    
    public static <T> Future<T> submit(Runnable runnable, T result) {
        if (runner != null) {
            return runner.submit(runnable, result);
        } else {
            init();
            return runner.submit(runnable, result);
        }
    }
    
    public static <T> Future<T> submit(Callable<T> callable) {
        if (runner != null) {
            return runner.submit(callable);
        } else {
            init();
            return runner.submit(callable);
        }
    }
    
    public static void add(Runnable runnable) {
        if (runner != null) {
            runner.execute(runnable);
        } else {
            init();
            runner.execute(runnable);
        }
    }
    
    public static void add(Runnable... runnables) {
        if (XArrays.isEmpty(runnables)) {
            Arrays.stream(runnables).forEach(Runner::add);
        }
    }
    
    private synchronized static void init() {
        if (runner == null) {
            runner = New.threadPool(MAX);
        }
    }
    
}
