package com.maximus.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created because scala io is just not there yet.
 */
public class JavaFileIO {
    /**
     * write a bytebuffer to the named file
     * @param filename  the name of the file
     * @param buffer  data to be written
     * @throws IOException
     */
    public static void writeBytes(String filename, byte[] buffer) throws IOException{
        File file = new File(filename);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(buffer);
            fos.flush();
            fos.close();
        }
    }

    /**
     * read a file
     * @param filename the name of the filename
     * @return byte[] with the file contencts
     * @throws IOException
     */
    public static byte[] readBytes(String filename) throws IOException{
        File file = new File(filename);
        try (FileInputStream fis = new FileInputStream(file)) {
            int available = fis.available();
            byte[] buffer = new byte[available];
            int bytesRead = fis.read(buffer);
            if (bytesRead !=  available){
                System.err.print(bytesRead);
            }
            fis.close();
            return buffer;
        }
    }

    /**
     * exists returns true if the file exists and is readable and writeable
     * @param filename
     * @return
     */
    public static boolean exists(String filename){
        File javaFile = new File(filename );
        if (javaFile.exists() && javaFile.canRead() && javaFile.canWrite()){
            return true;
        }
        return false;
    }

    public static File create(String fileName, InputStream inputStream){
        File file = new File(fileName);
        int read = 0;
        byte[] bytes = new byte[1024];

        try (FileOutputStream outputStream = new FileOutputStream(file)){

            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            return file;
        } catch (IOException e){
          throw new RuntimeException("unable to create output file: "+fileName, e);
        }

    }

}
