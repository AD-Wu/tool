package com.x.commons.util.teamwork;

import com.x.commons.timming.Timer;

import java.util.concurrent.TimeUnit;

/**
 * @Desc
 * @Date 2019-11-30 23:19
 * @Author AD
 */
public class Team<REQ, RESP> {
    
    private final TaskScanner<REQ> scanner;
    
    private final TaskActor<REQ, RESP> actor;
    
    private final ResultHandler<RESP> handler;
    
    private Timer timer;
    
    private volatile boolean start;
    
    public Team(TaskScanner<REQ> scanner, TaskActor<REQ, RESP> actor, ResultHandler<RESP> handler) {
        
        this.scanner = scanner;
        this.actor = actor;
        this.handler = handler;
        this.start = false;
    }
    
    public void start(int period, TimeUnit unit) {
        if (start) {
            return;
        }
        synchronized (this) {
            if (start) {
                return;
            }
            
            this.timer = new Timer("Team");
            timer.add(scanner, period, unit);
            
            timer.start();
            actor.start();
            handler.start();
            start = true;
        }
    }
    
    public void stop() {
        if (!start) {
            return;
        }
        synchronized (this) {
            if (!start) {
                return;
            }
            timer.destroy();
            actor.stop();
            handler.stop();
            start = false;
        }
    }
    
}
