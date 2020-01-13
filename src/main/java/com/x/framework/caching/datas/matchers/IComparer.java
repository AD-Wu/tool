package com.x.framework.caching.datas.matchers;

public interface IComparer<T> {
    boolean compare(T first, T second);
}
