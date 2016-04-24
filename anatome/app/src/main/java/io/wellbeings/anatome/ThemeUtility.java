package io.wellbeings.anatome;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Provide app-wide styling functionality.
 *
 * @author Team WellBeings - Josh, Bettina
 */
public class ThemeUtility implements Utility {

    // Log status of utility.
    protected STATUS utilityStatus;

    // Store application context for access to local preferences.
    private static Context ctx;

    // Store colours loaded from resource file.
    private static Map<String, Integer> appColours = new HashMap<String, Integer>();

    /**
     * Constructor initializes utility, storing state.
     *
     * @param ctx   The Android context to act upon.
     */
    public ThemeUtility (Context ctx) {

        // Attempt to start to set-up the utility using the arguments provided.
        // Store the context.
        this.ctx = ctx;

        // Attempt to start to set-up the utility using the argument provided.
>>>>>>> origin/master
        try {
            utilityStatus = initialize();
        } catch (IOException e) {
            utilityStatus = STATUS.FAIL;
        }

    }

    /**
     * Set-up the utility's core functionality.
     *
     * @return              If the utility loaded correctly.
     * @throws IOException  If the colours could not be loaded.
     */
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
        }

        // At this point, have succeded.
        return STATUS.SUCCESS;

    }

    /**
     * Provide access to this specific utility's status.
     *
     * @return
     */
    @Override
    public STATUS getState() {
        return utilityStatus;
    }

    /**
     * Remove any necessary files from memory and shut-down.
     *
     * @return
     */
    @Override
    public STATUS shutdown() {

        // Remove theme references.
        appColours.clear();
        appColours = null;

        return STATUS.SUCCESS;
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

    /**
     * Retrieve a custom font not stored in the
     * default Android system.
     *
     * @param font  The name of the font to load.
     * @return      The font as a Typeface object.
     */
    public static Typeface getFont(String font) {

        // Retrieve font assets.
        AssetManager assetManager = ctx.getAssets();
        // Select specific one and create object representation.
        Typeface customFont = Typeface.createFromAsset(assetManager, "fonts/" + font + ".ttf");

        return customFont;

    }

>>>>>>> origin/master
}
