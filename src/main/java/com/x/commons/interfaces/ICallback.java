package com.x.commons.interfaces;

/**
 * @Desc 回调接口
 * @Date 2019-11-30 20:00
 * @Author AD
 */
public interface ICallback<T> {
    
    /**
     * 成功处理
     *
     * @param res 需要回复的数据
     *
     * @throws Exception 抛出异常
     */
    void succeed(T res) throws Exception;
    
    /**
     * 失败处理
     *
     * @param status 失败状态
     * @param msg    失败信息
     * @param res    响应数据
     *
     * @throws Exception 抛出异常
     */
    void error(int status, String msg, T res) throws Exception;
    
}
