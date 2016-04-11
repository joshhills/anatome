package io.wellbeings.anatome;

import android.content.ContentResolver;
import android.content.Context;

/**
 * Created by thirawat on 11/04/2016.
 */
public class ContentResolverUltility {
    static public ContentResolver tryGetContentResolver(Context c){
        ContentResolver contentResolver = c.getContentResolver();
        return contentResolver;
    }
}
