// package com.x;
//
// import org.springframework.boot.SpringApplication;
// import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
// import org.springframework.context.ApplicationContext;
//
// import com.x.commons.util.file.Files;
// import com.x.commons.util.test.AutoRun;
//
// @SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
// public class App {
//
//     public static void main(String[] args) throws Exception {
//         ApplicationContext run = SpringApplication.run(App.class, args);
//         // Auto.run(App.class);
//     }
//
//     @AutoRun
//     public static void test(){
//         String[] jarsPath = Files.getJarsPath();
//         for (String s : jarsPath) {
//             System.out.println(s);
//         }
//     }
//
// }
//
