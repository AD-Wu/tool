package com.x.commons.util;

import com.ax.commons.collection.KeyValue;
import com.ax.commons.collection.NameValue;
import com.ax.commons.collection.Where;
import com.ax.framework.protocol.actor.models.CountResult;
import com.ax.framework.protocol.actor.models.PageRequest;
import com.ax.framework.protocol.actor.models.UpdateRequest;
import com.ax.framework.protocol.actor.models.WhereRequest;
import com.ax.protocol.annotations.AxActor;
import com.ax.protocol.annotations.AxAttribute;
import com.ax.protocol.annotations.AxData;
import com.ax.protocol.core.DataConfig;
import com.ax.protocol.core.Property;
import com.ax.protocol.layers.application.AnnotationInfo;
import com.x.commons.local.Locals;
import com.x.commons.util.bean.New;
import com.x.commons.util.collection.XArrays;
import com.x.commons.util.string.Strings;

import java.io.File;
import java.io.FileFilter;
import java.io.Serializable;
import java.lang.reflect.*;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @Desc TODO
 * @Date 2019-11-29 00:47
 * @Author AD
 */
public final class Annotations {
    private static final FileFilter fileFilter = new FileFilter() {
        public boolean accept(File file) {
            return file.isFile() ? file.getName().endsWith(".class") : true;
        }
    };

    public Annotations() {
    }

    public static void main(String[] args) throws Exception {
        String className = Annotations.class.getName();
        String pack = "com.x";
        getAnnotationInfo(className, pack, false);
    }

