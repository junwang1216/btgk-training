package com.training.core.common.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class RijndaelCrypt {

    public static final String TAG = "RijndaelCrypt";

    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
    private static final String ALGORITHM = "AES";

    private SecretKey _password;
    private IvParameterSpec _IVParamSpec;

    private Cipher cipher;
    /**
     Constructor

     */
    RijndaelCrypt(byte[] key, byte[] iv) {

        try {
            _password = new SecretKeySpec(key, ALGORITHM);

            //Initialize objects
            cipher = Cipher.getInstance(TRANSFORMATION);

            _IVParamSpec = new IvParameterSpec(iv);

        } catch (Exception e) {
        }

    }



    public String decrypt(byte[] cipherText ) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, _password, _IVParamSpec);
            byte[] decryptedVal = cipher.doFinal(cipherText);
            return new String(decryptedVal);
        } catch (Exception e) {
            return null;
        }
    }


    /**
     Encryptor.

      String to be encrypted
     @return Base64 encrypted text

     */
    public String encrypt(byte[] text) {

        try {
            cipher.init(Cipher.ENCRYPT_MODE, _password, _IVParamSpec);
                byte[] encryptedData = cipher.doFinal(text);
                return Base64.encodeBase64String(encryptedData);


        }  catch (Exception e) {
            //LogUtils.error(e);
        }
        return null;

    }

    public String encrypt(String text) {
        byte[] data = StringUtils.getBytesUtf8(text);
        return encrypt(data);
    }


}
