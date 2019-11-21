package com.x.commons.util.file;

import com.x.commons.util.reflact.Loader;
import lombok.SneakyThrows;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * @Date 2018-12-20 22:36
 * @Author AD
 */
public final class Files {

    public static void main(String[] args) throws IOException {
        String path = "/Users/sunday/Java/StudyVideo/SpringMVC";
        // String old = "北京动力节点-SpringMVC4-";
        // String newChar = "";
        // editNames(path, old, newChar);

    }

    private static final ClassLoader LOADER = Loader.get();

    public static void editNames(String path, String old, String newChar) {
        Arrays.stream(getFiles(path, old)).forEach(f -> f.renameTo(new File(f.getAbsolutePath().replace(old, newChar))));
    }

    /**
     * 获取某个文件夹路径，不用Paths.get(path),会报找不到文件异常
     *
     * @param path 必须是一个文件夹路径
     * @return
     * @throws Exception
     */
    public static Path getPath(String path) throws Exception {
        URL url = LOADER.getResource(path);
        return Paths.get(url.toURI());
    }

    /**
     * 适用于获取项目路径下的文件，如编译后的根路径或根路径下的某个文件夹路径
     *
     * @param path 如：项目根路径-"/"或""，根路径下的文件夹路径为文件夹名，如：config
     * @return java.io.File
     * @author AD
     * @date 2019-10-17 22:03
     */
    public static File getFile(String path) {
        URL url = LOADER.getResource(path);
        try {
            File file = new File(url.toURI());
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 适用于获取电脑文件
     *
     * @param path 如："/Users/sunday/Java/StudyVideo/SpringMVC"为文件夹路径
     * @return path路径下于pattern匹配的所有文件
     * @author AD
     * @date 2019-10-17 22:00
     */
    public static File[] getFiles(String path, String pattern) {
        return new File(path).listFiles(f -> f.getName().contains(pattern));
    }

    @SneakyThrows
    public static File[] getFiles(String packageName) {
        final URI uri = LOADER.getResource(packageName.replace(".", File.separator)).toURI();
        return new File(uri).listFiles();
    }

    @SneakyThrows
    public static InputStreamReader getUnicodeReader(InputStream in, String encoding) {
        if (in == null) {
            return null;
        } else {
            byte[] buf = new byte[4];
            PushbackInputStream back = new PushbackInputStream(in, 4);
            int byteNums = back.read(buf, 0, buf.length);
            int unread;
            if (buf[0] == 0 && buf[1] == 0 && buf[2] == -2 && buf[3] == -1) {
                encoding = "UTF-32BE";
                unread = byteNums - 4;
            } else if (buf[0] == -1 && buf[1] == -2 && buf[2] == 0 && buf[3] == 0) {
                encoding = "UTF-32LE";
                unread = byteNums - 4;
            } else if (buf[0] == -17 && buf[1] == -69 && buf[2] == -65) {
                encoding = "UTF-8";
                unread = byteNums - 3;
            } else if (buf[0] == -2 && buf[1] == -1) {
                encoding = "UTF-16BE";
                unread = byteNums - 2;
            } else if (buf[0] == -1 && buf[1] == -2) {
                encoding = "UTF-16LE";
                unread = byteNums - 2;
            } else {
                if (encoding == null || encoding.equals("")) {
                    encoding = Charset.defaultCharset().name();
                }

                unread = byteNums;
            }

            if (unread > 0) {
                back.unread(buf, byteNums - unread, unread);
            }

            return new InputStreamReader(back, encoding);
        }
    }

}
