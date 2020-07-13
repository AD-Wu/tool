package com.x.protocol.layers.application;

import com.x.commons.collection.KeyValue;
import com.x.commons.collection.NameValue;
import com.x.commons.collection.Where;
import com.x.commons.local.Locals;
import com.x.commons.util.bean.New;
import com.x.commons.util.collection.XArrays;
import com.x.commons.util.reflact.Clazzs;
import com.x.commons.util.reflact.Loader;
import com.x.commons.util.string.Strings;
import com.x.framework.protocol.actor.model.CountResult;
import com.x.framework.protocol.actor.model.PageRequest;
import com.x.framework.protocol.actor.model.UpdateRequest;
import com.x.framework.protocol.actor.model.WhereRequest;
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
    
    public static AnnotationInfo getAnnotationInfo(String base, String pkgName, boolean checkDoc) throws Exception {
        if (pkgName == null) pkgName = "";
        Class<?> baseClass = !Strings.isNull(base) ? Class.forName(base) : null;
        List<Class<?>> actors = New.list();
        List<Class<?>> interfaces = New.list();
        List<Class<?>> datas = New.list();
        List<Class<?>> readyActors = New.list();
        
        ClassLoader loader = Loader.get();
        String pkgPath = pkgName.replaceAll("\\.", "/");
        Enumeration<URL> urlEnums = loader.getResources(pkgPath);
        if (urlEnums == null) {
            URL[] urls = ((URLClassLoader) loader).getURLs();
            for (URL url : urls) {
                String filePath = url.getPath();
                if (!filePath.endsWith("classes/")) {
                    String fullPath = filePath + "!/" + pkgPath;
                    String[] jarPkg = fullPath.split("!");
                    String jarPath = jarPkg[0].substring(jarPkg[0].indexOf("/"));
                    try (JarFile jar = new JarFile(jarPath);) {
                        handleJarEntity(jar, pkgPath, baseClass, actors, datas, readyActors,
                                interfaces);
                    } catch (Exception e) {
                        throw e;
                    }
                }
            }
        } else {
            while (urlEnums.hasMoreElements()) {
                URL url = urlEnums.nextElement();
                String protocol = url.getProtocol();
                if ("file".equals(protocol)) {
                    getPackageClass(pkgName, filterClassFiles(url.getPath()),
                            baseClass, actors, datas, readyActors, interfaces);
                } else if ("jar".equals(protocol)) {
                    JarURLConnection urlConn = (JarURLConnection) url.openConnection();
                    
                    try (JarFile jarFile = urlConn.getJarFile();) {
                        handleJarEntity(jarFile, pkgPath, baseClass, actors, datas, readyActors, interfaces);
                    } catch (Exception e) {
                        throw e;
                    }
                }
            }
        }
        addDefaultAnnotation(datas);
        Map<Class<?>, DataConfig> dataConfigMap = New.map();
        analyzeDatas(datas, dataConfigMap, checkDoc);
        unionDatas(dataConfigMap);
        return new AnnotationInfo(actors, dataConfigMap, readyActors, interfaces);
    }
    
    public static void checkError(Class<?> dataClazz, Object xAnnotation, String fieldName,
            String fieldValue, boolean needCheck)
            throws Exception {
        if (needCheck) {
            if (Strings.isNull(fieldValue)) {
                throw new Exception(
                        Locals.text("protocol.layer.annotation.err", fieldName,
                                dataClazz.getName(),
                                xAnnotation.toString()));
            }
        }
    }
    
    private static void handleJarEntity(JarFile jarFile, String pkgPath, Class<?> clazz, List<Class<?>> actors,
            List<Class<?>> datas, List<Class<?>> readyActors, List<Class<?>> interfaces) {
        Enumeration<JarEntry> jarEntries = jarFile.entries();
        
        while (jarEntries.hasMoreElements()) {
            JarEntry jarEntry = jarEntries.nextElement();
            if (!jarEntry.isDirectory()) {
                String jarInfo = jarEntry.getName();
                if (jarInfo != null && jarInfo.length() >= 8) {
                    if (jarInfo.charAt(0) == '/') {
                        jarInfo = jarInfo.substring(1);
                    }
                    if (jarInfo.startsWith(pkgPath) && jarInfo.endsWith(".class")) {
                        jarInfo = jarInfo.replaceAll("/", ".");
                        jarInfo = jarInfo.substring(0, jarInfo.length() - 6);
                        analyzeClass(jarInfo, clazz, actors, datas, readyActors, interfaces);
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
    
    private static void analyzeDatas(List<Class<?>> datas, Map<Class<?>, DataConfig> dataConfigMap, boolean checkDoc)
            throws Exception {
        Iterator<Class<?>> it = datas.iterator();
        
        while (it.hasNext()) {
            Class<?> clazz = it.next();
            XData xdata = clazz.getAnnotation(XData.class);
            checkError(clazz, xdata, "doc", xdata.doc(), checkDoc);
            String version = Strings.fixNull(xdata.version(), "1");
            
            DataConfig dc = new DataConfig();
            dc.setDataClass(clazz);
            dc.setDoc(xdata.doc());
            dc.setVersion(version);
            dc.setCache(xdata.cache());
            dc.setHistory(xdata.history());
            dc.setTable(xdata.table().trim());
            List<String> pks = New.list();
            List<Property> props = New.list();
            analyzeProperties(clazz, props, pks, checkDoc);
            dc.setPks(pks.toArray(new String[0]));
            dc.setProperties(props);
            dataConfigMap.put(clazz, dc);
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
    
    public static Class<?> getActualType(Class<?> annotationClass, Class<?> clazz, Class<?> type, Type genericType) {
        if (type.isPrimitive()) {
            return type;
        } else {
            Class<?> at;
            if (type.equals(Serializable.class)) {
                at = getGenericType(genericType);
                if (at != null) {
                    return at;
                }
                
                if (annotationClass.isInterface()) {
                    Type[] gtypes = annotationClass.getGenericInterfaces();
                    if (gtypes != null && gtypes.length > 0) {
                        at = getGenericType(gtypes[0]);
                        if (at != null) {
                            return at;
                        }
                    }
                }
                
                at = getGenericType(clazz.getGenericSuperclass());
                if (at != null) {
                    return at;
                }
            } else if (type.isArray()) {
                at = type.getComponentType();
                if (at.equals(Serializable.class)) {
                    Class gtype = getGenericType(genericType);
                    if (gtype != null) {
                        return gtype;
                    }
                    
                    if (annotationClass.isInterface()) {
                        Type[] e = annotationClass.getGenericInterfaces();
                        if (e != null && e.length > 0) {
                            gtype = getGenericType(e[0]);
                            if (gtype != null) {
                                try {
                                    return Class.forName("[L" + gtype.getName() + ";");
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }
                    }
                    
                    gtype = getGenericType(clazz.getGenericSuperclass());
                    if (gtype != null) {
                        try {
                            return Class.forName("[L" + gtype.getName() + ";");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            
            return type;
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
    
    private static String getClassName(String pkgName, String className) {
        if (className == null) {
            return null;
        } else {
            int last = className.lastIndexOf(".");
            return last <= 0 ? null : pkgName + "." + className.substring(0, last);
        }
    }
    
    private static void analyzeClass(String className, Class<?> baseClass, List<Class<?>> actors,
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
                        } else if (baseClass == null) {
                            actors.add(clazz);
                        } else if (!baseClass.equals(clazz) && baseClass.isAssignableFrom(clazz)) {
                            actors.add(clazz);
                        }
                    }
                    
                    if (baseClass == null) {
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
                    } else if (!baseClass.equals(clazz) && baseClass.isAssignableFrom(clazz)) {
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
    
    private static void getPackageClass(String pkgName, File[] files, Class<?> actorClass, List<Class<?>> actors,
            List<Class<?>> datas, List<Class<?>> readyActors, List<Class<?>> interfaces) {
        if (!XArrays.isEmpty(files)) {
            for (File file : files) {
                String className = file.getName();
                if (file.isFile()) {
                    analyzeClass(getClassName(pkgName, className), actorClass, actors, datas,
                            readyActors, interfaces);
                } else {
                    String nextPkg = pkgName.length() > 0 ? pkgName + "." + className : className;
                    getPackageClass(nextPkg, filterClassFiles(file.getPath()), actorClass, actors,
                            datas, readyActors, interfaces);
                }
            }
            
        }
    }
    
}
