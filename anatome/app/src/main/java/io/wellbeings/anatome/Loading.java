package io.wellbeings.anatome;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

/**
 * Allow application to begin crucial tasks
 * while covering operation with quirky veneer.
 */
public class Loading extends Activity {

    // Store useful private fields.
    private static final int SPLASH_DURATION = 4000;
    private TextView mText;
    private Handler mHandler;
    private STATUS okGo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide the notification bar.
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Load the corresponding style.
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

                // Simple idle during async task.
                while(okGo == null) {}

               // Log.d("DDD", UtilityManager.getUserUtility(getApplicationContext()).getPassword());

                // If a user profile exists.
                if (okGo == STATUS.SUCCESS) {

                    // Check for the existence of a user password.
                    if(UtilityManager.getUserUtility(getApplicationContext()).getPassword() != null) {

                        // Create password input.
                        final AlertDialog.Builder builder = new AlertDialog.Builder(Loading.this, AlertDialog.THEME_HOLO_LIGHT);
                        builder.setTitle("Input Password");
                        builder.setCancelable(false);
                        final EditText pwInput = new EditText(Loading.this);

                        // Force numerical keyboard and hidden values.
                        pwInput.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        // Force maximum length.
                        pwInput.setFilters(new InputFilter[] {new InputFilter.LengthFilter(
                                UtilityManager.getUserUtility(getApplicationContext()).getPASSWORD_LENGTH()
                        )});
                        builder.setView(pwInput);

                        // Dictate how the buttons should look.
                        builder.setPositiveButton("OK", null);
                        builder.setNegativeButton("RESET", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Erase user data in order to continue.
                                UtilityManager.getUserUtility(getApplicationContext()).reset();
                                dialog.dismiss();
                                Toast.makeText(Loading.this, "Profile erased.",
                                        Toast.LENGTH_SHORT).show();
                                // Set-up new profile.
                                Intent i = new Intent(Loading.this, Preamble.class);
                                Loading.this.startActivity(i);
                            }
                        });
                        // Show and override positive button.
                        final AlertDialog pwScreen = builder.create();
                        pwScreen.show();
                        pwScreen.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // If the password is correct.
                                if (pwInput.getText().toString().equals(
                                        UtilityManager.getUserUtility(getApplicationContext()).getPassword())) {
                                    pwScreen.dismiss();
                                    // OK to navigate to main scroll.
                                    Intent i = new Intent(Loading.this, MainScroll.class);
                                    Loading.this.startActivity(i);
                                } else {
                                    Toast.makeText(Loading.this, "Incorrect password.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                    else {
                        // Go to main scroll.
                        Intent i = new Intent(Loading.this, MainScroll.class);
                        Loading.this.startActivity(i);
                    }
                }
                else {

                    // Otherwise user profile does not exist, so set one up.
                    Intent i = new Intent(Loading.this, Preamble.class);
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





