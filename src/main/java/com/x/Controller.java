// package com.x;
//
// import com.x.commons.util.file.Files;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.multipart.MultipartFile;
// import org.springframework.web.multipart.MultipartHttpServletRequest;
//
// import javax.servlet.ServletOutputStream;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;
// import java.io.File;
// import java.io.FileInputStream;
// import java.io.IOException;
// import java.time.LocalDateTime;
// import java.util.Iterator;
// import java.util.Map;
//
// /**
//  * @Desc：
//  * @Author：AD
//  * @Date：2020/1/6 9:50
//  */
// @RestController
// public class Controller {
//
//     @RequestMapping(value = "post/form")
//     public Object form(
//             @RequestParam(value = "name", required = false) String name,
//             @RequestParam(value = "age", required = false) int age,
//             @RequestParam(value = "sex", required = false) boolean sex) {
//         System.out.println("name=" + name + ",age=" + age + ",sex=" + sex);
//         return "AD-Form-" + LocalDateTime.now();
//     }
//
//     @RequestMapping("post/json")
//     public Object post(@RequestBody Map map) {
//         System.out.println(map);
//         return "AD-JSON-" + LocalDateTime.now();
//     }
//
//     @RequestMapping(value = "upload")
//     public Object upload(MultipartHttpServletRequest request,
//             @RequestParam(value = "a", required = false) String aText,
//             @RequestParam(value = "b", required = false) int b) throws IOException {
//         System.out.println("a=" + aText);
//         System.out.println("b=" + b);
//         String className = request.getClass().getName();
//         System.out.println(className);
//         Iterator<Map.Entry<String, MultipartFile>> it = request.getFileMap().entrySet().iterator();
//         while (it.hasNext()) {
//             Map.Entry<String, MultipartFile> next = it.next();
//             String key = next.getKey();
//             MultipartFile file = next.getValue();
//             String fileName = file.getName();
//             String originalFilename = file.getOriginalFilename();
//             File upload = Files.createFileAtResources("upload", originalFilename);
//             System.out.println(upload.getName());
//             file.transferTo(upload);
//             System.out.println("key=" + key);
//             System.out.println("fileName=" + fileName);
//             System.out.println("originalFilename=" + originalFilename);
//         }
//         return LocalDateTime.now().toString();
//     }
//
//     @RequestMapping("download")
//     public Object download(HttpServletRequest request, HttpServletResponse response) {
//         String filename = "AD-File";
//         response.setContentType("multipart/form-data");
//         response.setHeader("content-disposition", "attachment;filename=" + filename);
//         File file = Files.getFile("upload/一寸彩照.jpg");
//         byte[] data = new byte[0];
//         try {
//             // ImageInputStream in = new FileImageInputStream(file);
//             // long len = in.length();
//             // int available = (int) len;
//
//             FileInputStream in = new FileInputStream(file);
//             int available = in.available();
//             data = new byte[available];
//             in.read(data, 0, available);
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//         try {
//             ServletOutputStream out = response.getOutputStream();
//             out.write(data, 0, data.length);
//             response.flushBuffer();
//             out.close();
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//         return null;
//     }
//
// }
