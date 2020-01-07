package com.x.commons.util;

import com.x.commons.local.Locals;
import com.x.commons.util.bean.New;
import com.x.commons.util.bean.SB;
import com.x.commons.util.file.Files;
import com.x.commons.util.string.Strings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;

/**
 * @Desc TODO
 * @Date 2019-11-02 12:59
 * @Author AD
 */
public final class Systems {
    
    private Systems() {
    }
    
    public static String getSystem() {
        return System.getProperty("os.name");
    }
    
    /**
     * 系统运行时信息
     *
     * @return 系统运行状态，CPU: {0} 核心; 最大内存: {1} M; 已使用: {2} M; 空闲: {3} M; 线程数: {4}
     */
    public static String runtimeInfo() {
        
        Runtime run = Runtime.getRuntime();
        int cpu = run.availableProcessors();
        double max = getMemory(run.maxMemory());
        double total = getMemory(run.totalMemory());
        double free = getMemory(run.freeMemory());
        int size = Thread.getAllStackTraces().size();
        
        return Locals.text("commons.system.status", cpu, max, total, free, size);
    }
    
    /**
     * 查询BIOS序列号
     *
     * @return
     */
    public static String getBiosID() {
        String id = getWindowsHardwareID("bios", "SerialNumber");
        return Strings.isNull(id) ? "" : id;
    }
    
    /**
     * 查询主板序列号
     *
     * @return
     */
    public static String getBaseBoardID() {
        String id = getWindowsHardwareID("baseboard", "SerialNumber");
        return Strings.isNull(id) ? "" : id;
    }
    
    /**
     * 获取CPU序列号
     *
     * @return
     */
    public static String getCpuID() {
        String id = getWindowsHardwareID("cpu", "processorid");
        return Strings.isNull(id) ? "" : id;
    }
    
    /**
     * 获取网卡信息
     *
     * @return
     */
    public static String getNetCard() {
        String id = getWindowsHardwareID("nicconfig", "macaddress");
        return Strings.isNull(id) ? "" : id;
    }
    
    /**
     * Windows系统下使用wmic命令查看硬件信息
     * <p>
     * 1、查询网卡信息：wmic nicconfig get macaddress
     * <p>
     * 2、查询cpu序列号：wmic cpu get processorid
     * <p>
     * 3、查询主板序列号：wmic baseboard get serialnumber
     * <p>
     * 4、查询BIOS序列号：wmic bios get serialnumber
     *
     * @param device
     * @param content
     *
     * @return
     */
    private static String getWindowsHardwareID(String device, String content) {
        if (!Strings.isNull(device) && !Strings.isNull(content)) {
            Process process = null;
            InputStreamReader inReader = null;
            BufferedReader buf = null;
            boolean succeed = false;
            
            try {
                process = Runtime.getRuntime().exec(new String[]{"wmic", device, "get", content});
                process.getOutputStream().close();
                inReader = new InputStreamReader(process.getInputStream());
                buf = new BufferedReader(inReader);
                String line = buf.readLine();
                if (line == null || !content.toUpperCase().equals(Strings.toUppercase(line))) {
                    return null;
                } else {
                    succeed = true;
                    SB sb = New.sb();
                    
                    while (true) {
                        String msg;
                        try {
                            if (!Strings.isNull((msg = buf.readLine()))) {
                                sb.append(msg.trim());
                                continue;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        
                        msg = sb.length() > 0 ? sb.toString() : null;
                        return msg;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } finally {
                if (process != null) {
                    try {
                        if (buf != null) {
                            buf.close();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    
                    try {
                        if (inReader != null) {
                            inReader.close();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    
                    if (succeed) {
                        try {
                            process.getInputStream().close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        
                        try {
                            process.destroy();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                
            }
        } else {
            return null;
        }
    }
    
    private static double getMemory(long memory) {
        return Math.floor((double) memory / 1024.0D / 1024.0D * 100.0D) / 100.0D;
    }
    
    /**
     * 获取系统默认语言
     *
     * @return 如：zh_CN
     */
    public static String getLanguage() {
        Locale locale = Locale.getDefault();
        String lang = locale.getLanguage() + "_" + locale.getCountry();
        return lang;
    }
    
    public static void main(String[] args) {

        Properties prop = System.getProperties();
        Set<String> names = prop.stringPropertyNames();
        int i = 0;
        SB sb = New.sb();
        for (String name : names) {
            String property = System.getProperty(name);
            // if (property.length() > 50) {
            //     continue;
            // }
            sb.append(++i + ":" + name + " = " + property).append("\n");
        }
        
        String srcPath = Files.getResourcesPath();
        System.out.println(srcPath);
        Files.createFile(srcPath, "application.properties", sb.toString());
        System.out.println(getSystem());
    }
    
}
