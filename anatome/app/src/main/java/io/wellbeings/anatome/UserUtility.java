package io.wellbeings.anatome;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;

/**
 * Model a user based on our applications
 * persistent needs.
 */
public class UserUtility implements Utility {

    // Log status of utility.
    protected STATUS utilityStatus;

    // Store application context for access to local preferences.
    private Context ctx;

    // Store private settings key.
    private final String SETTINGS_KEY = "io.wellbeings.anatome.USER_SETTINGS";

    // Store user settings.
    private SharedPreferences settings;
    private SharedPreferences.Editor editor;

    /**
     * Constructor to be called with resources
     * delivered by calling context.
     *
     * @param ctx   The context of the application.
     */
    public UserUtility(Context ctx) {

        // Store reference to context.
        this.ctx = ctx;

        utilityStatus = initialize();

    }

    @Override
    public STATUS initialize() {

        // Initialize the preferences.
        settings = ctx.getSharedPreferences(
                SETTINGS_KEY, Context.MODE_PRIVATE);
        editor = settings.edit();

        // Check to see if user profile exists.
        if(settings.getString("NAME", null) == null) {
            return STATUS.NONE;
        }
        else {
            return STATUS.SUCCESS;
        }

    }

    /**
     * Flexible method allows for generic lower-level
     * modification of user settings.
     *
     * @param key   The name of the setting.
     * @param value The value to be stored.
     * @param <T>   Any kind of primitive type to be stored.
     */
    public <T> void editSetting(String key, T value) {
        if(value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        }
        if(value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        }
        if(value instanceof String) {
            editor.putString(key, (String) value);
        }
    }

    /* Specific method for ease-of-use. */

    /**
     * Change the user's language, for use in
     * content-loading.
     *
     * @param lang  XML-compliant abbreviated identifier.
     */
    public void editLanguage(String lang) {
        editSetting("LANG", lang);
        editor.apply();
    }
    public String getLanguage() {
        return settings.getString("LANG", "en");
    }

    // TODO: Complete the shutdown method.
    @Override
    public STATUS shutdown() {
        return null;
    }

    @Override
    public STATUS getState() {
        return utilityStatus;
    }

}
