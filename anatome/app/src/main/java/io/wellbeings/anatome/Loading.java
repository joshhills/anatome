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
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

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

    FasterAnimationsContainer mFasterAnimationsContainer;
    private static final int[] IMAGE_RESOURCES = { R.drawable.look_2,
            R.drawable.look_4, R.drawable.look_6, R.drawable.look_8,
            R.drawable.look_10, R.drawable.look_12, R.drawable.look_14,
            R.drawable.look_16, R.drawable.look_18, R.drawable.look_20,
            R.drawable.look_22, R.drawable.look_24, R.drawable.look_26,
            R.drawable.look_28, R.drawable.look_30, R.drawable.look_32,
            R.drawable.look_34, R.drawable.look_36, R.drawable.look_38,
            R.drawable.look_40, R.drawable.look_42, R.drawable.look_44,
            R.drawable.look_46, R.drawable.look_48, R.drawable.look_50,
            R.drawable.look_52, R.drawable.look_54, R.drawable.look_56,
            R.drawable.look_58, R.drawable.look_60, R.drawable.look_62,
            R.drawable.look_64, R.drawable.look_66, R.drawable.look_68,
            R.drawable.look_70, R.drawable.look_72, R.drawable.look_74,
            R.drawable.look_76, R.drawable.look_78, R.drawable.look_80,
            R.drawable.look_82, R.drawable.look_84, R.drawable.look_86,
            R.drawable.look_88, R.drawable.look_90, R.drawable.look_92,
            R.drawable.look_94, R.drawable.look_96, R.drawable.look_98,
            R.drawable.look_100, R.drawable.look_102, R.drawable.look_104,
            R.drawable.look_106};

    private static final int ANIMATION_INTERVAL = 100;// 500ms

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
                // mFasterAnimationsContainer.stop();
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
        ImageView imageView = (ImageView) findViewById(R.id.eyeball_image);

        /*mFasterAnimationsContainer = FasterAnimationsContainer
                .getInstance(imageView);
        mFasterAnimationsContainer.addAllFrames(IMAGE_RESOURCES,
                ANIMATION_INTERVAL);
        mFasterAnimationsContainer.start();

        // Glide.with(this).load(R.drawable.eyeball_animation).into(mImageViewFilling);
        // ((AnimationDrawable) mImageViewFilling.getDrawable()).start();*/


        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(imageView);
        Glide.with(this).load(R.drawable.animtwo).into(imageViewTarget);

    }

    // Custom styling.
    private Typeface defineCustomFont() {
        AssetManager assetManager = getAssets();
        Typeface customFont = Typeface.createFromAsset(assetManager, "fonts/champagne.ttf");
        return customFont;
    }
}





