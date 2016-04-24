package io.wellbeings.anatome;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;

/**
 * Provide a simple implementation of a
 * file-filter, separated for potential
 * future expansion.
 *
 * @author Team WellBeings - Thirawat
 */
class FileExtensionFilter implements FilenameFilter {

    /**
     * Determine whether a file should be displayed
     * in the audio recording.
     *
     * @param dir   The directory of the file.
     * @param name  The file name.
     * @return      True on successful pass through filter.
     */
    public boolean accept(File dir, String name) {
        return (name.endsWith(".mp3") || name.endsWith(".MP3"));
    }
}


