package com.x.commons.util.date;

import com.x.commons.util.bean.New;
import com.x.commons.util.date.parser.BaseDateTimeParser;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Desc TODO
 * @Date 2020-01-11 13:02
 * @Author AD
 */
class DateTimeParsers {
    
    private static int index = 0;
    
    private static volatile boolean inited = false;
    
    private static final Object INIT_LOCK = new Object();
    
    private static final Lock LOCK = new ReentrantLock();
    
    private static List<BaseDateTimeParser> parsers;
    
    private static int parserCount = 0;
    
    private DateTimeParsers() {}
    
    static LocalDateTime autoParse(String dateTime) {
        if (!inited) {
            synchronized (INIT_LOCK) {
                if (!inited) {
                    init();
                }
            }
        }
        LOCK.lock();
        if (end()) {
            index = 0;
            return null;
        }
        BaseDateTimeParser parser = parsers.get(index);
        try {
            LocalDateTime parse = parser.parse(dateTime);
            index = 0;
            return parse;
        } catch (Exception e) {
            index++;
            LocalDateTime parse = autoParse(dateTime);
            return parse;
        } finally {
            LOCK.unlock();
        }
        
    }
    
    private static boolean end() {
        return index >= parserCount;
    }
    
    private static void init() {
        parsers = New.list();
        ServiceLoader<BaseDateTimeParser> load = ServiceLoader.load(BaseDateTimeParser.class);
        Iterator<BaseDateTimeParser> it = load.iterator();
        while (it.hasNext()) {
            BaseDateTimeParser parser = it.next();
            parsers.add(parser);
        }
        parserCount = parsers.size();
        inited = true;
    }
    
}
