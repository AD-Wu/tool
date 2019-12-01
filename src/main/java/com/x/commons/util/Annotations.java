package com.x.commons.util;

import com.ax.commons.collection.KeyValue;
import com.ax.commons.collection.NameValue;
import com.ax.commons.collection.Where;
import com.ax.commons.local.LocalManager;
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

import java.io.File;
import java.io.FileFilter;
import java.io.Serializable;
import java.lang.reflect.*;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @Desc TODO
 * @Date 2019-11-29 00:47
 * @Author AD
 */
public final class Annotations {
    private static final FileFilter fileFiter = new FileFilter() {
        public boolean accept(File var1) {
            return var1.isFile() ? var1.getName().endsWith(".class") : true;
        }
    };
    
    public Annotations() {
    }
    
    public static void main(String[] args) throws Exception {
        getAnnotationInfo("", "", true);
    }
    
    public static AnnotationInfo getAnnotationInfo(String var0, String var1, boolean var2) throws Exception {
        if (var1 == null) {
            var1 = "";
        }
        
        Class var3 = var0 != null && var0.trim().length() != 0 ? Class.forName(var0) : null;
        ArrayList var4 = new ArrayList();
        ArrayList var5 = new ArrayList();
        ArrayList var6 = new ArrayList();
        ArrayList var7 = new ArrayList();
        ClassLoader var8 = Thread.currentThread().getContextClassLoader();
        String var9 = var1.replaceAll("\\.", "/");
        Enumeration var10 = var8.getResources(var9);
        if (var10 == null) {
            URL[] var45 = ((URLClassLoader)var8).getURLs();
            
            for(int var47 = 0; var47 < var45.length; ++var47) {
                URL var48 = var45[var47];
                String var49 = var48.getPath();
                if (!var49.endsWith("classes/")) {
                    String var50 = var49 + "!/" + var9;
                    String[] var51 = var50.split("!");
                    String var17 = var51[0].substring(var51[0].indexOf("/"));
                    JarFile var18 = null;
                    
                    try {
                        var18 = new JarFile(var17);
                        Enumeration var19 = var18.entries();
                        
                        while(var19.hasMoreElements()) {
                            JarEntry var20 = (JarEntry)var19.nextElement();
                            if (!var20.isDirectory()) {
                                String var21 = var20.getName();
                                if (var21 != null && var21.length() >= 8) {
                                    if (var21.charAt(0) == '/') {
                                        var21 = var21.substring(1);
                                    }
                                    
                                    if (var21.startsWith(var9) && var21.endsWith(".class")) {
                                        var21 = var21.replaceAll("/", ".");
                                        var21 = var21.substring(0, var21.length() - 6);
                                        analyzeClass(var21, var3, var4, var6, var7, var5);
                                    }
                                }
                            }
                        }
                    } catch (Exception var41) {
                        throw var41;
                    } finally {
                        try {
                            if (var18 != null) {
                                var18.close();
                            }
                        } catch (Exception var39) {
                            var39.printStackTrace();
                        }
                        
                    }
                }
            }
        } else {
label502:
            while(true) {
                while(true) {
                    URL var11;
                    do {
                        if (!var10.hasMoreElements()) {
                            break label502;
                        }
                        
                        var11 = (URL)var10.nextElement();
                    } while(var11 == null);
                    
                    String var12 = var11.getProtocol();
                    if ("file".equals(var12)) {
                        getPackageClass(var1, filterClassFiles(var11.getPath()), var3, var4, var6, var7, var5);
                    } else if ("jar".equals(var12)) {
                        JarFile var13 = null;
                        
                        try {
                            var13 = ((JarURLConnection)var11.openConnection()).getJarFile();
                            Enumeration var14 = var13.entries();
                            
                            while(var14.hasMoreElements()) {
                                JarEntry var15 = (JarEntry)var14.nextElement();
                                if (!var15.isDirectory()) {
                                    String var16 = var15.getName();
                                    if (var16 != null && var16.length() >= 8) {
                                        if (var16.charAt(0) == '/') {
                                            var16 = var16.substring(1);
                                        }
                                        
                                        if (var16.startsWith(var9) && var16.endsWith(".class")) {
                                            var16 = var16.replaceAll("/", ".");
                                            var16 = var16.substring(0, var16.length() - 6);
                                            analyzeClass(var16, var3, var4, var6, var7, var5);
                                        }
                                    }
                                }
                            }
                        } catch (Exception var43) {
                            throw var43;
                        } finally {
                            try {
                                if (var13 != null) {
                                    var13.close();
                                }
                            } catch (Exception var40) {
                                var40.printStackTrace();
                            }
                            
                        }
                    }
                }
            }
        }
        
        if (var6.indexOf(KeyValue.class) == -1) {
            var6.add(KeyValue.class);
        }
        
        if (var6.indexOf(NameValue.class) == -1) {
            var6.add(NameValue.class);
        }
        
        if (var6.indexOf(Where.class) == -1) {
            var6.add(Where.class);
        }
        
        if (var6.indexOf(CountResult.class) == -1) {
            var6.add(CountResult.class);
        }
        
        if (var6.indexOf(PageRequest.class) == -1) {
            var6.add(PageRequest.class);
        }
        
        if (var6.indexOf(UpdateRequest.class) == -1) {
            var6.add(UpdateRequest.class);
        }
        
        if (var6.indexOf(WhereRequest.class) == -1) {
            var6.add(WhereRequest.class);
        }
        
        HashMap var46 = new HashMap();
        analyzeDatas(var6, var46, var2);
        unionDatas(var46);
        return new AnnotationInfo(var4, var46, var7, var5);
    }
    
