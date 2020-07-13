package com.x;

import com.google.common.io.Files;
import com.x.commons.enums.Charsets;
import com.x.commons.util.bean.New;
import com.x.commons.util.bean.SB;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * @Desc
 * @Date 2020-04-03 21:05
 * @Author AD
 */
public class Test {

     public static void main(String[] args) throws IOException {
         String path = com.x.commons.util.file.Files.getResourcesPath()+"actor.txt";
         File file = com.x.commons.util.file.Files.getFile(path);
         List<String> lines = Files.readLines(file, Charsets.UTF8);
         Iterator<String> it = lines.iterator();
         SB sb = New.sb();
         while (it.hasNext()){
             String next = it.next();
             String[] splits = next.split("\t");
             String sql = splits[0].trim().replace("''","\"\"");
             String name = splits[1].trim();
             String fullSQL = sql.replace("'$'", "\""+name+"\"");
             sb.append(fullSQL).append("\r\n");
             System.out.println(fullSQL);
         }
         String content = sb.toString();
         com.x.commons.util.file.Files.createFile(com.x.commons.util.file.Files.getResourcesPath(),"sql.txt",content);

     }
    
}
