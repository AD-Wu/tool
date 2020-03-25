package com.x.commons.util.file.listener;

import com.x.commons.util.bean.New;

import java.io.File;
import java.util.Set;

/**
 * @Desc：
 * @Author：AD
 * @Date：2020/3/25 15:30
 */
public abstract class FileListener implements IFileListener {

    private final Set<String> modifies = New.syncSet();

    private final Set<String> creates = New.syncSet();

    @Override
    public final void onModify(File file) {
        if (file.isFile() && file.exists()) {
            if (file.length() > 0) {
                try {
                    modifies.add(file.getAbsolutePath());
                    onFileModify(file);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                if (modifies.contains(file.getAbsolutePath())) {
                    try {
                        modifies.remove(file.getAbsolutePath());
                        onFileModify(file);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public final void onCreate(File file) {
        if (file.isFile() && file.length() == 0) {
            creates.add(file.getAbsolutePath());
            onFileCreate(file);
        }
    }

    @Override
    public final void onDelete(File file) {
        if (!file.exists() && creates.contains(file.getAbsolutePath())) {
            onFileDelete(file);
        }
    }


    protected abstract void onFileModify(File file);

    protected abstract void onFileCreate(File file);

    protected abstract void onFileDelete(File file);
}
