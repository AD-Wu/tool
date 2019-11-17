package com.x.commons.util.test;

/**
 * @Author AD
 * @Date 2018/12/21 15:20
 */
public final class Printer {

    private Printer() {}

    public static void print(Object o) {
        /**
         * invokers[0].getMethodName = getStackTrace 当前方法
         * invokers[1].getMethodName = print 本方法
         * invokers[2].getMethodName = map 调用者
         */
        final StackTraceElement[] invokers = Thread.currentThread().getStackTrace();
        System.out.printf("%-15s %s \n", invokers[2].getMethodName(), o);
    }

}
