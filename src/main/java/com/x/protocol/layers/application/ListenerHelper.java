package com.x.protocol.layers.application;

import com.x.commons.util.bean.New;
import com.x.commons.util.collection.XArrays;
import com.x.commons.util.reflact.Clazzs;
import com.x.commons.util.string.Strings;
import com.x.protocol.core.IProtocolListener;
import com.x.protocol.layers.application.config.ListenerConfig;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

/**
 * @Desc
 * @Date 2020-03-08 19:54
 * @Author AD
 */
public final class ListenerHelper {
    
    // ------------------------ 构造方法 ------------------------
    
    private ListenerHelper() {}
    
    // ------------------------ 方法定义 ------------------------
    public static List<Class<?>> getListeners(ListenerConfig config) throws Exception {
        String pkgName = Strings.fixNull(config.getPkgName());
        String base = config.getBase();
        Class listener = Clazzs.getClazz(base, IProtocolListener.class);
        List<Class<?>> container = New.list();
        ClassLoader loader = Clazzs.getLoader();
        Enumeration<URL> resources = loader.getResources(pkgName.replace("\\.", "/"));
        while (resources.hasMoreElements()) {
            URL url = resources.nextElement();
            if (url != null) {
                getPackageClass(pkgName, filterClassFile(url.getPath()), listener, container);
            }
        }
        return container;
    }
    
    // ------------------------ 私有方法 ------------------------
    private static void getPackageClass(String pkgName, File[] files, Class<?> baseClass, List<Class<?>> container) {
        if (!XArrays.isEmpty(files)) {
            Arrays.stream(files).forEach(file -> {
                String name = file.getName();
                if (file.isFile()) {
                    // 判断是不是子类
                    analyzeClass(getClassName(pkgName, name), baseClass, container);
                } else {
                    // 获取下一级目录文件
                    String nextPath = pkgName.length() > 0 ? pkgName + "." + name : name;
                    getPackageClass(nextPath, filterClassFile(file.getPath()), baseClass, container);
                }
            });
        }
    }
    
    private static void analyzeClass(String className, Class<?> baseClass, List<Class<?>> container) {
        try {
            Class<?> subClass = Class.forName(className);
            if (baseClass != subClass && baseClass.isAssignableFrom(subClass)) {
                if (subClass.getConstructors().length == 0) {
                    return;
                }
                container.add(subClass);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static String getClassName(String pkgName, String className) {
        if (Strings.isNull(className)) {
            return null;
        } else {
            int end = className.indexOf(".");
            return end <= 0 ? null : pkgName + "." + className.substring(0, end);
        }
    }
    
    private static File[] filterClassFile(String path) {
        if (Strings.isNull(path)) {
            return XArrays.EMPTY_FILE;
        } else {
            return new File(path).listFiles(Clazzs.FILTER);
        }
    }
    
}
