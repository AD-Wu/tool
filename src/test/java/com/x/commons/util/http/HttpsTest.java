package com.x.commons.util.http;

import com.x.commons.enums.Charset;
import com.x.commons.util.file.Files;
import com.x.commons.util.http.data.HttpParam;
import com.x.commons.util.http.data.HttpResult;
import com.x.commons.util.http.data.Json;
import com.x.commons.util.http.data.ValueType;
import com.x.commons.util.http.factory.HttpConfig;
import com.x.commons.util.string.Strings;
import org.junit.jupiter.api.Test;

import java.io.File;

/**
 * @Desc TODO
 * @Date 2020-01-05 12:53
 * @Author AD
 */
class HttpsTest {

    @Test
    void get() throws Exception {
        String url = "http://localhost:8080/get";
        HttpParam param = new HttpParam();
        param.add("user", "阳光").add("age", 12);
        HttpResult resp = Https.get(url);
        byte[] result = resp.getResult();
        String s = new String(result, Charset.UTF8);
        System.out.println(resp);
        System.out.println(s);
    }

    @Test
    void post() throws Exception {
        String url = "http://localhost:8080/post/json";
        HttpParam param = new HttpParam();
        param.add("user", "ad").add("pwd", "123").add("age", 12);
        HttpResult resp = Https.post(url, param);
        byte[] result = resp.getResult();
        String s = new String(result, Charset.UTF8);
        System.out.println(resp);
        System.out.println(s);
    }
    
    @Test
    void postForm() throws Exception {
        String url = "http://localhost:8080/post/form";
        HttpParam param = new HttpParam();
        param.add("name", "ad").add("age", 1).add("sex", true);
        HttpResult resp = Https.post(url, param, HttpConfig.formConfig(url));
        byte[] result = resp.getResult();
        String s = new String(result, Charset.UTF8);
        System.out.println(resp);
        System.out.println(s);
    }

    @Test
    void put() throws Exception {
        String url = "http://localhost:8080/put";
        HttpParam param = new HttpParam();
        param.add("user", "ad").add("pwd", "123").add("age", 12);
        HttpResult resp = Https.put(url, param);
        byte[] result = resp.getResult();
        String s = new String(result, Charset.UTF8);
        System.out.println(resp);
        System.out.println(s);
    }

    @Test
    void delete() throws Exception {
        String url = "http://localhost:8080/delete";
        HttpParam param = new HttpParam();
        param.add("user", "阳光").add("age", 12);
        HttpResult resp = Https.delete(url);
        byte[] result = resp.getResult();
        String s = new String(result, Charset.UTF8);
        System.out.println(resp);
        System.out.println(s);
    }

    @Test
    void upload() throws Exception {
        String url = "http://localhost:8080/upload";
        Json put = new Json().put("a", "Sunday").put("b", 1);
        HttpParam param = new HttpParam();
        // param.add(ValueType.FILE_PATH, "parser.dtd", "x-framework/parser.dtd");
        // param.add(ValueType.FILE_PATH, "x.yml", "x-framework/x.yml");
        // param.add(ValueType.FILE_PATH, "parse.rar", "x-framework/parse.rar");
        // param.add(ValueType.FILE_PATH, "IDEA激活码.rar", "x-framework/IDEA激活码.rar");
        // param.add(ValueType.FILE_PATH, "bg.jpg", "x-framework/bg.jpg");
        // param.add(ValueType.FILE_PATH, "tool-1.0.jar", "x-framework/tool-1.0.jar");
        param.add(ValueType.FILE_PATH, "logback.xml","logback.xml");
        param.add("a", "Sunday").add( "b", 1);
        HttpResult upload = Https.upload(url, param);
        System.out.println(upload);
        String charset = upload.getCharset();
        charset = Strings.isNull(charset) ? Charset.UTF8 : charset;
        String s = new String(upload.getResult(), charset);
        System.out.println(s);
    }
    
    @Test
    void download() throws Exception{
        String url = "http://localhost:8080/download";
        HttpResult download = Https.download(url, null);
        File file = Https.bytesToFile(download.getResult(), Files.getResourcesPath(), "img.jpg");
        System.out.println(file.getName());
        System.out.println(file.getPath());
    }
    
    /**
     * /Users/sunday/Work/tool/src/main/resources/
     * /Users/sunday/Work/tool/target/classes/
     * /Users/sunday/Work/tool/
     * /Users/sunday/Work/tool/target/classes/com/x/commons/util/http/
     * /Users/sunday/Work/tool/resources/upload
     */
    @Test
    void path(){
        String resourcesPath = Files.getResourcesPath();
        String rootPath = Files.getRootPath();
        String appPath = Files.getAppPath();
        String[] jarsPath = Files.getJarsPath();
        String currentClassPath = Files.getCurrentClassPath(Https.class);
        String localPath = Files.getLocalPath("resources/upload", false);
        System.out.println("resourcesPath=" + resourcesPath);
        System.out.println("rootPath=" + rootPath);
        System.out.println("appPath=" + appPath);
        System.out.println("currentClassPath=" + currentClassPath);
        System.out.println("localPath=" + localPath);
    }


}