package com.x.commons.encrypt.rsa;

import com.ax.commons.collection.DataSet;
import com.ax.commons.encrypt.base64.BASE64;
import com.ax.commons.utils.ConvertHelper;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @Desc：
 * @Author：AD
 * @Date：2020/3/27 14:38
 */
public class RSA {
    private RSAPublicKey publicKey;
    private RSAPrivateKey privateKey;

    public RSA() {
    }

    public static KeyPair generateKeyPair(int var0) throws Exception {
        KeyPairGenerator var1 = KeyPairGenerator.getInstance("RSA");
        var1.initialize(var0, new SecureRandom());
        KeyPair var2 = var1.genKeyPair();
        return var2;
    }

    public static RSAPublicKey generateRSAPublicKey(byte[] var0, byte[] var1) throws Exception {
        KeyFactory var2 = KeyFactory.getInstance("RSA");
        RSAPublicKeySpec var3 = new RSAPublicKeySpec(new BigInteger(var0), new BigInteger(var1));
        return (RSAPublicKey)var2.generatePublic(var3);
    }

    public static RSAPrivateKey generateRSAPrivateKey(byte[] var0, byte[] var1) throws Exception {
        KeyFactory var2 = KeyFactory.getInstance("RSA");
        RSAPrivateKeySpec var3 = new RSAPrivateKeySpec(new BigInteger(var0), new BigInteger(var1));
        return (RSAPrivateKey)var2.generatePrivate(var3);
    }

    public static DataSet generateKeys(int var0) throws Exception {
        KeyPair var1 = generateKeyPair(var0);
        RSAPublicKey var2 = (RSAPublicKey)var1.getPublic();
        RSAPrivateKey var3 = (RSAPrivateKey)var1.getPrivate();
        byte[] var4 = var2.getModulus().toByteArray();
        byte[] var5 = var2.getPublicExponent().toByteArray();
        byte[] var6 = var3.getModulus().toByteArray();
        byte[] var7 = var3.getPrivateExponent().toByteArray();
        RSAPublicKey var8 = generateRSAPublicKey(var4, var5);
        RSAPrivateKey var9 = generateRSAPrivateKey(var6, var7);
        BASE64 var10 = new BASE64();
        byte[] var11 = var10.encode(var8.getEncoded());
        byte[] var12 = var10.encode(var4);
        byte[] var13 = var10.encode(var5);
        byte[] var14 = var10.encode(var9.getEncoded());
        byte[] var15 = var10.encode(var6);
        byte[] var16 = var10.encode(var7);
        DataSet var17 = new DataSet();
        var17.add("PublicKey", new String(var11, "ASCII"));
        var17.add("PublicModules", new String(var12, "ASCII"));
        var17.add("PublicExponent", new String(var13, "ASCII"));
        var17.add("PrivateKey", new String(var14, "ASCII"));
        var17.add("PrivateModulus", new String(var15, "ASCII"));
        var17.add("PrivateExponent", new String(var16, "ASCII"));
        return var17;
    }

    public static RSAPublicKey decodePublicKey(String var0) throws Exception {
        BASE64 var1 = new BASE64();
        byte[] var2 = var1.decode(var0.getBytes());
        X509EncodedKeySpec var3 = new X509EncodedKeySpec(var2);
        KeyFactory var4 = KeyFactory.getInstance("RSA");
        return (RSAPublicKey)var4.generatePublic(var3);
    }

    public static RSAPrivateKey decodePrivateKey(String var0) throws Exception {
        BASE64 var1 = new BASE64();
        byte[] var2 = var1.decode(var0.getBytes());
        PKCS8EncodedKeySpec var3 = new PKCS8EncodedKeySpec(var2);
        KeyFactory var4 = KeyFactory.getInstance("RSA");
        return (RSAPrivateKey)var4.generatePrivate(var3);
    }

    public void setKey(RSAPublicKey var1, RSAPrivateKey var2) {
        this.publicKey = var1;
        this.privateKey = var2;
    }

