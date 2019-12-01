package com.x.commons.util.teamwork;

import com.x.commons.util.bean.New;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Desc
 * @Date 2019-11-30 21:31
 * @Author AD
 */
public abstract class TaskActor<REQ, RESP> {
    
    private final Task<REQ> reqs;
    
    private final Task<RESP> resps;
    
    private REQ req;
    
    private Thread getter;
    
    private ThreadPoolExecutor runner;
    
    private Object lock = new Object();
    
    public TaskActor(Task<REQ> reqs, Task<RESP> resps) {
        this.reqs = reqs;
        this.resps = resps;
        runner = New.threadPool();
    }
    
    void start() {
        getter = new Thread(() -> {
            while (!getter.isInterrupted()) {
                REQ req = reqs.get();
                runner.execute(() -> {
                    try {
                        RESP resp = run(req);
                        if (resp != null) {
                            resps.put(resp);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        notifyAll();
                    }
                });
            }
        });
        getter.start();
    }
    
    void stop() {
        runner.shutdown();
        getter.isInterrupted();
        getter = null;
    }
    
    public abstract RESP run(REQ task) throws Exception;
    
}
