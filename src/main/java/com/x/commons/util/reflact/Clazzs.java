package com.x.commons.util.reflact;

import com.x.commons.util.bean.New;
import com.x.commons.util.string.Strings;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarFile;

import static java.util.stream.Collectors.toList;

/**
 * @Date 2018-12-18 23:49
 * @Author AD
 */
public final class Clazzs {
    // ------------------------ 变量定义 ------------------------
    
    /**
     * 类加载器
     */
    private static final ClassLoader LOADER = Clazzs.class.getClassLoader();
    
    /**
     * class文件过滤器
     */
    public static final FileFilter FILTER = new FileFilter() {
        
        @Override
        public boolean accept(File file) {
            return !file.isFile() || file.getName().endsWith(".class");
        }
    };
    
    // ------------------------ 构造方法 ------------------------
    private Clazzs() {
    }
    // ------------------------ 方法定义 ------------------------
    
    public static ClassLoader getLoader() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        return loader == null ? LOADER : loader;
    }
    
    public static <T> T newInstance(Class<T> clazz) throws Exception {
        Constructor<T> c = clazz.getDeclaredConstructor();
        c.setAccessible(true);
        return c.newInstance();
    }
    
    public static <T> T newInstance(String className, Class<T> returnType) throws Exception {
        Class<?> clazz = Class.forName(className);
        if (clazz.equals(returnType) || returnType.isAssignableFrom(clazz)) {
            return (T) newInstance(clazz);
        }
        return null;
    }
    
    public static Class getClazz(String className, Class<?> defaultClass) {
        if (!Strings.isNull(className)) {
            try {
                return Class.forName(className);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return defaultClass;
    }
    
    public static boolean isPrimitive(Class<?> clazz) {
        return clazz != null && clazz.isPrimitive();
    }
    
    public static boolean isString(Class<?> clazz) {
        return String.class == clazz;
    }
    
    public static boolean isObject(Class<?> clazz) {
        return !isPrimitive(clazz) && !isString(clazz) && !isArray(clazz);
    }
    
    public static boolean isPrimitiveArray(Class<?> clazz) {
        return isArray(clazz) && isPrimitive(clazz.getComponentType());
    }
    
    public static boolean isObjectArray(Class<?> clazz) {
        return isArray(clazz) && !isPrimitive(clazz.getComponentType());
    }
    
    public static boolean isArray(Class<?> clazz) {
        return clazz != null && clazz.isArray();
    }
    
    public static List<Class<?>> getClasses(String packageName) throws IOException {
        if (packageName == null) {
            packageName = "";
        }
        String pkgPath = packageName.replaceAll("\\.", "/");
        Enumeration<URL> urlEnums = LOADER.getResources(pkgPath);
        if (urlEnums == null) {
            URL[] urls = ((URLClassLoader) Loader.get()).getURLs();
            for (URL url : urls) {
                String path = url.getPath();
                System.out.println(path);
            }
        } else {
            while (urlEnums.hasMoreElements()) {
                URL url = urlEnums.nextElement();
                String prtc = url.getProtocol();
                if ("file".equals(prtc)) {
                    System.out.println("file:" + url.getPath());
                } else if ("jar".equals(prtc)) {
                    JarURLConnection urlConn = (JarURLConnection) url.openConnection();
                    try (JarFile jar = urlConn.getJarFile()) {
                        System.out.println("jar:" + jar.getName());
                    }
                }
            }
        }
        return null;
    }
    
    /**
     * 获取包下带某个注解的类
     *
     * @param packageName 包名
     * @param annotation  注解类
     */
    private static <T extends Annotation> List<Class<?>> getClass(
            String packageName,
            Class<T> annotation) {
        return getClass(packageName, New.list())
                .stream()
                .filter(c -> c.getDeclaredAnnotation(annotation) != null)
                .collect(toList());
    }
    
    private static <A extends Annotation, I> List<Class<I>> getClass(
            String packageName,
            Class<A> annotation,
            Class<I> interfaceClass) {
        List<Class<?>> collect = null;
        try {
            collect = getClass(packageName, New.list());
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Class<I>> result = collect.stream()
                .filter(c -> c.getDeclaredAnnotation(annotation) != null &&
                             interfaceClass.isAssignableFrom(c))
                .map(c -> (Class<I>) c).collect(toList());
        return result;
    }
    
    /**
     * 获取某个包下的所有类
     *
     * @param packageName 包名
     * @param list        容器
     */
    private static List<Class<?>> getClass(String packageName, List<Class<?>> list) {
        File[] files = null;
        for (File f : files) {
            if (f.isDirectory()) { getClass(getPath(packageName, f), list); }
            if (f.getName().endsWith(".class")) {
                try {
                    list.add(LOADER.loadClass(getClassName(packageName, f)));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }
    
    // ------------------------ 私有方法 ------------------------
    
    private static String getPath(String packageName, File file) {
        return packageName + "." + file.getName();
    }
    
    private static String getClassName(String packageName, File file) {
        return getPath(packageName, file).replace(".class", "");
    }
    
}
