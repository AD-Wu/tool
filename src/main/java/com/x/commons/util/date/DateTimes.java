package com.x.commons.util.date;

import com.x.commons.util.bean.New;
import com.x.commons.util.log.Logs;
import com.x.commons.util.string.Strings;
import org.slf4j.Logger;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

/**
 * @Date 2018-12-19 23:32
 * @Author AD
 */
public final class DateTimes {
    
    // ----------------------- 静态变量 -----------------------
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(
            "yyyy-MM-dd HH:mm:ss");
    
    public static final DateTimeFormatter DEFAULT = DateTimeFormatter.ofPattern(
            "yyyy-MM-dd HH:mm:ss.SSS");
    
    public static final DateTimeFormatter NO_MARK = DateTimeFormatter.ofPattern(
            "yyyyMMddHHmmssSSS");
    
    public static final DateTimeFormatter NO_MARK_SECONDS = DateTimeFormatter.ofPattern(
            "yyyyMMddHHmmss");
    
    private static final Logger LOG = Logs.get(DateTimes.class);
    
    private static final List<String> formatters = New.list();
    // ------------------------ 构造方法 ------------------------
    
    private DateTimes() {}
    
    // ----------------------- 静态方法 -----------------------
    
    /**
     * 获取当前日期时间（带毫秒）
     *
     * @return yyyy-MM-dd HH:mm:ss.SSS
     */
    public static String now() {
        return now(DEFAULT);
    }
    
    /**
     * 获取当前日期时间（带毫秒）
     *
     * @return yyyyMMddHHmmssSSS
     */
    public static String now(DateTimeFormatter formatter) {
        return LocalDateTime.now().format(formatter);
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
    
    public static LocalDateTime toLocalDataTime(long timeMillSeconds) {
        return toLocalDateTime(new Date(timeMillSeconds));
    }
    
    public static LocalDate toLocalDate(String date) throws Exception {
        LocalDateTime localDateTime = toLocalDateTime(date);
        return localDateTime.toLocalDate();
    }
    
    public static LocalTime toLocalTime(String time) throws Exception {
        LocalDateTime localDateTime = toLocalDateTime(time);
        return localDateTime.toLocalTime();
    }
    
    public static LocalDateTime toLocalDateTime(String dateTime) throws Exception {
        LocalDateTime parse = autoParse(dateTime, 0, formatters.size());
        if (parse == null) {
            String patter = "Can not parse the dateTime={0},implements {1} and add the annotation:@AutoService({2})";
            String name = IFormatter.class.getName();
            String msg = Strings.replace(patter, dateTime, name, name);
            Logs.get(DateTimes.class).error(msg);
        }
        return parse;
    }
    
    private static LocalDateTime autoParse(String dateTime, int index, int patternSize) {
        if (index < patternSize) {
            String pattern = formatters.get(index);
            try {
                return parse(dateTime, pattern);
            } catch (Exception e) {
                return autoParse(dateTime, ++index, patternSize);
            }
        }
        return null;
    }
    
    public static LocalDateTime parse(String dateTime, String pattern) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = sdf.parse(dateTime);
        return toLocalDateTime(date);
    }
    
    public static LocalDateTime toLocalDateTime(Date date) {
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime before = date.toInstant().atZone(zoneId).toLocalDateTime();
        /**
         * 修正,如：
         * date=1700-3-2 1:2:3.234 => localDateTime=1700-03-02T01:07:46
         * date=1100-3-2 1:2:3.234 => localDateTime=1100-03-09T01:07:46.234
         */
        LocalDateTime result = fixLocalDateTime(date, before);
        return result;
    }
    
    /**
     * 是否闰年
     *
     * @param year
     */
    public static boolean isLeapYear(int year) {
        if (year % 100 != 0) {
            if (year % 4 == 0) {
                return true;
            }
        } else if (year % 400 == 0) {
            return true;
        }
        return false;
    }
    
