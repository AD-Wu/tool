package com.x.commons.util.file.monitor;

/**
 * @Desc TODO
 * @Date 2019-10-25 23:20
 * @Author AD
 */
public interface IWatcher<T> {

    IWatcher addWatched(T t);
    IWatcher deleteWatched(T t);
}
