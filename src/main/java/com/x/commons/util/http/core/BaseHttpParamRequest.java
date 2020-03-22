package com.x.commons.util.http.core;

import com.x.commons.util.file.Files;
import com.x.commons.util.http.data.HttpParam;
import com.x.commons.util.http.data.ValueType;
import com.x.commons.util.http.enums.HttpContentType;
import com.x.commons.util.http.factory.HttpConfig;
import com.x.commons.util.http.factory.HttpEntitys;
import com.x.commons.util.http.factory.IHttpEntityGetter;
import com.x.commons.util.log.Logs;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map;

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
        ContentType contentType = config.getContentType();
        switch (contentType.getMimeType()) {
            case HttpContentType.JSON:
                return getJsonEntity(config.getInEncoding());
            case HttpContentType.FORM:
                return getFormEntity(config.getInEncoding());
            case HttpContentType.FORM_DATA:
                return getFormDataEntity(config);
            default:
                IHttpEntityGetter getter = HttpEntitys.getEntityGetter(contentType.getMimeType());
                if (getter != null) {
                    return getter.getEntity(param, config);
                }
                Logs.get(this.getClass())
                        .error("Can't get the HttpEntity with the content type is {},you can implements IHttpEntityGetter and add the annotation toLocalDataTime AutoService to " +
                               "get the entity", contentType);
                return null;
        }
    }
    
    // ------------------------ 私有方法 ------------------------
    
    /**
     * 获取Content-Type=application/json的entity
     *
     * @param charset
     *
     * @return
     */
    private HttpEntity getJsonEntity(String charset) {
        String json = param.toJson();
        ContentType type = getContentType(charset, HttpContentType.JSON);
        return new StringEntity(json, type);
    }
    
    /**
     * 获取Content-Type=application/x-www-form-urlencoded的entity
     *
     * @param charset
     *
     * @return
     */
    private HttpEntity getFormEntity(String charset) {
        String kvs = param.toKeyValue();
        ContentType type = getContentType(charset, HttpContentType.FORM);
        return new StringEntity(kvs, type);
    }
    
    /**
     * 获取上传文件的entity，默认Content-Type=multipart/form-data
     *
     * @param config
     *
     * @return
     */
    private HttpEntity getFormDataEntity(HttpConfig config) {
        // 获取entity构建器
        MultipartEntityBuilder builder = getMultipartEntityBuilder(config);
        ContentType contentType = getContentType(config.getInEncoding(), HttpContentType.JSON); ;
        
        // 遍历entity，并判断类型
        Iterator<Map.Entry<ValueType, Object>> it = param.iterator();
        while (it.hasNext()) {
            Map.Entry<ValueType, Object> next = it.next();
            ValueType valueType = next.getKey();
            // 获取实际的key和value
            String key = String.valueOf(valueType.getKey());
            Object value = next.getValue();
            // 获取value的类型
            ValueType type = valueType.get();
            if (type == ValueType.FILE) {
                FileBody file = new FileBody((File) value);
                builder.addPart(key, file);
            } else {
                if (type == ValueType.FILE_PATH) {
                    File file = Files.getFile(key);
                    FileBody body = new FileBody(file);
                    builder.addPart(key, body);
                } else {
                    if (type == ValueType.BYTES) {
                        byte[] bs = (byte[]) value;
                        builder.addBinaryBody(key, bs, contentType, key);
                    } else {
                        builder.addPart(key, new StringBody(String.valueOf(value), contentType));
                    }
                }
            }
        }
        return builder.build();
    }
    
    private MultipartEntityBuilder getMultipartEntityBuilder(HttpConfig config) {
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setCharset(Charset.forName(config.getInEncoding()));
        // 浏览器兼容,不设置中文文件名会乱码导致上传失败
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        return builder;
    }
    
    private ContentType getContentType(String charset, String httpContentType) {
        return ContentType.create(httpContentType, charset);
    }
    
}
