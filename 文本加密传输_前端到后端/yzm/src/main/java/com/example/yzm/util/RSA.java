package com.example.yzm.util;

import org.apache.tomcat.util.codec.binary.Base64;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;



public class RSA {

    public static String PUBLICKEY="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQChrHThkqOhAEJjrNgIURolH0yvn61ypjmqkPXaWuQXlt6GY1G8JcygCMys+54p/rLkOzOzEtcovX638I+BU+HZE7lCL0Wjn0sq/qgC8uikB2MXdtC9lpBYlq71w5mrANfmoUgxsleQgQaFCAZjQqFMfD10VAWnya1j6Uxfa2jnQQIDAQAB";
    public static String PRIVATEKEY="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKGsdOGSo6EAQmOs2AhRGiUfTK+frXKmOaqQ9dpa5BeW3oZjUbwlzKAIzKz7nin+suQ7M7MS1yi9frfwj4FT4dkTuUIvRaOfSyr+qALy6KQHYxd20L2WkFiWrvXDmasA1+ahSDGyV5CBBoUIBmNCoUx8PXRUBafJrWPpTF9raOdBAgMBAAECgYEAlcLOsnXHpUg8N2DQ7UNTWLfZrYXYga+BE4McepxfVYqnrw0plYPePRBpqIOcS4Jn7Ar7JRJ6xH5kd7ca983gEl5y3fkjYBnjlzFBKydQOJc2YJnM68bB3cYABBJi1M/AbdlAyZmgsCkCrBDe0W1ZbY3172f6znRP1AKVt3FfFwECQQDjgRrZ3J2fPK+hyJCbE55D6zsNkTHleNWj2eEAtIkgljhmeTSvx96gYv/9qC9ZaDQLfao4NC/3TI/1EJdORmw5AkEAteyAJETnlSJL+mGYdj9BV6eY31UC8n9hUKjPy9s8AosQnIubYwjUom+UaKmoqwUhhI3OEIHxnBr+nnRQPUpjSQJAZ9i3w/iTLmuKFAQ0FcV3ubL3SrMbHo7wnhFHRoYiDfDkH8p1xUMrmPR3u3Z43ovVLeWXvH394xlcoNWI617z6QJBAKfszEOuTs28QMG3gJH1xYzxqQzNXdEVf3CCwnx7u6LlVsoJm78Ika32rNkHcieartu6QXG/YJQl3s3Is6A/n/ECQEFEEEisobTlD6poI9SDztJfmy6QC46wzUycVIsJJCjvKQyiyOHfJHTWRa8hUHP2mvvi1FJypvPgwGFm3gC2PwM=";

    public static Map<Integer, String> genKeyPair() {
        Map<Integer, String> keyMap = new HashMap<Integer, String>(); // 用于封装随机产生的公钥与私钥
        try {
            // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");

            // 初始化密钥对生成器，密钥大小为96-1024位
            keyPairGen.initialize(1024, new SecureRandom());

            // 生成一个密钥对，保存在keyPair中
            KeyPair keyPair = keyPairGen.generateKeyPair();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate(); // 得到私钥
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic(); // 得到公钥

            // 得到公钥字符串
            String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));
            // 得到私钥字符串
            String privateKeyString = new String(Base64.encodeBase64((privateKey.getEncoded())));
            // 将公钥和私钥保存到Map
            keyMap.put(0, publicKeyString); // 0表示公钥
            keyMap.put(1, privateKeyString); // 1表示私钥
        } catch (Exception e) {
            return null;
        }
        return keyMap;
    }

    /**
     * RSA公钥加密
     *
     * @param str
     *            需要加密的字符串
     * @param publicKey
     *            公钥
     * @return 公钥加密后的内容
     */
    public static String encrypt(String str, String publicKey) {
        String outStr = null;
        try {
            // base64编码的公钥
            byte[] decoded = Base64.decodeBase64(publicKey);
            RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA")
                    .generatePublic(new X509EncodedKeySpec(decoded));
            // RSA加密
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            outStr = Base64.encodeBase64String(cipher.doFinal(str.getBytes("UTF-8")));
        } catch (Exception e) {
        }
        return outStr;
    }

    /**
     * RSA私钥解密
     *
     * @param str
     *            加密字符串
     * @param privateKey
     *            私钥
     * @return 私钥解密后的内容
     */
    public static String decrypt(String str, String privateKey) {
        String outStr = null;
        try {
            // 64位解码加密后的字符串
            byte[] inputByte = Base64.decodeBase64(str.getBytes("UTF-8"));
            // base64编码的私钥
            byte[] decoded = Base64.decodeBase64(privateKey);
            RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA")
                    .generatePrivate(new PKCS8EncodedKeySpec(decoded));
            // RSA解密
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, priKey);
            outStr = new String(cipher.doFinal(inputByte));
        } catch (Exception e) {
        }
        return outStr;
    }

}