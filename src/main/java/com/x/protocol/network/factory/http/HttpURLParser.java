package com.x.protocol.network.factory.http;

import com.x.commons.enums.Charset;
import com.x.commons.util.bean.New;
import com.x.commons.util.collection.XArrays;
import com.x.commons.util.string.Strings;

import java.util.List;
import java.util.Map;

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
    
    public Map<String,List<String>> getParamString(String param){
        Map<String,List<String>> map = New.map();
        if(!Strings.isNull(param)){
            String[] split = param.split("[\\&]");
            if(!XArrays.isEmpty(split)){
                for (String s : split) {
                
                }
            }
        }
        return null;
    }
    // ------------------------ 私有方法 ------------------------
    
}
