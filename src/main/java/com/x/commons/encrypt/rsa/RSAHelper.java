package com.x.commons.encrypt.rsa;

import com.ax.commons.encrypt.base64.BASE64;
import com.ax.commons.encrypt.rsa.RSA;
import com.ax.commons.utils.ConvertHelper;
import com.x.commons.collection.DataSet;
import com.x.commons.util.string.Strings;

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
 * @Desc TODO
 * @Date 2019-11-25 2 * @Author AD
 */
public class RSAHelper {
    
    // ------------------------ 变量定义 ------------------------
    
    private RSAPublicKey publicKey;
    
    private RSAPrivateKey privateKey;
    
    // ------------------------ 构造方法 ------------------------
    
    public RSAHelper() {}
    
    // ------------------------ 方法定义 ------------------------
    
    /**
     * 生成秘钥对
     *
     * @param keySize 秘钥长度
     *
     * @return
     *
     * @throws Exception
     */
    public static KeyPair generateKeyPair(int keySize) throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(keySize, new SecureRandom());
        return generator.genKeyPair();
    }
    
    /**
     * 生成RSA公要
     *
     * @param modulus        模数
     * @param publicExponent 公共指数
     *
     * @return RSAPublicKey RSA公钥
     *
     * @throws Exception
     */
    public static RSAPublicKey generateRSAPublicKey(byte[] modulus, byte[] publicExponent) throws Exception {
        KeyFactory factory = KeyFactory.getInstance("RSA");
        // RSA公钥规范
        RSAPublicKeySpec keySpec = new RSAPublicKeySpec(new BigInteger(modulus), new BigInteger(publicExponent));
        return (RSAPublicKey) factory.generatePublic(keySpec);
    }
    
    /**
     * 生成私钥
     *
     * @param modulus         模数
     * @param privateExponent 私钥指数
     *
     * @return RSAPrivateKey RSA私钥
     *
     * @throws Exception
     */
    public static RSAPrivateKey generateRSAPrivateKey(byte[] modulus, byte[] privateExponent) throws Exception {
        KeyFactory factory = KeyFactory.getInstance("RSA");
        // 私钥规范
        RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(new BigInteger(modulus), new BigInteger(privateExponent));
        return (RSAPrivateKey) factory.generatePrivate(keySpec);
    }
    
    public static DataSet generateKeys(int keySize) throws Exception {
        KeyPair pair = generateKeyPair(keySize);
        RSAPublicKey publicKey = (RSAPublicKey) pair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) pair.getPrivate();
        byte[] publicModulus = publicKey.getModulus().toByteArray();
        byte[] publicExponent = publicKey.getPublicExponent().toByteArray();
        byte[] privateModulus = privateKey.getModulus().toByteArray();
        byte[] privateExponent = privateKey.getPrivateExponent().toByteArray();
        RSAPublicKey var8 = generateRSAPublicKey(publicModulus, publicExponent);
        RSAPrivateKey var9 = generateRSAPrivateKey(privateModulus, privateExponent);
        BASE64 base64 = new BASE64();
        byte[] publicKeyBytes = base64.encode(var8.getEncoded());
        byte[] publicModulusBytes = base64.encode(publicModulus);
        byte[] publicExponentBytes = base64.encode(publicExponent);
        byte[] privateKeyBytes = base64.encode(var9.getEncoded());
        byte[] privateModulusBytes = base64.encode(privateModulus);
        byte[] privateExponentBytes = base64.encode(privateExponent);
        DataSet data = new DataSet();
        data.add("PublicKey", new String(publicKeyBytes, "ASCII"));
        data.add("PublicModules", new String(publicModulusBytes, "ASCII"));
        data.add("PublicExponent", new String(publicExponentBytes, "ASCII"));
        data.add("PrivateKey", new String(privateKeyBytes, "ASCII"));
        data.add("PrivateModulus", new String(privateModulusBytes, "ASCII"));
        data.add("PrivateExponent", new String(privateExponentBytes, "ASCII"));
        return data;
    }
    
    public static RSAPublicKey decodePublicKey(String var0) throws Exception {
        BASE64 base64 = new BASE64();
        byte[] bs = base64.decode(var0.getBytes());
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bs);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        return (RSAPublicKey) factory.generatePublic(keySpec);
    }
    
    public static RSAPrivateKey decodePrivateKey(String var0) throws Exception {
        BASE64 var1 = new BASE64();
        byte[] var2 = var1.decode(var0.getBytes());
        PKCS8EncodedKeySpec var3 = new PKCS8EncodedKeySpec(var2);
        KeyFactory var4 = KeyFactory.getInstance("RSA");
        return (RSAPrivateKey) var4.generatePrivate(var3);
    }
    
    public void setKey(RSAPublicKey var1, RSAPrivateKey var2) {
        this.publicKey = var1;
        this.privateKey = var2;
    }
    
    public byte[] encode(byte[] data) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(1, this.publicKey);
        // 该秘钥能够加密对最大字节长度
        int splitLength = this.publicKey.getModulus().bitLength() / 8 - 11;
        int size = cipher.getOutputSize(data.length);
        int var5 = data.length % splitLength;
        int var6 = var5 != 0 ? data.length / splitLength + 1 : data.length / splitLength;
        byte[] var7 = new byte[size * var6];
        
        for (int var8 = 0; data.length - var8 * splitLength > 0; ++var8) {
            if (data.length - var8 * splitLength > splitLength) {
                cipher.doFinal(data, var8 * splitLength, splitLength, var7, var8 * size);
            } else {
                cipher.doFinal(data, var8 * splitLength, data.length - var8 * splitLength, var7, var8 * size);
            }
        }
    
        // int var10 = cipher.getOutputSize(data.length);
        // int var11 = data.length % var9;
        // int var12 = var11 != 0 ? data.length / var9 + 1 : data.length / var9;
        // byte[] var13 = new byte[var10 * var12];
        //
        // for (int var14 = 0; data.length - var14 * var9 > 0; ++var14) {
        //     if (data.length - var14 * var9 > var9) {
        //         cipher.doFinal(data, var14 * var9, var9, var13, var14 * var10);
        //     } else {
        //         cipher.doFinal(data, var14 * var9, data.length - var14 * var9, var13, var14 * var10);
        //     }
        // }
        
        return var7;
    }
    
    public byte[] decode(byte[] var1) throws Exception {
        Cipher var2 = Cipher.getInstance("RSA");
        var2.init(2, this.privateKey);
        int var3 = this.privateKey.getModulus().bitLength() / 8;
        ByteArrayOutputStream var4 = new ByteArrayOutputStream(64);
        
        for (int var5 = 0; var1.length - var5 * var3 > 0; ++var5) {
            var4.write(var2.doFinal(var1, var5 * var3, var3));
        }
        
        return var4.toByteArray();
    }
    
    public static String rsaEncodeTest() throws Exception {
        String var0 = "admin|E10ADC3949BA59ABBE56E057F20F883E|1456145396000";
        byte[] var1 = var0.getBytes("UTF-8");
        String var2 = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJJfsLg+gyTR8HyylVVbwDk8zbCr8eDMP7mdg3QUePLcVYS4+qOfwkrgEAB" +
                      "+1bXXZ5oHz4emplPpqlTFuOneenMCAwEAAQ==";
        RSAPublicKey var7 = decodePublicKey(var2);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(1, var7);
        int var9 = var7.getModulus().bitLength() / 8 - 11;
        int var10 = cipher.getOutputSize(var1.length);
        int var11 = var1.length % var9;
        int var12 = var11 != 0 ? var1.length / var9 + 1 : var1.length / var9;
        byte[] var13 = new byte[var10 * var12];
        
        for (int var14 = 0; var1.length - var14 * var9 > 0; ++var14) {
            if (var1.length - var14 * var9 > var9) {
                cipher.doFinal(var1, var14 * var9, var9, var13, var14 * var10);
            } else {
                cipher.doFinal(var1, var14 * var9, var1.length - var14 * var9, var13, var14 * var10);
            }
        }
        
        return Strings.toHex(var13);
    }
    
    // ------------------------ 私有方法 ------------------------
    
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
            RSA var8 = new RSA();
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
