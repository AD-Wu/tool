package com.x.commons.local;

import com.x.commons.util.Systems;
import com.x.commons.util.string.Strings;

import javax.swing.*;
import java.awt.*;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static com.x.commons.enums.Language.en_US;
import static com.x.commons.enums.Language.zh_CN;

/**
 * @Desc 区域性文本管理者
 * @Date 2019-11-02 23:48
 * @Author AD
 */
public final class Locals {
    
    /**
     * 中文字体
     */
    private static final String[] zh_CN_FONTS = new String[]{"微软雅黑", "黑体", "宋体"};
    
    /**
     * 默认字体大小
     */
    private static final int defaultFontSize = 16;
    
    /**
     * 默认字体
     */
    private static Font defaultFont = null;
    
    /**
     * 默认区域性文本，默认：zh_CN
     */
    private static LocalString defLocal;
    
    /**
     * 默认语言key，默认：zh_CN
     */
    private static String defaultLangKey;
    
    /**
     * 区域文本存放路径，默认：com/x/commons/local/data/{0}.properties
     */
    private static String defPath;
    
    /**
     * 语言key所对应的锁，一个公共资源对应一把锁
     */
    private static Object langLock = new Object();
    
    /**
     * 公共锁
     */
    private static Object lock = new Object();
    
    /**
     * 区域性文本Map，language-localString
     */
    private static Map<String, LocalString> localMap;
    
    /**
     * 所支持的language集合，如：zh_CN，en_US
     */
    private static Set<String> SUPPORT_LANGUAGES;
    
    /**
     * 根据key将args替换到区域性文本信息中
     *
     * @param key  配置在com/x/commons/local/en_US.properties文件中的key
     * @param args 需要替换到参数
     *
     * @return
     */
    public static String text(String key, Object... args) {
        LocalString local = getLocal();
        return local == null ? "" : local.text(key, args);
    }
    
    /**
     * 获取默认区域性文本，默认zh_CN
     *
     * @return
     */
    public synchronized static LocalString getLocal() {
        // defaultLocal也是公共资源，和defaultLanguage在一起需要多把锁
        if (defLocal != null) return defLocal;
        // 存在多个公共资源，需要多把锁
        synchronized (langLock) {
            if (defLocal != null) return defLocal;
            String path = Strings.replace(defPath, defaultLangKey);
            defLocal = new LocalString();
            defLocal.load(path);
        }
        return defLocal;
    }
    
    /**
     * 获取区域性文本信息
     *
     * @param langKey 语言key，如：zh_CN,en_US
     *
     * @return
     */
    public static LocalString getLocal(String langKey) {
        LocalString local = localMap.get(langKey);
        if (local != null) {
            return local;
        }
        synchronized (lock) {
            local = localMap.get(langKey);
            if (local != null) {
                return local;
            }
            local = new LocalString();
            if (!local.load(Strings.replace(defPath, langKey))) {
                local = getLocal();
            }
            return local;
        }
        
    }
    
    /**
     * 设置语言
     *
     * @param langKey 如：zh_CN,en_US
     */
    public static void setDefaultLangKey(String langKey) {
        if (!Strings.isNull(langKey) && SUPPORT_LANGUAGES.contains(langKey)) {
            synchronized (langLock) {
                defaultLangKey = langKey;
                defLocal = null;
            }
        }
    }
    
    /**
     * 获取默认语言key，如：zh_CN,en_US
     *
     * @return
     */
    public static String getDefaultLangKey() {
        return defaultLangKey;
    }
    
    /**
     * 获取系统语言
     *
     * @return 如：zh_CN,en_US
     */
    public static String getSystemLangKey() {
        return Systems.getLangKey();
    }
    
    /**
     * 获取大小为16的字体
     *
     * @return
     */
    public static Font getDefaultFont() {
        return getDefaultFont(defaultFontSize);
    }
    
    /**
     * 获取默认字体
     *
     * @param size 字体大小
     *
     * @return
     */
    public static Font getDefaultFont(int size) {
        if (!"zh_CN".equals(defaultLangKey)) {
            return defaultFont.deriveFont(0, (float) size);
        } else {
            for (String fontName : zh_CN_FONTS) {
                Font f = new Font(fontName, 0, size);
                if (fontName.equals(f.getFamily())) {
                    return f;
                }
            }
            return defaultFont.deriveFont(0, (float) size);
        }
    }
    
    public static boolean setDefaultLookAndFeel() {
        try {
            UIDefaults e = UIManager.getDefaults();
            Enumeration eKeys = e.keys();
            Font font = getDefaultFont();
            
            while (true) {
                String key;
                do {
                    if (!eKeys.hasMoreElements()) {
                        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                        return true;
                    }
                    key = eKeys.nextElement().toString();
                } while (key.indexOf("font") == -1 && key.indexOf("Font") == -1);
                
                UIManager.put(key, font);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    static {
        defLocal = null;
        // 默认中文
        defaultLangKey = en_US;
        // 默认路径
        defPath = "com/x/commons/local/data/{0}.properties";
        localMap = new ConcurrentHashMap<>();
        SUPPORT_LANGUAGES = new HashSet<>();
        // 添加默认支持的语言
        SUPPORT_LANGUAGES.add(en_US);
        SUPPORT_LANGUAGES.add(zh_CN);
    }
    
}
