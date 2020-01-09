package com.x.commons.util.log;

import com.x.commons.util.bean.New;
import com.x.commons.util.bean.SB;
import com.x.commons.util.date.DateTimes;
import com.x.commons.util.file.Files;
import com.x.commons.util.string.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @Desc TODO
 * @Date 2019-11-01 22:38
 * @Author AD
 */
public class Logs {

    public static Logger get(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }

    public static boolean createLogbackXML(String filename) throws Exception {
        // 读取文件内容
        String logPath = Files.getCurrentClassPath(Logs.class) + "log.xml";
        String txt = Files.readTxt(logPath);
        // 修正目标文件名
        String fixName = fixFilename(filename, false);
        // 获取目标路径
        String targetPath = Files.getResourcesPath();
        // 判断文件是否存在
        File file = new File(targetPath + fixName);
        if (file.exists()) {
            fixName = fixFilename(fixName, true);
        }
        // 生成文件
        return Files.createFile(targetPath, fixName, txt);

    }

    private static String fixFilename(String filename, boolean timeMS) {
        if (Strings.isNull(filename, ".xml")) {
            return "logback.xml";
        }
        if (!filename.endsWith(".xml")) {
            if (timeMS) {
                SB sb = New.sb(filename, "-", DateTimes.now(DateTimes.NO_MARK), ".xml");
                return sb.get();
            } else {
                return filename + ".xml";
            }
        } else {
            if (timeMS) {
                int end = filename.lastIndexOf(".");
                filename = filename.substring(0, end);
                SB sb = New.sb(filename, "-", DateTimes.now(DateTimes.NO_MARK), ".xml");
                return sb.get();
            } else {
                return filename;
            }
        }
    }

}
