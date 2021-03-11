/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.util;

/**
 *
 * @author Samrit
 */
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES_CBC_Encrypter {

    private Cipher ecipher;
    private Cipher dcipher;
    private CryptUtil cryptutil;
    private boolean DEBUG = false;

    AES_CBC_Encrypter(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2) {
        try {
            this.cryptutil = new CryptUtil();
            if (this.DEBUG) {
                System.out.println("key = " + this.cryptutil.asHex(paramArrayOfByte1));
            }
            if (this.DEBUG) {
                System.out.println("iv = " + this.cryptutil.asHex(paramArrayOfByte2));
            }
            SecretKeySpec localSecretKeySpec = new SecretKeySpec(paramArrayOfByte1, "AES");
            if (this.DEBUG) {
                System.out.println("Generated Key = " + this.cryptutil.formatKey(localSecretKeySpec));
            }
            IvParameterSpec localIvParameterSpec = new IvParameterSpec(paramArrayOfByte2);
            this.ecipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            this.dcipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            this.ecipher.init(1, localSecretKeySpec, localIvParameterSpec);
            this.dcipher.init(2, localSecretKeySpec, localIvParameterSpec);
        } catch (NoSuchPaddingException localNoSuchPaddingException) {
        } catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {
        } catch (InvalidAlgorithmParameterException localInvalidAlgorithmParameterException) {
        } catch (InvalidKeyException localInvalidKeyException) {
        }
    }

    public String encrypt(String paramString) {
        try {
            byte[] arrayOfByte1 = paramString.getBytes("UTF8");
            byte[] arrayOfByte2 = this.ecipher.doFinal(arrayOfByte1);
            return this.cryptutil.BASE64_encode(arrayOfByte2);
        } catch (BadPaddingException localBadPaddingException) {
        } catch (IllegalBlockSizeException localIllegalBlockSizeException) {
        } catch (UnsupportedEncodingException localUnsupportedEncodingException) {
        } catch (Exception localIOException) {
        }
        return null;
    }

    public String decrypt(String paramString) {
        try {
            byte[] arrayOfByte1 = this.cryptutil.BASE64_decode(paramString);
            byte[] arrayOfByte2 = this.dcipher.doFinal(arrayOfByte1);
            return new String(arrayOfByte2, "UTF8");
        } catch (BadPaddingException localBadPaddingException) {
        } catch (IllegalBlockSizeException localIllegalBlockSizeException) {
        } catch (UnsupportedEncodingException localUnsupportedEncodingException) {
        } catch (Exception localIOException) {
        }
        return null;
    }
}
