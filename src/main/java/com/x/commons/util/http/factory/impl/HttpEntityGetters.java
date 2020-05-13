package com.x.commons.util.http.factory.impl;

import com.x.commons.util.bean.New;
import com.x.commons.util.http.factory.IHttpEntityGetter;
import com.x.commons.util.string.Strings;

import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * @Desc：
 * @Author：AD
 * @Date：2020/5/13 17:43
 */
public final class HttpEntityGetters {

    private static final Map<String, IHttpEntityGetter> GETTERS = New.concurrentMap();

    private static volatile boolean inited = false;

    private HttpEntityGetters() {}

    public static IHttpEntityGetter get(String contentType) {
        if (!inited) {
            synchronized (HttpEntityGetters.class) {
                if (!inited) {
                    init();
                    inited = true;
                }
            }
        }
        return GETTERS.get(contentType);
    }

    private static void init() {
        Iterator<IHttpEntityGetter> it = ServiceLoader.load(IHttpEntityGetter.class).iterator();
        while (it.hasNext()) {
            IHttpEntityGetter next = it.next();
            if (!Strings.isNull(next.getContentType())) {
                GETTERS.put(next.getContentType(), next);
            }
        }
    }
}
