package com.x.commons.util.collection;

import com.x.commons.util.bean.New;

import java.util.Set;

/**
 * @Date 2018-12-20 22:13
 * @Author AD
 */
public final class Sets {

    private Sets() {}

    /**
     * 两个集合的全部元素
     *
     * @param a
     * @param b
     * @return
     */
    public static <T> Set<T> union(Set<T> a, Set<T> b) {
        Set<T> set = New.set(a);
        set.addAll(b);
        return set;
    }

    /**
     * 交集，共有的元素
     *
     * @param a
     * @param b
     * @return
     */
    public static <T> Set<T> intersection(Set<T> a, Set<T> b) {
        Set<T> set = New.set(a);
        set.retainAll(b);
        return set;
    }

    /**
     * 移除共有的元素
     *
     * @param superset
     * @param subset
     * @return
     */
    public static <T> Set<T> difference(Set<T> superset, Set<T> subset) {
        Set<T> set = New.set(superset);
        set.remove(subset);
        return set;
    }

    /**
     * 两个集合分别拥有的元素
     *
     * @param a
     * @param b
     * @return
     */
    public static <T> Set<T> complement(Set<T> a, Set<T> b) {
        return difference(union(a, b), intersection(a, b));
    }

}
