package com.x.commons.util.xml;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.DateConverter;
import com.x.commons.enums.Charset;
import com.x.commons.util.file.Files;
import com.x.commons.util.string.Strings;
import com.x.commons.util.xml.data.ClassInfo;
import com.x.commons.util.xml.enums.XStreamMode;
import com.x.commons.util.xml.test.Parser;
import com.x.commons.util.xml.test.Result;
import com.x.commons.util.xml.test.XMLTest;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.util.List;

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
    
    public static <T> T from(String path, Class<T> clazz) throws Exception {
        DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = fact.newDocumentBuilder();
        FileInputStream in = new FileInputStream(path);
        Document doc = builder.parse(in);
        String encoding = doc.getXmlEncoding();
        DocumentType doctype = doc.getDoctype();
        Element elem = doc.getDocumentElement();
        
        return null;
    }
    
    public static void parseDocument(String path) throws Exception {
        DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = fact.newDocumentBuilder();
        FileInputStream in = new FileInputStream(path);
        
        Document doc = builder.parse(in);
        
        // 根节点名称
        String rootName = doc.getDocumentElement().getTagName();
        System.out.println("根节点: " + rootName);
        
        System.out.println("递归解析--------------begin------------------");
        // 递归解析Element
        Element element = doc.getDocumentElement();
        parseElement(element);
        System.out.println("递归解析--------------end------------------");
        
    }
    
    public static void parseElement(Element element) {
        System.out.print("<" + element.getTagName());
        NamedNodeMap attris = element.getAttributes();
        for (int i = 0, L = attris.getLength(); i < L; ++i) {
            Attr attr = (Attr) attris.item(i);
            System.out.print(" " + attr.getName() + "=\"" + attr.getValue() + "\"");
            
        }
        System.out.println(">");
        NodeList nodes = element.getChildNodes();
        Node node;
        for (int i = 0, L = nodes.getLength(); i < L; i++) {
            node = nodes.item(i);
            // 判断是否属于节点
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                // 判断是否还有子节点
                if (node.hasChildNodes()) {
                    parseElement((Element) node);
                }
            }else if (node.getNodeType() != Node.COMMENT_NODE) {
                System.out.print(node.getTextContent());
            }
        }
        System.out.println("</" + element.getTagName() + ">");
    }
    
    public static void main(String[] args) throws Exception {
        
        // toXML();
        // from();
        // String file ="/Users/sunday/Work/tool/src/main/resources/x-framework/parser/parser.xml";
        // parseDocument(file);
        String msg = ("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
        msg = msg + "<DESET>" +
              "<R>" +
              "<C N=\"rtnCode\">1</C>" +
              "<C N=\"rtnMsg\">xxx</C>" +
              "<C N=\"dataList\">" +
              "<R>" +
              "<C N=\"id\">1</C>" +
              "<C N=\"deptCode\">1111</C>" +
              "<C N=\"deptName\">11111111</C>" +
              "</R>" +
              "<R>" +
              "<C N=\"id\">2</C>" +
              "<C N=\"deptCode\">2222</C>" +
              "<C N=\"deptName\">22222222</C>" +
              "</R>" +
              "</C>"+
              "</R>" +
              "</DESET>";
    
        DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = fact.newDocumentBuilder();
        ByteArrayInputStream in = new ByteArrayInputStream(msg.getBytes());
        Document doc = builder.parse(in);
        String en = doc.getXmlEncoding();
        String text = doc.getTextContent();
        boolean b = doc.hasChildNodes();
        NodeList nodes = doc.getChildNodes();
        int length = nodes.getLength();
        System.out.println(text);
        System.out.println(en);
        System.out.println(b);
        System.out.println(nodes);
        System.out.println(length);
        String rootName = doc.getDocumentElement().getTagName();
        System.out.println("根节点: " + rootName);
    
        System.out.println("递归解析--------------begin------------------");
        // 递归解析Element
        Element element = doc.getDocumentElement();
        parseElement(element);
        System.out.println("递归解析--------------end------------------");
        
        
    }
    
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
                return (T) o;
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
