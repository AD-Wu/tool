package com.x.protocol.layers.presentation;

import com.x.commons.collection.NameValue;
import com.x.commons.util.bean.New;
import com.x.commons.util.collection.XArrays;
import com.x.commons.util.reflact.Clazzs;
import com.x.commons.util.string.Strings;
import com.x.protocol.core.ISerializer;
import com.x.protocol.layers.presentation.config.SerializerConfig;

import java.util.List;
import java.util.Map;

/**
 * @Desc
 * @Date 2020-03-08 21:57
 * @Author AD
 */
public class Presentation {
    
    // ------------------------ 变量定义 ------------------------
    private Serializer defaultConfig;
    
    private Map<String, Serializer> channelConfigs = New.concurrentMap();
    
    // ------------------------ 构造方法 ------------------------
    public Presentation(List<SerializerConfig> configs) {
        if (!XArrays.isEmpty(configs)) {
            configs.forEach(config -> {
                try {
                    String channel = config.getChannel();
                    Class<?> clazz = Class.forName(config.getClazz());
                    if (Strings.isNotNull(channel)) {
                        channelConfigs.put(channel, new Serializer(clazz, config.getParameters()));
                    } else {
                        this.defaultConfig = new Serializer(clazz, config.getParameters());
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            });
        }
    }
    
    // ------------------------ 方法定义 ------------------------
    public ISerializer createSerializer(String channel) {
        Serializer serializer = channelConfigs.get(channel);
        if (serializer == null) {
            serializer = this.defaultConfig;
        }
        if (serializer == null) {
            return new DefaultSerializer();
        } else {
            Class<?> clazz = serializer.getSerializerClass();
            try {
                ISerializer seria = (ISerializer)Clazzs.newInstance(clazz);
                List<NameValue> parameters = serializer.getParameters();
                parameters.forEach(param -> {
                    seria.add(param.getName(), param.getValue());
                });
                return seria;
            } catch (Exception e) {
                e.printStackTrace();
                return new DefaultSerializer();
            }
        }
    }
    
}
