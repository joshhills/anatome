package io.wellbeings.anatome;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;
import java.util.Map;
import java.util.Stack;

/**
 * Model a user based on our applications
 * persistent needs.
 */
public class UserUtility implements Utility {

    //variables for the liver widget
    private double units;
    private Stack<Double> drinks;

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
     * @param ctx   The context of the application
     */
    public UserUtility(Context ctx) {

        // Store reference to context.
        this.ctx = ctx;

        utilityStatus = initialize();

        //units start at zero
        units = 0;
        drinks = new Stack<Double>();

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

    /**
     * Flexible method allows for retrieval of any user setting.
     *
     * @param key   The name of the setting.
     * @return      The setting as an object.
     */
    public Object getSetting(String key) {
        return settings.getAll().get(key);
    }

    /* Specific method for ease-of-use. */

    // TODO: Fully comment with JavaDoc.

    /**
     * Alter the user's language, for use in
     * content-loading.
     *
     * @param lang  XML-compliant abbreviated identifier.
     */
    public void setLanguage(String lang) {
        editSetting("LANG", lang);
        editor.apply();
    }
    public String getLanguage() {
        return settings.getString("LANG", "en");
    }

    public void setName(String name) {
        editor.putString("NAME", name);
        editor.apply();
    }
    public String getName() {
        return settings.getString("NAME", "Guest");
    }

    public void setEmail(String email) {
        editor.putString("EMAIL", email);
        editor.apply();
    }
    public String getEmail() {
        return settings.getString("EMAIL", null);
    }

    public void allowNotifications(boolean allow) {
        editor.putBoolean("NOTIFICATIONS", allow);
        editor.apply();
    }
    public boolean isNotifications() {
        return settings.getBoolean("NOTIFICATIONS", true);
    }

    public void allowNetwork(boolean allow) {
        editor.putBoolean("NETWORK", allow);
        editor.apply();
    }
    public boolean isNetwork() {
        return settings.getBoolean("NETWORK", true);
    }

    // TODO: Password!!

    @Override
    public STATUS getState() {
        return utilityStatus;
    }

    @Override
    public STATUS shutdown() {
        return null;
    }

    //getters and setters
    public double getUnits(){
        return units;
    }
    public void setUnits(double units){
        this.units = units;
        //empty the stack since the drinks dont match the units anymore
        drinks = new Stack<Double>();
    }
    public void addDrink(double drink){
        units += drink;
        drinks.push(drink);
    }
    public void removeDrink(){
        if(!drinks.empty()) {
            units -= drinks.pop();
            if(units < 0){
                units = 0;
            }
        }
    }
}
