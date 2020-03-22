package com.x.protocol.layers.session;

import com.x.commons.util.bean.New;
import com.x.commons.util.date.DateTimes;
import com.x.commons.util.string.Strings;
import com.x.protocol.core.ChannelInfo;
import com.x.protocol.core.ISerializer;
import com.x.protocol.core.ISession;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;

/**
 * @Desc
 * @Date 2020-03-08 22:21
 * @Author AD
 */
public class Session implements ISession {

    // ------------------------ 变量定义 ------------------------
    private String id;

    private ISerializer serializer;

    private long refreshTime;

    private long createTime;

    private String langKey;

    private Map<String, ChannelInfo> channels = New.concurrentMap();

    private ChannelInfo[] channelArray = new ChannelInfo[0];

    private Map<String, Object> datas = New.concurrentMap();

    // ------------------------ 构造方法 ------------------------
    Session(String id) {
        this.id = id;
        this.createTime = System.currentTimeMillis();
        this.refreshTime = createTime;
    }

    // ------------------------ 方法定义 ------------------------

    /**
     * 会话更新时间，如：创建时间，重新连接时间，即最近一次的动作更新时间
     *
     * @return
     */
    public long getSessionTime() {
        return refreshTime;
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public ISerializer getSerializer() {
        return serializer;
    }

    @Override
    public synchronized Object setData(String key, Object value) {
        return datas.put(key, value);
    }

    @Override
    public <T> T getData(String key) {
        return (T) datas.get(key);
    }

    @Override
    public <T> T removeData(String key) {
        return (T) datas.remove(key);
    }

    @Override
    public boolean containDataKey(String key) {
        return datas.containsKey(key);
    }

    @Override
    public boolean containData(Object data) {
        return datas.containsValue(data);
    }

    @Override
    public boolean containChannelInfo(ChannelInfo info) {
        return channels.containsKey(info.getChannelInfoKey());
    }

    @Override
    public int getChannelInfoSize() {
        return channels.size();
    }

    @Override
    public ChannelInfo[] getChannelInfos() {
        return channelArray;
    }

    @Override
    public LocalDateTime getCreateTime() {
        return DateTimes.toLocalDataTime(createTime);
    }

    @Override
    public void updateSessionTime() {
        this.refreshTime = System.currentTimeMillis();
    }

    @Override
    public String getLangKey() {
        return langKey;
    }

    @Override
    public synchronized void setLangKey(String langKey) {
        if (Strings.isNotNull(langKey)) {
            if (langKey.equals(this.langKey)) {
                return;
            }
            this.langKey = langKey;
            Arrays.stream(channelArray).forEach(channelInfo -> {
                channelInfo.getConsent().setLangKey(langKey);
            });
        }
    }

    // ------------------------ 缺省方法 ------------------------
    void setSerializer(ISerializer serializer) {
        this.serializer = serializer;
    }

    void addChannelInfo(ChannelInfo info) {
        if (Strings.isNotNull(langKey)) {
            info.getConsent().setLangKey(langKey);
        }
        channels.put(info.getChannelInfoKey(), info);
        this.channelArray = channels.values().toArray(new ChannelInfo[0]);
    }

    void removeChannelInfo(ChannelInfo info) {
        channels.remove(info.getChannelInfoKey());
        this.channelArray = channels.values().toArray(new ChannelInfo[0]);

    }

}
