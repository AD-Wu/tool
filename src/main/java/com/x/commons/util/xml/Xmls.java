package com.x.commons.util.xml;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.DateConverter;
import com.x.commons.enums.Charset;
import com.x.commons.util.file.Files;
import com.x.commons.util.string.Strings;
import com.x.commons.util.xml.data.ClassInfo;
import com.x.commons.util.xml.enums.XStreamMode;

/**
 * @Desc
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
    
    public static String getXMLHeader(String charset) {
        return "<?xml version=\"1.0\" encoding=\"" + charset + "\"?>\r\n";
    }
    
    public static String getDTDString(String rootName, String dtdMark) {
        return "<!DOCTYPE " + rootName + " " + dtdMark + ">\r\n";
    }
    
    public static boolean saveXML(String xmlFileName, String rootName, String dtdMark, String var3) {
        return saveXML(xmlFileName, rootName, dtdMark, var3, Charset.UTF8);
    }
    
    public static boolean saveXML(String xmlFileName, String rootName, String dtdMark, String xml, String charset) {
        String xmls = getXMLHeader(charset) + getDTDString(rootName, dtdMark) + xml;
        return Files.createFile(xmlFileName, xmls, charset);
    }
    
    public static String loadXML(String xmlFileName) throws Exception {
        try {
            return Files.readTxt(xmlFileName);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public static <T> String toXML(T t, ClassInfo[] classInfos, ClassInfo[] fieldInfos) {
        return toXML(t, classInfos, fieldInfos, "");
    }
    
    public static <T> String toXML(T bean, ClassInfo[] dataClasses, ClassInfo[] attributes, String dateFormat) {
        if (bean == null) {
            return null;
        } else {
            XStream xs = getXStream(dataClasses, attributes);
            if (!Strings.isNull(dateFormat)) {
                xs.registerConverter(new DateConverter(dateFormat, ACCEPTABLE_FORMATS));
            }
            
            return xs.toXML(bean);
        }
    }
    
    public static <T> T fromXML(String xmlPath, ClassInfo[] dataClasses, ClassInfo[] attributes) {
        return fromXML(xmlPath, dataClasses, attributes, "");
    }
    
    public static <T> T fromXML(String xmlPath, ClassInfo[] dataClasses, ClassInfo[] attributes, String dateFormat) {
        if (Strings.isNull(xmlPath)) {
            XStream xs = getXStream(dataClasses, attributes);
            if (!Strings.isNull(dateFormat)) {
                xs.registerConverter(new DateConverter(dateFormat, ACCEPTABLE_FORMATS));
            }
            try {
                return (T) xs.fromXML(xmlPath);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }
    
    public static XStream getXStream() {
        return getXStream(XStreamMode.NoReferences);
    }
    
    public static XStream getXStream(XStreamMode mode) {
        XStream xs = new XStream();
        xs.setMode(mode.code());
        return xs;
    }
    
    // ------------------------ 私有方法 ------------------------
    
    private static XStream getXStream(ClassInfo[] dataClasses, ClassInfo[] attributes) {
        XStream xs = new XStream();
        xs.setMode(XStream.NO_REFERENCES);
        ClassInfo[] infos;
        int length;
        int i;
        ClassInfo info;
        if (dataClasses != null && dataClasses.length > 0) {
            infos = dataClasses;
            length = dataClasses.length;
            
            for (i = 0; i < length; ++i) {
                info = infos[i];
                xs.alias(info.getName(), info.getType());
            }
        }
        
        if (attributes != null && attributes.length > 0) {
            infos = attributes;
            length = attributes.length;
            
            for (i = 0; i < length; ++i) {
                info = infos[i];
                xs.useAttributeFor(info.getType(), info.getName());
            }
        }
        return xs;
    }
    
}
