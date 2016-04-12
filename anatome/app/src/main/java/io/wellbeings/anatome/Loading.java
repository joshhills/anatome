package io.wellbeings.anatome;

/**
 * Created by bettinaalexieva on 25/03/2016.
 */

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

public class Loading extends Activity {

    private static final int SPLASH_DURATION = 6000;
    private boolean mBackBtnPress;
    private TextView mText;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_layout);

        Typeface customFont = defineCustomFont();
        mText = (TextView)findViewById(R.id.loading_text);
        mText.setTypeface(customFont);

        ImageView mImageViewFilling = (ImageView) findViewById(R.id.eyeball_image);
        ((AnimationDrawable) mImageViewFilling.getBackground()).start();

        mHandler = new Handler();

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (!mBackBtnPress) {
                    Intent i = new Intent(Loading.this, MainScroll.class);
                    Loading.this.startActivity(i);
                }
            }
        }, SPLASH_DURATION);
    }

    @Override
    public void onBackPressed() {
        mBackBtnPress = true;
        super.onBackPressed();
    }

    private Typeface defineCustomFont() {
        AssetManager assetManager = getAssets();
        Typeface customFont = Typeface.createFromAsset(assetManager, "fonts/champagne.ttf");

        return customFont;
    }
}





