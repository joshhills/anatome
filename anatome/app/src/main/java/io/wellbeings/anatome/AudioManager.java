package io.wellbeings.anatome;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by thirawat on 22/04/2016.
 */
public final class AudioManager {
    // SDCard Path
    final String MEDIA_PATH = new Environment().getExternalStorageDirectory().getPath();
    private ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();

    public AudioManager() {

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
