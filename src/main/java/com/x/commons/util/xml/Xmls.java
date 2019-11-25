package com.x.commons.util.xml;

import com.ax.commons.local.LocalManager;
import com.ax.commons.utils.XMLHelper;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.DateConverter;
import com.x.commons.enums.Charset;
import com.x.commons.util.file.Files;
import com.x.commons.util.reflact.Loader;
import com.x.commons.util.string.Strings;
import com.x.commons.util.xml.data.ClassInfo;
import com.x.commons.util.xml.enums.XStreamMode;
import com.x.commons.util.xml.test.Parser;
import com.x.commons.util.xml.test.Result;
import com.x.commons.util.xml.test.XMLTest;
import com.x.commons.util.yml.test.Config;

import java.io.InputStream;
import java.util.List;
import java.util.StringJoiner;

/**
 * @Desc TODO
 * @Date 2019-11-24 00:00
 * @Author AD
 */
public final class Xmls {
    
    // ------------------------ 成员变量 ------------------------
    private static final String[] ACCEPTABLE_FORMATS = {
            "yyyy-MM-dd HH:mm:ss.S z",
            "yyyy-MM-dd HH:mm:ss.S 'UTC'",
            "yyyy-MM-dd HH:mm:ssz",
            "yyyy-MM-dd HH:mm:ss z"
    };
    // ------------------------ 构造方法 ------------------------
    
    private Xmls() {}
    
    // ------------------------ 成员方法 ------------------------
    
    public static String getXMLHeader() {
        return getXMLHeader(Charset.UTF8);
    }
    
    public static String getXMLHeader(String encoding) {
        return "<?xml version=\"1.0\" encoding=\"" + encoding + "\"?>\r\n";
    }
    
    public static String getDTDString(String root, String dtdName) {
        return "<!DOCTYPE " + root + " " + dtdName + ">\r\n";
    }
    
    public static boolean saveXML(String savePath, String dtdRoot, String dtdName, String var3) {
        return saveXML(savePath, dtdRoot, dtdName, var3, Charset.UTF8);
    }
    
    public static boolean saveXML(String savePath, String dtdRoot, String dtdName, String content, String encoding) {
        String xml = getXMLHeader(encoding) + getDTDString(dtdRoot, dtdName) + content;
        return Files.createFile(savePath, xml, encoding);
    }
    
    public static String loadXML(String path) throws Exception {
        try {
            return Files.readTxt(path);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public static <T> String toXML(T t, ClassInfo[] classInfos, ClassInfo[] fieldInfos) {
        return toXML(t, classInfos, fieldInfos, "");
    }
    
    public static <T> String toXML(T t, ClassInfo[] classInfos, ClassInfo[] fieldInfos, String defaultDateFormat) {
        if (t == null) {
            return null;
        } else {
            XStream xs = getXStream(classInfos, fieldInfos);
            if (!Strings.isNull(defaultDateFormat)) {
                xs.registerConverter(new DateConverter(defaultDateFormat, ACCEPTABLE_FORMATS));
            }
            
            return xs.toXML(t);
        }
    }
    
    public static <T> T fromXML(String xmlPath, ClassInfo[] classInfos, ClassInfo[] fieldInfos) {
        return fromXML(xmlPath, classInfos, fieldInfos, "");
    }
    
    public static <T> T fromXML(String xmlPath, ClassInfo[] classInfos, ClassInfo[] fieldInfos, String defaultDateFormat) {
        if (Strings.isNull(xmlPath)) {
            XStream xs = getXStream(classInfos, fieldInfos);
            if (!Strings.isNull(defaultDateFormat)) {
                xs.registerConverter(new DateConverter(defaultDateFormat, ACCEPTABLE_FORMATS));
            }
            try {
                Object o = xs.fromXML(xmlPath);
                return (T)o;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }
    
    public static XStream getXStream(){
        return getXStream(XStreamMode.NoReferences);
    }
    public static XStream getXStream(XStreamMode mode){
        XStream xs = new XStream();
        xs.setMode(mode.code());
        return xs;
    }
    
    // ------------------------ 私有方法 ------------------------
    
    public static void main(String[] args) throws Exception {
        
        // toXML();
        from();
        
    }
    
    private static void from() throws Exception {
        XStream xs = getXStream();
        // 设置好结构
        xs.alias("parsers", List.class);
        xs.alias("parser", Parser.class);
        xs.useAttributeFor(Parser.class, "clazz");
        xs.alias("result", Result.class);
        xs.useAttributeFor(Result.class, "clazz");
        // 获取源
        String s = loadXML("x-framework/parser/parser.xml");
        // 解析
        Object o = xs.fromXML(s);
        System.out.println(s);
        System.out.println(o);
        
    
    }
    
    private static void toXML() {
        XStream xs = getXStream();
        
        xs.alias("Test", XMLTest.class);
        XMLTest test = new XMLTest("AD", 28, "123");
        String s = xs.toXML(test);
        System.out.println(s);
    }
    
    
    private static XStream getXStream(ClassInfo[] classInfos, ClassInfo[] fieldInfos) {
        XStream xs = new XStream();
        xs.setMode(XStream.NO_REFERENCES);
        ClassInfo[] infos;
        int length;
        int i;
        ClassInfo info;
        if (classInfos != null && classInfos.length > 0) {
            infos = classInfos;
            length = classInfos.length;
            
            for (i = 0; i < length; ++i) {
                info = infos[i];
                xs.alias(info.getName(), info.getType());
            }
        }
        
        if (fieldInfos != null && fieldInfos.length > 0) {
            infos = fieldInfos;
            length = fieldInfos.length;
            
            for (i = 0; i < length; ++i) {
                info = infos[i];
                xs.useAttributeFor(info.getType(), info.getName());
            }
        }
        return xs;
    }
    
    
    
    
    
    
}
