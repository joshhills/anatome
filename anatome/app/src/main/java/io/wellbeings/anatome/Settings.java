package io.wellbeings.anatome;

import android.app.Activity;
import android.os.Bundle;

/**
 * Settings.
 */
public class Settings extends Activity {

    /**
     * On activity creation, set up canvas.
     *
     * @param savedInstanceState    Previously cached state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Load previous state if applicable.
        super.onCreate(savedInstanceState);

        // Load the corresponding view.
        setContentView(R.layout.activity_settings);

        // Set correct section values.
        initDisplay();

        // Initialization of components.
        attachListeners();

    }

    // Alter colour and headers accordingly.
    private void initDisplay() {



    }

    // Modulate set-up tasks for easy alteration.
    private void attachListeners() {



    }

}
