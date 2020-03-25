package com.x.commons.util.file.monitor;

import com.x.commons.timming.Timer;
import com.x.commons.util.file.Files;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import static com.sun.nio.file.SensitivityWatchEventModifier.HIGH;
import static java.nio.file.StandardWatchEventKinds.*;

/**
 * @Desc 监测文件夹下某个文件（包括文件夹）的变化，不能递归监测，只能监测一级
 * @Date 2019-10-22 22:35
 * @Author AD
 */
public final class FolderWatcher implements IWatcher<FileWatched> {
    
    private final WatchService watcher;
    
    private final Map<String, FileWatched> watchedMap;
    
    private int period;
    
    private TimeUnit timeUnit;
    
    private final String folderPath;
    
    private Runnable runnable;
    
    private volatile boolean start = false;
    
    public static FolderWatcher getModifyWatcher(String folderPath) throws Exception {
        return getModifyWatcher(folderPath, 60);
    }
    
    public static FolderWatcher getModifyWatcher(String folderPath, int period) throws Exception {
        return new Builder(folderPath).period(period).modify().build();
    }
    
    public FolderWatcher(Builder builder) throws Exception {
        this.watcher = FileSystems.getDefault().newWatchService();
        this.watchedMap = new ConcurrentHashMap(10);
        this.folderPath = builder.folderPath;
        Path folder = Files.getPath(folderPath);
        folder.register(watcher, builder.getEvents(), HIGH);
        this.period = builder.period;// 默认一分钟监听一次
        this.timeUnit = builder.timeUnit;// 默认秒
    }
    
    /**
     * 增加监测文件
     *
     * @param watched 被监测的文件名
     *
     * @return
     */
    @Override
    public FolderWatcher addWatched(FileWatched watched) {
        watchedMap.put(watched.getFilename(), watched);
        return this;
    }
    
    /**
     * 删除监测文件
     *
     * @param watched 被删除的文件对象
     *
     * @return
     */
    @Override
    public FolderWatcher deleteWatched(FileWatched watched) {
        watchedMap.remove(watched.getFilename());
        return this;
    }
    
    /**
     * 设置监测间隔，默认单位秒
     *
     * @param period
     *
     * @return
     */
    public FolderWatcher setPeriod(int period) {
        this.period = period;
        return this;
    }
    
    /**
     * 设置监测间隔
     *
     * @param period
     *
     * @return
     */
    public FolderWatcher setPeriod(int period, TimeUnit timeUnit) {
        this.period = period;
        this.timeUnit = timeUnit;
        return this;
    }
    
    public void start() {
        // 定时器，内部有线程池
        if (!start) {
            Timer timer = Timer.get();
            this.runnable = new Runnable() {
                
                @Override
                public void run() {
                    try {
                        /**
                         * take：获取变化信息的监控池，没有则一直等待，适合长时间监控
                         * poll：获取变化信息的监控池，没有则返回null,适合某个时间点监控
                         */
                        WatchKey key = watcher.take();
                        List<WatchEvent<?>> events = key.pollEvents();
                        for (WatchEvent event : events) {
                            Path context = (Path)event.context();
                            String filename = String.valueOf(context.getFileName());
                            WatchEvent.Kind kind = event.kind();// modify、create、delete
                            FileWatched watched = watchedMap.get(filename);
                            if (watched != null) {
                                String path = Files.fixPath(folderPath) + filename;
                                watched.change(Files.getFile(path));
                            }
                        }
                        /**
                         * -每次take()\poll()都会导致线程监控阻塞，操作文件可能时间长，
                         *     如果监听目录下有其他事件发生，将会导致事件丢失。
                         *
                         * -重置操作表示重启该线程，后续的事件都会被读到。
                         */
                        if (key != null) {
                            boolean reset = key.reset();
                            if (!reset) {
                                return;
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            timer.add(this.runnable, period, timeUnit);
            start = true;
        }
    }
    
    public void stop() throws IOException {
        start = false;
        Timer.get().remove(this.runnable);
        watcher.close();
        watchedMap.clear();
    }
    
    public static class Builder {
        
        /**
         * 监听间隔
         */
        private int period = 60;
        
        /**
         * 时间单位
         */
        private TimeUnit timeUnit = TimeUnit.SECONDS;
        
        /**
         * 所监听的文件夹路径，必须是文件夹
         */
        private final String folderPath;
        
        /**
         * 监听事件集合
         */
        private final List<WatchEvent.Kind> events;
        
        public Builder(String folderPath) throws Exception {
            if (!Files.getFile(folderPath).isDirectory()) {
                throw new Exception(folderPath + " is not a directory");
            }
            this.folderPath = folderPath;
            this.events = new ArrayList<>(5);
        }
        
        WatchEvent.Kind[] getEvents() {
            return events.toArray(new WatchEvent.Kind[0]);
        }
        
        public Builder period(int period) {
            if (period > 0) {
                this.period = period;
            }
            return this;
        }
        
        public Builder timeUnit(TimeUnit unit) {
            this.timeUnit = unit;
            return this;
        }
        
        // 监听修改
        public Builder modify() {
            events.add(ENTRY_MODIFY);
            return this;
        }
        
        // 监听创建
        public Builder create() {
            events.add(ENTRY_CREATE);
            return this;
        }
        
        // 监听删除
        public Builder delete() {
            events.add(ENTRY_DELETE);
            return this;
        }
        
        public FolderWatcher build() throws Exception {
            return new FolderWatcher(this);
        }
        
    }
    
}
