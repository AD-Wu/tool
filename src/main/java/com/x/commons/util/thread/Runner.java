package com.x.commons.util.thread;

import com.x.commons.util.bean.New;
import com.x.commons.util.collection.XArrays;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;

/**
 * @Desc
 * @Date 2019-11-02 11:45
 * @Author AD
 */
public final class Runner {
    
    private static final int MAX = 100;
    
    private static ExecutorService runner;
    
    private Runner() {}
    
    public static void add(Runnable runnable) {
        if (runner != null) {
            runner.execute(runnable);
        } else {
            synchronized (Runner.class) {
                if (runner == null) {
                    runner = New.threadPool(MAX);
                }
                runner.execute(runnable);
            }
        }
    }
    
    public static void add(Runnable... runnables) {
        if (XArrays.isEmpty(runnables)) {
            Arrays.stream(runnables).forEach(Runner::add);
        }
    }
    
}
