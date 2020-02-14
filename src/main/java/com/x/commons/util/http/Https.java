package com.x.commons.util.http;

import com.x.commons.util.file.Files;
import com.x.commons.util.http.core.*;
import com.x.commons.util.http.data.HttpParam;
import com.x.commons.util.http.data.HttpResult;
import com.x.commons.util.http.enums.HttpEntityMethod;
import com.x.commons.util.http.enums.HttpMethod;
import com.x.commons.util.http.factory.HttpConfig;

import java.io.File;
import java.io.IOException;

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
    
    /**
     * get请求
     *
     * @param url 请求路径，带参数
     *
     * @return
     *
     * @throws Exception
     */
    public static HttpResult get(String url) throws Exception {
        return get(url, HttpConfig.defaultConfig(url));
    }
    
    /**
     * get请求
     *
     * @param url    请求路径，带参数
     * @param config 请求配置
     *
     * @return
     *
     * @throws Exception
     */
    public static HttpResult get(String url, HttpConfig config) throws Exception {
        return new GetRequest(url).send(config);
    }
    
    /**
     * post请求，Content-Type=application/json
     *
     * @param url   请求路径
     * @param param 请求参数
     *
     * @return
     *
     * @throws Exception
     */
    public static HttpResult post(String url, HttpParam param) throws Exception {
        return post(url, param, HttpConfig.defaultConfig(url));
    }
    
    /**
     * post请求，Content-Type=application/x-www-form-urlencoded
     *
     * @param url   请求路径
     * @param param 请求参数
     *
     * @return
     *
     * @throws Exception
     */
    public static HttpResult postForm(String url, HttpParam param) throws Exception {
        return post(url, param, HttpConfig.formConfig(url));
    }
    
    /**
     * post请求
     *
     * @param url    请求路径
     * @param param  请求参数
     * @param config 请求配置
     *
     * @return
     *
     * @throws Exception
     */
    public static HttpResult post(String url, HttpParam param, HttpConfig config) throws Exception {
        return new PostRequest(url, param).send(config);
    }
    
    /**
     * put请求
     *
     * @param url   请求路径
     * @param param 请求参数
     *
     * @return
     *
     * @throws Exception
     */
    public static HttpResult put(String url, HttpParam param) throws Exception {
        return put(url, param, HttpConfig.defaultConfig(url));
    }
    
    /**
     * put请求，Content-Type=application/x-www-form-urlencoded
     *
     * @param url   请求路径
     * @param param 请求参数
     *
     * @return
     *
     * @throws Exception
     */
    public static HttpResult putForm(String url, HttpParam param) throws Exception {
        return put(url, param, HttpConfig.formConfig(url));
    }
    
    /**
     * put请求
     *
     * @param url    请求路径
     * @param param  请求参数
     * @param config 请求配置
     *
     * @return
     *
     * @throws Exception
     */
    public static HttpResult put(String url, HttpParam param, HttpConfig config) throws Exception {
        return new PutRequest(url, param).send(config);
    }
    
    /**
     * patch请求
     *
     * @param url   请求路径
     * @param param 请求参数
     *
     * @return
     *
     * @throws Exception
     */
    public static HttpResult patch(String url, HttpParam param) throws Exception {
        return patch(url, param, HttpConfig.defaultConfig(url));
    }
    
    /**
     * patch请求，Content-Type=application/x-www-form-urlencoded
     *
     * @param url   请求路径
     * @param param 请求参数
     *
     * @return
     *
     * @throws Exception
     */
    public static HttpResult patchForm(String url, HttpParam param) throws Exception {
        return patch(url, param, HttpConfig.formConfig(url));
    }
    
    /**
     * patch请求
     *
     * @param url    请求路径
     * @param param  请求参数
     * @param config 请求配置
     *
     * @return
     *
     * @throws Exception
     */
    public static HttpResult patch(String url, HttpParam param, HttpConfig config) throws Exception {
        return new PatchRequest(url, param).send(config);
    }
    
    /**
     * delete请求
     *
     * @param url 请求路径，带参数
     *
     * @return
     *
     * @throws Exception
     */
    public static HttpResult delete(String url) throws Exception {
        return delete(url, HttpConfig.defaultConfig(url));
    }
    
    /**
     * delete请求
     *
     * @param url    请求路径，带参数
     * @param config 请求配置
     *
     * @return
     *
     * @throws Exception
     */
    public static HttpResult delete(String url, HttpConfig config) throws Exception {
        return new DeleteRequest(url).send(config);
    }
    
    /**
     * head请求
     *
     * @param url 请求路径，带参数
     *
     * @return
     *
     * @throws Exception
     */
    public static HttpResult head(String url) throws Exception {
        return head(url, HttpConfig.defaultConfig(url));
    }
    
    /**
     * head请求
     *
     * @param url    请求路径，带参数
     * @param config 请求配置
     *
     * @return
     *
     * @throws Exception
     */
    public static HttpResult head(String url, HttpConfig config) throws Exception {
        return new HeadRequest(url).send(config);
    }
    
    /**
     * trace请求
     *
     * @param url 请求路径，带参数
     *
     * @return
     *
     * @throws Exception
     */
    public static HttpResult trace(String url) throws Exception {
        return trace(url, HttpConfig.defaultConfig(url));
    }
    
    /**
     * trace请求
     *
     * @param url    请求路径，带参数
     * @param config 请求配置
     *
     * @return
     *
     * @throws Exception
     */
    public static HttpResult trace(String url, HttpConfig config) throws Exception {
        return new TraceRequest(url).send(config);
    }
    
    /**
     * options请求
     *
     * @param url 请求路径，带参数
     *
     * @return
     *
     * @throws Exception
     */
    public static HttpResult options(String url) throws Exception {
        return options(url, HttpConfig.defaultConfig(url));
    }
    
    /**
     * option请求
     *
     * @param url    请求路径，带参数
     * @param config 请求配置
     *
     * @return
     *
     * @throws Exception
     */
    public static HttpResult options(String url, HttpConfig config) throws Exception {
        return new OptionsRequest(url).send(config);
    }
    
    /**
     * 上传文件
     *
     * @param url   请求路径
     * @param param 请求参数
     *
     * @return
     *
     * @throws Exception
     */
    public static HttpResult upload(String url, HttpParam param) throws Exception {
        return upload(url, param, HttpEntityMethod.POST);
    }
    
    /**
     * 上传文件
     *
     * @param url    请求路径
     * @param param  请求参数
     * @param method 请求方法
     *
     * @return
     *
     * @throws Exception
     */
    public static HttpResult upload(String url, HttpParam param, HttpEntityMethod method) throws Exception {
        return upload(url, param, method, HttpConfig.formDataConfig(url));
    }
    
    /**
     * 上传文件
     *
     * @param url    请求路径
     * @param param  请求参数
     * @param method 请求方法
     * @param config 请求配置
     *
     * @return
     *
     * @throws Exception
     */
    public static HttpResult upload(String url, HttpParam param, HttpEntityMethod method, HttpConfig config) throws Exception {
        return new FileUploadRequest(url, param, method).send(config);
    }
    
    /**
     * 下载文件
     *
     * @param url   目标路径
     * @param param 请求参数，默认Content-Type=application/json
     *
     * @return
     *
     * @throws Exception
     */
    public static HttpResult download(String url, HttpParam param) throws Exception {
        return download(url, param, HttpMethod.POST, HttpConfig.jsonConfig(url));
    }
    
    /**
     * 下载文件
     *
     * @param url    目标路径
     * @param param  请求参数（put,post,patch可携带entity，其它请求要写在URL里）
     * @param method 请求方法
     * @param config 请求配置（可配置param的Content-Type）
     *
     * @return
     *
     * @throws Exception
     */
    public static HttpResult download(String url, HttpParam param, HttpMethod method, HttpConfig config) throws Exception {
        switch (method) {
            case PUT:
                return put(url, param, config);
            case POST:
                return post(url, param, config);
            case PATCH:
                return patch(url, param, config);
            case GET:
                return get(url, config);
            case HEAD:
                return head(url, config);
            case TRACE:
                return trace(url, config);
            case DELETE:
                return trace(url, config);
            case OPTIONS:
                return options(url, config);
            default:
                return post(url, param, config);
        }
    }
    
    /**
     * 将字节数据转为File对象，可用于download后的数据转换
     *
     * @param data       文件字节数组数据
     * @param folderPath 存放文件的文件夹路径
     * @param filename   文件名（带后缀）
     *
     * @return
     *
     * @throws IOException
     */
    public static File bytesToFile(byte[] data, String folderPath, String filename) throws IOException {
        return Files.toFile(data, folderPath, filename);
    }
    
}
