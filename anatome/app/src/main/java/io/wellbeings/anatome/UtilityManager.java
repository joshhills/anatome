package io.wellbeings.anatome;

import android.app.Activity;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.DialogInterface;

/**
 * Handles initial set-up of the app and consistent
 * singleton management of necessary utilities in lieu
 * of service implementation.
 */
public class UtilityManager extends Activity {

    // Create a utilities array - necessary hard-coding.
    Utility[] utilities = new Utility[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();

        handleErrors();

    }

    // Initialize necessary utility sub-classes.
    private void init() {

        // Add user profile management.
        utilities[0] = new UserUtility();

        // Add content-loading from local XML files.
        utilities[1] = new ContentLoader(getResources().openRawResource(R.raw.content),
                getResources().openRawResource(R.raw.contentschema));

        // TODO: Add database interpolation for social and organization integration.

    }

    // Check for status errors to determine flow of app.
    private void handleErrors() {

        // Store state of errors.
        boolean fatalError = false;
        // Store potential error messages.
        String errors = null;

        // Iterate through all utilities.
        for(int i = 0; i < utilities.length; i++) {

            // If the utility has not succeeded...
            if(utilities[i].getState() != STATUS.SUCCESS) {
                // If the error was fatal, log this and break loop.
                if(utilities[i].getState() == STATUS.FAIL) {
                    fatalError = true;
                }
                // Otherwise, make a note of state.
                else {
                    errors.concat(utilities[i].getClass().toString() + " STATUS: "
                            + utilities[i].getState().toString() + "\n");
                }
            }

        }

        // If everything succeeded, continue.
        if(!fatalError && errors == null) {
            return;
        }

        // Otherwise, prompt is necessary.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if(fatalError) {
            builder.setTitle("A fatal error occured!")
                .setMessage(errors)
                .setCancelable(false)
                .setPositiveButton("Quit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Quit app in this situation.
                        finish();
                        System.exit(-1);
                    }
                });
        }
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

}
