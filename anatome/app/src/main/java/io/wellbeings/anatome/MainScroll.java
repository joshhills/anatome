package io.wellbeings.anatome;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Intent;

/**
 * Main activity handles navigation to custom
 * informative sections, serves as a graphical
 * launchpad for app's key features.
 */
public class MainScroll extends Activity {

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
        setContentView(R.layout.activity_main_scroll);

        // Initialization of components.
        attachListeners();

    }

    // Modulate set-up tasks for easy alteration.
    private void attachListeners() {

        // Retrieve navigation components and set mutual listeners.
        findViewById(R.id.brain).setOnClickListener(navigateToSection);
        findViewById(R.id.heart).setOnClickListener(navigateToSection);
        findViewById(R.id.liver).setOnClickListener(navigateToSection);

    }

    // Mutual re-usable interface type to manage section routing.
    private OnClickListener navigateToSection = new OnClickListener(){
        public void onClick(View arg) {

            // Retrieve the name of the section, strip extra data.
            String section = arg.getResources().getResourceName(arg.getId());
            section = section.replace("io.wellbeings.anatome:id/", "");

            // Build intent to start new section's activity.
            Intent intent = new Intent(arg.getContext(), Section.class);
            intent.putExtra("section", section);

            // Start activity with message passed.
            startActivity(intent);

        }
    };

}
