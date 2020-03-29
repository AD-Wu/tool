package com.x.commons.util.bean;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @Desc
 * @Date 2019-11-25 20:31
 * @Author AD
 */
public final class ByteArray extends ByteArrayOutputStream {
    
    public ByteArray() {
        super();
    }
    
    public ByteArray(int size) {
        super(size);
    }
    
    public void write(byte[] data, int offset, int len) {
        super.write(data, offset, len);
    }
    
    public void write(int data) {
        super.write(data);
    }
    
    public void write(byte[] data) throws IOException {
        super.write(data);
    }
    
    public void writeTo(OutputStream output) throws IOException {
        super.writeTo(output);
    }
    public byte[] get(){
        return super.toByteArray();
    }
}
