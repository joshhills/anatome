package io.wellbeings.anatome;

import android.os.Environment;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class contents taken from tutorial in playing audio in Android at:
 * http://www.androidhive.info/2012/03/android-building-audio-player-tutorial/
 *
 * Implemented and commented by Thirawat.
 */
public final class AudioManager {

    // Find the SD card path.
    final String MEDIA_PATH = new Environment().getExternalStorageDirectory().getPath();
    private ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();

    /**
     * Read all existing mp3 files in device storage
     * into a list for access by our widgets.
     *
     * @return
     */
    public ArrayList<HashMap<String, String>> getPlayList() {

        File home = new File(MEDIA_PATH);

        // As long as the files exist...
        if (home.listFiles(new FileExtensionFilter()).length > 0) {

            // For each one...
            for (File file : home.listFiles(new FileExtensionFilter())) {
                HashMap<String, String> song = new HashMap<String, String>();

                // Add its name and title.
                song.put("songTitle", file.getName().substring(0, (file.getName().length() - 4)));
                song.put("songPath", file.getPath());

                songsList.add(song);

            }
        }

        // Return the finished amalgamation.
        return songsList;

    }


}
