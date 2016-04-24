package io.wellbeings.anatome;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.DialogInterface;

/**
 * Handles initial set-up of the app and consistent
 * singleton management of necessary utilities in lieu
 * of service implementation.
 */
public class UtilityManager {

    // Store only existing instance of manager.
    private static UtilityManager instance;

    // Store application context for access to file loading.
    private Context ctx;

    // Create a utilities array - necessary hard-coding.
    Utility[] utilities = new Utility[4];

    /**
     * Method models the Singleton design pattern, which serves to
     * provide over-arching management of all app facilities.
     *
     * @return The only available instance of the 'UtilityManager' class.
     */
    public static UtilityManager getInstance(Context ctx) {

        // If one does not exist...
        if(instance == null) {
            // Create one.
            instance = new UtilityManager(ctx);
        }
        // Return instance of 'User'.
        return instance;

    }

    /**
     * Constructor is hidden; this prevents any component
     * from creating another instance.
     */
    protected UtilityManager(Context ctx) {

        this.ctx = ctx;

        init();

        // handleErrors();

    }

    // Initialize necessary utility sub-classes.
    private void init() {

        // Add user profile management.
        utilities[0] = new UserUtility(ctx);

        // Add content-loading from local XML files.
        utilities[1] = new ContentLoader(ctx, ctx.getResources().openRawResource(R.raw.content),
                ctx.getResources().openRawResource(R.raw.contentschema));

        // Add database interpolation for social and organization integration.
        utilities[2] = new DbUtility(ctx);

        // Add stylistic functions.
        utilities[3] = new ThemeUtility(ctx);

    }

    /**
     * Check for status errors to determine flow of app.
     *
     * This was for developers, and has been left in to
     * give a flavour of our structured dynamic testing
     * throughout the duration of the project - more than
     * the now removed 'log' statements.
     *
    private void handleErrors() {

        // Store state of errors.
        boolean fatalError = false;
        // Store potential error messages.
        String errors = null;

        // Iterate through all utilities.
        for(int i = 0; i < utilities.length; i++) {

            // As long as the utility has implementation code...
            if(utilities[i] != null) {

                // If the utility has not succeeded...
                if (utilities[i].getState() != STATUS.SUCCESS &&
                        utilities[i].getState() != STATUS.ACTIVE) {
                    // If the error was fatal, log this and break loop.
                    if (utilities[i].getState() == STATUS.FAIL) {
                        fatalError = true;
                    }
                    // Otherwise, make a note of state.
                    else {
                        errors = errors
                                + utilities[i].getClass().toString().replace("io.wellbeings.anatome.", "")
                                + " " + utilities[i].getState().toString()
                                + "\n";
                    }
                }

            }

        }

        // If everything succeeded, continue.
        if(!fatalError && errors == null) {
            return;
        }

        // Otherwise, prompt is necessary.
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);

        // Build fatal prompt.
        if(fatalError) {
            builder.setTitle("A fatal error occured!")
                .setMessage(errors)
                .setCancelable(false)
                .setPositiveButton("Quit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Quit app in this situation.
                        System.exit(-1);
                    }
                });
        }
        // Build warning prompt.
        else {
            builder.setTitle("Warning...")
                .setMessage(errors)
                .setCancelable(false)
                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        }

        AlertDialog alert = builder.create();
        alert.show();

    }
    */

    /**
     * Retrieve all existing utility class objects.
     *
     * @return  An array of utilities.
     */
    private Utility[] getUtilities() {
        return utilities;
    }

    /**
     * Return the specific subclass requested.
     *
     * @param ctx       The Android specific lifecycle object.
     * @param subClass  The type of utility needed.
     * @return          The requested utility if it exists.
     */
    public static Utility getUtility(Context ctx, Class subClass) {
        for (Utility u : getInstance(ctx).getUtilities()) {
            if(subClass.isInstance(u)) {
                return u;
            }
        }
        return null;
    }

    /**
     * @param ctx   The Android specific lifecycle object.
     * @return      The utility managing user settings.
     */
    public static UserUtility getUserUtility(Context ctx) {
        return (UserUtility) getUtility(ctx, UserUtility.class);
    }

    /**
     * @param ctx   The Android specific lifecycle object.
     * @return      The utility managing internationalised content population.
     */
    public static ContentLoader getContentLoader(Context ctx) {
        return (ContentLoader) getUtility(ctx, ContentLoader.class);
    }

    /**
     * @param ctx   The Android specific lifecycle object.
     * @return      The utility managing user settings.
     */
    public static ThemeUtility getThemeUtility(Context ctx) {
        return (ThemeUtility) getUtility(ctx, ThemeUtility.class);
    }

    /**
     * @param ctx   The Android specific
     * @return
     */
    public static DbUtility getDbUtility(Context ctx) {
        return (DbUtility) getUtility(ctx, DbUtility.class);
    }

    /**
     * Ensure services are stopped correctly.
     */
    public static void shutdown(Context ctx) {

        // Cycle through every one.
        for (Utility u : getInstance(ctx).getUtilities()) {
            u.shutdown();
        }

        // TODO: Check failure of shut downs, display message as appropriate.

    }

}