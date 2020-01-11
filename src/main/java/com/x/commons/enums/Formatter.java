package com.x.commons.enums;

import java.time.format.DateTimeFormatter;

/**
 * @Desc TODO
 * @Date 2019-12-01 17:50
 * @Author AD
 */
public enum Formatter {
    
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
    TOTAL_SLASH_O_MILL_SECONDS("yyyy/MM/dd HH:mm:ss");
    
    private final String pattern;
    
    private Formatter(String pattern) {
        this.pattern = pattern;
    }
    
    public String pattern() {
        return this.pattern;
    }
    
    public DateTimeFormatter get() {
        return DateTimeFormatter.ofPattern(this.pattern);
    }
}
    