    public static void checkError(Class<?> var0, Object var1, String var2, String var3, boolean var4) throws Exception {
        if (var4) {
            if (var3 == null) {
                var3 = "";
            }
            
            if (var3.trim().length() < 1) {
                throw new Exception(LocalManager.defaultText("protocol.layer.annotation.err", new Object[]{var2, var0.getName(), var1.toString()}));
            }
        }
    }
    
    private static void analyzeDatas(List<Class<?>> var0, Map<Class<?>, DataConfig> var1, boolean var2) throws Exception {
        Iterator var3 = var0.iterator();
        
        while(var3.hasNext()) {
            Class var4 = (Class)var3.next();
            AxData var5 = (AxData)var4.getAnnotation(AxData.class);
            checkError(var4, var5, "doc", var5.doc(), var2);
            String var6 = var5.version();
            if (var6 == null || var6.trim().length() == 0) {
                var6 = "1";
            }
            
            DataConfig var7 = new DataConfig();
            var7.setDataClass(var4);
            var7.setDoc(var5.doc());
            var7.setVersion(var6);
            var7.setCaching(var5.caching());
            var7.setCachingHistory(var5.history());
            var7.setTable(var5.table().trim());
            ArrayList var8 = new ArrayList();
            ArrayList var9 = new ArrayList();
            analyzeProperties(var4, var9, var8, var2);
            var7.setPks((String[])var8.toArray(new String[0]));
            var7.setProperties(var9);
            var1.put(var4, var7);
        }
        
    }
    
    private static void analyzeProperties(Class<?> var0, List<Property> var1, List<String> var2, boolean var3) throws Exception {
        Class var4 = var0.getSuperclass();
        if (var4 != null && var4.isAnnotationPresent(AxData.class)) {
            analyzeProperties(var4, var1, var2, var3);
        }
        
        Field[] var5 = var0.getDeclaredFields();
        if (var5 != null && var5.length > 0) {
            Field[] var6 = var5;
            int var7 = var5.length;
            
            for(int var8 = 0; var8 < var7; ++var8) {
                Field var9 = var6[var8];
                if (var9.isAnnotationPresent(AxAttribute.class)) {
                    AxAttribute var10 = (AxAttribute)var9.getAnnotation(AxAttribute.class);
                    checkError(var0, var10, "doc", var10.doc(), var3);
                    Class var11 = var9.getType();
                    Class var12 = var11;
                    if (!var11.isPrimitive() && !var11.equals(Object.class)) {
                        if (var11.isAssignableFrom(List.class)) {
                            Type var13 = var9.getGenericType();
                            if (var13 instanceof ParameterizedType) {
                                ParameterizedType var14 = (ParameterizedType)var13;
                                Type[] var15 = var14.getActualTypeArguments();
                                if (var15 != null && var15.length == 1) {
                                    var12 = (Class)var15[0];
                                }
                            }
                        } else if (var11.isArray()) {
                            var12 = var11.getComponentType();
                        }
                    }
                    
                    var9.setAccessible(true);
                    String var16 = var9.getName();
                    Property var17 = new Property();
                    var17.setId(var16);
                    var17.setSid(var10.sid());
                    var17.setMin(var10.min());
                    var17.setMax(var10.max());
                    var17.setLength(var10.length());
                    var17.setLengthProp(var10.lengthProp());
                    var17.setFormat(var10.format());
                    var17.setPk(var10.pk());
                    var17.setDoc(var10.doc());
                    var17.setField(var9);
                    var17.setType(var11);
                    var17.setDataClass(var12);
                    if (var10.pk() && var2.indexOf(var16) == -1) {
                        var2.add(var16);
                    }
                    
                    var1.add(var17);
                }
            }
        }
        
    }
    
