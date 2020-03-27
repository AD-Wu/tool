package com.x.commons.encrypt.base64;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @Desc：
 * @Author：AD
 * @Date：2020/3/27 14:43
 */
public class BASE64OutputStream extends OutputStream {
    private OutputStream out;

    private int buffer;

    private int byteCounter;

    private int lineCounter;

    private int lineLength;

    /*
    按照RFC822规定，Base64编码每76个字符需加上一个回车换行
     */
    public BASE64OutputStream(OutputStream out) {
        this(out, 76);
    }

    public BASE64OutputStream(OutputStream out, int lineLength) {
        this.out = null;
        this.buffer = 0;
        this.byteCounter = 0;
        this.lineCounter = 0;
        this.lineLength = 0;
        this.out = out;
        this.lineLength = lineLength;
    }

    @Override
    public void write(int b) throws IOException {
        int var2 = (b & 255) << 16 - this.byteCounter * 8;
        buffer |= var2;
        ++byteCounter;
        if (byteCounter == 3) {
            commit();
        }

    }

    public void close() throws IOException {
        commit();
        out.close();
    }

    protected void commit() throws IOException {
        if (this.byteCounter > 0) {
            if (this.lineLength > 0 && this.lineCounter == this.lineLength) {
                this.out.write("\r\n".getBytes());
                this.lineCounter = 0;
            }

            char var1 = BASE64.chars.charAt(this.buffer << 8 >>> 26);
            char var2 = BASE64.chars.charAt(this.buffer << 14 >>> 26);
            char var3 = this.byteCounter < 2 ? 61 : BASE64.chars.charAt(this.buffer << 20 >>> 26);
            char var4 = this.byteCounter < 3 ? 61 : BASE64.chars.charAt(this.buffer << 26 >>> 26);
            out.write(var1);
            out.write(var2);
            out.write(var3);
            out.write(var4);
            lineCounter += 4;
            byteCounter = 0;
            buffer = 0;
        }

    }

}