    public byte[] encode(byte[] var1) throws Exception {
        Cipher var2 = Cipher.getInstance("RSA");
        var2.init(1, this.publicKey);
        int var3 = this.publicKey.getModulus().bitLength() / 8 - 11;
        int var4 = var2.getOutputSize(var1.length);
        int var5 = var1.length % var3;
        int var6 = var5 != 0 ? var1.length / var3 + 1 : var1.length / var3;
        byte[] var7 = new byte[var4 * var6];

        for(int var8 = 0; var1.length - var8 * var3 > 0; ++var8) {
            if (var1.length - var8 * var3 > var3) {
                var2.doFinal(var1, var8 * var3, var3, var7, var8 * var4);
            } else {
                var2.doFinal(var1, var8 * var3, var1.length - var8 * var3, var7, var8 * var4);
            }
        }

        return var7;
    }

    public byte[] decode(byte[] var1) throws Exception {
        Cipher var2 = Cipher.getInstance("RSA");
        var2.init(2, this.privateKey);
        int var3 = this.privateKey.getModulus().bitLength() / 8;
        ByteArrayOutputStream var4 = new ByteArrayOutputStream(64);

        for(int var5 = 0; var1.length - var5 * var3 > 0; ++var5) {
            var4.write(var2.doFinal(var1, var5 * var3, var3));
        }

        return var4.toByteArray();
    }

    public static String rsaEncodeTest() throws Exception {
        String var0 = "admin|E10ADC3949BA59ABBE56E057F20F883E|1456145396000";
        byte[] var1 = var0.getBytes("UTF-8");
        String var2 = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJJfsLg+gyTR8HyylVVbwDk8zbCr8eDMP7mdg3QUePLcVYS4+qOfwkrgEAB+1bXXZ5oHz4emplPpqlTFuOneenMCAwEAAQ==";
        BASE64 var3 = new BASE64();
        byte[] var4 = var3.decode(var2.getBytes());
        X509EncodedKeySpec var5 = new X509EncodedKeySpec(var4);
        KeyFactory var6 = KeyFactory.getInstance("RSA");
        RSAPublicKey var7 = (RSAPublicKey)var6.generatePublic(var5);
        Cipher var8 = Cipher.getInstance("RSA");
        var8.init(1, var7);
        int var9 = var7.getModulus().bitLength() / 8 - 11;
        int var10 = var8.getOutputSize(var1.length);
        int var11 = var1.length % var9;
        int var12 = var11 != 0 ? var1.length / var9 + 1 : var1.length / var9;
        byte[] var13 = new byte[var10 * var12];

        for(int var14 = 0; var1.length - var14 * var9 > 0; ++var14) {
            if (var1.length - var14 * var9 > var9) {
                var8.doFinal(var1, var14 * var9, var9, var13, var14 * var10);
            } else {
                var8.doFinal(var1, var14 * var9, var1.length - var14 * var9, var13, var14 * var10);
            }
        }

        return ConvertHelper.byte2Hex(var13);
    }

    public static void main(String[] var0) throws Exception {
        try {
            DataSet var1 = generateKeys(512);
            System.out.println("-------------- 公钥 --------------");
            String var2 = var1.getString("PublicKey");
            System.out.println("PublicKey:\r\n" + var2);
            System.out.println("PublicModules:\r\n" + var1.getString("PublicModules"));
            System.out.println("PublicExponent:\r\n" + var1.getString("PublicExponent"));
            System.out.println("-------------- 私钥 --------------");
            String var3 = var1.getString("PrivateKey");
            System.out.println("PrivateKey:\r\n" + var3);
            System.out.println("PrivateModulus:\r\n" + var1.getString("PrivateModulus"));
            System.out.println("PrivateExponent:\r\n" + var1.getString("PrivateExponent"));
            System.out.println("-------------- 加密 --------------");
            String var4 = "admin|E10ADC3949BA59ABBE56E057F20F883E|1456145396000";
            byte[] var5 = var4.getBytes("UTF-8");
            RSAPublicKey var6 = decodePublicKey(var2);
            RSAPrivateKey var7 = decodePrivateKey(var3);
            System.out.println("公钥长度: " + var6.getEncoded().length);
            System.out.println("私钥长度: " + var7.getEncoded().length);
            com.ax.commons.encrypt.rsa.RSA var8 = new com.ax.commons.encrypt.rsa.RSA();
            var8.setKey(var6, var7);
            byte[] var9 = var8.encode(var5);
            System.out.println("加密结果: " + ConvertHelper.byte2Hex(var9));
            byte[] var10 = var8.decode(var9);
            String var11 = new String(var10, "UTF-8");
            System.out.println("加密前: " + var4);
            System.out.println("解密后: " + var11);
        } catch (Exception var12) {
            var12.printStackTrace();
        }

    }
}
