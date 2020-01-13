package com.x.commons.interfaces;

import com.x.commons.events.IListener;

public interface ITarget<T> {

    void addListener(IListener<T> listener) throws Exception;

    void removeListener(IListener<T> listener) throws Exception;

    void notifyListeners() throws Exception;

}
