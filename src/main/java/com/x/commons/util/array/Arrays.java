package com.x.commons.util.array;

/**
 * @Date 2019-03-04 21:52
 * @Author AD
 */
public enum Arrays {
    ;

    public static <T> boolean isEmpty(T[] arrays) {
        return arrays == null || arrays.length == 0;
    }

    public static boolean isEmpty(int[] arrays) {
        return arrays == null || arrays.length == 0;
    }
}
