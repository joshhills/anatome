package io.wellbeings.anatome;

import android.net.Uri;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.io.IOException;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public class Preamble extends AppCompatActivity implements Section, PreambleLanguage.OnFragmentInteractionListener, PreambleCarousel.OnFragmentInteractionListener {

    // TODO: PAGER TESTING
    private static final int NUM_STEPS = 2;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;




    // Declare the section meta-information.
    private final String sectionName = "brain";

    // Store the section's content loader.
    ContentLoader cLoad = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Set the correct view.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preamble);

        // Attempt to initiate content loading.
        try {
            cLoad = new ContentLoader(getResources().openRawResource(R.raw.content),
                    getResources().openRawResource(R.raw.contentschema));
        } catch(IOException e) {
            // TODO: Provide visual prompt to utility failure.
        }

        // TODO: PAGER TESTING
        mPager = (ViewPager) findViewById(R.id.preamble_carousel);
        mPagerAdapter = new MyPageAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

    }

    private class MyPageAdapter extends FragmentPagerAdapter {

        public MyPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return NUM_STEPS;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new PreambleLanguage();
                case 1:
                    return new PreambleCarousel();
                default:
                    return new PreambleCarousel();
            }
        }

    }

    /**
     * Fill the text elements of the page with content from local '.xml' file,
     * based on user preferences.
     *
     * @return  Status of function - whether here were errors.
     */
    public STATUS populateContent() {

        /*
         * Here, you would grab each text element and call 'setText'
         * passing cLoad._____("xpath/to/correct/node") or similar
         * ContentLoader functions as arguments.
         */

        return STATUS.SUCCESS;

    }

    public void onFragmentInteraction(Uri uri){
        //you can leave it empty
    }

}