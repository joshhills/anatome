package io.wellbeings.anatome;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.support.v4.app.FragmentTabHost;

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

        FragmentTabHost mTabHost = (FragmentTabHost) findViewById(R.id.tabhost);

        // Set correct header, capitalising first letter.
        ((TextView)findViewById(R.id.section_name)).setText(
                section.substring(0, 1).toUpperCase() +
                        section.substring(1));

        // Set correct section icon.
        final int resourceId = getResources().getIdentifier(
                section + "_ico", "drawable", getApplicationContext().getPackageName()
        );



        final int secondaryColourId = (ContextCompat.getColor(this,
                UtilityManager.getThemeUtility(this).getColour(section + "_secondary_bg")));

        final int backBtnColourId = (ContextCompat.getColor(this,
                UtilityManager.getThemeUtility(this).getColour(section + "_back_btn_bg")));

        final int mainColourId = (ContextCompat.getColor(this,
                UtilityManager.getThemeUtility(this).getColour(section + "_main_bg")));


        ((ImageView) findViewById(R.id.section_image)).setImageResource(resourceId);
        ((FrameLayout) findViewById(R.id.section_top_layout)).setBackgroundColor(secondaryColourId);
        ((Button) findViewById(R.id.back)).setBackgroundColor(backBtnColourId);
        ((TextView) findViewById(R.id.section_name)).setBackgroundColor(secondaryColourId);
        ((TabWidget) findViewById(R.id.tabs)).setBackgroundColor(mainColourId);




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
                                .setIndicator(interactiveHeader.toUpperCase()),
                        BrainWidget.class, null);
                break;
            case "heart" :
                mTabHost.addTab(mTabHost.newTabSpec(interactiveHeader)
                                .setIndicator(interactiveHeader.toUpperCase()),
                        HeartWidget.class, null);
                break;
            case "liver" :
                mTabHost.addTab(mTabHost.newTabSpec(interactiveHeader)
                                .setIndicator(interactiveHeader.toUpperCase()),
                        LiverWidget.class, null);
                break;
        }

        /* Create informative fragment. */

        // Add message to bundle.
        Bundle contentBundle = new Bundle();
        contentBundle.putString("section", section);

        final int graphicsId = getResources().getIdentifier(
                section + "_graphic", "drawable", getApplicationContext().getPackageName()
        );

        // Add fragment with bundle.
        mTabHost.addTab(mTabHost.newTabSpec(informationHeader).setIndicator(informationHeader), ContentFragment.class,
                contentBundle);

        // Alter the dimensions of the tabs programatically.
        mTabHost.getTabWidget().getChildAt(0).getLayoutParams().height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics());
        mTabHost.getTabWidget().getChildAt(1).getLayoutParams().height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics());

    }

}
