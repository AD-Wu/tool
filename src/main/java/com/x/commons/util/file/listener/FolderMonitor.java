package com.x.commons.util.file.listener;

import com.sun.nio.file.SensitivityWatchEventModifier;
import com.x.commons.util.file.Files;
import com.x.commons.util.thread.Runner;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Desc：
 * @Author：AD
 * @Date：2020-3-25 10:08
 */
public final class FolderMonitor {

    private static final WatchEvent.Kind[] events;

    private final String folder;

    private final WatchService watcher;

    private final IFileListener listener;

    private ExecutorService runner;

    private volatile boolean started = false;

    public FolderMonitor(String folder, IFileListener listener) throws Exception {
        this.folder = folder;
        Path path = Files.getPath(folder);
        this.watcher = FileSystems.getDefault().newWatchService();
        path.register(watcher, events, SensitivityWatchEventModifier.HIGH);
        this.listener = listener;
    }

    public void start() {
        if (started) {
            return;
        }
        synchronized (this) {
            if (started) {
                return;
            }
            this.started = true;
            this.runner = Executors.newSingleThreadExecutor();
            runner.execute(() -> {
                while (!runner.isShutdown()) {
                    /*
                     * take：获取变化信息的监控池，没有则一直等待，适合长时间监控
                     * poll：获取变化信息的监控池，没有则返回null,适合某个时间点监控
                     */
                    WatchKey key = null;
                    try {
                        key = watcher.take();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                    for (WatchEvent event : key.pollEvents()) {
                        Path context = (Path) event.context();
                        String filename = context.getFileName().toString();
                        if (filename.contains("___jb_old___") ||
                                filename.contains("___jb_tmp___")) {
                            continue;
                        }
                        String path = Files.fixPath(folder) + filename;
                        File file = Files.getFile(path);
                        if (file == null) {
                            continue;
                        }
                        WatchEvent.Kind kind = event.kind();// modify、create、delete、overflow
                        Runner.add(() -> {
                            switch (kind.name()) {
                                case "ENTRY_MODIFY":
                                    if (file.isFile() && file.exists()) {
                                        listener.onModify(file);
                                    }
                                    break;
                                case "ENTRY_CREATE":
                                    /*
                                     - 修改已存在文件时,会产生modify、create事件,需过滤create
                                     - 如果是复制,会产生create、modify事件，复制由modify处理
                                     */
                                    if (file.isFile() && file.length() == 0) {
                                        listener.onCreate(file);
                                    }
                                    break;
                                case "ENTRY_DELETE":
                                    /*
                                     - 此处必须双重验证
                                     -（window测试：会莫名其妙触发删除动作,实际文件并没有动作）
                                     */
                                    if (!file.exists()) {
                                        File confirm = Files.getFile(file.getAbsolutePath());
                                        if (confirm == null || !confirm.exists()) {
                                            listener.onDelete(file);
                                        }
                                    }
                                    break;
                            }
                        });
                    }
                    /*
                     * -每次take()\poll()都会导致线程监控阻塞，操作文件可能时间长，
                     *  如果监听目录下有其他事件发生，将会导致事件丢失。
                     * -重置操作表示重启该线程，后续的事件都会被读到。
                     */
                    key.reset();
                }
            });
        }
    }

    public void stop() {
        if (started) {
            try {
                watcher.close();
                runner.shutdown();
                this.started = false;
            } catch (IOException e) {
                e.printStackTrace();
            }
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
