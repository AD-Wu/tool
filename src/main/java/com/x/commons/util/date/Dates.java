package com.x.commons.util.date;

import com.x.commons.util.string.Strings;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @Date 2018-12-18 23:49
 * @Author AD
 */
public final class Dates {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private Dates() {}

    /**
     * get current date
     *
     * @return yyyy-MM-dd
     */
    public static String now() {

        return LocalDate.now().format(DATE_FORMATTER);
    }

    public static void main(String[] args) {
        LocalDate to = to("2019-09-09");
        System.out.println(to);
    }

    public static LocalDate to(String localDate) {
        return Strings.isDate(localDate) ? LocalDate.parse(localDate) : null;
    }

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

}
