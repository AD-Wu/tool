package com.x.commons.enums;

import com.x.commons.util.string.Strings;

/**
 * @Desc TODO
 * @Date 2019-12-01 17:50
 * @Author AD
 */
public enum Formatter {
    
    Total("yyyy-MM-dd HH:mm:ss.SSS"),
    TotalSlash("yyyy/MM/dd HH:mm:ss.SSS"),
    TotalAllSlash("yyyy/MM/dd/HH/mm/ss/SSS"),
    TotalSlashNoMillSeconds("yyyy/MM/dd HH:mm:ss"),
    TotalAllSlashNoMillSeconds("yyyy/MM/dd/HH/mm/ss"),
    NoMillSeconds("yyyy-MM-dd HH:mm:ss"),
    NoSplit("yyyyMMddHHmmssSSS"),
    NoMillSecondsSplit("yyyyMMddHHmmss"),
    
    Date_Total("yyyy-MM-dd"),
    Date_Total_Slash("yyyy/MM/dd"),
    Date_Total_No_Split("yyyyMMdd");
    
    private final String pattern;
    
    private Formatter(String pattern) {
        this.pattern = pattern;
    }
    
    public static void main(String[] args) {
        String s ="12:12:12";
        boolean date = Strings.isTime(s);
        System.out.println(date);
    }
}
    

