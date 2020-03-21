package com.x.commons.util.teamwork;

import com.x.commons.timming.Timer;
import com.x.commons.util.bean.New;
import com.x.commons.util.collection.XArrays;

import java.util.Arrays;
import java.util.concurrent.*;

/**
 * @Desc 团队协作者，如：<br>
 * boss -> taskQueue -> worker
 * @Date 2019-11-30 23:19
 * @Author AD
 */
public abstract class Team<TASK> {
    
    // ------------------------ 变量定义 ------------------------
    private Task<TASK> taskQueue;
    
    private Boss boss;
    
    private Worker worker;
    
    private Timer timer;
    
    private volatile boolean started;
    
    // ------------------------ 构造方法 ------------------------
    
    public Team(int capacity) {
        int max = Math.max(500, capacity) ;
        this.taskQueue = new Task<>(max);
        this.boss = new Boss();
        this.worker = new Worker();
        this.started = false;
    }
    
    // ------------------------ 方法定义 ------------------------
    
    public void start(int period, TimeUnit unit) {
        if (started) {
            return;
        }
        synchronized (this) {
            if (started) {
                return;
            }
            
            this.timer = new Timer("Team");
            timer.add(boss, period, unit);
            
            worker.start();
            timer.start();
            started = true;
        }
    }
    
    public void stop() {
        if (!started) {
            return;
        }
        synchronized (this) {
            if (!started) {
                return;
            }
            timer.destroy();
            worker.stop();
            started = false;
        }
    }
    
    protected boolean filter(TASK task) {
        return true;
    }
    
    protected abstract TASK[] getTask();
    
    protected abstract void runTask(TASK task);
    
    // ------------------------ 内部类 ------------------------
    
    private class Boss implements Runnable {
        
        @Override
        public void run() {
            try {
                // 扫描请求，以数组的结果返回
                TASK[] tasks = Team.this.getTask();
                if (!XArrays.isEmpty(tasks)) {
                    // 存入任务队列
                    Arrays.stream(tasks)
                            .filter(task -> Team.this.filter(task))
                            .forEach(task -> {
                                try {
                                    Team.this.taskQueue.put(task);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
    }
    
    private class Worker {
        
        private volatile boolean firstError = true;
        
        private final Object lock = new Object();
        
        private ExecutorService getter;
        
        private ThreadPoolExecutor runner;
        
        private void start() {
            getter = New.singleThreadPool();
            runner = New.threadPool();
            getter.execute(() -> {
                while (!getter.isShutdown()) {
                    // 从阻塞队列里获取请求
                    TASK task = Team.this.taskQueue.get();
                    try {
                        runner.execute(() -> {
                            Team.this.runTask(task);
                        });
                    } catch (Exception e) {
                        if (e.getClass().equals(RejectedExecutionException.class)) {
                            if (firstError) {
                                synchronized (lock) {
                                    if (firstError) {
                                        firstError = false;
                                        int size = runner.getMaximumPoolSize();
                                        runner.setMaximumPoolSize(size * 2);
                                    }
                                }
                            }
                            System.out.println("线程池拒绝策略:" + task);
                            Team.this.taskQueue.put(task);
                        } else {
                            e.printStackTrace();
                        }
                    }
                }
            });
            
        }
        
        private void stop() {
            runner.shutdown();
            getter.shutdownNow();
        }
        
    }
    
}
