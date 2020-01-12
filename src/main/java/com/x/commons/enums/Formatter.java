package com.x.commons.enums;

import com.x.commons.util.date.DateTimes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Desc TODO
 * @Date 2019-12-01 17:50
 * @Author AD
 */
public enum Formatter {
    // ------------------------ 变量定义 ------------------------
    
    /**
     * yyyy-MM-dd HH:mm:ss.SSS
     */
    TOTAL("yyyy-MM-dd HH:mm:ss.SSS"),
    
    /**
     * yyyy-MM-dd HH:mm:ss
     */
    TOTAL_NO_MILL_SECONDS("yyyy-MM-dd HH:mm:ss"),
    
    /**
     * yyyyMMddHHmmssSSS
     */
    NO_MARK("yyyyMMddHHmmssSSS"),
    
    /**
     * yyyyMMddHHmmss
     */
    NO_MARK_MILL_SECONDS("yyyyMMddHHmmss"),
    
    /**
     * yyyy/MM/dd HH:mm:ss.SSS
     */
    TOTAL_SLASH("yyyy/MM/dd HH:mm:ss.SSS"),
    
    /**
     * yyyy/MM/dd HH:mm:ss
     */
    TOTAL_SLASH_NO_MILL_SECONDS("yyyy/MM/dd HH:mm:ss");
    
    private final String pattern;
    
    // ------------------------ 构造方法 ------------------------
    
    private Formatter(String pattern) {
        this.pattern = pattern;
    }
    
    // ------------------------ 方法定义 ------------------------
    
    public String pattern() {
        return this.pattern;
    }
    
    public DateTimeFormatter get() {
        return DateTimeFormatter.ofPattern(this.pattern);
    }
    
    public LocalDateTime parse(String dateTime) throws Exception {
        return DateTimes.parse(dateTime, this.pattern);
    }
    
    public static LocalDateTime autoParse(String dateTime) throws Exception {
        LOCK.lock();
        if (end()) {
            index = 0;
            return null;
        }
        Formatter formatter = formatters[index];
        try {
            LocalDateTime parse = formatter.parse(dateTime);
            index = 0;
            return parse;
        } catch (Exception e) {
            index++;
            return autoParse(dateTime);
        } finally {
            LOCK.unlock();
        }
    }
    
    private static int index = 0;
    
    private static Formatter[] formatters = Formatter.values();
    
    private static int formatterCount = formatters.length;
    
    private static final Lock LOCK = new ReentrantLock();
    
    // ------------------------ 私有方法 ------------------------
    
    private static boolean end() {
        return index >= formatterCount;
    }
    
    // 不实用这种方式，pattern=yyyyMMddHHmmssSSS时解析不出来，恐有其它bug
    private LocalDateTime doParse(String dateTime) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(this.pattern);
        TemporalAccessor accessor = formatter.parse(dateTime);
        return LocalDateTime.from(accessor);
    }
    
}


