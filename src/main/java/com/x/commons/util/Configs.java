package com.x.commons.util;

import com.x.commons.parser.Parsers;
import com.x.commons.parser.core.IParser;
import com.x.commons.util.bean.New;
import com.x.commons.util.file.Files;
import com.x.commons.util.log.Logs;
import com.x.commons.util.prop.Props;
import com.x.commons.util.string.Strings;
import com.x.commons.util.yml.Ymls;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * @Desc
 * @Date 2019-12-01 16:41
 * @Author AD
 */
public final class Configs {
    
    // ------------------------ 变量定义 ------------------------
    
    private static Map<String, String> props;
    
    private static volatile boolean inited = false;
    
    private static final Object LOCK = new Object();
    
    // ------------------------ 构造方法 ------------------------
    
    private Configs() {}
    
    // ------------------------ 方法定义 ------------------------
    
    public static String get(String key) {
        return props.get(key);
    }
    
    public static <T> T get(String key, Class<T> returnType) {
        if (!inited) {
            synchronized (LOCK) {
                if (!inited) {
                    init();
                }
            }
        }
        String s = props.get(key);
        IParser<T, Object> parser = Parsers.getParser(returnType);
        if (parser != null) {
            T parse = null;
            try {
                return parser.parse(s);
            } catch (Exception e) {
                Logs.get(Configs.class).error(Strings.getExceptionTrace(e));
                e.printStackTrace();
            }
        }
        return null;
    }
    // ------------------------ 私有方法 ------------------------
    
    private static void init() {
        props = New.concurrentMap();
        String src = Files.getResourcesPath();
        File[] files = Files.getFiles(src, New.list());
        for (File file : files) {
            String name = file.getName();
            if (name.endsWith(".yml") || name.endsWith(".yaml")) {
                try {
                    Map<String, String> prop = Ymls.loadAsFlatMap(file.getPath());
                    props.putAll(prop);
                } catch (Exception e) {
                    Logs.get(Configs.class).error(Strings.getExceptionTrace(e));
                }
            } else if (name.endsWith(".properties")) {
                try {
                    Map<String, String> load = Props.load(file.getPath());
                    props.putAll(load);
                } catch (Exception e) {
                    Logs.get(Configs.class).error(Strings.getExceptionTrace(e));
                }
            }
        }
        inited = true;
    }
    
    public static void main(String[] args) throws Exception {
        System.out.println(props);
        LocalDateTime time = get("user.birthday", LocalDateTime.class);
        System.out.println(time);
        System.out.println(props);
    }
    
}