    public static AnnotationInfo getAnnotationInfo(String className, String packageName, boolean var2) throws Exception {
        if (packageName == null) {
            packageName = "";
        }

        Class<?> clazz = !Strings.isNull(className) ? Class.forName(className) : null;
        List var4 = new ArrayList();
        List<Class<?>> actors = New.list();
        List<Class<?>> datas = New.list();
        List var7 = new ArrayList();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        String packagePath = packageName.replaceAll("\\.", "/");
        Enumeration<URL> urlEnums = loader.getResources(packagePath);
        if (urlEnums == null) {
            URL[] urls = ((URLClassLoader) loader).getURLs();

            for (int i = 0; i < urls.length; ++i) {
                URL url = urls[i];
                String path = url.getPath();
                if (!path.endsWith("classes/")) {
                    String var50 = path + "!/" + packagePath;
                    String[] var51 = var50.split("!");
                    String var17 = var51[0].substring(var51[0].indexOf("/"));
                    JarFile jarFile = null;

                    try {
                        jarFile = new JarFile(var17);
                        Enumeration<JarEntry> jarEntries = jarFile.entries();

                        while (jarEntries.hasMoreElements()) {
                            JarEntry jarEntry = jarEntries.nextElement();
                            if (!jarEntry.isDirectory()) {
                                String jarName = jarEntry.getName();
                                if (jarName != null && jarName.length() >= 8) {
                                    if (jarName.charAt(0) == '/') {
                                        jarName = jarName.substring(1);
                                    }

                                    if (jarName.startsWith(packagePath) && jarName.endsWith(
                                            ".class")) {
                                        jarName = jarName.replaceAll("/", ".");
                                        jarName = jarName.substring(0, jarName.length() - 6);
                                        analyzeClass(jarName, clazz, var4, datas, var7, actors);
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        throw e;
                    } finally {
                        try {
                            if (jarFile != null) {
                                jarFile.close();
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        } else {
            label502:
            while (true) {
                while (true) {
                    URL url;
                    do {
                        if (!urlEnums.hasMoreElements()) {
                            break label502;
                        }

                        url = urlEnums.nextElement();
                    } while (url == null);

                    String protocol = url.getProtocol();
                    if ("file".equals(protocol)) {
                        getPackageClass(packageName, filterClassFiles(url.getPath()), clazz, var4,
                                        datas,
                                        var7, actors);
                    } else if ("jar".equals(protocol)) {
                        JarFile jarFile = null;

                        try {
                            URLConnection urlConn = url.openConnection();
                            jarFile = ((JarURLConnection) urlConn).getJarFile();
                            Enumeration<JarEntry> jarEntries = jarFile.entries();

                            while (jarEntries.hasMoreElements()) {
                                JarEntry jarEntry = jarEntries.nextElement();
                                if (!jarEntry.isDirectory()) {
                                    String jarName = jarEntry.getName();
                                    if (jarName != null && jarName.length() >= 8) {
                                        if (jarName.charAt(0) == '/') {
                                            jarName = jarName.substring(1);
                                        }

                                        if (jarName.startsWith(packagePath) && jarName.endsWith(
                                                ".class")) {
                                            jarName = jarName.replaceAll("/", ".");
                                            jarName = jarName.substring(0, jarName.length() - 6);
                                            analyzeClass(jarName, clazz, var4, datas, var7, actors);
                                        }
                                    }
                                }
                            }
                        } catch (Exception e) {
                            throw e;
                        } finally {
                            try {
                                if (jarFile != null) {
                                    jarFile.close();
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }

                        }
                    }
                }
            }
        }

        if (datas.indexOf(KeyValue.class) == -1) {
            datas.add(KeyValue.class);
        }

        if (datas.indexOf(NameValue.class) == -1) {
            datas.add(NameValue.class);
        }

        if (datas.indexOf(Where.class) == -1) {
            datas.add(Where.class);
        }

        if (datas.indexOf(CountResult.class) == -1) {
            datas.add(CountResult.class);
        }

        if (datas.indexOf(PageRequest.class) == -1) {
            datas.add(PageRequest.class);
        }

        if (datas.indexOf(UpdateRequest.class) == -1) {
            datas.add(UpdateRequest.class);
        }

        if (datas.indexOf(WhereRequest.class) == -1) {
            datas.add(WhereRequest.class);
        }

        Map<Class<?>, DataConfig> dataConfigMap = New.map();
        analyzeDatas(datas, dataConfigMap, var2);
        unionDatas(dataConfigMap);
        return new AnnotationInfo(var4, dataConfigMap, var7, actors);
    }

    public static void checkError(Class<?> dataClazz, Object annotation, String annotationDoc, String var3, boolean var4) throws Exception {
        if (var4) {
            if (var3 == null) {
                var3 = "";
            }

            if (var3.trim().length() < 1) {
                throw new Exception(
                        Locals.text("protocol.layer.annotation.err", annotationDoc,
                                    dataClazz.getName(),
                                    annotation.toString()));
            }
        }
    }

    private static void analyzeDatas(List<Class<?>> datas, Map<Class<?>, DataConfig> dataConfigMap, boolean var2) throws Exception {
        Iterator<Class<?>> it = datas.iterator();

        while (it.hasNext()) {
            Class<?> clazz = it.next();
            AxData axData = clazz.getAnnotation(AxData.class);
            checkError(clazz, axData, "doc", axData.doc(), var2);
            String version = axData.version();
            if (Strings.isNull(version)) {
                version = "1";
            }

            DataConfig dataConfig = new DataConfig();
            dataConfig.setDataClass(clazz);
            dataConfig.setDoc(axData.doc());
            dataConfig.setVersion(version);
            dataConfig.setCaching(axData.caching());
            dataConfig.setCachingHistory(axData.history());
            dataConfig.setTable(axData.table().trim());
            List<String> pks = New.list();
            List<Property> props = New.list();
            analyzeProperties(clazz, props, pks, var2);
            dataConfig.setPks((String[]) pks.toArray(new String[0]));
            dataConfig.setProperties(props);
            dataConfigMap.put(clazz, dataConfig);
        }

    }

    private static void analyzeProperties(Class<?> dataClazz, List<Property> props, List<String> pks, boolean var3) throws Exception {
        Class superclass = dataClazz.getSuperclass();
        if (superclass != null && superclass.isAnnotationPresent(AxData.class)) {
            analyzeProperties(superclass, props, pks, var3);
        }

        Field[] fields = dataClazz.getDeclaredFields();
        if (!XArrays.isEmpty(fields)) {
            for (int i = 0; i < fields.length; ++i) {
                Field field = fields[i];
                if (field.isAnnotationPresent(AxAttribute.class)) {
                    AxAttribute axAttribute = field.getAnnotation(AxAttribute.class);
                    checkError(dataClazz, axAttribute, "doc", axAttribute.doc(), var3);
                    Class<?> fieldType = field.getType();
                    Class dataClass = fieldType;
                    if (!fieldType.isPrimitive() && !fieldType.equals(Object.class)) {
                        if (fieldType.isAssignableFrom(List.class)) {
                            Type genericType = field.getGenericType();
                            if (genericType instanceof ParameterizedType) {
                                ParameterizedType paramType = (ParameterizedType) genericType;
                                Type[] actualTypes = paramType.getActualTypeArguments();
                                if (actualTypes != null && actualTypes.length == 1) {
                                    dataClass = (Class) actualTypes[0];
                                }
                            }
                        } else if (fieldType.isArray()) {
                            dataClass = fieldType.getComponentType();
                        }
                    }

                    field.setAccessible(true);
                    String fieldName = field.getName();
                    Property prop = new Property();
                    prop.setId(fieldName);
                    prop.setSid(axAttribute.sid());
                    prop.setMin(axAttribute.min());
                    prop.setMax(axAttribute.max());
                    prop.setLength(axAttribute.length());
                    prop.setLengthProp(axAttribute.lengthProp());
                    prop.setFormat(axAttribute.format());
                    prop.setPk(axAttribute.pk());
                    prop.setDoc(axAttribute.doc());
                    prop.setField(field);
                    prop.setType(fieldType);
                    prop.setDataClass(dataClass);
                    if (axAttribute.pk() && pks.indexOf(fieldName) == -1) {
                        pks.add(fieldName);
                    }

                    props.add(prop);
                }
            }
        }

    }

    public static Class<?> getActualType(Class<?> clazz, Class<?> var1, Class<?> var2, Type var3) {
        if (var2.isPrimitive()) {
            return var2;
        } else {
            Class var4;
            if (var2.equals(Serializable.class)) {
                var4 = getGenericType(var3);
                if (var4 != null) {
                    return var4;
                }

                if (clazz.isInterface()) {
                    Type[] var5 = clazz.getGenericInterfaces();
                    if (var5 != null && var5.length > 0) {
                        var4 = getGenericType(var5[0]);
                        if (var4 != null) {
                            return var4;
                        }
                    }
                }

                var4 = getGenericType(var1.getGenericSuperclass());
                if (var4 != null) {
                    return var4;
                }
            } else if (var2.isArray()) {
                var4 = var2.getComponentType();
                if (var4.equals(Serializable.class)) {
                    Class var10 = getGenericType(var3);
                    if (var10 != null) {
                        return var10;
                    }

                    if (clazz.isInterface()) {
                        Type[] var6 = clazz.getGenericInterfaces();
                        if (var6 != null && var6.length > 0) {
                            var10 = getGenericType(var6[0]);
                            if (var10 != null) {
                                try {
                                    return Class.forName("[L" + var10.getName() + ";");
                                } catch (Exception var9) {
                                    var9.printStackTrace();
                                }
                            }
                        }
                    }

                    var10 = getGenericType(var1.getGenericSuperclass());
                    if (var10 != null) {
                        try {
                            return Class.forName("[L" + var10.getName() + ";");
                        } catch (Exception var8) {
                            var8.printStackTrace();
                        }
                    }
                }
            }

            return var2;
        }
    }

    private static Class<?> getGenericType(Type type) {
        if (type == null) {
            return null;
        } else if (type instanceof ParameterizedType) {
            return getGenericClass((ParameterizedType) type);
        } else {
            if (type instanceof GenericArrayType) {
                Class clazz = getGenericType(((GenericArrayType) type).getGenericComponentType());
                if (clazz == null) {
                    return null;
                }

                try {
                    return Class.forName("[L" + clazz.getName() + ";");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (type instanceof TypeVariable) {
                return getGenericType(((TypeVariable) type).getBounds()[0]);
            }

            return null;
        }
    }

    private static Class<?> getGenericClass(ParameterizedType paramType) {
        Type[] types = paramType.getActualTypeArguments();
        if (!XArrays.isEmpty(types)) {
            Type type = types[0];
            if (type instanceof ParameterizedType) {
                return (Class) ((ParameterizedType) type).getRawType();
            } else {
                return type instanceof TypeVariable ? getGenericType(
                        ((TypeVariable) type).getBounds()[0]) : (Class) type;
            }
        } else {
            return null;
        }
    }

    private static void unionDatas(Map<Class<?>, DataConfig> dataConfigMap) {
        Iterator<DataConfig> it = dataConfigMap.values().iterator();

        while (it.hasNext()) {
            DataConfig dataConfig = it.next();
            Property[] props = dataConfig.getProperties();
            int len = props.length;
            for (int i = 0; i < len; ++i) {
                Property prop = props[i];
                Class<?> clazz = prop.getDataClass();
                if (dataConfigMap.containsKey(clazz)) {
                    prop.setDataConfig(dataConfigMap.get(clazz));
                }
            }
        }

    }

    private static File[] filterClassFiles(String path) {
        return Strings.isNull(path) ? null : (new File(path)).listFiles(fileFilter);
    }

    private static String getClassName(String var0, String var1) {
        if (var1 == null) {
            return null;
        } else {
            int var2 = var1.lastIndexOf(".");
            return var2 <= 0 ? null : var0 + "." + var1.substring(0, var2);
        }
    }

    private static void analyzeClass(String className, Class<?> var1, List<Class<?>> var2, List<Class<?>> datas, List<Class<?>> var4, List<Class<?>> actors) {
        if (!Strings.isNull(className)) {
            Class<?> clazz = null;

            try {
                clazz = Class.forName(className);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (clazz != null) {
                if (clazz.isAnnotationPresent(AxData.class)) {
                    datas.add(clazz);
                } else {
                    if (clazz.isAnnotationPresent(AxActor.class)) {
                        if (clazz.isInterface()) {
                            actors.add(clazz);
                        } else if (var1 == null) {
                            var2.add(clazz);
                        } else if (!var1.equals(clazz) && var1.isAssignableFrom(clazz)) {
                            var2.add(clazz);
                        }
                    }

                    if (var1 == null) {
                        try {
                            if (clazz.getInterfaces().length == 0) {
                                return;
                            }

                            if (clazz.getConstructors().length == 0) {
                                return;
                            }

                            var4.add(clazz);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (!var1.equals(clazz) && var1.isAssignableFrom(clazz)) {
                        try {
                            if (clazz.getConstructors().length == 0) {
                                return;
                            }

                            var4.add(clazz);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        }
    }

    private static void getPackageClass(String packageName, File[] files, Class<?> var2, List<Class<?>> var3, List<Class<?>> var4, List<Class<?>> var5, List<Class<?>> var6) {
        if (!XArrays.isEmpty(files)) {

            for (int i = 0, L = files.length; i < L; ++i) {
                File file = files[i];
                String fileName = file.getName();
                if (file.isFile()) {
                    analyzeClass(getClassName(packageName, fileName), var2, var3, var4, var5, var6);
                } else {
                    String var12 = packageName.length() > 0 ? packageName + "." + fileName : fileName;
                    getPackageClass(var12, filterClassFiles(file.getPath()), var2, var3, var4,
                                    var5, var6);
                }
            }

        }
    }
}