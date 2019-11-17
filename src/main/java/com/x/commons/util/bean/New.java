package com.x.commons.util.bean;

import com.x.commons.encrypt.MD5;
import lombok.NonNull;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * TODO 静态工厂，对象创建者
 *
 * @Date 2018-12-19 20:50
 * @Author AD
 */
public enum New {

    ;

    public static <K,V> Map<K, V> map() {
        return new HashMap<K, V>();
    }

    public static <K,V> Map<K, V> map(int size) {
        return new HashMap<K, V>(size);
    }

    public static <K,V> Map<K, V> treeMap() {
        return new TreeMap<>();
    }

    public static <K,V> Map<K, V> concurrentMap() {
        return new ConcurrentHashMap<>();
    }

    public static <K,V> Map<K, V> skipListMap() {
        return new ConcurrentSkipListMap<>();
    }

    public static <T> Set<T> set() {
        return new HashSet<>();
    }

    public static <T> Set<T> set(@NonNull Set<T> a) {
        return new HashSet<>(a);
    }

    public static <T> Set<T> set(int size) {
        return new HashSet<>(size);
    }

    public static <T> Set<T> concurrentSet() {
        return new ConcurrentSkipListSet<>();
    }

    public static <T> Set<T> treeSet() {
        return new TreeSet<>();
    }

    public static <T> List<T> list() {
        return new ArrayList<>();
    }

    public static <T> List<T> list(int size) {
        return new ArrayList<>(size);
    }

    public static <T> LinkedList<T> linkedList() {
        return new LinkedList<>();
    }

    public static <T> Queue<T> queue() {
        return new LinkedList<>();
    }

    public static ByteBuffer buf(int capacity) {
        return ByteBuffer.allocate(capacity);
    }

    public static ByteBuffer buf(@NonNull byte[] bs) {
        return ByteBuffer.wrap(bs);
    }

    public static ByteArrayOutputStream byteOutStream() {
        return new ByteArrayOutputStream();
    }

    public static SB sb() {
        return new SB();
    }

    public static MD5 md5() throws Exception { return new MD5(); }

}
