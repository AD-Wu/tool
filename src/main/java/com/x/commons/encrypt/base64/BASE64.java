package com.x.commons.encrypt.base64;

import com.x.commons.enums.Charset;
import com.x.commons.util.string.Strings;
import org.apache.commons.codec.binary.Base64InputStream;

import java.io.*;

/**
 * @Desc TODO
 * @Date 2020-03-21 00:59
 * @Author AD
 */
public class BASE64 {
    public static final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

    static final char pad = '=';

    private String charset;

    public BASE64() {
        this.charset = Charset.UTF8;
    }

    public BASE64(String charset) {
        this.charset = charset;
    }

    public String encode(String s) throws Exception {
        return encode(s, charset);
    }

    public String encode(String s, String charset) throws Exception {
        if (Strings.isNotNull(s)) {
            byte[] bytes = s.getBytes(charset);
            byte[] var4 = this.encode(bytes, 0);
            return new String(var4, charset);
        } else {
            return s;
        }
    }

    public String decode(String var1) throws Exception {
        return this.decode(var1, this.charset);
    }

    public String decode(String var1, String var2) throws Exception {
        if (var1 != null && var1.length() != 0) {
            byte[] var3 = var1.getBytes(var2);
            byte[] var4 = this.decode(var3);
            return new String(var4, var2);
        } else {
            return var1;
        }
    }

    public byte[] encode(byte[] var1) throws Exception {
        return this.encode(var1, 0);
    }

    public byte[] encode(byte[] var1, int var2) throws Exception {
        ByteArrayInputStream var3 = new ByteArrayInputStream(var1);
        ByteArrayOutputStream var4 = new ByteArrayOutputStream();
        this.encode((InputStream) var3, (OutputStream) var4, var2);

        try {
            var3.close();
        } catch (Throwable var7) {
        }

        try {
            var4.close();
        } catch (Throwable var6) {
        }

        return var4.toByteArray();
    }

    public byte[] decode(byte[] var1) throws Exception {
        ByteArrayInputStream var2 = new ByteArrayInputStream(var1);
        ByteArrayOutputStream var3 = new ByteArrayOutputStream();
        this.decode((InputStream) var2, (OutputStream) var3);

        try {
            var2.close();
        } catch (Throwable var6) {
        }

        try {
            var3.close();
        } catch (Throwable var5) {
        }

        return var3.toByteArray();
    }

    public void encode(InputStream var1, OutputStream var2) throws Exception {
        this.encode((InputStream) var1, (OutputStream) var2, 0);
    }

    public void encode(InputStream var1, OutputStream var2, int var3) throws Exception {
        BASE64OutputStream var4 = new BASE64OutputStream(var2, var3);
        this.copy(var1, var4);
        var4.commit();
    }

    public void decode(InputStream var1, OutputStream var2) throws Exception {
        this.copy(new Base64InputStream(var1), var2);
    }

    public void encode(File var1, File var2, int var3) throws Exception {
        FileInputStream var4 = new FileInputStream(var1);
        FileOutputStream var5 = new FileOutputStream(var2);
        this.encode((InputStream) var4, (OutputStream) var5, var3);

        try {
            var4.close();
        } catch (Throwable var8) {
        }

        try {
            var5.close();
        } catch (Throwable var7) {
        }

    }

    public void encode(File var1, File var2) throws Exception {
        FileInputStream var3 = new FileInputStream(var1);
        FileOutputStream var4 = new FileOutputStream(var2);
        this.encode((InputStream) var3, (OutputStream) var4);

        try {
            var3.close();
        } catch (Throwable var7) {
        }

        try {
            var4.close();
        } catch (Throwable var6) {
        }

    }

    public void decode(File var1, File var2) throws Exception {
        FileInputStream var3 = new FileInputStream(var1);
        FileOutputStream var4 = new FileOutputStream(var2);
        this.decode((InputStream) var3, (OutputStream) var4);

        try {
            var3.close();
        } catch (Throwable var7) {
        }

        try {
            var4.close();
        } catch (Throwable var6) {
        }

    }

    private void copy(InputStream var1, OutputStream var2) throws IOException {
        byte[] var3 = new byte[1024];

        int var4;
        while ((var4 = var1.read(var3)) != -1) {
            var2.write(var3, 0, var4);
        }

    }
}
