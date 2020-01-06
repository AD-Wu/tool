package com.x.commons.util.http.core;

import com.x.commons.enums.Charsets;
import com.x.commons.util.http.data.Json;
import com.x.commons.util.http.factory.HttpConfig;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map;

/**
 * @Desc：
 * @Author：AD
 * @Date：2020/1/6 13:38
 */
public class FileUploadRequest extends BaseHttpRequest {

    public FileUploadRequest(String url, Json param) {
        super(url, param);
    }

    @Override
    protected HttpRequestBase getRequest(HttpConfig config) throws Exception {
        HttpPost post = new HttpPost(url);
        post.setEntity(this.getEntity(config));
        return post;
    }

    private HttpEntity getEntity(HttpConfig config) {
        Iterator<Map.Entry<Object, Object>> it = param.iterator();
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setCharset(Charset.forName(config.getInEncoding()));
        // 浏览器兼容,不设置中文文件名会乱码导致上传失败
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        ContentType type = ContentType.create(ContentType.TEXT_PLAIN.getMimeType(),
                                              Charsets.UTF8);
        while (it.hasNext()) {
            Map.Entry<Object, Object> next = it.next();
            String key = String.valueOf(next.getKey());
            Object value = next.getValue();
            if (value != null) {
                if (value instanceof File) {
                    FileBody file = new FileBody((File) value);
                    builder.addPart(key, file);
                } else if (value instanceof byte[]) {
                    byte[] bs = (byte[]) value;
                    builder.addBinaryBody(key, bs, type, key);
                } else {
                    builder.addPart(key, new StringBody(String.valueOf(value), type));
                }
            }
        }
        return builder.build();
    }
}
