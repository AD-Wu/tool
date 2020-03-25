package com.x.commons.util.file.listener;

import com.x.commons.util.file.Files;

import java.io.File;

/**
 * @Desc：
 * @Author：AD
 * @Date：2020/3/25 11:15
 */
public class Test {
    public static void main(String[] args) throws Exception {
        FolderMonitor monitor = new FolderMonitor(Files.getResourcesPath());
        monitor.addListener(new IFileListener() {
            @Override
            public void onModify(File file) {
                System.out.println("modify:" + file.getName());

            }

            @Override
            public void onCreate(File file) {
                System.out.println("create:" + file.getName());
            }

            @Override
            public void onDelete(File file) {
                System.out.println("delete:" + file.getName());
            }
        });

    }
}
