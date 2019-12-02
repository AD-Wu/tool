package com.x.commons.util.xml;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;

/**
 * @Desc TODO
 * @Date 2019-12-02 21:47
 * @Author AD
 */
public class Deset {
    
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
            } else if (node.getNodeType() != Node.COMMENT_NODE) {
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
              "</C>" +
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
    
}
