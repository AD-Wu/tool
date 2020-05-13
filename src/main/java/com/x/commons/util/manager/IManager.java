package com.x.commons.util.manager;

public interface IManager<T, KEY> {

    T get(KEY key);
}
