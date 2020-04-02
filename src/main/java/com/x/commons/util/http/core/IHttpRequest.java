package com.x.commons.util.http.core;

import com.x.commons.util.http.data.HttpResult;
import com.x.commons.util.http.factory.HttpConfig;

/**
 * @Desc
 * @Date 2020-01-05 13:44
 * @Author AD
 */
public interface IHttpRequest {
    HttpResult send(HttpConfig config) throws Exception;
}
