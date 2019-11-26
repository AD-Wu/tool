package com.x.commons.util.collection;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Date 2019-03-04 21:52
 * @Author AD
 */
public final class Arrays {
    
    private Arrays() {}
    
    public static <T> boolean isEmpty(T[] arrays) {
        return arrays == null || arrays.length == 0;
    }
    
    public static boolean isEmpty(int[] arrays) {
        return arrays == null || arrays.length == 0;
    }
    
    public static boolean isEmpty(byte[] arrays) {
        return arrays == null || arrays.length == 0;
    }
    
    public static boolean isEmpty(short[] arrays) {
        return arrays == null || arrays.length == 0;
    }
    
    public static boolean isEmpty(long[] arrays) {
        return arrays == null || arrays.length == 0;
    }
    
    public static boolean isEmpty(float[] arrays) {
        return arrays == null || arrays.length == 0;
    }
    
    public static boolean isEmpty(double[] arrays) {
        return arrays == null || arrays.length == 0;
    }
    
    public static boolean isEmpty(char[] arrays) {
        return arrays == null || arrays.length == 0;
    }
    
    public static <T> Set<T> toSet(T[] ts) {
        return Stream.of(ts).collect(Collectors.toSet());
    }
    
}
