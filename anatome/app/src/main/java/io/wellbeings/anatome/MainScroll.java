package io.wellbeings.anatome;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Main activity handles navigation to custom
 * informative sections, serves as a graphical
 * launchpad for app's key features.
 */
public class MainScroll extends Activity {


    /**
     * On activity creation, set up canvas.
     *
     * @param savedInstanceState Previously cached state.
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


        // ************************************************************
        findViewById(R.id.bookingInfoButton).setOnClickListener(navigateToBookingSystem);
        // ************************************************************

        // ************************************************************
        findViewById(R.id.settingsImage).setOnClickListener(navigateToSettings);
        // ************************************************************

    }

    // Mutual re-usable interface type to manage section routing.
    private OnClickListener navigateToSection = new OnClickListener() {
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

    // ************************************************************
    private OnClickListener navigateToBookingSystem = new OnClickListener() {
        @Override
        public void onClick(View v) {

            TextView needMoreHelpText = (TextView) findViewById(R.id.needMoreHelpText);
            ImageButton infoButtonOnMainScroll = (ImageButton) v;
            ImageButton bookImageButtonOnMainScroll = (ImageButton) findViewById(R.id.bookButtonOnMainScroll);

            infoButtonOnMainScroll.setVisibility(View.INVISIBLE);
            //needMoreHelpText.setVisibility(View.INVISIBLE);


            AssetManager assetManager = getAssets();
            Typeface customFontBariol = Typeface.createFromAsset(assetManager, "fonts/Bariol.ttf");
            Typeface customFontHelvetica = Typeface.createFromAsset(assetManager, "fonts/Helvetica.ttf");

            needMoreHelpText.setTextSize(20);
            needMoreHelpText.setText("Book an appointment with \nthe Wellbeing Service");
            bookImageButtonOnMainScroll.setVisibility(View.VISIBLE);

            bookImageButtonOnMainScroll.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), BookingSystem.class);
                    startActivity(intent);
                }
            });


        }
    };

    private OnClickListener navigateToSettings = new OnClickListener() {
        @Override
        public void onClick(View v) {


            ((ImageButton) findViewById(R.id.settingsImage)).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainScroll.this, Settings.class));
                }
            });


        }
    };
}
