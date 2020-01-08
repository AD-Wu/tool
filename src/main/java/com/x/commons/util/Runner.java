package com.x.commons.util;

import com.x.commons.util.bean.New;

import java.util.concurrent.ExecutorService;

/**
 * @Desc
 * @Date 2019-11-02 11:45
 * @Author AD
 */
public final class Runner {

    private static final int threadNum = 3;

    private static ExecutorService runner;

    private Runner() {}

    public static void add(Runnable runnable) {
        if (runner != null) {
            runner.submit(runnable);
        } else {
            synchronized (Runner.class) {
                if (runner == null) {
                    runner = New.threadPool(threadNum);
                }
                runner.submit(runnable);
            }
        }

    }


}
