package io.wellbeings.anatome;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by avery on 11/04/2016.
 */
public class ThemeUtility implements Utility {

    // Log status of utility.
    protected STATUS utilityStatus;

    // Store application context for access to local preferences.
    private static Context ctx;

    // Store colours loaded from resource file.
    private static Map<String, Integer> appColours = new HashMap<String, Integer>();

    public ThemeUtility (Context ctx) {

        // Attempt to start to set-up the utility using the arguments provided.
        try {
            utilityStatus = initialize();
        } catch (IOException e) {
            utilityStatus = STATUS.FAIL;
        }

    }

    @Override
    public STATUS initialize() throws IOException {

        // Retrieve all colour fields.
        Field[] fields = R.color.class.getDeclaredFields();

        //Create arrays for color names and values
        String [] names = new String[fields.length];
        int [] colours = new int [fields.length];
        // Extract them from file.
        try {
            for(int i=0; i < fields.length; i++) {
                names [i] = fields[i].getName();
                colours [i] = fields[i].getInt(null);
            }
        } catch (Exception ex) {
            throw new IOException();
        }

        // Add to mapped colours.

        for(int i=0; i < colours.length; i++) {
            appColours.put(names[i], colours[i]);
            Log.d("Colours", names[i] + " " + colours[i]);
        }

        return STATUS.SUCCESS;

    }

    @Override
    public STATUS getState() {
        return utilityStatus;
    }

    @Override
    public STATUS shutdown() {
        return null;
    }

    /**
     * Replacement for deprecated Android function,
     * retrieves colour from file using variable string
     * for dynamic themeing.
     *
     * @return  The resource ID for the matched colour.
     */
    public static int getColour(String colour) {
        return appColours.get(colour);
    }

}
