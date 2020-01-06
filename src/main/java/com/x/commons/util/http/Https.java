package com.x.commons.util.http;

import com.x.commons.util.file.Files;
import com.x.commons.util.http.core.*;
import com.x.commons.util.http.data.HttpResult;
import com.x.commons.util.http.data.Json;
import com.x.commons.util.http.factory.HttpClients;
import com.x.commons.util.http.factory.HttpConfig;
import com.x.commons.util.http.factory.HttpContexts;
import com.x.commons.util.string.Strings;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;

import java.io.File;

/**
 * @Desc http工具类
 * @Date 2019-11-16 19:42
 * @Author AD
 */
public final class Https {

    // ------------------------ 变量定义 ------------------------

    // ------------------------ 构造方法 ------------------------

    private Https() {}

    // ------------------------ 方法定义 ------------------------

    public static HttpResult get(String url, Json param) throws Exception {
        HttpConfig config = HttpConfig.defaultConfig(isHttps(url));
        return get(url, param, config);
    }

    public static HttpResult get(String url, Json param, HttpConfig config) throws Exception {
        return new GetRequest(url, param).send(config);
    }

    public static HttpResult post(String url, Json param) throws Exception {
        return post(url, param, false);
    }

    public static HttpResult post(String url, Json param, boolean isForm) throws Exception {
        HttpConfig config = HttpConfig.defaultConfig(isHttps(url));
        return post(url, param, isForm, config);
    }

    public static HttpResult post(String url, Json param, boolean isForm, HttpConfig config) throws Exception {
        return new PostRequest(url, param, isForm).send(config);
    }

    public static HttpResult put(String url, Json param) throws Exception {
        HttpConfig config = HttpConfig.defaultConfig(isHttps(url));
        return put(url, param, config);
    }

    public static HttpResult put(String url, Json param, HttpConfig config) throws Exception {
        return new PutRequest(url, param).send(config);
    }

    public static HttpResult patch(String url, Json param) throws Exception {
        HttpConfig config = HttpConfig.defaultConfig(isHttps(url));
        return patch(url, param, config);
    }

    public static HttpResult patch(String url, Json param, HttpConfig config) throws Exception {
        return new PatchRequest(url, param).send(config);
    }

    public static HttpResult delete(String url, Json param) throws Exception {
        HttpConfig config = HttpConfig.defaultConfig(isHttps(url));
        return delete(url, param, config);
    }

    public static HttpResult delete(String url, Json param, HttpConfig config) throws Exception {
        return new DeleteRequest(url, param).send(config);
    }

    public static HttpResult head(String url, Json param) throws Exception {
        HttpConfig config = HttpConfig.defaultConfig(isHttps(url));
        return head(url, param, config);
    }

    public static HttpResult head(String url, Json param, HttpConfig config) throws Exception {
        return new HeadRequest(url, param).send(config);
    }

    public static HttpResult trace(String url, Json param) throws Exception {
        HttpConfig config = HttpConfig.defaultConfig(isHttps(url));
        return trace(url, param, config);
    }

    public static HttpResult trace(String url, Json param, HttpConfig config) throws Exception {
        return new TraceRequest(url, param).send(config);
    }

    public static HttpResult options(String url, Json param) throws Exception {
        HttpConfig config = HttpConfig.defaultConfig(isHttps(url));
        return options(url, param, config);
    }

    public static HttpResult options(String url, Json param, HttpConfig config) throws Exception {
        return new OptionsRequest(url, param).send(config);
    }

    public static HttpResult upload(String path) throws Exception {
        File file = Files.getFile(path);
        if (file == null || !file.isFile()) {
            throw new Exception("file is null or file is a directory");
        }
        FileBody body = new FileBody(file);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        HttpEntity entity = builder.addPart("AD-upload-" + file.getName(), body).build();
        HttpPost post = new HttpPost("http://localhost:8080/upload");
        post.setEntity(entity);
        HttpClient client = HttpClients.http();
        HttpResponse resp = null;
        HttpResult result = null;
        try {
            resp = client.execute(post, HttpContexts.create());
            result = new HttpResult(resp);
        } finally {
            ((CloseableHttpResponse) resp).close();
        }
        return result;
    }
    // ------------------------ 私有方法 ------------------------

    private static boolean isHttps(String url) {
        if (Strings.isNull(url)) {
            return false;
        }
        if (url.toLowerCase().startsWith("https://")) {
            return true;
        }
        return false;
    }

}
