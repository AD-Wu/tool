package com.x.commons.util.file.listener;

import com.x.commons.util.file.Files;

import java.io.File;
import java.io.FileInputStream;

/**
 * @Desc：
 * @Author：AD
 * @Date：2020/3/25 11:15
 */
public class Test {
    public static void main(String[] args) throws Exception {
        FolderMonitor monitor = new FolderMonitor(Files.getResourcesPath());
        monitor.addFolderListener(new IFileListener() {
            @Override
            public void onModify(File file) {
                if (!file.isDirectory() && file.length() > 0) {
                    try (FileInputStream in = new FileInputStream(file)) {
                        System.out.println("modify:" + file.getName());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onCreate(File file) {
                if (!file.isDirectory()) {
                    if (file.length() == 0) {
                        System.out.println("create:" + file.getName());
                    }
                }


            }

            @Override
            public void onDelete(File file) {
                if (!file.isDirectory()) {
                    if (!file.exists()) {
                        System.out.println("删除->" + file.getName());
                    }
                }
            }
        });
        System.out.println("异步");
    }
}
