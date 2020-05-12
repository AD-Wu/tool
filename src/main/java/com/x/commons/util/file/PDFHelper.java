package com.x.commons.util.file;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;

/**
 * @Desc：
 * @Author：AD
 * @Date：2020/5/12 17:29
 */
public final class PDFHelper {
    private PDFHelper() {}

    public static String read(String filepath) {
        //创建文档对象
        PDDocument doc = null;
        String content = "";
        try {
            //加载一个pdf对象
            doc = PDDocument.load(new File(filepath));
            //获取一个PDFTextStripper文本剥离对象
            PDFTextStripper stripper = new PDFTextStripper();
            content = stripper.getText(doc);
            System.out.println("内容:" + content);
            System.out.println("全部页数" + doc.getNumberOfPages());
            //关闭文档
            doc.close();
            return content;
        } catch (Exception e) {
            // TODO: handle exception
        }
        return "";
    }

    public static void main(String[] args) {
        String path = Files.getResourcesPath()+"test1.pdf";
        String read = read(path);
        Files.createFile(Files.getResourcesPath(),"test1.txt",read);
    }
}
