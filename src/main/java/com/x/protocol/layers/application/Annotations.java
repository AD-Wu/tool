package com.x.protocol.layers.application;

import com.ax.framework.protocol.actor.models.CountResult;
import com.ax.framework.protocol.actor.models.PageRequest;
import com.ax.framework.protocol.actor.models.UpdateRequest;
import com.ax.framework.protocol.actor.models.WhereRequest;
import com.x.commons.collection.KeyValue;
import com.x.commons.collection.NameValue;
import com.x.commons.collection.Where;
import com.x.commons.local.Locals;
import com.x.commons.util.bean.New;
import com.x.commons.util.collection.XArrays;
import com.x.commons.util.reflact.Clazzs;
import com.x.commons.util.reflact.Loader;
import com.x.commons.util.string.Strings;
import com.x.protocol.annotations.XActor;
import com.x.protocol.annotations.XData;
import com.x.protocol.annotations.XField;
import com.x.protocol.core.DataConfig;
import com.x.protocol.core.Property;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.*;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @Desc
 * @Date 2020-03-08 19:58
 * @Author AD
 */
public final class Annotations {

    // ------------------------ 变量定义 ------------------------

    // ------------------------ 构造方法 ------------------------
    private Annotations() {}
    // ------------------------ 方法定义 ------------------------

    public static void main(String[] args) throws Exception {
        String className = Annotations.class.getName();
        String pack = "com.x";
        getAnnotationInfo(className, pack, false);
    }

    public static AnnotationInfo getAnnotationInfo(String baseActor, String packageName, boolean disableDoc) throws Exception {
        if (packageName == null) packageName = "";
        Class<?> clazz = !Strings.isNull(baseActor) ? Class.forName(baseActor) : null;
        List<Class<?>> actors = New.list();
        List<Class<?>> interfaces = New.list();
        List<Class<?>> datas = New.list();
        List<Class<?>> readyActors = New.list();

        ClassLoader loader = Loader.get();
        String packagePath = packageName.replaceAll("\\.", "/");
        Enumeration<URL> urlEnums = loader.getResources(packagePath);
        if (urlEnums == null) {
            URL[] urls = ((URLClassLoader) loader).getURLs();
            for (URL url : urls) {
                String path = url.getPath();
                if (!path.endsWith("classes/")) {
                    String fullPath = path + "!/" + packagePath;
                    String[] jarPaths = fullPath.split("!");
                    String jarPath = jarPaths[0].substring(jarPaths[0].indexOf("/"));
                    try (JarFile jarFile = new JarFile(jarPath);) {
                        handleJarEntity(jarFile, packagePath, clazz, actors, datas, readyActors,
                                        interfaces);
                    } catch (Exception e) {
                        throw e;
                    }
                }
            }
        } else {
            out:
            while (true) {
                URL url;
                do {
                    if (!urlEnums.hasMoreElements()) break out;
                    url = urlEnums.nextElement();
                } while (url == null);
                String protocol = url.getProtocol();
                if ("file".equals(protocol)) {
                    getPackageClass(packageName, filterClassFiles(url.getPath()),
                                    clazz, actors, datas, readyActors, interfaces);
                } else if ("jar".equals(protocol)) {
                    JarURLConnection urlConn = (JarURLConnection) url.openConnection();

                    try (JarFile jarFile = urlConn.getJarFile();) {
                        handleJarEntity(jarFile, packagePath, clazz, actors, datas, readyActors,
                                        interfaces);
                    } catch (Exception e) {
                        throw e;
                    }
                }
            }
        }
        addDefaultAnnotation(datas);
        Map<Class<?>, DataConfig> dataConfigMap = New.map();
        analyzeDatas(datas, dataConfigMap, disableDoc);
        unionDatas(dataConfigMap);
        return new AnnotationInfo(actors, dataConfigMap, readyActors, interfaces);
    }

    public static void checkError(Class<?> dataClazz, Object xAnnotation, String docString,
                                  String docValue, boolean disableDoc)
            throws Exception {
        if (disableDoc) {
            if (Strings.isNull(docValue)) {
                throw new Exception(
                        Locals.text("protocol.layer.annotation.err", docString,
                                    dataClazz.getName(),
                                    xAnnotation.toString()));
            }
        }
    }

