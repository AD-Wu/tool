package com.x.commons.util.http.factory.impl;

import com.google.auto.service.AutoService;
import com.x.commons.util.http.data.HttpParam;
import com.x.commons.util.http.enums.HttpContentType;
import com.x.commons.util.http.factory.HttpConfig;
import com.x.commons.util.http.factory.IHttpEntityGetter;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;

/**
 * @Desc：
 * @Author：AD
 * @Date：2020/5/13 17:33
 */
@AutoService(IHttpEntityGetter.class)
public class JsonEntityGetter implements IHttpEntityGetter {

    @Override
    public HttpEntity getEntity(HttpParam param, HttpConfig config) {
        ContentType type = ContentType.create(getContentType(), config.getInEncoding());
        return new StringEntity(param.toJson(), type);
    }

    @Override
    public String getContentType() {
        return HttpContentType.JSON;
    }
}
