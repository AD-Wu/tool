package com.x.protocol.network.factory.http.utils;

import com.x.commons.enums.Charset;
import com.x.commons.util.bean.New;
import com.x.commons.util.collection.XArrays;
import com.x.commons.util.string.Strings;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

/**
 * @Desc TODO
 * @Date 2020-03-04 22:30
 * @Author AD
 */
public final class HttpURLParser {
    
    // ------------------------ 变量定义 ------------------------
    
    /**
     * 原始URL
     */
    private String originalURL;
    
    /**
     * 基本URL
     */
    private String baseURL;
    
    /**
     * 请求路径
     */
    private String requestURL;
    
    /**
     * host
     */
    private String host;
    
    /**
     * 端口
     */
    private int port = 80;
    
    /**
     * URL参数
     */
    private Map<String, List<String>> params = New.map();
    
    /**
     * 标识符
     */
    private String reference;
    
    /**
     * 字符编码
     */
    private String charset = Charset.UTF8;
    
    // ------------------------ 构造方法 ------------------------
    
    public HttpURLParser() {}
    
    // ------------------------ 方法定义 ------------------------
    
    public Map<String, List<String>> parseParamString(String param) {
        Map<String, List<String>> map = New.map();
        if (!Strings.isNull(param)) {
            String[] pairs = param.split("[\\&]");
            if (!XArrays.isEmpty(pairs)) {
                for (String pair : pairs) {
                    String[] kv = pair.split("[\\=]");
                    if (kv != null && kv.length > 1) {
                        String k = kv[0];
                        String v = "";
                        for (int i = 1; i < kv.length; i++) {
                            if (i > 1) {
                                v = v + "=";
                            }
                            v = v + kv[i];
                        }
                        try {
                            URLDecoder.decode(v, this.charset);
                        } catch (UnsupportedEncodingException e) {
                        }
                        List<String> vs = map.get(k);
                        if (vs == null) {
                            vs = New.list();
                            map.put(k, vs);
                        }
                        vs.add(v);
                    }
                }
            }
        }
        return map;
    }
    
