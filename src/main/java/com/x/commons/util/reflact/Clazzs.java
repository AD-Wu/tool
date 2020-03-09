package com.x.commons.util.reflact;

import com.x.commons.util.bean.New;
import com.x.commons.util.file.Files;
import com.x.commons.util.string.Strings;
import lombok.SneakyThrows;

import java.io.File;
import java.io.FileFilter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.List;

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
            return file.isFile() ? file.getName().endsWith(".class") : true;
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
        return clazz == null ? false : clazz.isArray();
    }
    
    /**
     * 获取包下带某个注解的类
     *
     * @param packageName 包名
     * @param annotation  注解类
     *
     * @return
     *
     * @author AD
     * @date 2018-12-22 23:37
     */
    @SneakyThrows
    public static <T extends Annotation> List<Class<?>> getClass(
            String packageName,
            Class<T> annotation) {
        return getClass(packageName, New.list())
                .stream()
                .filter(c -> c.getDeclaredAnnotation(annotation) != null)
                .collect(toList());
    }
    
    public static <A extends Annotation, I> List<Class<I>> getClass(
            String packageName,
            Class<A> annotation,
            Class<I> interfaceClass) {
        List<Class<?>> collect = getClass(packageName, New.list());
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
     * @param list
     *
     * @return
     *
     * @author AD
     * @date 2018-12-22 23:36
     */
    @SneakyThrows
    public static List<Class<?>> getClass(String packageName, List<Class<?>> list) {
        for (File f : Files.getFiles(packageName)) {
            if (f.isDirectory()) { getClass(getPath(packageName, f), list); }
            if (f.getName().endsWith(".class")) {
                list.add(LOADER.loadClass(getClassName(packageName, f)));
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
