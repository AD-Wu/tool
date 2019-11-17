package com.x.commons.util.reflact;

import java.io.InputStream;

/**
 * @Desc TODO
 * @Date 2019-10-22 22:54
 * @Author AD
 */
public class Loader {

    public static ClassLoader get() {
        return Thread.currentThread().getContextClassLoader();
    }

    public static InputStream getStream(String path) {
        return get().getResourceAsStream(path);
    }

}