    private static void handleJarEntity(JarFile jarFile, String packagePath, Class<?> clazz, List<Class<?>> actors,
                                        List<Class<?>> datas, List<Class<?>> readyActors, List<Class<?>> interfaces) {
        Enumeration<JarEntry> jarEntries = jarFile.entries();

        while (jarEntries.hasMoreElements()) {
            JarEntry jarEntry = jarEntries.nextElement();
            if (!jarEntry.isDirectory()) {
                String jarName = jarEntry.getName();
                if (jarName != null && jarName.length() >= 8) {
                    if (jarName.charAt(0) == '/') {
                        jarName = jarName.substring(1);
                    }
                    if (jarName.startsWith(packagePath) && jarName.endsWith(".class")) {
                        jarName = jarName.replaceAll("/", ".");
                        jarName = jarName.substring(0, jarName.length() - 6);
                        analyzeClass(jarName, clazz, actors, datas, readyActors, interfaces);
                    }
                }
            }
        }
    }

    private static void addDefaultAnnotation(List<Class<?>> datas) {

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
    }

    private static void analyzeDatas(List<Class<?>> datas, Map<Class<?>, DataConfig> dataConfigMap, boolean disableDoc)
            throws Exception {
        Iterator<Class<?>> it = datas.iterator();

        while (it.hasNext()) {
            Class<?> clazz = it.next();
            XData xdata = clazz.getAnnotation(XData.class);
            checkError(clazz, xdata, "doc", xdata.doc(), disableDoc);
            String version = Strings.fixNull(xdata.version(), "1");

            DataConfig dataConfig = new DataConfig();
            dataConfig.setDataClass(clazz);
            dataConfig.setDoc(xdata.doc());
            dataConfig.setVersion(version);
            dataConfig.setCache(xdata.cache());
            dataConfig.setHistory(xdata.history());
            dataConfig.setTable(xdata.table().trim());
            List<String> pks = New.list();
            List<Property> props = New.list();
            analyzeProperties(clazz, props, pks, disableDoc);
            dataConfig.setPks(pks.toArray(new String[0]));
            dataConfig.setProperties(props);
            dataConfigMap.put(clazz, dataConfig);
        }

    }

    private static void analyzeProperties(Class<?> dataClazz, List<Property> props, List<String> pks, boolean checkNull)
            throws Exception {
        Class<?> superclass = dataClazz.getSuperclass();
        if (superclass != null && superclass.isAnnotationPresent(XData.class)) {
            analyzeProperties(superclass, props, pks, checkNull);
        }

        Field[] fields = dataClazz.getDeclaredFields();
        if (!XArrays.isEmpty(fields)) {
            for (Field field : fields) {
                if (field.isAnnotationPresent(XField.class)) {
                    XField xfield = field.getAnnotation(XField.class);
                    checkError(dataClazz, xfield, "doc", xfield.doc(), checkNull);
                    Class<?> fieldType = field.getType();
                    Class<?> dataClass = fieldType;
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
                    prop.setSid(xfield.sid());
                    prop.setMin(xfield.min());
                    prop.setMax(xfield.max());
                    prop.setLength(xfield.length());
                    prop.setLengthProp(xfield.lengthProp());
                    prop.setFormat(xfield.format());
                    prop.setPk(xfield.pk());
                    prop.setDoc(xfield.doc());
                    prop.setField(field);
                    prop.setType(fieldType);
                    prop.setDataClass(dataClass);
                    if (xfield.pk() && pks.indexOf(fieldName) == -1) {
                        pks.add(fieldName);
                    }
                    props.add(prop);
                }
            }
        }

    }

