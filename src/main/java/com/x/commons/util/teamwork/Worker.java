package com.x.commons.util.teamwork;

import com.x.commons.util.bean.New;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Desc 请求执行器
 * @Date 2019-11-30 21:31
 * @Author AD
 */
public abstract class Worker<REQ> {
    
    // ------------------------ 变量定义 ------------------------
    
    /**
     * 请求队列
     */
    private final Task<REQ> reqs;
    
    /**
     * 请求获取者
     */
    private ExecutorService getter;
    
    /**
     * 请求执行者
     */
    private ThreadPoolExecutor runner;
    // ------------------------ 构造方法 ------------------------
    
    public Worker(Task<REQ> reqs) {
        this.reqs = reqs;
    }
    
    // ------------------------ 方法定义 ------------------------
    void start() {
        getter = New.singleThreadPool();
        getter.submit(() -> {
            runner = New.threadPool();
            while (!getter.isShutdown()) {
                // 从阻塞队列里获取请求
                REQ req = reqs.get();
                System.out.println("actor req=" + req.toString());
                runner.execute(() -> {
                    try {
                        // 执行
                        run(req);
                        
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        });
    }
    
    /**
     * 关闭线程池，getter被阻塞，暴力关闭
     */
    void stop() {
        runner.shutdown();
        getter.shutdownNow();
    }
    
    public abstract void run(REQ req) throws Exception;
    
}
