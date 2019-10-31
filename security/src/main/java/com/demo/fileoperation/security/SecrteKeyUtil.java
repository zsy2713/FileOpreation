package com.demo.fileoperation.security;


import org.apache.commons.codec.binary.Base64;


import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public class SecrteKeyUtil {

    public static final String PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApRkQQVYa4EIY5nsKEAdgjJFBAfzYBd8cg5CseFIit6iTGL5l1Qe58W9ZI4E7g4N88Oyq9gz5OQUykAqV933ew8SACAqCuhfzLtqIiyDX0a7QUZlO3xplB57Vfgxawhd6tKIikX0W5NNneV5kTUX3GIzEfvQl8Xsc4jyJE+nTYF3lrDEX/EPv7zZ7ntXuTdHiLY3SjFap1Y0/4jdBQTXSgHgC+QHT3D6UqrhCdUL98psUBT1LfAGgc4Hcg7PEmAUaOngKsJxzXqaEe7CqgJo5AhuAqsE85+BQafaYFUjUEtSM+WWrmuzg9u8wqcu2JAiPXg86qc05+CTnyRVkBHkM2wIDAQAB";
    public static final String PRIVATE_KEY = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQClGRBBVhrgQhjmewoQB2CMkUEB/NgF3xyDkKx4UiK3qJMYvmXVB7nxb1kjgTuDg3zw7Kr2DPk5BTKQCpX3fd7DxIAICoK6F/Mu2oiLINfRrtBRmU7fGmUHntV+DFrCF3q0oiKRfRbk02d5XmRNRfcYjMR+9CXxexziPIkT6dNgXeWsMRf8Q+/vNnue1e5N0eItjdKMVqnVjT/iN0FBNdKAeAL5AdPcPpSquEJ1Qv3ymxQFPUt8AaBzgdyDs8SYBRo6eAqwnHNepoR7sKqAmjkCG4CqwTzn4FBp9pgVSNQS1Iz5Zaua7OD27zCpy7YkCI9eDzqpzTn4JOfJFWQEeQzbAgMBAAECggEAQPOOUJYGdsmqMCqht9KfKk4O7DBD8HNpLV1ibOerXuPEEwz81QV4IsLyp6Q0/LVjFwmU7L4H4mXoEYEIpNhxJkWNNRyld37qnjk9VLb0ETscG64buGwS6R+U0EUeA5PnI19SCDjVI+sDn10CSuBGqPLgby2wK8OKhnrYVuw7y4IiHDTBxC5hka/g8QiTPosF7yHKwl8n8wqV5AO9oQDLGGfA8xcNNBteuBB2l1ijd8wrp2SUY3sxDyhJ8mFNvPPwI7AWRNR6/ALFnzBayk2+ausF33io57abK0MzPTmeHQP+qrH/S5pSJPXBwmIbrkRiyZkQIEt802m+lbNeKslhEQKBgQDrGiEuqQLG7zhjw5xO2JZz3lSjhJfDQbl2p1adVusTJnPue6NM+BelbYuuOG01uutjGfharH7MWiG3zxKy787hFzhh74qhyJ7vGGfi96WL3fIFCOVW+zyTGCCdUSBJWuYCmMDOH4BONk4vI0GKH9CC/5LCy8u0wBlDGnlDbHAveQKBgQCzxfPaLx8Q/ay1a78vcox0+GWT0/yOhmDJ7dJvdnSvynav7h0AnAq1QlNTVWCaA3GASn8JnNjwBB0zbHZpp8xKHrqNF74WzmtBNcAB4Z3cFssPXH1SCa1K2/Rv00B8wIJoHZOog8c6TUtcahgqkCht7oxCFAD5HIlTK5jNarul8wKBgF7R6xAmmjghw1x5SFGEiSmyQfCxSYQ2vdZdJ1HN/IF8wMUumG4yoVbXF77sgx9ohAc0MjZf28QPlgnMOn5wub+O3e6h7gfSIkfMUBn6R4phuXrOW7D5IzUwRfctARiU0K+7bAe2LMlenanlTkmnCzHKYiauizobQBUQWFgZJCYBAoGAe/4ijPBHSx7MSm1AdmdZfSv+U5G49ky04VHI/NwE62KHrhxboJn/wLk4Y5fVgySw2j2HlYe7EQCla1x557G2c8DyBnLDx1MkItwBzMpiaPTHpZazUSwffCxtUduw9NqKl2ke+PG7Lfk160250VGQsRIBZ2oIqckG5niBcd0WBAUCgYAY5DuKOFRvDgvthl8yb6XdnECjV4/DdnxOTMa2KYNIV1Xfs/8JFDN5iqxnf8ZkUfwd5TwxM02HbKfEJuFWKUy1V4hFlwERUrQhM8l0lQAaEAjKVI8O2Apu+ohIEakaW6UqCiVCLbGi8d/rkGHfBJwK2l7JjyOMOJuGcmLMCI+5Uw==";
    public static final String SIGN_NAME = "RSA"; // des dsa

    /**
     * 生成公钥与私钥对
     *
     * @return 公钥Key=publicKey 私钥Key=privateKey
     * @throws Exception
     */
    public static Map<String, String> getKeys() throws Exception {
        int len = 2048;//rsa算法长度,生成秘钥对时用
        // 获得公钥与私钥对
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(SIGN_NAME);
        keyPairGen.initialize(len);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
        Map<String, String> keys = new HashMap<String, String>(2);
        String privateKey = Base64.encodeBase64String(rsaPrivateKey.getEncoded());
        keys.put("privateKey", privateKey);
        RSAPublicKey rsapublicKey = (RSAPublicKey) keyPair.getPublic();
        String publicKey = Base64.encodeBase64String(rsapublicKey.getEncoded());
        keys.put("publicKey", publicKey);
        return keys;
    }

    /**
     * 从字符串中加载公钥
     *
     * @param publicKeyStr 公钥数据字符串
     * @throws Exception 加载公钥时产生的异常
     */
    public static RSAPublicKey loadPublicKeyByStr(String publicKeyStr) throws Exception {
        try {
            byte[] buffer = Base64.decodeBase64(publicKeyStr);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance(SIGN_NAME);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("公钥非法");
        } catch (NullPointerException e) {
            throw new Exception("公钥数据为空");
        }
    }

    /**
     * 从字符串中加载私钥
     * @param privateKeyStr
     * @return
     * @throws Exception
     */
    public static RSAPrivateKey loadPrivateKeyByStr(String privateKeyStr) throws Exception {
        try {
            byte[] buffer = Base64.decodeBase64(privateKeyStr);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("私钥非法");
        } catch (NullPointerException e) {
            throw new Exception("私钥数据为空");
        }
    }

    /**
     * 私钥加密过程
     *
     * @param privateKey    私钥
     * @param plainTextData 明文数据
     * @return
     * @throws Exception 加密过程中的异常信息
     */
    public static String encrypt(RSAPrivateKey privateKey, byte[] plainTextData) throws Exception {
        if (privateKey == null) {
            throw new Exception("加密私钥为空, 请设置");
        }
        Cipher cipher = null;
        try {
            // 使用默认RSA
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            byte[] output = cipher.doFinal(plainTextData);
            return Base64.encodeBase64String(output);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此加密算法");
        } catch (NoSuchPaddingException e) {
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("加密私钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("明文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("明文数据已损坏");
        }
    }


    /**
     * 公钥解密过程
     *
     * @param publicKey  公钥
     * @param cipherData 密文数据
     * @return 明文
     * @throws Exception 解密过程中的异常信息
     */
    public static String decrypt(RSAPublicKey publicKey, byte[] cipherData) throws Exception {
        if (publicKey == null) {
            throw new Exception("解密公钥为空, 请设置");
        }
        Cipher cipher = null;
        try {
            // 使用默认RSA
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            byte[] output = cipher.doFinal(cipherData);
            return new String(output);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此解密算法");
        } catch (NoSuchPaddingException e) {
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("解密公钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("密文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("密文数据已损坏");
        }
    }

    /**
     * 私钥加密
     * @param content 明文
     * @return
     */
    public static String priEncrypt(String content){
        String res = "";
        try {
            //加载私钥
            RSAPrivateKey rsaprikey = loadPrivateKeyByStr(PRIVATE_KEY);
            //私钥加密
            res = encrypt(rsaprikey,content.getBytes());
        } catch (Exception e) {
        }
        return res;
    }

    /**
     * 公钥解密
     * @param content 加密后的密文
     */
    public static String pubdecrypt(String content) throws Exception{
        String res = "";
        try {
            //加载公钥
            RSAPublicKey rsapubkey = loadPublicKeyByStr(PUBLIC_KEY);
            //解密
            res = decrypt(rsapubkey,Base64.decodeBase64(content));
        } catch (Exception e) {
            throw new Exception(e);
        }
        return res;
    }


    public static void main(String[] args) {
        try {
            //生成密钥对
//            Map map = getKeys();
//            System.out.println("私钥："+map.get("privateKey"));
//            System.out.println("公钥："+map.get("publicKey"));
            //私钥加密-公钥解密
            String mes = "测试";
            String enc = priEncrypt(mes);
            System.out.println("加密后的密文："+enc);
            System.out.println("解密后的明文："+pubdecrypt(enc));


        } catch (Exception e) {
        }
    }

}