package com.x.protocol.config;

import com.thoughtworks.xstream.XStream;
import com.x.commons.collection.NameValue;
import com.x.commons.local.Locals;
import com.x.commons.util.log.Logs;
import com.x.commons.util.xml.Xmls;
import com.x.protocol.layers.application.config.*;
import com.x.protocol.layers.presentation.config.SerializerConfig;
import com.x.protocol.layers.session.config.ConnectionConfig;
import com.x.protocol.layers.session.config.LoginConfig;
import com.x.protocol.layers.session.config.SessionConfig;
import com.x.protocol.layers.transport.config.ChannelConfig;
import com.x.protocol.layers.transport.config.ClientConfig;
import com.x.protocol.layers.transport.config.ProtocolConfig;
import com.x.protocol.layers.transport.config.ServerConfig;
import org.slf4j.Logger;

import java.util.List;

/**
 * @Desc
 * @Date 2020-03-21 11:21
 * @Author AD
 */
public final class ProtocolConfigure {
    private static final Logger LOG = Logs.get(ProtocolConfigure.class);
    private ProtocolConfigure() {
    }
    
    public static List<ServiceConfig> loadServices(String fileName) {
        try {
            String xml = Xmls.loadXML(fileName);
            XStream xs = new XStream();
            buildServiceXStream(xs);
            return (List)xs.fromXML(xml);
        } catch (Exception e) {
            LOG.error(Locals.text("protocol.config.load", fileName), e);
            return null;
        }
    }
    
    public static boolean saveServices(String fileName, List<ServiceConfig> services) {
        try {
            XStream xs = new XStream();
            buildServiceXStream(xs);
            String xml = xs.toXML(services);
            return Xmls.saveXML(fileName, "framework", "PUBLIC \"-//X//DTD FRAMEWORK 1.0\" \"framework.dtd\"", xml, "UTF-8");
        } catch (Exception e) {
            LOG.error(Locals.text("protocol.config.save", fileName), e);
            return false;
        }
    }
    
    private static void buildServiceXStream(XStream xs) {
        xs.setMode(1001);
        xs.alias("framework", List.class);
        xs.alias("service", ServiceConfig.class);
        xs.useAttributeFor(ServiceConfig.class, "name");
        xs.useAttributeFor(ServiceConfig.class, "enabled");
        xs.useAttributeFor(ServiceConfig.class, "debug");
        xs.useAttributeFor(ServiceConfig.class, "remark");
        xs.alias("parameters", List.class);
        xs.alias("p", NameValue.class);
        xs.useAttributeFor(NameValue.class, "name");
        xs.useAttributeFor(NameValue.class, "value");
        xs.alias("application", ApplicationConfig.class);
        xs.alias("actors", ActorsConfig.class);
        xs.alias("setting", ActorSetting.class);
        xs.useAttributeFor(ActorSetting.class, "disableDoc");
        xs.useAttributeFor(ActorSetting.class, "defaultActor");
        xs.alias("packages", List.class);
        xs.alias("package", PackageConfig.class);
        xs.useAttributeFor(PackageConfig.class, "base");
        xs.useAttributeFor(PackageConfig.class, "name");
        xs.useAttributeFor(PackageConfig.class, "remark");
        xs.alias("listeners", List.class);
        xs.alias("listener", ListenerConfig.class);
        xs.useAttributeFor(ListenerConfig.class, "base");
        xs.useAttributeFor(ListenerConfig.class, "pkgName");
        xs.useAttributeFor(ListenerConfig.class, "remark");
        xs.alias("initializer", InitializerConfig.class);
        xs.useAttributeFor(InitializerConfig.class, "clazz");
        xs.alias("database", DatabaseConfig.class);
        xs.useAttributeFor(DatabaseConfig.class, "type");
        xs.alias("presentation", List.class);
        xs.alias("serializer", SerializerConfig.class);
        xs.useAttributeFor(SerializerConfig.class, "channel");
        xs.useAttributeFor(SerializerConfig.class, "clazz");
        xs.alias("session", SessionConfig.class);
        xs.alias("login", LoginConfig.class);
        xs.useAttributeFor(LoginConfig.class, "loginMode");
        xs.useAttributeFor(LoginConfig.class, "commands");
        xs.useAttributeFor(LoginConfig.class, "multiSerializer");
        xs.alias("connection", ConnectionConfig.class);
        xs.useAttributeFor(ConnectionConfig.class, "maxSize");
        xs.useAttributeFor(ConnectionConfig.class, "minuteLimit");
        xs.useAttributeFor(ConnectionConfig.class, "limitTime");
        xs.useAttributeFor(ConnectionConfig.class, "sessionTimeout");
        xs.alias("transport", List.class);
        xs.alias("channel", ChannelConfig.class);
        xs.useAttributeFor(ChannelConfig.class, "type");
        xs.useAttributeFor(ChannelConfig.class, "name");
        xs.useAttributeFor(ChannelConfig.class, "enabled");
        xs.useAttributeFor(ChannelConfig.class, "corePoolSize");
        xs.useAttributeFor(ChannelConfig.class, "maxPoolSize");
        xs.useAttributeFor(ChannelConfig.class, "queue");
        xs.useAttributeFor(ChannelConfig.class, "readTimeout");
        xs.useAttributeFor(ChannelConfig.class, "sendTimeout");
        xs.alias("protocol", ProtocolConfig.class);
        xs.useAttributeFor(ProtocolConfig.class, "senderClass");
        xs.useAttributeFor(ProtocolConfig.class, "readerClass");
        xs.useAttributeFor(ProtocolConfig.class, "maxMappingIndex");
        xs.alias("server", ServerConfig.class);
        xs.useAttributeFor(ServerConfig.class, "maxConnection");
        xs.alias("clients", List.class);
        xs.alias("client", ClientConfig.class);
        xs.useAttributeFor(ClientConfig.class, "name");
        xs.useAttributeFor(ClientConfig.class, "enabled");
    }
}
