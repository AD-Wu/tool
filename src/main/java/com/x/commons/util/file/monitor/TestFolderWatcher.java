package com.x.commons.util.file.monitor;

import com.x.commons.util.file.Files;

import java.io.File;

/**
 * @Desc 文件监测测试，主要用于配置文件的监测
 * @Date 2019-10-26 11:56
 * @Author AD
 */
public class TestFolderWatcher {

    public static void main(String[] args) throws Exception {
        FolderWatcher watcher = FolderWatcher.getModifyWatcher(Files.getResourcesPath(), 10);
        watcher.addWatched(new FileWatched("application.yml") {
            @Override
            public void change(File file) {
                String name = file.getName();
                String path = file.getPath();
                System.out.println(name);
                System.out.println(path);
                boolean b = file.canRead();
            }
        });
        watcher.start();

        FolderWatcher modify = FolderWatcher.getModifyWatcher(
                Files.getResourcesPath() + "x-framework", 5);
        modify.addWatched(new FileWatched("value.properties") {
            @Override
            public void change(File file) {
                String name = file.getName();
                String path = file.getPath();
                System.out.println(name);
                System.out.println(path);
            }
        });
        modify.start();



        System.out.println("不阻塞");
    }


}
