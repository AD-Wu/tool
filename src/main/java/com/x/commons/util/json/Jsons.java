package com.x.commons.util.json;

import com.google.gson.Gson;
import lombok.NonNull;
import lombok.SneakyThrows;

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
    public static String to(Object src) {
        return gson().toJson(src);
    }

    /**
     * 将JSON字符串解析成对应的对象
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    @SneakyThrows
    public static <T> T from(String json, @NonNull Class<T> clazz) {
        return gson().fromJson(json, clazz);
    }

    /**
     * 判断是否是有效的json字符串
     *
     * @param check
     * @return
     */
    public static boolean valid(String check) {
        try {
            from(check, Object.class);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static Gson gson() {
        return new Gson();
    }

}
