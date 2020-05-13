package com.x.commons.util.http.factory.impl;

import com.google.auto.service.AutoService;
import com.x.commons.util.file.Files;
import com.x.commons.util.http.data.HttpParam;
import com.x.commons.util.http.data.ValueType;
import com.x.commons.util.http.enums.HttpContentType;
import com.x.commons.util.http.factory.HttpConfig;
import com.x.commons.util.http.factory.IHttpEntityGetter;
import org.apache.http.HttpEntity;
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
 * @Date：2020/5/13 17:40
 */
@AutoService(IHttpEntityGetter.class)
public class FormDataEntityGetter implements IHttpEntityGetter {

    @Override
    public HttpEntity getEntity(HttpParam param, HttpConfig config) {
        // 获取entity构建器
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        // 设置编码
        builder.setCharset(Charset.forName(config.getInEncoding()));
        // 浏览器兼容,不设置中文文件名会乱码导致上传失败
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        ContentType contentType = ContentType.create(HttpContentType.JSON, config.getInEncoding());

        // 遍历entity，并判断类型
        Iterator<Map.Entry<ValueType, Object>> it = param.iterator();
        while (it.hasNext()) {
            Map.Entry<ValueType, Object> next = it.next();
            ValueType valueType = next.getKey();
            // 获取实际的key和value
            String key = String.valueOf(valueType.getKey());
            // 获取value的类型
            ValueType type = valueType.get();
            if (type == ValueType.FILE) {
                FileBody file = new FileBody((File) next.getValue());
                builder.addPart(key, file);
            } else if (type == ValueType.FILE_PATH) {
                File file = Files.getFile(key);
                FileBody body = new FileBody(file);
                builder.addPart(key, body);
            } else if (type == ValueType.BYTES) {
                byte[] bs = (byte[]) next.getValue();
                builder.addBinaryBody(key, bs, contentType, key);
            } else {
                builder.addPart(key, new StringBody(String.valueOf(next.getValue()), contentType));
            }
        }
        return builder.build();
    }

    @Override
    public String getContentType() {
        return HttpContentType.FORM_DATA;
    }

}
