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

public class TimerTask {
    private static TimerTask instance;
    private static Object instanceLock = new Object();
    private static List<TimerTask> timers = new ArrayList();
    private boolean stopped;
    private Thread checker;
    private ThreadPoolExecutor pool;
    private Object lock;
    private List<TaskRunner> tasks;
    private TaskRunner[] taskArray;

    public TimerTask(String name) {
        this(name, 0, 10, 60L, 0);
    }

    public TimerTask(String name, int var2, int var3, long var4, int var6) {
        this.lock = new Object();
        this.tasks = new ArrayList();
        this.taskArray = new TaskRunner[0];
        synchronized(instanceLock) {
            timers.add(this);
        }

        this.checker = new Thread("TimerTask[" + name + "]") {
            public void run() {
                try {
                    TimerTask.this.checkTask();
                } catch (Exception var2) {
                    var2.printStackTrace();
                }

            }
        };
        BlockingQueue var7 = (BlockingQueue)(var6 <= 0 ? new SynchronousQueue() : new ArrayBlockingQueue(var6));
        this.pool = new ThreadPoolExecutor(var2, var3, var4, TimeUnit.SECONDS, var7, new AbortPolicy());
    }

    public static TimerTask timer() {
        if (instance != null) {
            return instance;
        } else {
            synchronized(instanceLock) {
                if (instance != null) {
                    return instance;
                }

                instance = new TimerTask("system", 0, 100, 60L, 0);
                instance.start();
            }

            return instance;
        }
    }

    public static void clearAll() {
        synchronized(instanceLock) {
            Iterator<TimerTask> it = timers.iterator();

            while(it.hasNext()) {
                TimerTask task = it.next();
                task.clear();
            }

        }
    }

    public static void destroyAll() {
        TimerTask[] tasks;
        synchronized(instanceLock) {
            instance = null;
            tasks = (TimerTask[])timers.toArray(new TimerTask[0]);
        }

        TimerTask[] temp = tasks;
        int taskLength = tasks.length;

        for(int i = 0; i < taskLength; ++i) {
            TimerTask task = temp[i];
            task.destroy();
        }

    }

    public void add(Runnable var1, long var2) {
        this.add(var1, var2, 1L, 1L);
    }

    public void add(Runnable var1, long var2, long var4) {
        this.add(var1, var2, var4, 0L);
    }

    public void add(Runnable var1, long var2, long var4, long var6) {
        if (var1 != null) {
            TaskRunner task = new TaskRunner(var1, var2, var4, var6);
            synchronized(this.lock) {
                this.tasks.add(task);
                this.taskArray = (TaskRunner[])this.tasks.toArray(new TaskRunner[this.tasks.size()]);
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

    public boolean contains(Runnable taskRunner) {
        TaskRunner[] taskArray = this.taskArray;
        TaskRunner[] tasks = taskArray;
        int len = taskArray.length;

        for(int i = 0; i < len; ++i) {
            TaskRunner task = tasks[i];
            if (task.getRunner().equals(taskRunner)) {
                return true;
            }
        }

        return false;
    }

    public boolean remove(Runnable taskRunner) {
        TaskRunner[] taskArray = this.taskArray;
        TaskRunner task = null;
        TaskRunner[] tasks = taskArray;
        int len = taskArray.length;

        for(int i = 0; i < len; ++i) {
            TaskRunner t = tasks[i];
            if (t.getRunner().equals(taskRunner)) {
                task = t;
                break;
            }
        }

        if (task != null) {
            synchronized(this.lock) {
                this.tasks.remove(task);
                this.taskArray = (TaskRunner[])this.tasks.toArray(new TaskRunner[this.tasks.size()]);
                return true;
            }
        } else {
            return false;
        }
    }

    public void update(Runnable var1, int var2) {
        TaskRunner[] var3 = this.taskArray;
        TaskRunner[] var4 = var3;
        int var5 = var3.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            TaskRunner var7 = var4[var6];
            if (var7.getRunner().equals(var1)) {
                var7.updateInterval(var2);
                break;
            }
        }

    }

    public void clear() {
        synchronized(this.lock) {
            this.tasks.clear();
            this.taskArray = (TaskRunner[])this.tasks.toArray(new TaskRunner[this.tasks.size()]);
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
            synchronized(instanceLock) {
                timers.remove(this);
            }

            this.clear();

            try {
                this.pool.shutdown();
            } catch (Exception var3) {
                var3.printStackTrace();
            }

            this.notifyAll();
        }
    }

    private void checkTask() {
        long now = System.currentTimeMillis();
        long var5 = 0L;
        Object lock = new Object();
        synchronized(lock) {
            while(true) {
                while(!this.stopped) {
                    TaskRunner[] taskRunners = this.taskArray;
                    if (taskRunners.length == 0) {
                        try {
                            lock.wait(100L);
                        } catch (Exception var18) {
                        }
                    } else {
                        long var1 = System.currentTimeMillis();
                        int var10;
                        TaskRunner[] var11;
                        int var12;
                        int var13;
                        TaskRunner var14;
                        if (var1 < now || var1 > now + (long)(taskRunners.length * 2) + var5 + 1000L) {
                            var10 = (int)(var1 - now);
                            var11 = taskRunners;
                            var12 = taskRunners.length;

                            for(var13 = 0; var13 < var12; ++var13) {
                                var14 = var11[var13];
                                var14.updateTime(var10);
                            }
                        }

                        now = var1;
                        var10 = 0;
                        var11 = taskRunners;
                        var12 = taskRunners.length;

                        for(var13 = 0; var13 < var12; ++var13) {
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
                                        synchronized(this.lock) {
                                            this.tasks.remove(var14);
                                            this.taskArray = (TaskRunner[])this.tasks.toArray(new TaskRunner[this.tasks.size()]);
                                        }
                                    }
                                } else {
                                    var14.setRunning(false);
                                }
                            }
                        }

                        var5 = (long)(var10 * 100);

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
