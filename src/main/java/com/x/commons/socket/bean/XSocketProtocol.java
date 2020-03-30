package com.x.commons.socket.bean;

import com.x.commons.util.collection.XArrays;

import java.io.Serializable;
import java.util.Arrays;
import java.util.StringJoiner;

/**
 * @Desc START[0x68] LEN[2] DATA[N] CHECK[1] END[0x16]
 * @Date 2020-03-28 15:34
 * @Author AD
 */
public class XSocketProtocol implements Serializable {
    
    public static final byte START = 0x68;
    
    public static final byte END = 0x16;
    
    private final byte start = START;
    
    private final int length;// data长度
    
    private final byte[] data;
    
    private final byte check;
    
    private final byte end = END;
    
    public XSocketProtocol(byte[] data) {
        this.data = data != null ? data : XArrays.EMPTY_BYTE;
        this.length = data != null ? data.length : 0;
        int checkSum = 0;
        for (byte b : data) {
            checkSum += b;
        }
        this.check = (byte) (checkSum & 0xFF);
    }
    
    public XSocketProtocol(byte[] data, byte check) {
        this.data = data != null ? data : XArrays.EMPTY_BYTE;
        this.length = data != null ? data.length : 0;
        this.check = check;
    }
    
    public byte getStart() {
        return start;
    }
    
    public int getLength() {
        return length;
    }
    
    public byte[] getData() {
        return data;
    }
    
    public byte getCheck() {
        return check;
    }
    
    public byte getEnd() {
        return end;
    }
    
    @Override
    public String toString() {
        return new StringJoiner(", ", XSocketProtocol.class.getSimpleName() + "[", "]")
                .add("start=" + start)
                .add("length=" + length)
                .add("data=" + Arrays.toString(data))
                .add("check=" + check)
                .add("end=" + end)
                .toString();
    }
    
}
