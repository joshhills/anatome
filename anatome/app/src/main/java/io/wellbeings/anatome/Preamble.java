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

public class Preamble extends AppCompatActivity {

    // TODO: PAGER TESTING
    public static final int NUM_STEPS = 4;
    public static ViewPager mPager;
    private PagerAdapter mPagerAdapter;

    // Declare the section meta-information.
    private final String sectionName = "preamble";

    // Store the section's content loader.
    public static ContentLoader cLoad = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Set the correct view.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preamble);

        // Attempt to initiate content loading.
        cLoad = new ContentLoader(getResources().openRawResource(R.raw.content),
                getResources().openRawResource(R.raw.contentschema));

        // TODO: PAGER TESTING
        mPager = (ViewPager) findViewById(R.id.preamble_carousel);
        mPagerAdapter = new MyPageAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            @Override
            public void onPageScrollStateChanged(int state) {}

            @Override
            public void onPageSelected(int position) {
                PreambleCarousel fragment = (PreambleCarousel) getSupportFragmentManager().findFragmentById(R.id.carousel_fragment);
                fragment.changeTab(position);
            }

        });

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
                    return new PreambleIntro();
                case 2:
                    return new PreambleName();
                case 3:
                    return new PreambleLock();
                default:
                    return null;
            }
        }

    }

    public void onFragmentInteraction(Uri uri){
        //you can leave it empty
    }

}