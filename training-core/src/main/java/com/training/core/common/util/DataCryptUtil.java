package com.training.core.common.util;

import com.training.core.common.config.WebConfig;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DataCryptUtil {

    private static byte[] key = Base64.decodeBase64(WebConfig.getDataEncryptKey());
    private static byte[] ivkey = Base64.decodeBase64(WebConfig.getDataEncryptIV());

    public static String encrypt(String plainText) {
        if (plainText == null) return null;
        RijndaelCrypt rijndaelCrypt = new RijndaelCrypt(key, ivkey);
        plainText += WebConfig.getDataEncryptSalt();
        return rijndaelCrypt.encrypt(org.apache.commons.codec.binary.StringUtils.getBytesUtf8(plainText));
    }

    public static String decrypt(String encrypted) {
        if (encrypted == null) return null;
        return decrypt(encrypted, WebConfig.getDataEncryptSalt(), key, ivkey);
    }

    public static String decrypt(String cipherText, String salt, byte[] key, byte[] iv) {
        if (cipherText == null) return null;
        String decrypted = decryptStringFromBytes(Base64.decodeBase64(cipherText), key, iv);
        if (StringUtils.isNotEmpty(decrypted) && decrypted.endsWith(salt) && (decrypted.length() - salt.length() > 0)) {
            return decrypted.substring(0, (decrypted.length() - salt.length()));
        }
        return null;
    }

    public static String decryptStringFromBytes(byte[] cipherText, byte[] Key, byte[] IV) {
        RijndaelCrypt rijndaelCrypt = new RijndaelCrypt(Key, IV);
        return rijndaelCrypt.decrypt(cipherText);
    }

    public static String encryptSimpleXor(int source) {
        int xorCode = source ^ 963854721;
        return Integer.toHexString(xorCode);
    }

    public static int decryptSimpleXor(String xorCode) {
        return Integer.parseInt(xorCode, 16) ^ 963854721;
    }

    public static String sha1(String decript) throws NoSuchAlgorithmException {
        if (decript == null)
            return null;
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        digest.update(decript.getBytes());
        byte messageDigest[] = digest.digest();
        // Create Hex String
        StringBuffer hexString = new StringBuffer();
        // 字节数组转换为 十六进制 数
        for (int i = 0; i < messageDigest.length; i++) {
            String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
            if (shaHex.length() < 2) {
                hexString.append(0);
            }
            hexString.append(shaHex);
        }
        return hexString.toString();
    }

    public static void main(String[] args) {
        System.out.println(DataCryptUtil.encrypt("123456"));
    }
}
