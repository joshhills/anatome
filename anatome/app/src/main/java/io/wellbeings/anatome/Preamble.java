package io.wellbeings.anatome;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.io.IOException;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class Preamble extends AppCompatActivity {

    public static final int NUM_STEPS = 4;
    public static ViewPager mPager;
    private PagerAdapter mPagerAdapter;

    // Declare the section meta-information.
    private final String sectionName = "preamble";

    // Store the section's content loader.
    public static ContentLoader cLoad = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /* Begin app, determine flow. */

        // Hide intrusive android status bars.
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_IMMERSIVE);

        super.onCreate(savedInstanceState);

        // Set the correct view.
        setContentView(R.layout.activity_preamble);

        // Attempt to initiate content loading.
        cLoad = new ContentLoader(this, getResources().openRawResource(R.raw.content),
                getResources().openRawResource(R.raw.contentschema));

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

                // Retrieve the fragment.
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

    public void onFragmentInteraction(Uri uri){}

}