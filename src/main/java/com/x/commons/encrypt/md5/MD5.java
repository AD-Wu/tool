package com.x.commons.encrypt.md5;

import com.x.commons.util.string.Strings;

import java.io.File;
import java.io.FileInputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Date 2018-12-19 20:19
 * @Author AD
 */
public final class MD5 {
    
    private MessageDigest md;
    
    public MD5() {
        try {
            this.md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
    
    public String encode(String str) throws Exception {
        md.update(str.getBytes());
        return Strings.toHex(md.digest());
    }
    
    public String encode(File file) throws Exception {
        
        try (FileInputStream in = new FileInputStream(file)) {
            MappedByteBuffer buf = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0L, file.length());
            md.update(buf);
        }
        return Strings.toHex(md.digest());
    }
    
}
