//
// Source code recreated fromJson a .class file by IntelliJ IDEA
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
public final class Timer {
    
    // ------------------------ 变量定义 ------------------------
    
    /**
     * 自身对象，定时器是重量级资源，一般用get()方法获取定时器，默认单例
     */
    private static Timer instance;
    
    /**
     * 单例对象锁
     */
    private static Object instanceLock = new Object();
    
    /**
     * 定时器集合，管理所有的定时器
     */
    private static List<Timer> timerList = new ArrayList();
    
    /**
     * 定时器是否停止的标识（定时器其实就是一条线程监测线程池里的任务）
     */
    private volatile boolean stopped;
    
    /**
     * 监测线程
     */
    private Thread checker;
    
    /**
     * 任务线程池
     */
    private ThreadPoolExecutor pool;
    
    /**
     * 对象锁
     */
    private Object lock;
    
    /**
     * 对象任务集合
     */
    private List<Task> taskList;
    
    /**
     * 任务数组，List转换而来，数组效率高
     */
    private Task[] tasks;
    
    // ------------------------ 构造方法 ------------------------
    
    /**
     * 定时器构造方法
     *
     * @param name 监测线程名
     */
    public Timer(String name) {
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
    public Timer(String name, int remainSize, int maxPoolSize, long keepActiveTime, int queueSize) {
        this.lock = new Object();
        this.taskList = new ArrayList();
        this.tasks = new Task[0];
        synchronized (instanceLock) {
            timerList.add(this);
        }
        
        this.checker = new Thread("X-[Timer-Checker]-[" + name + "]") {
            
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
         * （5）runnableTaskQueue：阻塞队列。可以使用ArrayBlockingQueue、LinkedBlockingQueue、SynchronousQueue、PriorityBlockingQueue
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
    
    // ------------------------ 方法定义 ------------------------
    
    public static Timer get() {
        if (instance != null) {
            return instance;
        } else {
            synchronized (instanceLock) {
                if (instance != null) {
                    return instance;
                }
                instance = new Timer("system", 0, 100, 60L, 0);
                // 启动checker线程，开始检测任务
                instance.start();
            }
            return instance;
        }
    }
    
    /**
     * 默认立即执行,只执行一次
     *
     * @param runnable 可运行的任务
     */
    public void add(Runnable runnable) {
        add(runnable, 0, 0, 1);
    }
    
    /**
     * 默认无延迟固定周期执行（所有的定时任务都是上一次执行完毕才会执行下一次）
     *
     * @param runnable 可运行的任务
     * @param period   间隔周期
     * @param timeUnit 时间单位
     */
    public void add(Runnable runnable, int period, TimeUnit timeUnit) {
        long interval = timeUnit.toMillis(period);
        add(runnable, 0, interval);
    }
    
    /**
     * 延迟固定周期执行（所有的定时任务都是上一次执行完毕才会执行下一次）
     *
     * @param runnable 可运行的任务
     * @param delay    延迟时间
     * @param period   间隔周期
     * @param unit     时间单位（延迟和周期用同一个单位）
     */
    public void add(Runnable runnable, int delay, int period, TimeUnit unit) {
        add(runnable, unit.toMillis(delay), unit.toMillis(period));
    }
    
    /**
     * 延迟固定周期执行（所有的定时任务都是上一次执行完毕才会执行下一次）
     *
     * @param runnable   可运行的任务
     * @param delay      延迟时间
     * @param delayUnit  延迟单位
     * @param period     间隔周期
     * @param periodUnit 间隔单位
     */
    public void add(Runnable runnable, int delay, TimeUnit delayUnit, int period, TimeUnit periodUnit) {
        add(runnable, delayUnit.toMillis(delay), periodUnit.toMillis(period));
    }
    
    /**
     * 默认延迟执行一次
     *
     * @param runnable 可运行任务
     * @param delay    延迟时间
     */
    public void add(Runnable runnable, long delay) {
        this.add(runnable, delay, 1L, 1L);
    }
    
    /**
     * 默认延迟间隔循环执行，时间单位毫秒
     *
     * @param runnable 可执行任务
     * @param delay    延迟时间
     * @param period   周期
     */
    public void add(Runnable runnable, long delay, long period) {
        this.add(runnable, delay, period, 0);
    }
    
    /**
     * 延迟间隔有限次数执行，时间单位毫秒
     *
     * @param runnable    可执行任务
     * @param delay       延迟
     * @param period      周期
     * @param repeatCount 重复次数
     */
    public void add(Runnable runnable, long delay, long period, long repeatCount) {
        if (runnable != null) {
            Task task = new Task(runnable, delay, period, repeatCount);
            synchronized (this.lock) {
                this.taskList.add(task);
                this.tasks = taskList.toArray(new Task[0]);
            }
        }
    }
    
    /**
     * 是否包含任务
     *
     * @param runnable 可执行任务
     *
     * @return true：包含  false：不包含
     */
    public boolean contains(Runnable runnable) {
        
        for (Task task : tasks) {
            if (task.getTask().equals(runnable)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * 移除某个任务
     *
     * @param runnable 可执行任务
     *
     * @return
     */
    public boolean remove(Runnable runnable) {
        Task task = null;
        for (Task t : tasks) {
            if (t.getTask().equals(runnable)) {
                task = t;
                break;
            }
        }
        
        if (task != null) {
            synchronized (this.lock) {
                this.taskList.remove(task);
                this.tasks = taskList.toArray(new Task[0]);
                return true;
            }
        } else {
            return false;
        }
    }
    
    /**
     * 更新任务的执行周期
     *
     * @param runnable 可执行任务
     * @param period   周期
     */
    public void update(Runnable runnable, int period) {
        
        for (int i = 0, L = tasks.length; i < L; ++i) {
            Task task = tasks[i];
            if (task.getTask().equals(runnable)) {
                task.updatePeriod(period);
                break;
            }
        }
        
    }
    
    /**
     * 清除当前定时器的所有任务
     */
    public void clear() {
        synchronized (this.lock) {
            taskList.clear();
            tasks = taskList.toArray(new Task[0]);
        }
    }
    
    /**
     * 清除所有定时器的所有任务
     */
    static void clearAll() {
        synchronized (instanceLock) {
            Iterator<Timer> it = timerList.iterator();
            while (it.hasNext()) {
                Timer timer = it.next();
                timer.clear();
            }
        }
    }
    
    /**
     * 清除当前定时器的所有任务，并关闭任务线程池
     */
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
    
    /**
     * 销毁所有定时器的任务，并关闭所有定时器的任务线程池
     */
    public static void destroyAll() {
        Timer[] timers;
        synchronized (instanceLock) {
            instance = null;
            timers = (Timer[]) timerList.toArray(new Timer[0]);
        }
        for (Timer timer : timers) {
            timer.destroy();
        }
    }
    
    /**
     * 启动任务，如果是new Timer()获取的线程池，需手动调用，建议用get()方法获取定时器
     */
    public synchronized void start() {
        if (!this.stopped && !this.checker.isAlive()) {
            this.checker.start();
        }
    }
    
    // ------------------------ 私有方法 ------------------------
    
    private synchronized boolean run(Runnable task) {
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
    
    /**
     * 定时器就是一条线程监测线程池执行任务
     */
    private void checkTask() {
        // 获取开始时间
        long prev = System.currentTimeMillis();
        long complexity = 0L;
        // 对象锁
        Object taskLock = new Object();
        synchronized (taskLock) {
            while (true) {
                // 判断当前定时器是否终止
                while (!stopped) {
                    // 如果没有任务等待100毫秒
                    if (tasks.length == 0) {
                        try {
                            taskLock.wait(100L);
                        } catch (Exception e) {
                        }
                    } else {
                        // 计算间隔时长
                        long now = System.currentTimeMillis();
                        if (now < prev || now > prev + (long) (tasks.length * 2) + complexity + 1000L) {
                            // 计算出周期
                            int period = (int) (now - prev);
                            // 更新当前定时器所有任务的下一次执行时间
                            for (Task task : tasks) {
                                task.updateTime(period);
                            }
                        }
                        // 更新完毕，初始化变量
                        prev = now;
                        int runs = 0;
                        
                        // 下次时间更新完毕后，需执行任务
                        for (Task task : tasks) {
                            if (task.needToRun(now)) {
                                ++runs;
                                // 如果定时器已停止，则不执行任务
                                if (stopped) {
                                    break;
                                }
                                // 将任务设置为运行状态
                                task.setRunning(true);
                                // 将任务丢到线程池执行
                                if (run(task)) {
                                    // 更新任务的下次执行时间并计算执行次数
                                    task.updateStatus();
                                    // 判断任务执行一次之后是否需要移除
                                    if (task.needRemove()) {
                                        synchronized (this.lock) {
                                            taskList.remove(task);
                                            tasks = taskList.toArray(new Task[0]);
                                        }
                                    }
                                } else {
                                    task.setRunning(false);
                                }
                            }
                        }
                        complexity = (long) (runs * 100);
                        
                        try {
                            taskLock.wait(100L);
                        } catch (Exception e) {
                        }
                    }
                }
                return;
            }
        }
    }
    
}
