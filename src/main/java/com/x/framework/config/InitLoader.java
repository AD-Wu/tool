package com.x.framework.config;

import com.x.commons.config.Configure;

import java.util.HashMap;
import java.util.Map;

/**
 * @Desc
 * @Date 2020-03-21 22:01
 * @Author AD
 */
public final class InitLoader {
    private static final Map<String, Configure> configs = new HashMap();
    private static final Object lock = new Object();
    
    public InitLoader() {
    }
    
    public static Configure getConfigure(String fileName) {
        if (fileName != null && fileName.length() != 0) {
            Configure config = configs.get(fileName);
            if (config == null) {
                synchronized(lock) {
                    config = configs.get(fileName);
                    if (config != null) {
                        return config;
                    }
                    
                    config = new Configure();
                    config.load(fileName);
                    configs.put(fileName, config);
                }
            }
            
            return config;
        } else {
            return new Configure();
        }
    }
    
    public static void clearCaches() {
        synchronized(lock) {
            configs.clear();
        }
    }
}
