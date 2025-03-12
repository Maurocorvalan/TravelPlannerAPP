package com.example.aplicaciondeviajes.ui.gallery;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.IOException;

public class FileUtil {
    public static void copyInputStreamToFile(InputStream inputStream, File file) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, length);
        }
        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }
}
