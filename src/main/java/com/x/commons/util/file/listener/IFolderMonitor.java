package com.x.commons.util.file.listener;

public interface IFolderMonitor {

    void addFolderListener(IFileListener listener);

    void removeFolderListener();

    void addFileListener(String filename, IFileListener listener);

    void removeFileListener(String filename);
}
