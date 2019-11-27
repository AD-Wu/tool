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

/**
 * @Desc 定时器（JDK自带定时器有BUG，不推荐使用）
 * @Date 2019-11-27 15:09
 * @Author AD
 */
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

    /**
     * 定时器构造方法
     *
     * @param name 监测线程名
     */
    Timer(String name) {
        this(name, 0, 10, 60L, 0);
    }

    /**
     * 定时器构造方法
     *
     * @param name           定时器监测线程名（任务添加到线程池里执行）
     * @param remainSize     线程空闲时，运行在线程池中保留的数目
     * @param maxPoolSize    线程池大小
     * @param keepActiveTime 线程空闲后，线程存活时间。如果任务多，任务周期短，可以调大keepAliveTime，提高线程利用率。
     * @param queueSize      任务饱和时，将任务缓存到阻塞队列，需定义阻塞队列的大小
     */
    Timer(String name, int remainSize, int maxPoolSize, long keepActiveTime, int queueSize) {
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
        BlockingQueue queue = (BlockingQueue) (queueSize <= 0 ? new SynchronousQueue() : new ArrayBlockingQueue(
                queueSize));
        /**
         * （1）corePoolSize：核心线程池大小。如果调用了prestartAllCoreThread（）方法，那么线程池会提前创建并启动所有基本线程。
         *
         * （2）maximumPoolSize：线程池大小
         *
         * （3）keepAliveTime：线程空闲后，线程存活时间。如果任务多，任务周期短，可以调大keepAliveTime，提高线程利用率。
         *
         * （4）timeUnit：存活时间的单位
         *
         * （5）runnalbleTaskQueue：阻塞队列。可以使用ArrayBlockingQueue、LinkedBlockingQueue、SynchronousQueue、PriorityBlockingQueue
         *
         * 静态工厂方法Executors.newFixedThreadPool()使用了LinkedBlockingQueue；
         *
         * 静态工厂方法Executors.newCachedThreadPool()使用了SynchronousQueue；
         *
         * （6）handler：饱和策略的句柄，当线程池满了的时候，任务无法得到处理，这时需要饱和策略来处理无法完成的任务，饱和策略中有4种：
         *
         * AbortPolicy：这是默认的策略，直接抛出异常；
         *
         * CallerRunsPolicy：只是用调用者所在线程来运行任务；
         *
         * DiscardOldestPolicy：丢弃队列中最老的任务，并执行当前任务；
         *
         * DiscardPolicy：不处理，直接把当前任务丢弃；
         *
         */
        this.pool = new ThreadPoolExecutor(remainSize, maxPoolSize, keepActiveTime, TimeUnit.SECONDS, queue,
                                           new AbortPolicy());
    }

    public static Timer get() {
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

    public void add(Runnable runnable, long delay) {
        this.add(runnable, delay, 1L, 1L);
    }

    public void add(Runnable runnable, long delay, long period) {
        this.add(runnable, delay, period, 0L);
    }

    public void add(Runnable runnable, long delay, long period, long repeatCount) {
        if (runnable != null) {
            Task task = new Task(runnable, delay, period, repeatCount);
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
                pool.execute(task);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
    }

    public boolean contains(Runnable runnable) {

        for (int i = 0, L = tasks.length; i < L; ++i) {
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
        long old = System.currentTimeMillis();
        long var5 = 0L;
        Object lock = new Object();
        synchronized (lock) {
            while (true) {
                while (!this.stopped) {
                    Task[] tasks = this.tasks;
                    if (tasks.length == 0) {
                        try {
                            lock.wait(100L);
                        } catch (Exception e) {
                        }
                    } else {
                        long now = System.currentTimeMillis();
                        int period;
                        Task[] tasksTemp;
                        int taskLength;
                        int i;
                        Task task;
                        if (now < old || now > old + (long) (tasks.length * 2) + var5 + 1000L) {
                            period = (int) (now - old);
                            tasksTemp = tasks;
                            taskLength = tasks.length;

                            for (i = 0; i < taskLength; ++i) {
                                task = tasksTemp[i];
                                task.updateTime(period);
                            }
                        }

                        old = now;
                        period = 0;
                        tasksTemp = tasks;
                        taskLength = tasks.length;

                        for (i = 0; i < taskLength; ++i) {
                            task = tasksTemp[i];
                            if (task.needToRun(now)) {
                                ++period;
                                if (this.stopped) {
                                    break;
                                }
                                task.setRunning(true);
                                if (this.run(task)) {
                                    task.updateStatus();
                                    if (task.needRemove()) {
                                        synchronized (this.lock) {
                                            taskList.remove(task);
                                            tasks = (Task[]) taskList.toArray(new Task[taskList.size()]);
                                        }
                                    }
                                } else {
                                    task.setRunning(false);
                                }
                            }
                        }

                        var5 = (long) (period * 100);

                        try {
                            lock.wait(100L);
                        } catch (Exception e) {
                        }
                    }
                }

                return;
            }
        }
    }

}
