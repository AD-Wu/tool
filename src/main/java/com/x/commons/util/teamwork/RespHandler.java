package com.x.commons.util.teamwork;

import com.x.commons.util.bean.New;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Desc 响应结果处理者
 * @Date 2019-11-30 23:03
 * @Author AD
 */
public abstract class RespHandler<RESP> {
    
    // ------------------------ 变量定义 ------------------------
    
    /**
     * 响应队列
     */
    private final Task<RESP> resps;
    
    /**
     * 响应结果获取者
     */
    private ExecutorService getter;
    
    /**
     * 响应结果处理者
     */
    private ThreadPoolExecutor runner;
    
    // ------------------------ 构造方法 ------------------------
    
    public RespHandler(Task<RESP> resps) {
        this.resps = resps;
    }
    
    // ------------------------ 方法定义 ------------------------
    
    void start() {
        getter = New.singleThreadPool();
        getter.submit(() -> {
            runner = New.threadPool();
            while (!getter.isShutdown()) {
                RESP resp = resps.get();
                if (resp != null) {
                    System.out.println("handler resp=" + resp);
                    runner.execute(() -> {
                        try {
                            handle(resp);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        }, "X-[RespHandler]");
    }
    
    void stop() {
        runner.shutdown();
        getter.shutdownNow();
    }
    
    public abstract void handle(RESP resp) throws Exception;
    
}
