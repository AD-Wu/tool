package com.x.commons.util.http.core;

import com.x.commons.util.http.data.Json;
import com.x.commons.util.http.factory.HttpConfig;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;

import java.io.File;
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
        post.setEntity(this.getEntity());
        return post;
    }

    private HttpEntity getEntity() {
        Iterator<Map.Entry<Object, Object>> it = param.iterator();
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        while (it.hasNext()) {
            Map.Entry<Object, Object> next = it.next();
            String fileKey = String.valueOf(next.getKey());
            Object value = next.getValue();
            if (value instanceof File) {
                FileBody file = new FileBody((File) value);
                builder.addPart(fileKey, file);
            }
        }
        return builder.build();
    }
}
