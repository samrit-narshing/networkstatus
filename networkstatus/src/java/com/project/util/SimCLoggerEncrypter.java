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
public class SimCLoggerEncrypter {

    private AES_CBC_Encrypter encrypter;

    public int init_encrypter() {
        byte[] arrayOfByte1 = {66, 94, 126, 47, 49, 102, 24, 35, 18, 46, -18, 18, 0, -86, -107, -106};
        byte[] arrayOfByte2 = {17, 46, 125, 63, 65, -122, 40, 71, -15, -35, 125, -104, 83, 86, -112, 40};
        this.encrypter = new AES_CBC_Encrypter(arrayOfByte1, arrayOfByte2);
        return 0;
    }

    public String encrypt(String paramString) {
        init_encrypter();
        return this.encrypter.encrypt(paramString);
    }

    public String decrypt(String paramString) {
        init_encrypter();
        return this.encrypter.decrypt(paramString);
    }
}
