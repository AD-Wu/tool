package com.x.commons.util.thread;

import com.x.commons.util.collection.XArrays;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @Desc TODO
 * @Date 2019-11-20 21:54
 * @Author AD
 */
public final class Threads {
    
    private Threads() {}
    
    public static Thread get(String threadName) {
        Map<Thread, StackTraceElement[]> all = Thread.getAllStackTraces();
        AtomicReference<Thread> t = new AtomicReference<>();
        all.forEach((k, v) -> {
            if (k.getName().equals(threadName)) {
                t.set(k);
            }
        });
        return t.get();
    }
    
    public static void run(Runnable... runnables) {
        if(XArrays.isEmpty(runnables)){
            Arrays.stream(runnables).forEach(Runner::add);
        }
    }
    
}
