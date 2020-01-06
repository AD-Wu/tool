package com.x.commons.util.file;

import org.junit.jupiter.api.Test;

import java.io.File;

class FilesTest {

    @Test
    void createFolder() {
        boolean folder = Files.createFolder(Files.getResourcesPath() + "a/b/c");
        System.out.println(folder);
    }

    @Test
    void createFile(){
        String src = Files.getResourcesPath();
        File file = Files.createFile(src, "test.txt");
        System.out.println(file.getName());
    }

    @Test
    void createFileAtResource(){
        // File file = Files.createFileAtResource("src.yml");
        // System.out.println(file.getName());
        File f = Files.createFileAtResource("a/b/c", "folder.properties");
        System.out.println(f.getName());
    }
}