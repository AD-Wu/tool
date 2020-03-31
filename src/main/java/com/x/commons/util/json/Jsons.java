package com.x.commons.util.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.x.commons.util.string.Strings;

/**
 * Json字符串工具类
 *
 * @Date 2019-06-17 20:18
 * @Author AD
 */
public final class Jsons {

    private Jsons() {}

    /**
     * 将对象解析成JSON字符串
     *
     * @param src
     * @return
     */
    public static String toJson(Object src) {
        return toJson(src, "");
    }

    /**
     * 将对象解析成JSON字符串
     *
     * @param src
     * @param dateTimeFormatter
     * @return
     */
    public static String toJson(Object src, String dateTimeFormatter) {
        return gson(dateTimeFormatter).toJson(src);
    }

    /**
     * 将JSON字符串解析成对应的对象
     *
     * @param json
     * @param clazz
     * @return
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        return fromJson(json, clazz, "");
    }

    /**
     * 将JSON字符串解析成对应的对象
     *
     * @param json
     * @param clazz
     * @param dateTimeFormatter
     * @return
     */
    public static <T> T fromJson(String json, Class<T> clazz, String dateTimeFormatter) {
        return gson(dateTimeFormatter).fromJson(json, clazz);
    }

    /**
     * 判断是否是有效的json字符串
     *
     * @param check
     * @return
     */
    public static boolean valid(String check) {
        try {
            fromJson(check, Object.class, null);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取Google的Gson对象
     *
     * @return
     */
    public static Gson gson() {
        return new Gson();
    }

    public static Gson gson(String dateTimeFormatter) {
        if (Strings.isNull(dateTimeFormatter)) {
            return gson();
        } else {
            return new GsonBuilder().setDateFormat(dateTimeFormatter).create();
        }
    }
}