    public static Class<?> getActualType(Class<?> var0, Class<?> var1, Class<?> var2, Type var3) {
        if (var2.isPrimitive()) {
            return var2;
        } else {
            Class var4;
            if (var2.equals(Serializable.class)) {
                var4 = getGenericType(var3);
                if (var4 != null) {
                    return var4;
                }
                
                if (var0.isInterface()) {
                    Type[] var5 = var0.getGenericInterfaces();
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
                    
                    if (var0.isInterface()) {
                        Type[] var6 = var0.getGenericInterfaces();
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
    
    private static Class<?> getGenericType(Type var0) {
        if (var0 == null) {
            return null;
        } else if (var0 instanceof ParameterizedType) {
            return getGenericClass((ParameterizedType)var0);
        } else {
            if (var0 instanceof GenericArrayType) {
                Class var1 = getGenericType(((GenericArrayType)var0).getGenericComponentType());
                if (var1 == null) {
                    return null;
                }
                
                try {
                    return Class.forName("[L" + var1.getName() + ";");
                } catch (Exception var3) {
                    var3.printStackTrace();
                }
            } else if (var0 instanceof TypeVariable) {
                return getGenericType(((TypeVariable)var0).getBounds()[0]);
            }
            
            return null;
        }
    }
    
    private static Class<?> getGenericClass(ParameterizedType var0) {
        Type[] var1 = var0.getActualTypeArguments();
        if (var1 != null && var1.length != 0) {
            Type var2 = var1[0];
            if (var2 instanceof ParameterizedType) {
                return (Class)((ParameterizedType)var2).getRawType();
            } else {
                return var2 instanceof TypeVariable ? getGenericType(((TypeVariable)var2).getBounds()[0]) : (Class)var2;
            }
        } else {
            return null;
        }
    }
    
    private static void unionDatas(Map<Class<?>, DataConfig> var0) {
        Iterator var1 = var0.values().iterator();
        
        while(var1.hasNext()) {
            DataConfig var2 = (DataConfig)var1.next();
            Property[] var3 = var2.getProperties();
            Property[] var4 = var3;
            int var5 = var3.length;
            
            for(int var6 = 0; var6 < var5; ++var6) {
                Property var7 = var4[var6];
                Class var8 = var7.getDataClass();
                if (var0.containsKey(var8)) {
                    var7.setDataConfig((DataConfig)var0.get(var8));
                }
            }
        }
        
    }
    
    private static File[] filterClassFiles(String var0) {
        return var0 == null ? null : (new File(var0)).listFiles(fileFiter);
    }
    
    private static String getClassName(String var0, String var1) {
        if (var1 == null) {
            return null;
        } else {
            int var2 = var1.lastIndexOf(".");
            return var2 <= 0 ? null : var0 + "." + var1.substring(0, var2);
        }
    }
    
    private static void analyzeClass(String var0, Class<?> var1, List<Class<?>> var2, List<Class<?>> var3, List<Class<?>> var4, List<Class<?>> var5) {
        if (var0 != null && var0.length() != 0) {
            Class var6 = null;
            
            try {
                var6 = Class.forName(var0);
            } catch (Exception var10) {
                var10.printStackTrace();
            }
            
            if (var6 != null) {
                if (var6.isAnnotationPresent(AxData.class)) {
                    var3.add(var6);
                } else {
                    if (var6.isAnnotationPresent(AxActor.class)) {
                        if (var6.isInterface()) {
                            var5.add(var6);
                        } else if (var1 == null) {
                            var2.add(var6);
                        } else if (!var1.equals(var6) && var1.isAssignableFrom(var6)) {
                            var2.add(var6);
                        }
                    }
                    
                    if (var1 == null) {
                        try {
                            if (var6.getInterfaces().length == 0) {
                                return;
                            }
                            
                            if (var6.getConstructors().length == 0) {
                                return;
                            }
                            
                            var4.add(var6);
                        } catch (Exception var9) {
                            var9.printStackTrace();
                        }
                    } else if (!var1.equals(var6) && var1.isAssignableFrom(var6)) {
                        try {
                            if (var6.getConstructors().length == 0) {
                                return;
                            }
                            
                            var4.add(var6);
                        } catch (Exception var8) {
                            var8.printStackTrace();
                        }
                    }
                    
                }
            }
        }
    }
    
    private static void getPackageClass(String var0, File[] var1, Class<?> var2, List<Class<?>> var3, List<Class<?>> var4, List<Class<?>> var5, List<Class<?>> var6) {
        if (var1 != null && var1.length != 0) {
            File[] var7 = var1;
            int var8 = var1.length;
            
            for(int var9 = 0; var9 < var8; ++var9) {
                File var10 = var7[var9];
                String var11 = var10.getName();
                if (var10.isFile()) {
                    analyzeClass(getClassName(var0, var11), var2, var3, var4, var5, var6);
                } else {
                    String var12 = var0.length() > 0 ? var0 + "." + var11 : var11;
                    getPackageClass(var12, filterClassFiles(var10.getPath()), var2, var3, var4, var5, var6);
                }
            }
            
        }
    }
}