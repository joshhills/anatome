package io.wellbeings.anatome;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;

/**
 * Created by thirawat on 22/04/2016.
 */
class FileExtensionFilter implements FilenameFilter {
    public boolean accept(File dir, String name) {
        return (name.endsWith(".mp3") || name.endsWith(".MP3"));
    }
}


