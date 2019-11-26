package com.x.commons.util;

import com.x.commons.local.Locals;

import java.util.Locale;

/**
 * @Desc TODO
 * @Date 2019-11-02 12:59
 * @Author AD
 */
public final class Systems {

    private Systems() {}

    /**
     * 运行时信息
     *
     * @return
     */
    public static String runtimeInfo() {

        Runtime run = Runtime.getRuntime();
        int cpu = run.availableProcessors();
        double max = getMemory(run.maxMemory());
        double total = getMemory(run.totalMemory());
        double free = getMemory(run.freeMemory());
        int size = Thread.getAllStackTraces().size();

        return Locals.text("runtime.info",cpu,max,total,free,size);
    }

    private static double getMemory(long memory) {
        return Math.floor((double) memory / 1024.0D / 1024.0D * 100.0D) / 100.0D;
    }

    /**
     * 获取系统默认语言
     *
     * @return 如：zh_CN
     */
    public static String getLanguage() {
        Locale locale = Locale.getDefault();
        String lang = locale.getLanguage() + "_" + locale.getCountry();
        return lang;
    }

}
