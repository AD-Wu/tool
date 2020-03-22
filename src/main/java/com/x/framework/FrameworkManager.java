package com.x.framework;

import com.x.commons.local.Locals;
import com.x.commons.util.Systems;
import com.x.commons.util.bean.New;
import com.x.commons.util.collection.XArrays;
import com.x.commons.util.file.Files;
import com.x.commons.util.log.Logs;
import com.x.commons.util.reflact.Loader;
import com.x.commons.util.string.Strings;
import com.x.protocol.config.ProtocolConfigure;
import com.x.protocol.config.ServiceConfig;
import com.x.protocol.core.IStatusNotification;
import org.slf4j.Logger;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @Desc
 * @Date 2019-12-05 21:51
 * @Author AD
 */
public final class FrameworkManager {
    
    private static final Map<String, Framework> frameworks = New.concurrentMap();
    
    private static final Object LOCK = new Object();
    
    private static final Properties props = new Properties();
    
    private static final Logger LOG = Logs.get("FrameworkManager");
    
    static {
        try (InputStream in = Loader.get().getResourceAsStream("x-framework.properties");
             InputStreamReader reader = Files.getUnicodeReader(in)) {
            props.load(reader);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void init(String timeZone, String langKey, String logConfigFile) {
        // 获取系统默认时区
        String defaultTZ = TimeZone.getDefault().getID();
        // 获取系统LangKey
        String defaultLang = Locals.getSystemLangKey();
        if (Strings.isNotNull(timeZone)) {
            timeZone = timeZone.replace("UTC", "GMT").split("\\|")[0];
            // 默认时区与当前时区不一致
            if (!defaultTZ.equals(timeZone)) {
                TimeZone tz = TimeZone.getTimeZone(timeZone);
                TimeZone.setDefault(tz);
            }
        }
        // 设置语言
        if (Strings.isNotNull(langKey) &&
            !langKey.equals(Locals.getDefaultLangKey())) {
            Locals.setDefaultLangKey(langKey);
        }
        // 设置外观
        Locals.setDefaultLookAndFeel();
        if (!Logs.createLogbackXML()) {
            System.exit(0);
        }
        
        LOG.warn("========================== INIT ==========================");
        LOG.info(Locals.text("framework.init", defaultTZ, TimeZone.getDefault()
                .getID(), defaultLang, Locals.getDefaultLangKey()));
        LOG.info(Systems.runtimeInfo());
        
    }
    
    public static void start(String configFile, IStatusNotification status) {
        // 加载xml配置文件
        List<ServiceConfig> configs = ProtocolConfigure.loadServices(configFile);
        if (!XArrays.isEmpty(configs)) {
            List<String> fails = New.list();
            synchronized (LOCK) {
out:
                {
                    if (status != null) {
                        status.onLoadConfig(configs);
                    }
                    Iterator<ServiceConfig> msg = configs.iterator();
                    while (true) {
                        if (!msg.hasNext()) {
                            break out;
                        }
                        ServiceConfig config = msg.next();
                        if (config != null && config.isEnabled()) {
                            String name = config.getName();
                            Framework framework = new Framework();
                            frameworks.put(name, framework);
                            
                            try {
                                if (!framework.start(config, status)) {
                                    stop();
                                    if (status != null) {
                                        status.onStop();
                                    }
                                    break;
                                }
                            } catch (Exception e) {
                                LOG.error(Locals.text("protocol.service.start.err", name, e.getMessage()));
                                fails.add(name);
                                break out;
                            }
                        }
                    }
                    return;
                }
            }
            if (status != null && fails.size() > 0) {
                Iterator<String> it = fails.iterator();
                while (it.hasNext()) {
                    String name = it.next();
                    String errMsg = Locals.text("protocol.service.start.err", name);
                    try {
                        status.onError(name, null, errMsg);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                stop();
                status.onStop();
                
            }
        }
    }
    
    public static void stop() {
        synchronized (LOCK) {
            Framework[] fs = frameworks.values().toArray(new Framework[0]);
            frameworks.clear();
            Arrays.stream(fs).forEach(f -> {
                f.stop();
            });
            
        }
    }
    
    public static Framework getFramework(String name) {
        return frameworks.get(name);
    }
    
    public static Framework[] getFrameworks() {
        return frameworks.values().toArray(new Framework[0]);
    }
    
    public static String getVersion() {
        return props.getProperty("framework.version");
    }
    
}
