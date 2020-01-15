package com.x.commons.util.date;

import com.x.commons.enums.Formatter;
import com.x.commons.util.log.Logs;
import org.slf4j.Logger;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

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
     * @return
     */
    public static String now(boolean withMillSeconds) {
        return withMillSeconds ? now() : LocalDateTime.now().format(FORMATTER);
    }

    public static LocalDateTime autoParse(String dateTime) throws Exception {
        LocalDateTime parse = Formatter.autoParse(dateTime);
        if (parse == null) {
            parse = DateTimeParsers.autoParse(dateTime);
            if (parse == null) {
                Logs.get(DateTimes.class)
                        .error("Can't parse the dateTime={}, you can extends BaseDateTimeParser, and add the annotation with " +
                                       "\"@AutoService(BaseDateTimeParser.class)\"", dateTime);
                return null;
            }
        }
        return parse;
    }

    public static void main(String[] args) throws Exception {
        LocalDateTime time0 = autoParse("2011-12-13 14:15:16.777");
        LocalDateTime time1 = autoParse("2012-12-13 14:15:16");
        LocalDateTime time2 = autoParse("2013/12/13 14:15:16.777");
        LocalDateTime time3 = autoParse("2014/12/13 14:15:16");
        LocalDateTime time4 = autoParse("20151213141516777");
        LocalDateTime time5 = autoParse("20161213141516");
        LocalDateTime time6 = autoParse("2017|12|13|14|15|16|777");
        System.out.println(time0);
        System.out.println(time1);
        System.out.println(time2);
        System.out.println(time3);
        System.out.println(time4);
        System.out.println(time5);
        System.out.println(time6);
    }

    public static LocalDateTime parse(String dateTime, String pattern) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = sdf.parse(dateTime);
        LOG.debug("source={}", dateTime);
        LOG.debug("pattern={}", pattern);
        LOG.debug("Date={}", date);
        return toLocalDateTime(date);
    }

    /**
     * 将Date类转换成LocalDateTime类，不推荐使用Date
     *
     * @param date
     * @return
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime before = date.toInstant().atZone(zoneId).toLocalDateTime();
        /**
         * 修正,如：
         * date=1700-3-2 1:2:3.234 => localDateTime=1700-03-02T01:07:46
         * date=1100-3-2 1:2:3.234 => localDateTime=1100-03-09T01:07:46.234
         */
        LocalDateTime result = fixLocalDateTime(date, before);
        LOG.debug("LocalDateTime={}", before);
        LOG.debug("After fix localDateTime={}", result);
        return result;
    }

    /**
     * 将LocalDateTime类转换成类Date，不推荐使用Date
     *
     * @param localDateTime
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
        // ZonedDateTime of = ZonedDateTime.of(localDateTime, ZoneId.of(zoneID));
        // Instant instant = of.toInstant();
        // Date from = Date.from(instant);
        // return from;
    }

    /**
     * 将毫秒数解析成字符串时间
     *
     * @param time 毫秒数
     * @return yyyy-MM-dd HH:mm:ss.SSS
     */
    public static String format(long time) {
        return format(new Date(time));
    }

    /**
     * 将Date解析成字符串时间，推荐使用LocalDateTime
     *
     * @param date
     * @return yyyy-MM-dd HH:mm:ss.SSS
     */
    public static String format(Date date) {
        LocalDateTime dateTime = toLocalDateTime(date);
        return dateTime.format(DEFAULT);
    }

    /**
     * 将LocalDateTime解析成字符串时间
     *
     * @param dateTime
     * @return
     */
    public static String format(LocalDateTime dateTime) {
        return format(dateTime, DEFAULT);
    }

    /**
     * 将LocalDateTime解析成指定字符串时间
     *
     * @param dateTime
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
}
