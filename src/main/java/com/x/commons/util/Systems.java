package com.x.commons.util;

import com.ax.commons.encrypt.md5.MD5;
import com.x.commons.local.Locals;
import com.x.commons.util.bean.New;
import com.x.commons.util.bean.SB;
import com.x.commons.util.string.Strings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

/**
 * @Desc TODO
 * @Date 2019-11-02 12:59
 * @Author AD
 */
public final class Systems {

    private Systems() {
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

    public static String getPcID() {
        String id = getWindowsHardwareID("bios", "SerialNumber");
        if (id.length() > 0) {
            try {
                return new MD5().encode(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    private static String getWindowsHardwareID(String bios, String SerialNumber) {
        if (!Strings.isNull(bios) && !Strings.isNull(SerialNumber)) {
            Process process = null;
            InputStreamReader inReader = null;
            BufferedReader buf = null;
            boolean succeed = false;

            try {
                process = Runtime.getRuntime().exec(new String[]{"wmic", bios, "get", SerialNumber});
                process.getOutputStream().close();
                inReader = new InputStreamReader(process.getInputStream());
                buf = new BufferedReader(inReader);
                String line = buf.readLine();
                if (line == null || !SerialNumber.toUpperCase().equals(Strings.toUppercase(line))) {
                    return null;
                } else {
                    succeed = true;
                    SB sb = New.sb();

                    while (true) {
                        String msg;
                        try {
                            if ((msg = buf.readLine()) != null) {
                                msg = msg.trim();
                                if (msg.length() > 0) {
                                    sb.append(msg);
                                }
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
        String s = runtimeInfo();
        System.out.println(s);
        String pcID = getPcID();
        System.out.println(pcID);
    }

}
