package com.x;

import com.x.commons.util.file.Files;
import org.springframework.web.bind.annotation.RequestMapping;
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


    @RequestMapping("upload")
    public Object upload(MultipartHttpServletRequest request) throws IOException {
        String className = request.getClass().getName();
        System.out.println(className);
        Iterator<Map.Entry<String, MultipartFile>> it = request.getFileMap().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, MultipartFile> next = it.next();
            String key = next.getKey();
            MultipartFile file = next.getValue();
            String fileName = file.getName();
            String folderPath = Files.getResourcesPath() + "upload";
            boolean folder = Files.createFolder(folderPath);
            Files.createFile(folderPath,"upload.yml","");
            File upload = new File(folderPath + Files.SP + "upload.yml");
            file.transferTo(upload);
            System.out.println("key=" + key);
            System.out.println("fileName=" + fileName);
            System.out.println("path=" + folderPath);
            System.out.println("create=" + folder);
        }
        return LocalDateTime.now().toString();
    }
}
