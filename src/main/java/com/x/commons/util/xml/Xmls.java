package com.x.commons.util.xml;

import com.thoughtworks.xstream.XStream;
import com.x.commons.util.reflact.Loader;
import com.x.commons.util.xml.test.XMLTest;
import com.x.commons.util.yml.test.Config;

import java.io.InputStream;
import java.util.StringJoiner;

/**
 * @Desc TODO
 * @Date 2019-11-24 00:00
 * @Author AD
 */
public final class Xmls {
    
    // ------------------------ 成员变量 ------------------------
    
    // ------------------------ 构造方法 ------------------------
    
    private Xmls() {}
    
    // ------------------------ 成员方法 ------------------------
    
    // ------------------------ 私有方法 ------------------------
    
    public static void main(String[] args) {
        
        // toXML();
        from();
        
    }
    
    @Override
    public String toString() {
        return new StringJoiner(", ", Xmls.class.getSimpleName() + "[", "]")
                .toString();
    }
    
    private static void from() {
        XStream xs = new XStream();
        xs.alias("Test", XMLTest.class);
        String xml = "<Test>\n" +
                "  <user>AD</user>\n" +
                "  <age>28</age>\n" +
                "  <birthday>123</birthday>\n" +
                "</Test>";
        XMLTest o = (XMLTest)xs.fromXML(xml);
        
        System.out.println(o);
        
    }
    
    private static void toXML() {
        XStream xs = new XStream();
        xs.alias("Test", XMLTest.class);
        XMLTest test = new XMLTest("AD", 28, "123");
        String s = xs.toXML(test);
        System.out.println(s);
    }
    
}
