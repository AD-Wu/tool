package com.x.protocol.core;

import com.x.commons.util.string.Strings;

/**
 * @Desc
 * @Date 2020-03-10 21:08
 * @Author AD
 */
public class ResponseResult {
    // ------------------------ 变量定义 ------------------------
    
    /**
     * 通道信息
     */
    private final ChannelInfo info;
    
    /**
     * 通道数据
     */
    private final ChannelData data;
    
    /**
     * 成功标识
     */
    private final boolean succeed;
    
    // ------------------------ 构造方法 ------------------------
    
    /**
     * 响应结果构造方法
     *
     * @param info    通道信息
     * @param data    通道数据
     * @param succeed 成功标识
     */
    public ResponseResult(ChannelInfo info, ChannelData data, boolean succeed) {
        this.info = info;
        this.data = data;
        this.succeed = succeed;
    }
    // ------------------------ 方法定义 ------------------------
    
    /**
     * 获取通道信息
     *
     * @return ChannelInfo 通道信息
     */
    public ChannelInfo getInfo() {
        return this.info;
    }
    
    /**
     * 获取通道数据
     *
     * @return ChannelData 通道数据
     */
    public ChannelData getData() {
        return this.data;
    }
    
    /**
     * 获取成功标识
     *
     * @return boolean 成功标识
     */
    public boolean isSucceed() {
        return this.succeed;
    }
    
    @Override
    public String toString() {
        return Strings.defaultToString(this);
    }
    
}
