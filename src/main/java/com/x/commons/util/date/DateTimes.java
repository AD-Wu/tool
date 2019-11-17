package com.x.commons.util.date;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Date 2018-12-19 23:32
 * @Author AD
 */
public final class DateTimes {

    // ----------------------- 静态变量 -----------------------
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final DateTimeFormatter DEFAULT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    // ----------------------- 静态方法 -----------------------

    /**
     * get current date and time
     *
     * @return yyyy-MM-dd HH:mm:ss.SSS
     */
    public static String now() {
        return LocalDateTime.now().format(DEFAULT);
    }

    /**
     * get current date and time
     *
     * @param withMillSeconds if the result with the mill seconds
     * @return
     */
    public static String now(boolean withMillSeconds) {
        return withMillSeconds ? now() : LocalDateTime.now().format(FORMATTER);
    }



}
