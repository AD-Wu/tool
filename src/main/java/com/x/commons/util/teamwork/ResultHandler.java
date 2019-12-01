package com.x.commons.util.teamwork;

import com.x.commons.util.bean.New;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Desc
 * @Date 2019-11-30 23:03
 * @Author AD
 */
public abstract class ResultHandler<RESP> {
    
    private final Task<RESP> resps;
    
    private RESP resp;
    
    private Thread getter;
    
    private ThreadPoolExecutor runner;
    
    public ResultHandler(Task<RESP> resps) {
        this.resps = resps;
        runner = New.threadPool();
        runner = New.threadPool();
    }
    
    void start() {
        getter = new Thread(() -> {
            while (!getter.isInterrupted()) {
                RESP resp = resps.get();
                if (resp != null) {
                    runner.execute(() -> {
                        try {
                            handle(resp);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        });
        getter.start();
    }
    
    void stop() {
        runner.shutdown();
        getter.interrupt();
        getter = null;
    }
    
    public abstract void handle(RESP resp) throws Exception;
    
}
