/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test;

import com.project.util.CryptUtil;
import com.project.util.SHA1;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import javax.imageio.ImageIO;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Samrit
 */
public class Test {

    public static void main(String args[]) throws Exception {
        URL url = new URL("http://localhost:8084/semms-webservice/rest/web/filehandler/download/image/profile/st-9");
        InputStream is = null;
         byte[] fileContent ;
          is = url.openStream();
           fileContent = getBytes(is);
        try {
            is = url.openStream();
           fileContent = getBytes(is);
  FileUtils.writeByteArrayToFile(new File("d:/xa2.jpg"), fileContent);
         
        } catch (IOException e) {
            System.err.printf("Failed while reading bytes from %s: %s", url.toExternalForm(), e.getMessage());
            e.printStackTrace();
            // Perform any other exception handling that's appropriate.
        } finally {
            if (is != null) {
                is.close();
            }
        }

javax.imageio.ImageIO.read(new URL("http://localhost:8084/semms-webservice/rest/web/filehandler/download/image/profile/st-9"));
      

//        InputStream iStream = getContentResolver().openInputStream(uri);
//        byte[] inputData = getBytes(iStream);

    }

    public static byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }
}