    /**
     * 将LocalDateTime类转换成类Date，不推荐使用Date
     *
     * @param localDateTime
     *
     * @return
     */
    public static Date toDate(LocalDateTime localDateTime) {
        Date date = new Date();
        // LocalDateTime使用正数，如：1100=1100;Date：1100=1100-1900
        int localYear = localDateTime.getYear();
        int year = localYear - 1900;
        date.setYear(year);
        date.setMonth(localDateTime.getMonthValue() - 1);
        date.setDate(localDateTime.getDayOfMonth());
        date.setHours(localDateTime.getHour());
        date.setMinutes(localDateTime.getMinute());
        date.setSeconds(localDateTime.getSecond());
        LOG.debug("LocalDateTime={}", localDateTime);
        LOG.debug("Date={}", date);
        return date;
        /**
         * 不使用这种方式，如：
         * LocalDateTime = 1100-03-02T01:02:03.234 =>
         * Date = Fri Feb 24 00:56:20 CST 1100
         */
        // String zoneID = TimeZone.getDefault().getID();// 时区偏移
        // ZonedDateTime toLocalDataTime = ZonedDateTime.toLocalDataTime(localDateTime, ZoneId.toLocalDataTime(zoneID));
        // Instant instant = toLocalDataTime.toInstant();
        // Date from = Date.from(instant);
        // return from;
    }
    
    /**
     * 将毫秒数解析成字符串时间
     *
     * @param time 毫秒数
     *
     * @return yyyy-MM-dd HH:mm:ss.SSS
     */
    public static String format(long time) {
        return format(time, DEFAULT);
    }
    
    /**
     * 将毫秒数解析成字符串时间
     *
     * @param time      毫秒数
     * @param formatter 格式化样式
     *
     * @return
     */
    public static String format(long time, DateTimeFormatter formatter) {
        return format(new Date(time), formatter);
    }
    
    /**
     * 将Date解析成字符串时间，推荐使用LocalDateTime
     *
     * @param date
     *
     * @return yyyy-MM-dd HH:mm:ss.SSS
     */
    public static String format(Date date) {
        return format(date, DEFAULT);
    }
    
    /**
     * 将Date解析成字符串时间，推荐使用LocalDateTime
     *
     * @param date      日期
     * @param formatter 格式化样式
     *
     * @return
     */
    public static String format(Date date, DateTimeFormatter formatter) {
        LocalDateTime dateTime = toLocalDateTime(date);
        return dateTime.format(formatter);
    }
    
    /**
     * 将LocalDateTime解析成字符串时间
     *
     * @param dateTime
     *
     * @return
     */
    public static String format(LocalDateTime dateTime) {
        return format(dateTime, DEFAULT);
    }
    
    /**
     * 将LocalDateTime解析成指定字符串时间
     *
     * @param dateTime
     *
     * @return
     */
    public static String format(LocalDateTime dateTime, DateTimeFormatter formatter) {
        return dateTime.format(formatter);
    }
    
    // ------------------------ 私有方法 ------------------------
    
