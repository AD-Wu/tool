package com.x.protocol.network.factory.custom;

import com.x.commons.collection.DataSet;

public interface ICustomClient {
    CustomConsent connect(String name, DataSet data) throws Exception;
}
