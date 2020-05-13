package com.x.commons.util.manager;

public interface IManager<T, KEY> {

    T getWorker(KEY key);
}
