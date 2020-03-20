package com.x.protocol.network.factory.custom;

import com.x.commons.collection.DataSet;

public interface ICustomService {
    boolean onStart(DataSet data, ICustomNotification notification) throws Exception;

    void onStop() throws Exception;
}
