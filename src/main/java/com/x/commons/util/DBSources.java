package com.x.commons.util;

import com.thoughtworks.xstream.XStream;
import com.x.commons.util.bean.New;
import com.x.commons.util.collection.XArrays;
import com.x.commons.util.string.Strings;
import com.x.commons.util.xml.Xmls;
import com.x.framework.database.DaoManager;
import com.x.framework.database.IDaoManager;
import com.x.protocol.layers.application.config.DatabaseConfig;

import java.util.List;
import java.util.Map;

/**
 * @Desc
 * @Date 2020-03-23 22:15
 * @Author AD
 */
public final class DBSources {

    private static final Map<String, IDaoManager> managers = New.concurrentMap();

    private static IDaoManager FIRST;

    public static List<DatabaseConfig> loadXML(String path) throws Exception {
        String xml = Xmls.loadXML(path);
        XStream xs = Xmls.getXStream();
        xs.alias("databases", List.class);
        xs.alias("database", DatabaseConfig.class);
        xs.useAttributeFor(DatabaseConfig.class, "type");
        xs.useAttributeFor(DatabaseConfig.class, "name");
        xs.useAttributeFor(DatabaseConfig.class, "druid");
        return (List) xs.fromXML(xml);
    }

    public static void start(DatabaseConfig... configs) throws Exception {
        if (XArrays.isEmpty(configs)) {
            return;
        }
        for (DatabaseConfig config : configs) {
            if(!managers.containsKey(config.getName())){
                managers.put(config.getName(), new DaoManager(config.getName(), config));
            }
        }
    }

    public static void start(String xmlPath) throws Exception {
        List<DatabaseConfig> configs = loadXML(xmlPath);
        start(configs.toArray(new DatabaseConfig[0]));
    }

    public IDaoManager getDaoManager(String name) {
        return Strings.isNotNull(name) ? managers.get(name) : null;
    }

    public static IDaoManager getDaoManager() {
        if (FIRST != null) {
            return FIRST;
        }
        synchronized (DBSources.class) {
            if (FIRST != null) {
                return FIRST;
            }
            if (managers.size() == 1) {
                FIRST = managers.values().toArray(new IDaoManager[0])[0];
                return FIRST;
            }
        }
        return null;
    }


}
