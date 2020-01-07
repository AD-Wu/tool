package com.x.commons.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Desc
 * @Date 2019-11-02 11:45
 * @Author AD
 */
public final class Runner {

    private static final int threadNum = 3;

    private static final ExecutorService RUNNER = Executors.newFixedThreadPool(threadNum);

    private Runner() {}

    public static void add(Runnable runnable) {
        RUNNER.submit(runnable);
    }


}
