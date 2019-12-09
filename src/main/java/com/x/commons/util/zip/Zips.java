package com.x.commons.util.zip;

import com.ax.commons.local.LocalManager;
import com.x.commons.util.bean.ByteArray;
import com.x.commons.util.bean.New;
import com.x.commons.util.collection.XArrays;
import com.x.commons.util.file.Files;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.*;


/**
 * @Desc 压缩文件工具类
 * @Date 2019-11-25 20:27
 * @Author AD
 */
public final class Zips {
    
    // ------------------------ 成员变量 ------------------------
    
    private static final int BUFFER_SIZE = 1024;
    
    // ------------------------ 构造方法 ------------------------
    
    private Zips() {}
    
    // ------------------------ 成员方法 ------------------------
    
    /**
     * 压缩字节
     *
     * @param data 被压缩数据
     *
     * @return byte[] 被压缩之后的数据
     *
     * @throws Exception
     */
    public static byte[] compressBytes(byte[] data) throws Exception {
        byte[] result = new byte[0];
        if (XArrays.isEmpty(data)) {
            return data;
        }
        
        Deflater deflater = new Deflater();
        deflater.reset();
        deflater.setInput(data);
        deflater.finish();
        
        try (ByteArray out = New.byteArray(data.length);) {
            byte[] buf = new byte[BUFFER_SIZE];
            while (true) {
                if (!deflater.finished()) {
                    int len = deflater.deflate(buf);
                    if (len > 0) {
                        out.write(buf, 0, len); continue;
                    }
                }
                result = out.toByteArray();
                return result;
            }
        } catch (Exception e) {
            throw e;
        }
        
    }
    
    /**
     * 解压缩
     *
     * @param data 被解压数据
     *
     * @return 解压之后的数据
     *
     * @throws Exception
     */
    public static byte[] decompressBytes(byte[] data) throws Exception {
        byte[] result = new byte[0];
        if (XArrays.isEmpty(data)) {
            return data;
        }
        Inflater inflater = new Inflater();
        inflater.reset();
        inflater.setInput(data);
        
        try (ByteArray out = New.byteArray(data.length);) {
            byte[] buf = new byte[BUFFER_SIZE];
            
            while (true) {
                if (!inflater.finished()) {
                    int len = inflater.inflate(buf);
                    if (len > 0) {
                        out.write(buf, 0, len); continue;
                    }
                }
                result = out.toByteArray();
                return result;
            }
        } catch (Exception e) {
            throw e;
        }
    }
    
    /**
     * 压缩文件
     *
     * @param path       被压缩文件所在路径
     * @param targetPath 压缩之后的存放路径
     *
     * @return
     *
     * @throws Exception
     */
    public static boolean zip(String path, String targetPath) throws Exception {
        boolean succeed;
        File file = new File(path);
        if (!file.exists()) {
            succeed = false; return succeed;
        }
        try (FileOutputStream fileOut = new FileOutputStream(targetPath);
             ZipOutputStream zipOut = new ZipOutputStream(fileOut);) {
            if (file.isDirectory()) {
                zipMore(file, zipOut, file.getName());
            } else {
                zipOne(file, zipOut, file.getName());
            } succeed = true;
        } catch (Exception e) {
            throw new Exception(LocalManager.defaultText("commons.zip.file.zip", path, targetPath, e.getMessage()));
        } return succeed;
    }
    
    // ------------------------ 私有方法 ------------------------
    
    /**
     * 压缩文件夹嵌套的数据
     *
     * @param file
     * @param zipOut
     * @param supName
     *
     * @throws Exception
     */
    private static void zipMore(File file, ZipOutputStream zipOut, String supName) throws Exception {
        ZipEntry entry = new ZipEntry(supName + "/");
        zipOut.putNextEntry(entry);
        File[] files = file.listFiles();
        if (files != null) {
            for (int i = 0, L = files.length; i < L; ++i) {
                File sub = files[i];
                String name = supName + "/" + sub.getName();
                if (sub.isDirectory()) {
                    zipMore(sub, zipOut, name);
                } else {
                    zipOne(sub, zipOut, name);
                }
            }
        }
        
    }
    
    /**
     * 压缩单个文件
     *
     * @param file
     * @param zipOut
     * @param name
     *
     * @throws Exception
     */
    private static void zipOne(File file, ZipOutputStream zipOut, String name) throws Exception {
        
        try (FileInputStream in = new FileInputStream(file);) {
            ZipEntry entry = new ZipEntry(name);
            zipOut.putNextEntry(entry);
            byte[] buf = new byte[5120];
            int len;
            while ((len = in.read(buf)) != -1) {
                zipOut.write(buf, 0, len);
            }
            zipOut.flush();
        } catch (FileNotFoundException e) {
            throw e;
        }
    }
    
    public static boolean unZip(String zipPath, String target) throws Exception {
        target = target.endsWith(Files.SP) ? target : target + Files.SP;
        byte[] buf = new byte[BUFFER_SIZE];
        File zip = new File(zipPath);
        if (!zip.exists()) {
            return false;
        } else {
            Files.createFolder(target);
            try {
                ZipFile zipFile = new ZipFile(zip);
                Enumeration enums = zipFile.entries();
                ZipEntry entry = null;
                while (true) {
                    while (enums.hasMoreElements()) {
                        entry = (ZipEntry) enums.nextElement();
                        File file = new File(target + entry.getName());
                        if (entry.isDirectory()) {
                            file.mkdirs();
                        } else {
                            if (!file.getParentFile().exists()) {
                                file.getParentFile().mkdirs();
                            }
                            FileOutputStream out = new FileOutputStream(file);
                            InputStream in = zipFile.getInputStream(entry);
                            int len;
                            while ((len = in.read(buf)) > 0) {
                                out.write(buf, 0, len);
                            } out.flush();
                            out.close();
                        }
                    }
                    zipFile.close();
                    return true;
                }
            } catch (Exception e) {
                throw new Exception(
                        LocalManager.defaultText("commons.zip.file.unzip", zipPath, target, e.getMessage()));
            }
        }
    }
    
}
