package io.wellbeings.anatome;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;

import java.lang.reflect.Constructor;

/**
 * Section loads widget navigated to,
 * along with text.
 */
public class Section extends FragmentActivity {

    // Store name of current section.
    private String section;

    // Store language-dependent headers.
    // TODO: Implement language from content loader.
    private String interactiveHeader = "Interact";
    private String informationHeader = "Info";

    /**
     * On activity creation, set up canvas.
     *
     * @param savedInstanceState    Previously cached state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Load previous state if applicable.
        super.onCreate(savedInstanceState);

        // Initialize local variables.
        section = getIntent().getStringExtra("section");

        // Load the corresponding view.
        setContentView(R.layout.activity_section);

        // Set correct section values.
        initDisplay();

        // Initialization of components.
        attachListeners();

        // Load appropriate fragments.
        loadFragments();

    }

    // Alter colour and headers accordingly.
    private void initDisplay() {

        // Set correct header, capitalising first letter.
        ((TextView)findViewById(R.id.section_name)).setText(
                section.substring(0, 1).toUpperCase() +
                        section.substring(1));

        // Set correct section icon.
        final int resourceId = getResources().getIdentifier(
                section + "_ico", "drawable", getApplicationContext().getPackageName()
        );
        ((ImageView)findViewById(R.id.section_ico)).setImageResource(resourceId);

    }

    // Modulate set-up tasks for easy alteration.
    private void attachListeners() {

        // Enable backwards navigation.
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    /**
     * Load the correct interactive widget dynamically,
     * and send appropriate message to the content loading fragment.
     */
    private void loadFragments() {

        /* Create interactive fragment. */

        // Grab the tab host from the layout.
        FragmentTabHost mTabHost = (FragmentTabHost) findViewById(R.id.tabhost);
        // Point content towards the right container.
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        // Assign interactive widget based on section selected.
        switch(section) {
            case "brain" :
                mTabHost.addTab(mTabHost.newTabSpec(interactiveHeader)
                                .setIndicator(section.toUpperCase()),
                        BrainWidget.class, null);
                break;
            case "heart" :
                mTabHost.addTab(mTabHost.newTabSpec(interactiveHeader)
                                .setIndicator(section.toUpperCase()),
                        HeartWidget.class, null);
                break;
            case "liver" :
                mTabHost.addTab(mTabHost.newTabSpec(interactiveHeader)
                                .setIndicator(section.toUpperCase()),
                        LiverWidget.class, null);
                break;
        }

        /* Create informative fragment. */

        // Add message to bundle.
        Bundle contentBundle = new Bundle();
        contentBundle.putString("section", section);

        mTabHost.addTab(mTabHost.newTabSpec("1").setIndicator("Thing 2"), HeartWidget.class, null);

    }

}
