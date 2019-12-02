package com.x.commons.enums;

import com.ax.commons.utils.ConvertHelper;

import java.util.Date;

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
        String s ="2018-12-12 12:12:12.000";
        Date date = ConvertHelper.toDate(s);
        System.out.println(date);
    }
}
    

