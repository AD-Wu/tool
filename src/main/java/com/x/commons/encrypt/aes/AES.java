package com.x.commons.encrypt.aes;

import com.x.commons.enums.Charset;
import com.x.commons.util.collection.XArrays;
import com.x.commons.util.string.Strings;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * @Desc：
 * @Author：AD
 * @Date：2020/3/27 13:26
 */
public final class AES {
    private Cipher enCipher;

    private Cipher deCipher;

    private final String charset;

    public AES() {
        this.charset = Charset.UTF8;
    }

    public AES(String charset) {
        this.charset = charset;
    }

    public static byte[] generateKeyBytes(int length) throws Exception {
        KeyGenerator aes = KeyGenerator.getInstance("AES");
        aes.init(length);
        SecretKey secretKey = aes.generateKey();
        return secretKey.getEncoded();
    }

    public void setKey(String key) throws Exception {
        if (Strings.isNotNull(key) && key.length() % 8 == 0) {
            this.setKey(key.getBytes(charset));
        }
    }

    public void setKey(byte[] bytes) throws Exception {
        if (!XArrays.isEmpty(bytes)) {
            SecretKeySpec aes = new SecretKeySpec(bytes, "AES");
            this.enCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            this.enCipher.init(1, aes);
            this.deCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            this.deCipher.init(2, aes);
        }
    }

    public String encode(String encoded) throws Exception {
        return encode(encoded, this.charset);
    }

    public String encode(String encoded, String charset) throws Exception {
        byte[] bytes = encode(encoded.getBytes(charset));
        return Strings.toHex(bytes);
    }

    public byte[] encode(byte[] bytes) throws Exception {
        return enCipher.doFinal(bytes);
    }

    public String decode(String hex) throws Exception {
        return decode(hex, this.charset);
    }

    public String decode(String hex, String charset) throws Exception {
        byte[] bytes = decode(Strings.hexToBytes(hex));
        return new String(bytes, charset);
    }

    public byte[] decode(byte[] bytes) throws Exception {
        return deCipher.doFinal(bytes);
    }

    public static void main(String[] args) throws Exception {
        String encoded = "测试";
        byte[] bytes = generateKeyBytes(128);
        System.out.println("key len: " + bytes.length);
        AES aes = new AES();
        aes.setKey(bytes);
        String encode = aes.encode(encoded);
        System.out.println(encode);
        String decode = aes.decode(encode);
        System.out.println(decode);

    }
}