    public static Class<?> getActualType(Class<?> actorClass, Class<?> readyClass, Class<?> paramOrReturnType, Type type) {
        if (paramOrReturnType.isPrimitive()) {
            return paramOrReturnType;
        } else {
            Class<?> genericType;
            if (paramOrReturnType.equals(Serializable.class)) {
                genericType = getGenericType(type);
                if (genericType != null) {
                    return genericType;
                }

                if (actorClass.isInterface()) {
                    Type[] interfaceTypes = actorClass.getGenericInterfaces();
                    if (interfaceTypes != null && interfaceTypes.length > 0) {
                        genericType = getGenericType(interfaceTypes[0]);
                        if (genericType != null) {
                            return genericType;
                        }
                    }
                }

                genericType = getGenericType(readyClass.getGenericSuperclass());
                if (genericType != null) {
                    return genericType;
                }
            } else if (paramOrReturnType.isArray()) {
                genericType = paramOrReturnType.getComponentType();
                if (genericType.equals(Serializable.class)) {
                    Class var10 = getGenericType(type);
                    if (var10 != null) {
                        return var10;
                    }

                    if (actorClass.isInterface()) {
                        Type[] var6 = actorClass.getGenericInterfaces();
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

                    var10 = getGenericType(readyClass.getGenericSuperclass());
                    if (var10 != null) {
                        try {
                            return Class.forName("[L" + var10.getName() + ";");
                        } catch (Exception var8) {
                            var8.printStackTrace();
                        }
                    }
                }
            }

            return paramOrReturnType;
        }
    }

    // ------------------------ 私有方法 ------------------------

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

    /**
     * 属性字段里如果有对象属性,更新属性里的对象信息
     *
     * @param dataConfigMap
     */
    private static void unionDatas(Map<Class<?>, DataConfig> dataConfigMap) {
        Iterator<DataConfig> it = dataConfigMap.values().iterator();

        while (it.hasNext()) {
            DataConfig dataConfig = it.next();
            Property[] props = dataConfig.getProperties();
            for (Property prop : props) {
                Class<?> clazz = prop.getDataClass();
                if (dataConfigMap.containsKey(clazz)) {
                    prop.setDataConfig(dataConfigMap.get(clazz));
                }
            }
        }

    }

    private static File[] filterClassFiles(String path) {
        return Strings.isNull(path) ? null : (new File(path)).listFiles(Clazzs.FILTER);
    }

    private static String getClassName(String packageName, String className) {
        if (className == null) {
            return null;
        } else {
            int last = className.lastIndexOf(".");
            return last <= 0 ? null : packageName + "." + className.substring(0, last);
        }
    }

    private static void analyzeClass(String className, Class<?> actorClass, List<Class<?>> actors,
                                     List<Class<?>> datas, List<Class<?>> readyActors, List<Class<?>> interfaces) {
        if (!Strings.isNull(className)) {
            Class<?> clazz = null;

            try {
                clazz = Class.forName(className);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (clazz != null) {
                if (clazz.isAnnotationPresent(XData.class)) {
                    datas.add(clazz);
                } else {
                    if (clazz.isAnnotationPresent(XActor.class)) {
                        if (clazz.isInterface()) {
                            interfaces.add(clazz);
                        } else if (actorClass == null) {
                            actors.add(clazz);
                        } else if (!actorClass.equals(clazz) && actorClass.isAssignableFrom(clazz)) {
                            actors.add(clazz);
                        }
                    }

                    if (actorClass == null) {
                        try {
                            if (clazz.getInterfaces().length == 0) {
                                return;
                            }

                            if (clazz.getConstructors().length == 0) {
                                return;
                            }

                            readyActors.add(clazz);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (!actorClass.equals(clazz) && actorClass.isAssignableFrom(clazz)) {
                        try {
                            if (clazz.getConstructors().length == 0) {
                                return;
                            }

                            readyActors.add(clazz);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        }
    }

    private static void getPackageClass(String packageName, File[] files, Class<?> actorClass, List<Class<?>> actors,
                                        List<Class<?>> datas, List<Class<?>> readyActors, List<Class<?>> interfaces) {
        if (!XArrays.isEmpty(files)) {

            for (int i = 0, L = files.length; i < L; ++i) {
                File file = files[i];
                String className = file.getName();
                if (file.isFile()) {
                    analyzeClass(getClassName(packageName, className), actorClass, actors, datas,
                                 readyActors, interfaces);
                } else {
                    String var12 = packageName.length() > 0 ? packageName + "." + className : className;
                    getPackageClass(var12, filterClassFiles(file.getPath()), actorClass, actors,
                                    datas, readyActors, interfaces);
                }
            }

        }
    }

}
