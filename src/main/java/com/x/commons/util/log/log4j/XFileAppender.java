package com.x.commons.util.log.log4j;

import ch.qos.logback.core.rolling.RollingFileAppender;
import com.x.commons.timming.Timer;
import com.x.commons.util.bean.New;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @Desc TODO
 * @Date 2020-03-21 11:47
 * @Author AD
 */
public class XFileAppender extends RollingFileAppender {
    
    static List<XFileAppender> appenders = New.list();
    
    public XFileAppender() {}
    
    public synchronized void setFile(String fileName, boolean appender, boolean bufferedIO, int bufferSize) {
        File file = new File(fileName);
        File parent = file.getParentFile();
        if (parent != null) {
            parent.mkdirs();
        }
        super.setFile(fileName);
        // if (bufferedIO) {
        //     addAppender(this);
        // }
    }
    
    public void flush() {
        try {
            super.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void flushAll() {
        new XLoggingHockThread().start();
    }
    
    private static synchronized void addAppender(XFileAppender appender) {
        appenders.add(appender);
    }
    
    static {
        XLoggingHockThread hock = new XLoggingHockThread();
        Runtime.getRuntime().addShutdownHook(hock);
        int period = 30 * 60 * 1000;
        Timer.get().add(hock, period, period);
    }
}
