package com.x.commons.local;

import com.x.commons.util.Systems;
import com.x.commons.util.string.Strings;

import java.awt.*;
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
    private static String defLanguage;

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
            String path = Strings.replace(defPath, defLanguage);
            defLocal = new LocalString();
            defLocal.load(path);
        }
        return defLocal;
    }

    /**
     * 获取区域性文本信息
     *
     * @param language 语言key，如：zh_CN,en_US
     * @return
     */
    public static LocalString getLocal(String language) {
        LocalString local = localMap.get(language);
        if (local != null) {
            return local;
        }
        synchronized (lock) {
            local = localMap.get(language);
            if (local != null) {
                return local;
            }
            local = new LocalString();
            if (!local.load(Strings.replace(defPath, language))) {
                local = getLocal();
            }
            return local;
        }

    }

    /**
     * 设置语言
     *
     * @param language 如：zh_CN,en_US
     */
    public static void setLanguage(String language) {
        if (Strings.isNull(language) && SUPPORT_LANGUAGES.contains(language)) {
            synchronized (langLock) {
                defLanguage = language;
                defLocal = null;
            }
        }
    }

    /**
     * 获取默认语言key，如：zh_CN,en_US
     *
     * @return
     */
    public static String getLanguage() {
        return defLanguage;
    }

    /**
     * 获取系统语言
     *
     * @return 如：zh_CN,en_US
     */
    public static String getSystemLanguage() {
        return Systems.getLanguage();
    }

    static {
        defLocal = null;
        // 默认中文
        defLanguage = zh_CN;
        // 默认路径
        defPath = "com/x/commons/local/data/{0}.properties";
        localMap = new ConcurrentHashMap<>();
        SUPPORT_LANGUAGES = new HashSet<>();
        // 添加默认支持的语言
        SUPPORT_LANGUAGES.add(en_US);
        SUPPORT_LANGUAGES.add(zh_CN);
    }

}
