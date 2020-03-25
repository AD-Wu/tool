package com.x.commons.util.file.listener;

public interface IFolderMonitor {

    void addListener(IFileListener listener);

    void removeListener(IFileListener listener);

}
