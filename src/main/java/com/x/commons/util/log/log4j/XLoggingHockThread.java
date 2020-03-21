package com.x.commons.util.log.log4j;

/**
 * @Desc
 * @Date 2020-03-21 11:51
 * @Author AD
 */
public class XLoggingHockThread extends Thread {
    
    XLoggingHockThread(){}
    
    @Override
    public void run() {
        XFileAppender[] appenders = XFileAppender.appenders.toArray(new XFileAppender[0]);
        for (XFileAppender appender : appenders) {
            appender.flush();
        }
    }
    
}
