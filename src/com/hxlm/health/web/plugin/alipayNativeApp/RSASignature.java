package com.hxlm.health.web.plugin.alipayNativeApp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

/**
 * Created by dengyang on 15/8/12.
 */
public class RSASignature {

    private static Logger logger = LoggerFactory.getLogger(RSASignature.class);

    //合作商户ID。用签约支付宝账号登录ms.alipay.com后，在账户信息页面获取。
    public static final String PARTNER = "2088901382262731";
    // 账户ID。用签约支付宝账号登录ms.alipay.com后，在账户信息页面获取。
    public static final String SELLER = "sunziyi@changk.cn";
    // 商户（RSA）私钥
    public static final String RSA_PRIVATE = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAPEiK65Gy3QOB/5O46kRMcjZYl7S0QKFnsBgpTgX6bWUMoKyJDnSQe9Hvh5EWyecgBp7oyUQZbbFgGVQ8jA+TD/HM+H+q5fuQKADqLKRUrOMsRDHtY29fyXa6bEwCmeWUu3ehKXy2GG44WIb+F8YI11zs80hQpeP4U3V0vJIX8HDAgMBAAECgYEA8QBDAgr1u0duy8hTF5380Q4bmERB67Bay1GMr/SOfBHjLecX98ZiaeGdbajKJlvvWQirNRcfYb4hpqFxfbA2CTOhy/+guh/triQKJ66Ld5GxL87+w8o2hID0nkZZrf/fXu30jA6W0uJ7yh7Qp+3iGX/qY5qInkTro0rIyMhsm6kCQQD/BxcXN7fMDY1kH+X5eIgKYYaNato6X1poe81k5W4YLpPJfecxPG0ao3lAq9bYVraUgF/MYnVi2O64rA6+Zq4fAkEA8g2E/3hvARO9RSUlP0XpIBNaf5WZ96drRc6qy2INflMBqJxyNgH9GBflDiy2J4lOSBLgXK36hBJdRHowXERv3QJBAOaFGysicyGgMmkJqE/60kVH6F7V8hKHdGozVfJYrE5xc6bTBnJr41cL4yNA8L+2iezrqSxO5zqFSitlYyJaIBMCQHNMwVe4FJhcwMHZKd7z/FKNJYS0zHzNwSmlgnITKP5Np/KbY0QaumbpqvCPl81JeIKYi0tajISxAsCFu9eXufkCQQCjuqCseEyvSZhVNbaROcWFVoVO4ChMhPODP+z7zilmZuZJyHAnXTSgpNY5WSOunSiaXpO7pTZK2FBjmhYnAF1F";
    // 支付宝（RSA）公钥 用签约支付宝账号登录ms.alipay.com后，在密钥管理页面获取。
    public static final String RSA_ALIPAY_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCDQJxIXZZU1FZZU0gFbg2LGgojNqTHKGczRfDZ PjmiGaddLHtG2Db0pat+WbbuqJ4BRcnGna6qecUSRMV10RClmLE7KSnwQgcHCJ6RKv1barD6t6Rh 4mIKE7JY7scHK3BQ5mx2kgw5/tm7HB+oXAt6gz4An23imI91dPR0YNifawIDAQAB";

    /**

     * 解密

     * @param content 密文
     *
     * @param key 商户私钥

     * @return 解密后的字符串

     */
    public static String decrypt(String content, String key) throws Exception {
        PrivateKey prikey = getPrivateKey(key);

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, prikey);

        InputStream ins = new ByteArrayInputStream(Base64.decode(content));
        ByteArrayOutputStream writer = new ByteArrayOutputStream();
        //rsa解密的字节大小最多是128，将需要解密的内容，按128位拆开解密
        byte[] buf = new byte[128];
        int bufl;

        while ((bufl = ins.read(buf)) != -1) {
            byte[] block = null;

            if (buf.length == bufl) {
                block = buf;
            } else {
                block = new byte[bufl];
                for (int i = 0; i < bufl; i++) {
                    block[i] = buf[i];
                }
            }

            writer.write(cipher.doFinal(block));
        }

        return new String(writer.toByteArray(), "utf-8");
    }



    /**

     * 得到私钥

     * @param key 密钥字符串（经过base64编码）

     * @throws Exception

     */

    public static PrivateKey getPrivateKey(String key) throws Exception {

        byte[] keyBytes;

        keyBytes = Base64.decode(key);

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

        return privateKey;

    }

    public static final String  SIGN_ALGORITHMS = "SHA1WithRSA";
    /**
     * RSA签名
     * @param content 待签名数据
     * @param privateKey 商户私钥
     * @return 签名值
     */
    public static String sign(String content, String privateKey)
    {
        String charset = "utf-8";
        try
        {
            PKCS8EncodedKeySpec priPKCS8 	= new PKCS8EncodedKeySpec( Base64.decode(privateKey) );
            KeyFactory keyf 				= KeyFactory.getInstance("RSA");
            PrivateKey priKey 				= keyf.generatePrivate(priPKCS8);

            java.security.Signature signature = java.security.Signature
                    .getInstance(SIGN_ALGORITHMS);

            signature.initSign(priKey);
            signature.update( content.getBytes(charset) );

            byte[] signed = signature.sign();

            return Base64.encode(signed);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * RSA验签名检查
     * @param content 待签名数据
     * @param sign 签名值
     * @param publicKey 支付宝公钥
     * @return 布尔值
     *
     */
    public static boolean doCheck(String content, String sign, String publicKey) {
        boolean bverify = false;

        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] encodedKey = Base64.decode(publicKey);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));

            java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);

            signature.initVerify(pubKey);
            signature.update( content.getBytes("utf-8") );
            bverify = signature.verify( Base64.decode(sign) );
        } catch (Exception e) {
            e.printStackTrace();
        }

        logger.error("----------------------------------------- alipay doCheck ------------------------------------------"+bverify);
        return bverify;
    }

}
