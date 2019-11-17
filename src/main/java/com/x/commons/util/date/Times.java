package com.x.commons.util.date;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @Date 2018-12-19 23:31
 * @Author AD
 */
public final class Times {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    private static final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
    private Times() {}

    /**
     * get current time 
     *
     * @return HH:mm:ss.SSS
     */
    public static String now() {return now(true); }

    public static String now(boolean withMillSeconds) {
        final LocalTime now = LocalTime.now();
        return withMillSeconds ? now.format(DEFAULT_FORMATTER) : now.format(TIME_FORMATTER);
    }

}
