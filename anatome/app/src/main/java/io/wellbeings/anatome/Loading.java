package io.wellbeings.anatome;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * Allow application to begin crucial tasks
 * while covering operation with quirky veneer.
 */
public class Loading extends Activity {

    // Store useful private fields.
    private static final int SPLASH_DURATION = 4000;
    private boolean mBackBtnPress;
    private TextView mText;
    private Handler mHandler;
    private STATUS okGo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_layout);

        initGUI();

        // Determine first activity...
        new AsyncTask<Void, Void, STATUS>() {
            protected STATUS doInBackground(Void... params) {
                // Initialise crucial utility singletons.
                return UtilityManager.getUserUtility(getApplicationContext()).getState();
            }
            protected void onPostExecute(STATUS status) {
                // Set status to what was returned.
                okGo = status;
            }
        }.execute();

        // Add a delay to application start.
        mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Idle during async task.
                while(okGo == STATUS.NONE) {}
                // If user profile absent...
                if(okGo == STATUS.FAIL) {
                    // Set one up.
                    Intent i = new Intent(Loading.this, Preamble.class);
                    Loading.this.startActivity(i);
                }
                else if (okGo == STATUS.SUCCESS){
                    // Go to main scroll.
                    Intent i = new Intent(Loading.this, MainScroll.class);
                    Loading.this.startActivity(i);
                }
            }
        }, SPLASH_DURATION);

    }

    @Override
    public void onBackPressed() {
        // Do nothing purposefully.
    }

    private void initGUI() {
        // Style text properly.
        Typeface customFont = defineCustomFont();
        mText = (TextView)findViewById(R.id.loading_text);
        mText.setTypeface(customFont);

        // Begin loading icon animation.
        ImageView mImageViewFilling = (ImageView) findViewById(R.id.eyeball_image);
        // Glide.with(this).load(R.drawable.eyeball_animation)
        // ((AnimationDrawable) mImageViewFilling.getBackground()).start();
    }

    // Custom styling.
    private Typeface defineCustomFont() {
        AssetManager assetManager = getAssets();
        Typeface customFont = Typeface.createFromAsset(assetManager, "fonts/champagne.ttf");
        return customFont;
    }
}