    public void parse(String originalURL, String charset) {
        this.originalURL = originalURL;
        this.charset = charset;
        this.reset();
        if (!Strings.isNull(originalURL)) {
            String params = "";
            String reference = "";
            String baseURL = originalURL;
            int pos = originalURL.indexOf("?");
            int referPos;
            if (pos != -1) {
                baseURL = originalURL.substring(0, pos);
                params = originalURL.substring(pos + 1);
                referPos = params.indexOf("#");
                if (referPos != -1) {
                    reference = params.substring(referPos + 1);
                    params = params.substring(0, referPos);
                    
                }
            } else {
                referPos = originalURL.indexOf("#");
                if (referPos != -1) {
                    baseURL = originalURL.substring(0, referPos);
                    reference = originalURL.substring(referPos + 1);
                }
            }
            this.baseURL = baseURL;
            this.reference = reference;
            this.params = this.parseParamString(params);
            
            try {
                int split = baseURL.indexOf(":");
                if (split == -1) {
                    return;
                }
                URL url = new URL("http" + baseURL.substring(split));
                this.host = url.getHost();
                this.port = url.getPort();
                this.requestURL = url.getPath();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public void reset() {
        this.baseURL = "";
        this.charset = Charset.UTF8;
        this.host = "";
        this.port = 80;
        this.originalURL = "";
        this.reference = "";
        this.requestURL = "";
        this.params.clear();
        
    }
    
    public void clearParameters() {
        this.params.clear();
    }
    
    public void addParameter(String key, Object value) {
        if (key != null && key.length() != 0) {
            if (value == null) {
                value = "";
            }
            List<String> values = this.params.get(key);
            if (values == null) {
                values = New.list();
                this.params.put(key, values);
            }
            values.add(value.toString());
        }
    }
    
    public String getParameter(String key) {
        if (!Strings.isNull(key)) {
            List<String> values = this.params.get(key);
            return values == null ? null : values.get(0);
        } else {
            return null;
        }
    }
    
    public String[] getParameters(String key) {
        if (!Strings.isNull(key)) {
            List<String> values = this.params.get(key);
            return values == null ? null : values.toArray(XArrays.EMPTY_STRING);
        } else {
            return XArrays.EMPTY_STRING;
        }
    }
    
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> result = New.map();
        Iterator<Map.Entry<String, List<String>>> it = this.params.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, List<String>> next = it.next();
            String key = next.getKey();
            List<String> values = next.getValue();
            result.put(key, values.toArray(XArrays.EMPTY_STRING));
        }
        return result;
    }
    
    public Enumeration<String> getParameterNames() {
        return Collections.enumeration(this.params.keySet());
    }
    
    public String getQueryString() {
        String kvs = "";
        Iterator<Map.Entry<String, List<String>>> it = this.params.entrySet().iterator();

label37:
        while (it.hasNext()) {
            Map.Entry<String, List<String>> next = it.next();
            String key = next.getKey();
            List<String> values = next.getValue();
            Iterator<String> valueIt = values.iterator();
            
            while (true) {
                while (true) {
                    if (!valueIt.hasNext()) {
                        continue label37;
                    }
                    
                    String value = valueIt.next();
                    if (kvs.length() > 0) {
                        kvs = kvs + "&";
                    }
                    
                    if (!Strings.isNull(value)) {
                        try {
                            kvs = kvs + key + "=" + URLEncoder.encode(value, this.charset);
                        } catch (UnsupportedEncodingException var9) {
                            kvs = kvs + key + "=" + value;
                        }
                    } else {
                        kvs = kvs + key + "=";
                    }
                }
            }
        }
        
        return kvs;
    }
    
    @Override
    public String toString() {
        String baseURL = this.baseURL;
        if (this.params.size() > 0) {
            baseURL = baseURL + "?" + this.getQueryString();
        }
        
        if (this.reference != null && this.reference.length() > 0) {
            baseURL = baseURL + "#" + this.reference;
        }
        
        return baseURL;
    }
    
    public static HttpURLParser parseUrl(String originalURL, String charset) {
        HttpURLParser parser = new HttpURLParser();
        parser.parse(originalURL, charset);
        return parser;
    }
    
    public String getURL(){
        return this.toString();
    }
    // ------------------------ get and set ------------------------
    
    /**
     * 获取原始URL
     *
     * @return String 原始URL
     */
    public String getOriginalURL() {
        return this.originalURL;
    }
    
    /**
     * 设置原始URL
     *
     * @param originalURL 原始URL
     */
    public void setOriginalURL(String originalURL) {
        this.originalURL = originalURL;
    }
    
    /**
     * 获取基本URL
     *
     * @return String 基本URL
     */
    public String getBaseURL() {
        return this.baseURL;
    }
    
    /**
     * 设置基本URL
     *
     * @param baseURL 基本URL
     */
    public void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
    }
    
    /**
     * 获取请求路径
     *
     * @return String 请求路径
     */
    public String getRequestURL() {
        return this.requestURL;
    }
    
    /**
     * 设置请求路径
     *
     * @param requestURL 请求路径
     */
    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }
    
    /**
     * 获取host
     *
     * @return String host
     */
    public String getHost() {
        return this.host;
    }
    
    /**
     * 设置host
     *
     * @param host host
     */
    public void setHost(String host) {
        this.host = host;
    }
    
    /**
     * 获取端口
     *
     * @return int 端口
     */
    public int getPort() {
        return this.port;
    }
    
    /**
     * 设置端口
     *
     * @param port 端口
     */
    public void setPort(int port) {
        this.port = port;
    }
    
    /**
     * 获取URL参数
     *
     * @return Map<String       ,               List       <       String>> URL参数
     */
    public Map<String, List<String>> getParams() {
        return this.params;
    }
    
    /**
     * 设置URL参数
     *
     * @param params URL参数
     */
    public void setParams(Map<String, List<String>> params) {
        this.params = params;
    }
    
    /**
     * 获取标识符
     *
     * @return String 标识符
     */
    public String getReference() {
        return this.reference;
    }
    
    /**
     * 设置标识符
     *
     * @param reference 标识符
     */
    public void setReference(String reference) {
        this.reference = reference;
    }
    
    /**
     * 获取字符编码
     *
     * @return String 字符编码
     */
    public String getCharset() {
        return this.charset;
    }
    
    /**
     * 设置字符编码
     *
     * @param charset 字符编码
     */
    public void setCharset(String charset) {
        this.charset = charset;
    }
    
}
