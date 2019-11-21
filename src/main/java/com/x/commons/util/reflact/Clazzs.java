package com.x.commons.util.reflact;

import com.x.commons.util.bean.New;
import com.x.commons.util.file.Files;
import lombok.SneakyThrows;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @Date 2018-12-18 23:49
 * @Author AD
 */
public final class Clazzs {


    /**
     * 类加载器
     */
    private static final ClassLoader LOADER = Clazzs.class.getClassLoader();

    public static <T> T newObj(Class<T> clazz) throws Exception {
        return clazz.newInstance();
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
    public static <T extends Annotation> List<Class<?>> getClassBy(String packageName, Class<T> annotation) {
        return getClass(packageName, New.list())
                .stream()
                .filter(c -> c.getDeclaredAnnotation(annotation) != null)
                .collect(toList());
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
            if (f.isDirectory())
                getClass(getPath(packageName, f), list);
            if (f.getName().endsWith(".class"))
                list.add(LOADER.loadClass(getClassName(packageName, f)));
        }
        return list;
    }

    // ------------------------- 私有辅助方法 -------------------------

    private static String getPath(String packageName, File file) {
        return packageName + "." + file.getName();
    }
    private static String getClassName(String packageName, File file) {
        return getPath(packageName, file).replace(".class", "");
    }

}