    private static LocalDateTime fixLocalDateTime(Date date, LocalDateTime local) {
        // 年
        int dateYear = date.getYear();
        int localYear = local.getYear();
        // 月,Date：0=一月
        int dateMonth = date.getMonth() + 1;
        Month localMonth = local.getMonth();
        // 日
        int dateDay = date.getDate();
        int localDay = local.getDayOfMonth();
        // 时
        int dateHours = date.getHours();
        int localHours = local.getHour();
        // 分
        int dateMinutes = date.getMinutes();
        int localMinutes = local.getMinute();
        // 秒
        int dateSeconds = date.getSeconds();
        int localSeconds = local.getSecond();
        // 不相等才设置，否则会出现莫名其妙的错误
        if (dateYear != localYear) {
            if (dateMonth != localMonth.getValue()) {
                if (dateDay != localDay) {
                    if (dateHours != localHours) {
                        if (dateMinutes != localMinutes) {
                            if (dateSeconds != localSeconds) {
                                LocalDateTime result = local
                                        .withYear(dateYear)
                                        .withMonth(dateMonth)
                                        .withDayOfMonth(dateDay)
                                        .withHour(dateHours)
                                        .withMinute(dateMinutes)
                                        .withSecond(dateSeconds)
                                        .withNano(local.getNano());
                                return result;
                            } else {
                                LocalDateTime result = local
                                        .withNano(local.getNano());
                                return result;
                            }
                        } else {
                            LocalDateTime result = local
                                    .withSecond(dateSeconds)
                                    .withNano(local.getNano());
                            return result;
                        }
                    } else {
                        LocalDateTime result = local
                                .withMinute(dateMinutes)
                                .withSecond(dateSeconds)
                                .withNano(local.getNano());
                        return result;
                    }
                } else {
                    LocalDateTime result = local
                            .withHour(dateHours)
                            .withMinute(dateMinutes)
                            .withSecond(dateSeconds)
                            .withNano(local.getNano());
                    return result;
                }
            } else {
                LocalDateTime result = local
                        .withDayOfMonth(dateDay)
                        .withHour(dateHours)
                        .withMinute(dateMinutes)
                        .withSecond(dateSeconds)
                        .withNano(local.getNano());
                return result;
            }
        } else {
            LocalDateTime result = local
                    .withMonth(dateMonth)
                    .withDayOfMonth(dateDay)
                    .withHour(dateHours)
                    .withMinute(dateMinutes)
                    .withSecond(dateSeconds)
                    .withNano(local.getNano());
            return result;
        }
    }
    
    static {
        for (Formatter formatter : Formatter.values()) {
            formatters.add(formatter.pattern);
        }
        ServiceLoader<IFormatter> load = ServiceLoader.load(IFormatter.class);
        Iterator<IFormatter> it = load.iterator();
        while (it.hasNext()) {
            String formatter = it.next().get();
            formatters.add(formatter);
        }
    }
    
    // ------------------------ 内部类 ------------------------
    public interface IFormatter {
        
        String get();
        
    }
    
    public static enum Formatter {
        DATE_TIME("yyyy-MM-dd HH:mm:ss.SSS"),
        DATE_TIME_NO_MILL_SECONDS("yyyy-MM-dd HH:mm:ss"),
        
        DATE_TIME_NO_MARK("yyyyMMddHHmmssSSS"),
        DATE_TIME_NO_MARK_MILL_SECONDS("yyyyMMddHHmmss"),
        
        DATE_TIME_SLASH("yyyy/MM/dd HH:mm:ss.SSS"),
        DATE_TIME_SLASH_NO_MILL_SECONDS("yyyy/MM/dd HH:mm:ss"),
        
        DATE_TIME_CHINESE("yyyy年MM月dd日 HH时mm分ss秒SSS毫秒"),
        DATE_TIME_CHINESE_NO_MILL_SECONDS("yyyy年MM月dd日 HH时mm分ss秒"),
        
        DATE_TIME_CHINESE_NO_SPACE("yyyy年MM月dd日HH时mm分ss秒SSS毫秒"),
        DATE_TIME_CHINESE_NO_SPACE_MILL_SECONDS("yyyy年MM月dd日HH时mm分ss秒"),
        
        TIME("HH:mm:ss.SSS"),
        TIME_NO_MILL_SECONDS("HH:mm:ss"),
        
        TIME_NO_MARK("HHmmssSSS"),
        TIME_NO_MARK_MILL_SECONDS("HHmmss"),
        
        TIME_CHINESE("HH时mm分ss秒SSS毫秒"),
        TIME_CHINESE_NO_MILL_SECONDS("HH时mm分ss秒"),
        
        DATE("yyyy-MM-dd"),
        DATE_SLASH("yyyy/MM/dd"),
        DATE_CHINESE("yyyy年MM月dd日"),
        DATE_NO_MARK("yyyyMMdd");
        
        private final String pattern;
        
        private Formatter(String pattern) {
            this.pattern = pattern;
        }
    }
    
}
