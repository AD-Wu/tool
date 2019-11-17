package com.x.commons.util.file.monitor;

/**
 * @Desc TODO
 * @Date 2019-10-26 11:15
 * @Author AD
 */
public abstract class FileWatched implements IWatched {

    private final String filename;

    public FileWatched(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return this.filename;
    }

}
