package com.x.commons.util.http.factory;

import com.x.commons.util.bean.New;
import com.x.commons.util.string.Strings;

import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * @Desc HttpEntity管理者
 * @Date 2020-02-13 23:15
 * @Author AD
 */
public final class HttpEntitys {
    
    // ------------------------ 变量定义 ------------------------
    
    private static final Map<String, IHttpEntityGetter> getters = New.concurrentMap();
    
    // ------------------------ 构造方法 ------------------------
    
    private HttpEntitys() {}
    
    // ------------------------ 方法定义 ------------------------
    
    public static IHttpEntityGetter getEntityGetter(String httpContentType) {
        return getters.get(httpContentType);
    }
    
    // ------------------------ 私有方法 ------------------------
    
    private static void initCustom() {
        ServiceLoader<IHttpEntityGetter> load = ServiceLoader.load(IHttpEntityGetter.class);
        Iterator<IHttpEntityGetter> it = load.iterator();
        while (it.hasNext()) {
            IHttpEntityGetter next = it.next();
            String contentType = next.getContentType();
            if (!Strings.isNull(contentType)) {
                getters.put(next.getContentType(), next);
            }
        }
    }
    
    static {
        initCustom();
    }
    
}
