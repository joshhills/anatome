package io.wellbeings.anatome;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by thirawat on 12/02/2016.
 */
public class FileOperationHelper {
    private static FileOperationHelper ourInstance = new FileOperationHelper();
    String yourObject[];

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

    public void saveArrayList(Context context, String filename, List<Note> data) throws IOException {
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(data);
            objectOutputStream.flush();
            fileOutputStream.close();
        }
        catch(FileNotFoundException e) {
            Log.e("NO FILE", "FILE NOT FOUND!------------*");
        }
    }


    public List<Note> loadArrayList(Context context, String filename) throws IOException{

        FileInputStream fileInputStream = context.openFileInput(filename);
        ObjectInputStream ois = new ObjectInputStream(fileInputStream);
        ArrayList<Note> readBack = new ArrayList<Note>();
        try {
            readBack = (ArrayList<Note>) ois.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        ois.close();
        fileInputStream.close();

        return readBack;
    }


}