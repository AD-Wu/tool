package com.x.commons.util.http.core;

import com.x.commons.util.http.data.HttpParam;
import com.x.commons.util.http.factory.HttpConfig;
import com.x.commons.util.http.factory.IHttpEntityGetter;
import com.x.commons.util.http.factory.impl.HttpEntityGetters;
import com.x.commons.util.log.Logs;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;

/**
 * @Desc TODO
 * @Date 2020-02-13 16:44
 * @Author AD
 */
public abstract class BaseHttpParamRequest extends BaseHttpRequest {

    // ------------------------ 变量定义 ------------------------

    protected final HttpParam param;

    // ------------------------ 构造方法 ------------------------
    protected BaseHttpParamRequest(String url, HttpParam param) {
        super(url);
        this.param = param;
    }

    // ------------------------ 方法定义 ------------------------

    protected HttpEntity getEntity(HttpConfig config) {
        ContentType type = config.getContentType();
        IHttpEntityGetter getter = HttpEntityGetters.get(type.getMimeType());
        if (getter != null) {
            return getter.getEntity(param, config);
        } else {
            Logs.get(this.getClass())
                    .error("Can't get the HttpEntity with the content type is {},you can implements IHttpEntityGetter and add the annotation toLocalDataTime AutoService to " +
                                   "get the entity", type);
            return null;
        }
    }

}
