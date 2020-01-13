package com.x.commons.interfaces;

import com.x.commons.events.IListener;

public interface ITarget<T> {

    void addListener(IListener listener) throws Exception;

    void removeListener(IListener listener) throws Exception;

    void notifyListeners() throws Exception;

}
