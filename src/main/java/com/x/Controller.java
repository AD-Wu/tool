package com.x;

import com.x.commons.util.file.Files;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Map;

/**
 * @Desc：
 * @Author：AD
 * @Date：2020/1/6 9:50
 */
@RestController
public class Controller {


    @RequestMapping(value = "upload")
    public Object upload(MultipartHttpServletRequest request,
                         @RequestParam(value = "a", required = false) String aText,
                         @RequestParam(value = "b", required = false) int b) throws IOException {
        System.out.println("a="+aText);
        System.out.println("b="+b);
        String className = request.getClass().getName();
        System.out.println(className);
        Iterator<Map.Entry<String, MultipartFile>> it = request.getFileMap().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, MultipartFile> next = it.next();
            String key = next.getKey();
            MultipartFile file = next.getValue();
            String fileName = file.getName();
            String originalFilename = file.getOriginalFilename();
            File upload = Files.createFileAtResources("upload", originalFilename);
            System.out.println(upload.getName());
            file.transferTo(upload);
            System.out.println("key=" + key);
            System.out.println("fileName=" + fileName);
            System.out.println("originalFilename=" + originalFilename);
        }
        return LocalDateTime.now().toString();
    }
}
