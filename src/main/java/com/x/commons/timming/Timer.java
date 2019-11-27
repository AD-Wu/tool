//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.x.commons.timming;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;

public class Timer {
    
    private static Timer instance;
    
    private static Object instanceLock = new Object();
    
    private static List<Timer> timerList = new ArrayList();
    
    private boolean stopped;
    
    private Thread checker;
    
    private ThreadPoolExecutor pool;
    
    private Object lock;
    
    private List<Task> taskList;
    
    private Task[] tasks;
    
    public Timer(String name) {
        this(name, 0, 10, 60L, 0);
    }
    
    public Timer(String name, int var2, int var3, long var4, int var6) {
        this.lock = new Object();
        this.taskList = new ArrayList();
        this.tasks = new Task[0];
        synchronized (instanceLock) {
            timerList.add(this);
        }
        
        this.checker = new Thread("X-Timer[" + name + "]") {
            
            public void run() {
                try {
                    Timer.this.checkTask();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
            }
        };
        BlockingQueue queue = (BlockingQueue) (var6 <= 0 ? new SynchronousQueue() : new ArrayBlockingQueue(var6));
        this.pool = new ThreadPoolExecutor(var2, var3, var4, TimeUnit.SECONDS, queue, new AbortPolicy());
    }
    
    public static Timer timer() {
        if (instance != null) {
            return instance;
        } else {
            synchronized (instanceLock) {
                if (instance != null) {
                    return instance;
                }
                
                instance = new Timer("system", 0, 100, 60L, 0);
                instance.start();
            }
            
            return instance;
        }
    }
    
    public static void clearAll() {
        synchronized (instanceLock) {
            Iterator<Timer> it = timerList.iterator();
            
            while (it.hasNext()) {
                Timer timer = it.next();
                timer.clear();
            }
            
        }
    }
    
    public static void destroyAll() {
        Timer[] timers;
        synchronized (instanceLock) {
            instance = null;
            timers = (Timer[]) timerList.toArray(new Timer[0]);
        }
        
        int taskLength = timers.length;
        
        for (int i = 0; i < taskLength; ++i) {
            Timer timer = timers[i];
            timer.destroy();
        }
        
    }
    
    public void add(Runnable var1, long var2) {
        this.add(var1, var2, 1L, 1L);
    }
    
    public void add(Runnable var1, long var2, long var4) {
        this.add(var1, var2, var4, 0L);
    }
    
    public void add(Runnable runnable, long var2, long var4, long var6) {
        if (runnable != null) {
            Task task = new Task(runnable, var2, var4, var6);
            synchronized (this.lock) {
                this.taskList.add(task);
                this.tasks = (Task[]) taskList.toArray(new Task[this.taskList.size()]);
            }
        }
    }
    
    public synchronized boolean run(Runnable task) {
        if (task == null) {
            return false;
        } else {
            try {
                this.pool.execute(task);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
    }
    
    public boolean contains(Runnable runnable) {
        Task[] taskArray = this.tasks;
        Task[] tasks = taskArray;
        int len = taskArray.length;
        
        for (int i = 0; i < len; ++i) {
            Task task = tasks[i];
            if (task.getTask().equals(runnable)) {
                return true;
            }
        }
        
        return false;
    }
    
    public boolean remove(Runnable runnable) {
        Task task = null;
        
        for (int i = 0, L = tasks.length; i < L; ++i) {
            Task t = tasks[i];
            if (t.getTask().equals(runnable)) {
                task = t;
                break;
            }
        }
        
        if (task != null) {
            synchronized (this.lock) {
                this.taskList.remove(task);
                this.tasks = (Task[]) taskList.toArray(new Task[taskList.size()]);
                return true;
            }
        } else {
            return false;
        }
    }
    
    public void update(Runnable runnable, int period) {
        
        for (int i = 0, L = tasks.length; i < L; ++i) {
            Task task = tasks[i];
            if (task.getTask().equals(runnable)) {
                task.updatePeriod(period);
                break;
            }
        }
        
    }
    
    public void clear() {
        synchronized (this.lock) {
            taskList.clear();
            tasks = (Task[]) taskList.toArray(new Task[taskList.size()]);
        }
    }
    
    public synchronized void start() {
        if (!this.stopped && !this.checker.isAlive()) {
            this.checker.start();
        }
    }
    
    public synchronized void destroy() {
        if (!this.stopped) {
            this.stopped = true;
            synchronized (instanceLock) {
                timerList.remove(this);
            }
            
            this.clear();
            
            try {
                this.pool.shutdown();
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            this.notifyAll();
        }
    }
    
    private void checkTask() {
        long now = System.currentTimeMillis();
        long var5 = 0L;
        Object lock = new Object();
        synchronized (lock) {
            while (true) {
                while (!this.stopped) {
                    Task[] taskRunners = this.tasks;
                    if (taskRunners.length == 0) {
                        try {
                            lock.wait(100L);
                        } catch (Exception var18) {
                        }
                    } else {
                        long var1 = System.currentTimeMillis();
                        int var10;
                        Task[] var11;
                        int var12;
                        int var13;
                        Task var14;
                        if (var1 < now || var1 > now + (long) (taskRunners.length * 2) + var5 + 1000L) {
                            var10 = (int) (var1 - now);
                            var11 = taskRunners;
                            var12 = taskRunners.length;
                            
                            for (var13 = 0; var13 < var12; ++var13) {
                                var14 = var11[var13];
                                var14.updateTime(var10);
                            }
                        }
                        
                        now = var1;
                        var10 = 0;
                        var11 = taskRunners;
                        var12 = taskRunners.length;
                        
                        for (var13 = 0; var13 < var12; ++var13) {
                            var14 = var11[var13];
                            if (var14.needToRun(var1)) {
                                ++var10;
                                if (this.stopped) {
                                    break;
                                }
                                
                                var14.setRunning(true);
                                if (this.run(var14)) {
                                    var14.updateStatus();
                                    if (var14.needRemove()) {
                                        synchronized (this.lock) {
                                            this.taskList.remove(var14);
                                            this.tasks = (Task[]) this.taskList.toArray(new Task[this.taskList.size()]);
                                        }
                                    }
                                } else {
                                    var14.setRunning(false);
                                }
                            }
                        }
                        
                        var5 = (long) (var10 * 100);
                        
                        try {
                            lock.wait(100L);
                        } catch (Exception var19) {
                        }
                    }
                }
                
                return;
            }
        }
    }
    
}
