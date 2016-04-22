package io.wellbeings.anatome;

import android.os.Environment;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by thirawat on 22/04/2016.
 */
public class AudioManager {
    // SDCard Path
    final String MEDIA_PATH = new Environment().getExternalStorageDirectory().getPath();
//    final String MEDIA_PATH = new String("/sdcard/");
    private ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();

    // Constructor
    public AudioManager(){

    }

    //read all mp3 files and store them into the arraylist
    public ArrayList<HashMap<String, String>> getPlayList() {
        File home = new File(MEDIA_PATH);

        if (home.listFiles(new FileExtensionFilter()).length > 0) {
            for (File file : home.listFiles(new FileExtensionFilter())) {
                HashMap<String, String> song = new HashMap<String, String>();
                song.put("songTitle", file.getName().substring(0, (file.getName().length() - 4)));
                song.put("songPath", file.getPath());

                songsList.add(song);
            }
        }
        // return songslist
        return songsList;


    }


}
