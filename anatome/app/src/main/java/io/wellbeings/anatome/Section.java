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

    private String section;

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

        FragmentTabHost mTabHost = (FragmentTabHost) findViewById(R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        mTabHost.addTab(mTabHost.newTabSpec("0").setIndicator("Thing 1"), BrainWidget.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("1").setIndicator("Thing 2"), HeartWidget.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("2").setIndicator("Thing 3"), LiverWidget.class, null);

        /*Fragment interactive = null;

        // Assign it based on section selected.
        switch(section) {
            case "brain" :
                interactive = new LiverWidget();
                break;
            case "heart" :
                interactive = new LiverWidget();
                break;
            case "liver" :
                interactive = new LiverWidget();
                break;
        }

        /* Update the layout.
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.activity, interactive);
        ft.commit();

        TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);

        TabHost.TabSpec tab1 = tabHost.newTabSpec("First Tab");
        TabHost.TabSpec tab2 = tabHost.newTabSpec("Second Tab");

        tab1.setIndicator("Tab1");
        tab1.setContent(new Intent(this,LiverWidget.class));

        tab2.setIndicator("Tab2");
        tab2.setContent(new Intent(this,PreambleLanguage.class));

        tabHost.addTab(tab1);
        tabHost.addTab(tab2);*/




        /* Create informative fragment. */



    }

}
