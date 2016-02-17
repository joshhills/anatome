package io.wellbeings.anatome;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by thirawat on 12/02/2016.
 */
public class FileOperationHelper {
    private static FileOperationHelper ourInstance = new FileOperationHelper();

    public static FileOperationHelper getInstance() {
        if(ourInstance == null)
            ourInstance = new FileOperationHelper();
        return ourInstance;
    }

    private FileOperationHelper() {
    }

    public void saveFile(Context context, String filename, String data) throws IOException {
        FileOutputStream fileOutputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
        fileOutputStream.write(data.getBytes());
        fileOutputStream.close();


    }

    public String readFile(Context context, String filename) throws IOException{
        FileInputStream fileInputStream = context.openFileInput(filename);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        StringBuffer buffer = new StringBuffer();
        String line;
        while ((line = bufferedReader.readLine()) != null){
            buffer.append(line);

        }

        return buffer.toString();
    }
}
