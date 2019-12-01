package com.x.commons.util.teamwork;

import com.x.commons.util.collection.Arrays;

/**
 * @Desc 请求扫描者
 * @Date 2019-11-30 19:38
 * @Author AD
 */
public abstract class ReqScanner<REQ> implements Runnable {
    
    // ------------------------ 变量定义 ------------------------
    
    /**
     * 请求队列
     */
    private Task<REQ> queue;
    
    // ------------------------ 构造方法 ------------------------
    
    public ReqScanner(Task<REQ> queue) {
        this.queue = queue;
    }
    
    // ------------------------ 方法定义 ------------------------
    @Override
    public void run() {
        try {
            // 扫描请求，以数组的结果返回
            REQ[] reqs = scan();
            if (!Arrays.isEmpty(reqs)) {
                // 遍历过滤
                for (REQ req : reqs) {
                    if (filter(req)) {
                        System.out.println("scanner req=" + req.toString());
                        queue.put(req);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 生成请求
     *
     * @return 结果数组
     *
     * @throws Exception 抛出异常
     */
    public abstract REQ[] scan() throws Exception;
    
    /**
     * 过滤请求
     *
     * @param req 请求对象
     *
     * @return true：存入队列执行任务，反之则否
     *
     * @throws Exception 抛出异常
     */
    public abstract boolean filter(REQ req) throws Exception;
    
}
