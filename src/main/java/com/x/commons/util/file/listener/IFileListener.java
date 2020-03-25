package com.x.commons.util.file.listener;

import java.io.File;

 interface IFileListener {
     void onModify(File file);

    void onCreate(File file);

    void onDelete(File file) ;
}
