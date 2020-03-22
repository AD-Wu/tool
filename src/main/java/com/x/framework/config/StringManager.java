package com.x.framework.config;

import com.ax.commons.local.LocalManager;
import com.x.commons.util.bean.New;

import java.util.Map;

/**
 * @Desc
 * @Date 2020-03-21 22:07
 * @Author AD
 */
public class StringManager {
    
    private String name;
    
    private Map<String, StringText> localMap = New.map();
    
    private final Object lock = new Object();
    
    public StringManager() {
    }
    
    public StringManager(String name) {
        this.name = name;
    }
    
    public StringText getDefaultLocalString() {
        return this.getLocalString(LocalManager.getDefaultLangKey());
    }
    
    public StringText getLocalString(String langKey) {
        StringText local = localMap.get(langKey);
        if (local != null) {
            return local;
        } else {
            synchronized (lock) {
                local = localMap.get(langKey);
                if (local != null) {
                    return local;
                } else {
                    try {
                        local = new StringText(name, langKey);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                    localMap.put(langKey, local);
                    return local;
                }
            }
        }
    }
    
}
