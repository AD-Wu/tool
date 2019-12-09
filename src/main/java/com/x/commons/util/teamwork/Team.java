package com.x.commons.util.teamwork;

import com.x.commons.timming.Timer;

import java.util.concurrent.TimeUnit;

/**
 * @Desc 团队协作者，如：<br>
 * scanner -> reqQueue -> actor
 * @Date 2019-11-30 23:19
 * @Author AD
 */
public class Team {
    
    // ------------------------ 变量定义 ------------------------
    
    /**
     * 请求扫描器
     */
    private final Boss boss;
    
    /**
     * 请求执行器
     */
    private final Worker worker;
    
    /**
     * 定时器，scanner用于定时执行任务
     */
    private Timer timer;
    
    /**
     * 团队是否开始工作的标识
     */
    private volatile boolean start;
    
    // ------------------------ 构造方法 ------------------------
    
    public Team(Boss boss, Worker worker) {
        
        this.boss = boss;
        this.worker = worker;
        this.start = false;
    }
    
    // ------------------------ 方法定义 ------------------------
    
    public void start(int period, TimeUnit unit) {
        if (start) {
            return;
        }
        synchronized (this) {
            if (start) {
                return;
            }
            
            this.timer = new Timer("Team");
            timer.add(boss, period, unit);
            
            timer.start();
            worker.start();
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
            worker.stop();
            start = false;
        }
    }
    
}
