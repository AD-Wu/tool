package com.x.commons.encrypt.base64;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Desc：
 * @Author：AD
 * @Date：2020/3/27 14:42
 */
public class BASE64InputStream extends InputStream {
    private InputStream in;
    private int[] buffer;
    private int bufferCounter = 0;
    private boolean eof = false;

    public BASE64InputStream(InputStream in) {
        this.in = in;
    }

    @Override
    public int read() throws IOException {
        if (buffer == null || bufferCounter == buffer.length) {
            if (eof) {
                return -1;
            }
            acquire();
            if (buffer.length == 0) {
                buffer = null;
                return -1;
            }
            bufferCounter = 0;
        }
        return buffer[bufferCounter++];
    }

    private void acquire() throws IOException {
        char[] results = new char[4];
        int i = 0;

        do {
            int nextByte = in.read();
            if (nextByte == -1) {
                if (i != 0) {
                    throw new IOException("Bad base64 stream");
                }
                buffer = new int[0];
                eof = true;
                return;
            }

            char read = (char)nextByte;
            if (BASE64.chars.indexOf(read) == -1 && read != '=') {
                if (read != '\r' && read != '\n') {
                    throw new IOException("Bad base64 stream");
                }
            } else {
                results[i++] = read;
            }
        } while(i < 4);

        boolean var6 = false;

        for(i = 0; i < 4; ++i) {
            if (results[i] != '=') {
                if (var6) {
                    throw new IOException("Bad base64 stream");
                }
            } else if (!var6) {
                var6 = true;
            }
        }

        byte var7;
        if (results[3] == '=') {
            if (this.in.read() != -1) {
                throw new IOException("Bad base64 stream");
            }

            eof = true;
            if (results[2] == '=') {
                var7 = 1;
            } else {
                var7 = 2;
            }
        } else {
            var7 = 3;
        }

        int var5 = 0;

        for(i = 0; i < 4; ++i) {
            if (results[i] != '=') {
                var5 |= BASE64.chars.indexOf(results[i]) << 6 * (3 - i);
            }
        }

        buffer = new int[var7];

        for(i = 0; i < var7; ++i) {
            buffer[i] = var5 >>> 8 * (2 - i) & 255;
        }

    }

    public void close() throws IOException {
        in.close();
    }
}
