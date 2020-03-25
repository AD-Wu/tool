package com.x.commons.util.file.listener;

import com.sun.nio.file.SensitivityWatchEventModifier;
import com.x.commons.util.bean.New;
import com.x.commons.util.file.Files;
import com.x.commons.util.thread.Runner;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Desc：
 * @Author：AD
 * @Date：2020-3-25 10:08
 */
public final class FolderMonitor implements IFolderMonitor, Runnable {

    private static final WatchEvent.Kind[] events;

    private final String folder;

    private final WatchService watcher;

    private final Set<IFileListener> listeners;

    private ExecutorService runner;

    private final Set<String> modifies = New.syncSet();

    private final Set<String> creates = New.syncSet();

    private volatile boolean started = false;

    public FolderMonitor(String folder) throws Exception {
        this.folder = folder;
        Path path = Files.getPath(folder);
        this.watcher = FileSystems.getDefault().newWatchService();
        path.register(watcher, events, SensitivityWatchEventModifier.HIGH);
        this.listeners = New.syncSet();

    }

    @Override
    public void addListener(IFileListener listener) {
        if (listener != null) {
            listeners.add(listener);
            if (!started) {
                synchronized (this) {
                    if (!started) {
                        started = true;
                        start();
                    }
                }
            }
        }
    }

    @Override
    public void removeListener(IFileListener listener) {
        listeners.remove(listener);
    }


    @Override
    public void run() {
        while (true) {
            /*
             * take：获取变化信息的监控池，没有则一直等待，适合长时间监控
             * poll：获取变化信息的监控池，没有则返回null,适合某个时间点监控
             */
            WatchKey key = null;
            try {
                key = watcher.take();
            } catch (Exception e) {
                e.printStackTrace();
                stop();
                return;
            }
            for (WatchEvent event : key.pollEvents()) {
                Path context = (Path) event.context();
                String filename = context.getFileName().toString();
                if (filename.contains("___jb_old___") || filename.contains("___jb_tmp___")) {
                    continue;
                }
                String path = Files.fixPath(folder) + filename;
                File file = Files.getFile(path);
                if (file == null) {
                    continue;
                }
                WatchEvent.Kind kind = event.kind();// modify、create、delete、overflow
                for (IFileListener listener : listeners) {
                    Runner.add(() -> {
                        switch (kind.name()) {
                            case "ENTRY_MODIFY":
                                if (file.isFile() && file.exists()) {
                                    if (file.length() > 0) {
                                        modifies.add(filename);
                                        listener.onModify(file);
                                    } else {
                                        if (modifies.contains(filename)) {
                                            modifies.remove(filename);
                                            listener.onModify(file);
                                        }
                                    }
                                }
                                break;
                            case "ENTRY_CREATE":
                                if (file.isFile() && file.length() == 0) {
                                    creates.add(filename);
                                    listener.onCreate(file);
                                }
                                break;
                            case "ENTRY_DELETE":
                                if (!file.exists() && creates.contains(filename)) {
                                    listener.onDelete(file);
                                }
                                break;
                        }
                    });
                }
            }
            /*
             * -每次take()\poll()都会导致线程监控阻塞，操作文件可能时间长，
             *  如果监听目录下有其他事件发生，将会导致事件丢失。
             * -重置操作表示重启该线程，后续的事件都会被读到。
             */
            key.reset();
        }
    }

    private void start() {
        runner = Executors.newSingleThreadExecutor();
        runner.execute(this);
    }

    private void stop() {
        try {
            watcher.close();
            runner.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getFolder() {
        return folder;
    }

    static {
        events = new WatchEvent.Kind[]{
                StandardWatchEventKinds.ENTRY_MODIFY,
                StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_DELETE,
                StandardWatchEventKinds.OVERFLOW
        };
    }

}
