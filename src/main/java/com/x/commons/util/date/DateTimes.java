package com.x.commons.util.date;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

/**
 * @Date 2018-12-19 23:32
 * @Author AD
 */
public final class DateTimes {
    
    // ----------------------- 静态变量 -----------------------
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    public static final DateTimeFormatter DEFAULT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    
    // ------------------------ 构造方法 ------------------------
    
    private DateTimes() {}
    
    // ----------------------- 静态方法 -----------------------
    
    /**
     * 获取当前日期时间（带毫秒）
     *
     * @return yyyy-MM-dd HH:mm:ss.SSS
     */
    public static String now() {
        return LocalDateTime.now().format(DEFAULT);
    }
    
    /**
     * 获取当前日期时间（不带毫秒）
     *
     * @param withMillSeconds 是否带毫秒
     *
     * @return
     */
    public static String now(boolean withMillSeconds) {
        return withMillSeconds ? now() : LocalDateTime.now().format(FORMATTER);
    }
    
    
    /**
     * 将Date类转换成LocalDateTime类，不推荐使用Date
     *
     * @param date
     *
     * @return
     */
    public static LocalDateTime to(Date date) {
        TimeZone timeZone = TimeZone.getDefault();
        return date.toInstant().atZone(timeZone.toZoneId()).toLocalDateTime();
    }
    
    /**
     * 将LocalDateTime类转换成类Date，不推荐使用Date
     *
     * @param localDateTime
     *
     * @return
     */
    public static Date to(LocalDateTime localDateTime) {
        // 时区偏移
        String zoneID = TimeZone.getDefault().getID();
        ZonedDateTime of = ZonedDateTime.of(localDateTime, ZoneId.of(zoneID));
        Instant instant = of.toInstant();
        return Date.from(instant);
    }
    
    /**
     * 将毫秒数解析成字符串时间
     *
     * @param time 毫秒数
     *
     * @return yyyy-MM-dd HH:mm:ss.SSS
     */
    public static String format(long time) {
        return format(new Date(time));
    }
    
    /**
     * 将Date解析成字符串时间，推荐使用LocalDateTime
     *
     * @param date
     *
     * @return yyyy-MM-dd HH:mm:ss.SSS
     */
    public static String format(Date date) {
        LocalDateTime dateTime = to(date);
        String format = dateTime.format(DEFAULT);
        return format;
    }
    
    // ------------------------ 私有方法 ------------------------
    
    public static void main(String[] args) {
        String format = format(System.currentTimeMillis());
        System.out.println(format);
        
        LocalDateTime localDateTime = LocalDateTime.now();
        Date to = to(localDateTime);
        String format1 = format(to);
        System.out.println(format1);
    }
    
}
