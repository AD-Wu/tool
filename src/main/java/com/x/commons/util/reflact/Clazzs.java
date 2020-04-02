package com.x.commons.util.reflact;

import com.x.commons.util.bean.New;
import com.x.commons.util.collection.XArrays;
import com.x.commons.util.string.Strings;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

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
    private Clazzs() {}
    // ------------------------ 方法定义 ------------------------
    
    public static boolean isArray(Class<?> clazz) {
        return clazz != null && clazz.isArray();
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
    
    /**
     * 获取包下所有类(如果依赖jar包没有打包进来，会发生NoClassDefFoundError异常，表示运行时找不到该类，但编译时可以通过)
     *
     * @param packageName 包名，一般要二级包名，如：com.x
     *
     * @return
     *
     * @throws IOException
     */
    public static List<Class<?>> getClasses(String packageName) throws IOException {
        if (packageName == null) {packageName = "";}
        String pkgPath = packageName.replaceAll("\\.", "/");
        Enumeration<URL> urlEnums = LOADER.getResources(pkgPath);
        List<Class<?>> classes = New.list();
        if (urlEnums == null) {
            URL[] urls = ((URLClassLoader) Loader.get()).getURLs();
            for (URL url : urls) {
                String filePath = url.getPath();
                if (!filePath.endsWith("classes/")) {
                    String fullPath = filePath + "!/" + pkgPath;
                    String[] jarPkg = fullPath.split("!");
                    String jarPath = jarPkg[0].substring(jarPkg[0].indexOf("/"));
                    try (JarFile jar = new JarFile(jarPath)) {
                        analyzeJar(jar, pkgPath, classes);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            while (urlEnums.hasMoreElements()) {
                URL url = urlEnums.nextElement();
                String prtc = url.getProtocol();
                if ("file".equals(prtc)) {
                    String path = url.getPath();
                    File[] files = new File(path).listFiles(FILTER);
                    analyzeFile(packageName, files, classes);
                } else if ("jar".equals(prtc)) {
                    try {
                        JarURLConnection jarConn = (JarURLConnection) url.openConnection();
                        try (JarFile jar = jarConn.getJarFile();) {
                            analyzeJar(jar, pkgPath, classes);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    
                }
            }
        }
        return classes;
    }
    
    private static void analyzeFile(String pkgName, File[] files, List<Class<?>> classes) {
        if (XArrays.isEmpty(files)) {return;}
        for (File file : files) {
            String name = file.getName();
            if (file.isFile()) {
                int last = name.lastIndexOf(".");
                if (last > 0) {
                    name = pkgName + "." + name.substring(0, last);
                    try {
                        classes.add(Class.forName(name));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                String nextPkg = Strings.isNotNull(pkgName) ? pkgName + "." + name : name;
                File[] childFiles = new File(file.getPath()).listFiles(FILTER);
                analyzeFile(nextPkg, childFiles, classes);
            }
        }
    }
    
    private static void analyzeJar(JarFile jarFile, String pkgPath, List<Class<?>> classes) {
        Enumeration<JarEntry> files = jarFile.entries();
        // 便利jar包里的所有元素（jar包其实是压缩包）
        while (files.hasMoreElements()) {
            /*
             获取jar元素，如：
             - javax/
             - javax/crypto/
             - javax/crypto/interfaces/
             - javax/crypto/interfaces/DHKey.class
            */
            JarEntry file = files.nextElement();
            if (!file.isDirectory()) {
                String name = file.getName();
                if (name != null && name.length() >= 8) {
                    if (name.charAt(0) == '/') {
                        name = name.substring(1);
                    }
                    if (name.startsWith(pkgPath) && name.endsWith(".class")) {
                        name = name.replaceAll("/", ".");
                        name = name.substring(0, name.length() - 6);
                        try {
                            Class<?> clazz = Class.forName(name);
                            classes.add(clazz);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
    
}
