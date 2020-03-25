package com.x.commons.util.file.listener;

import java.io.File;

public interface IFileListener {
    void onModify(File file);

    void onCreate(File file);

    void onDelete(File file) ;
}
