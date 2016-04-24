package io.wellbeings.anatome;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;

import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import android.widget.ScrollView;
import android.widget.TextView;
import java.util.Map;


/**
 * Main activity handles navigation to custom
 * informative sections, serves as a graphical
 * launchpad for app's key features.
 *
 * @author Team WellBeings - Everyone!
 */
public class MainScroll extends Activity {

    // Store scroll position for resume.
    public static int scrollY;

    /**
     * On activity creation, set up canvas.
     *
     * @param savedInstanceState Previously cached state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

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

        // Load previous state if applicable.
        super.onCreate(savedInstanceState);

        // Load the corresponding view.
        setContentView(R.layout.activity_main_scroll);

        // Initialization of components.
        attachListeners();

        // Initialise the GUI.
        initGUI();

    }

    /**
     * Override the native method called when the activity is paused
     */
    @Override
    protected void onPause() {
        super.onPause();

        // Log scroll position for ease of use.
        scrollY = ((ScrollView) findViewById(R.id.mainscroll_scroll_container)).getScrollY();

    }

    /**
     * Override the native method called when the activity is resumed
     */
    @Override
    protected void onResume()
    {
        super.onResume();

        // Restore the scroll position.
        ((ScrollView) findViewById(R.id.mainscroll_scroll_container)).post(new Runnable() {
            @Override
            public void run() {
                ((ScrollView) findViewById(R.id.mainscroll_scroll_container)).setScrollY(scrollY);
            }
        });

        // Reload paused animations.
        Animation brainAnimation = AnimationUtils.loadAnimation(this, R.anim.brain_animation);
        Animation heartAnimation = AnimationUtils.loadAnimation(this, R.anim.heart_animation);
        Animation liverAnimation = AnimationUtils.loadAnimation(this, R.anim.liver_animation);
        Animation rocketAnimation = AnimationUtils.loadAnimation(this, R.anim.rocket_animation);
        Animation fuelAnimation = AnimationUtils.loadAnimation(this, R.anim.rocket_fuel_animation);
        Animation fuelMovementAnimation = AnimationUtils.loadAnimation(this, R.anim.rocket_fuel_movement_animation);

        // Start them going again.
        ((ImageView) findViewById(R.id.heart)).startAnimation(heartAnimation);
        ((ImageView) findViewById(R.id.brain)).startAnimation(brainAnimation);
        ((ImageView) findViewById(R.id.liver)).startAnimation(liverAnimation);
        ((ImageView) findViewById(R.id.rocket_animation)).startAnimation(rocketAnimation);
        ((ImageView) findViewById(R.id.mainscroll_fuel_dark)).startAnimation(fuelMovementAnimation);
        ((ImageView) findViewById(R.id.mainscroll_fuel_light)).startAnimation(fuelMovementAnimation);
        ((ImageView) findViewById(R.id.mainscroll_fuel_light)).startAnimation(fuelAnimation);
    }

    /**
     * Initialise the display
     */
    private void initGUI() {

        // used to measure the screen
        DisplayMetrics displayMetrics = new DisplayMetrics();

        // fetch the window manager
        WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);

        // measure the display
        wm.getDefaultDisplay().getMetrics(displayMetrics);

        // save the default screen width
        int screenWidth = displayMetrics.widthPixels;

        // default screen scale
        float targetSize = 1;

        // check if screen size is found
        if (screenWidth != 0) {

            // scale screen size
            targetSize = 1080f / screenWidth;
        }

        // calculate the screen width
        int targetScreenWidth = (int) (1080f / targetSize);

        // load the rocket background image
        Glide.with(this)
                .load(R.drawable.mainscroll_background_rocket)
                .dontTransform()
                .override(targetScreenWidth, (int) (732 / targetSize))
                .into((ImageView) findViewById(R.id.mainscroll_rocket_background));

        // load the rocket animation image
        Glide.with(this)
                .load(R.drawable.rocket_animation)
                .dontTransform()
                .override(targetScreenWidth, (int) (732 / targetSize))
                .animate(R.anim.rocket_animation)
                .into((ImageView) findViewById(R.id.rocket_animation));

        // load the dark fuel animation image
        Glide.with(this)
                .load(R.drawable.mainscroll_fuel_dark)
                .dontTransform()
                .override(targetScreenWidth, (int) (732 / targetSize))
                .animate(R.anim.rocket_fuel_movement_animation)
                .into((ImageView) findViewById(R.id.mainscroll_fuel_dark));

        // load the light fuel animation image
        Glide.with(this)
                .load(R.drawable.mainscroll_fuel_light)
                .dontTransform()
                .override(targetScreenWidth, (int) (732 / targetSize))
                .animate(R.anim.rocket_fuel_animation)
                .into((ImageView) findViewById(R.id.mainscroll_fuel_light));

        // load the kite background image
        Glide.with(this)
                .load(R.drawable.mainscroll_kite)
                .dontTransform()
                .override(targetScreenWidth, (int) (732 / targetSize))
                .into((ImageView) findViewById(R.id.mainscroll_kite));

        // load the upper background image
        Glide.with(this)
                .load(R.drawable.mainscroll_background_upper)
                .dontTransform()
                .override(targetScreenWidth, (int) (2700 / targetSize))
                .into((ImageView) findViewById(R.id.mainscroll_background_upper));

        // load the lower background image
        Glide.with(this)
                .load(R.drawable.mainscroll_background_lower)
                .dontTransform()
                .override(targetScreenWidth, (int) (2638 / targetSize))
                .into((ImageView) findViewById(R.id.mainscroll_background_lower));

        // load the heart image
        Glide.with(this)
                .load(R.drawable.heart)
                .dontTransform()
                .override(targetScreenWidth, (int) (1014 / targetSize))
                .animate(R.anim.heart_animation)
                .into((ImageView) findViewById(R.id.heart));

        // load the brain image
        Glide.with(this)
                .load(R.drawable.brain)
                .dontTransform()
                .override(targetScreenWidth, (int) (1262 / targetSize))
                .animate(R.anim.brain_animation)
                .into((ImageView) findViewById(R.id.brain));

        // load the liver image
        Glide.with(this)
                .load(R.drawable.liver_front)
                .dontTransform()
                .override(targetScreenWidth, (int) (662 / targetSize))
                .animate(R.anim.liver_animation)
                .into((ImageView) findViewById(R.id.liver));

        // load the background liver image
        Glide.with(this)
                .load(R.drawable.liver_back)
                .dontTransform()
                .override(targetScreenWidth, (int) (662 / targetSize))
                .into((ImageView) findViewById(R.id.liver_back));

        // load the footer
        Glide.with(this)
                .load(R.drawable.footer)
                .dontTransform()
                .override(targetScreenWidth, (int) (731 / targetSize))
                .into((ImageView) findViewById(R.id.mainscroll_footer));

        // display the welcome message at the top of the mainScroll
        TextView welcomeHeader = (TextView)findViewById(R.id.mainscroll_welcome_text);
        welcomeHeader.setText(UtilityManager.getContentLoader(this).getHeaderText("mainscroll", "welcome"));

        // display a message to the user on the mainscroll
        TextView t = (TextView)findViewById(R.id.main_scroll_text);
        t.setText(UtilityManager.getContentLoader(this).getInfoText("mainscroll", "welcome_text"));

        // display booking message at the top of the mainScroll
        TextView bookingTextTitle = (TextView)findViewById(R.id.mainscroll_help);
        bookingTextTitle.setText(UtilityManager.getContentLoader(this).getHeaderText("mainscroll", "booking"));

        // display a header message in the footer
        TextView footerTextTitle = (TextView)findViewById(R.id.mainscroll_organisation_title);
        footerTextTitle.setText(UtilityManager.getContentLoader(this).getHeaderText("mainscroll", "more_help"));

        // display a message in the footer
        TextView footerTextInfo = (TextView)findViewById(R.id.mainscroll_more_help_text);
        footerTextInfo.setText(UtilityManager.getContentLoader(this).getInfoText("mainscroll", "more_help_text"));
    }

    /**
     * Modulate set-up tasks for easy alteration.
     **/
    private void attachListeners() {

        // Retrieve navigation components and set mutual listeners.
        findViewById(R.id.brain).setOnClickListener(navigateToSection);
        findViewById(R.id.heart).setOnClickListener(navigateToSection);
        findViewById(R.id.liver).setOnClickListener(navigateToSection);
        findViewById(R.id.bookingInfoButton).setOnClickListener(makeBookingButtonVisible);
        findViewById(R.id.rocket_animation).setOnClickListener(navigateToSettings);
        findViewById(R.id.bookButtonOnMainScroll).setOnClickListener(bookButtonOnClick);
        findViewById(R.id.settingsBtn).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainScroll.this, OrganizationActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_top_in, R.anim.slide_top_out);
            }
        });
    }

    /**
     * Mutual re-usable interface type to manage section routing.
     **/
    private OnClickListener navigateToSection = new OnClickListener() {
        public void onClick(View arg) {

            // Retrieve the name of the section, strip extra data.
            String section = arg.getResources().getResourceName(arg.getId());
            section = section.replace("io.wellbeings.anatome:id/", "");

            // Build intent to start new section's activity.
            Intent intent = new Intent(arg.getContext(), Section.class);
            intent.putExtra("section", section);

            // Start activity with message passed.
            startActivity(intent);
        }
    };

    /**
     * Enable navigation to the booking system
     */
    private OnClickListener makeBookingButtonVisible = new OnClickListener() {
        @Override
        public void onClick(View v) {

            // set clicked image to be invisible
            v.setVisibility(View.INVISIBLE);

            // find text view
            TextView needMoreHelpText = (TextView) findViewById(R.id.mainscroll_help);

            // override default text size of the text view
            needMoreHelpText.setTextSize(20);

            // set the text
            needMoreHelpText.setText(
                    UtilityManager.getContentLoader(MainScroll.this).getInfoText("mainscroll", "booking")
            );

            // select the book button
            findViewById(R.id.bookButtonOnMainScroll).setVisibility(View.VISIBLE);
        }
    };

    private OnClickListener bookButtonOnClick = new OnClickListener() {
        @Override
        public void onClick(View v) {

            // fetch the users email
            String email = UtilityManager.getUserUtility(MainScroll.this).getEmail();

            // prevent user from booking an appointment with no email,
            // remove whitespace in the email
            if (email == null || "".equals(email.trim())) {

                // create an alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(MainScroll.this, AlertDialog.THEME_HOLO_LIGHT);

                // set the details of the alert dialog
                builder.setMessage(
                        UtilityManager.getContentLoader(MainScroll.this).getNotificationText("email-error")
                )
                        .setCancelable(false)
                        // close the dialog when user clicks okay
                        .setPositiveButton(
                                UtilityManager.getContentLoader(MainScroll.this).getButtonText("ok"),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                // create and show the alert message dialog to the user
                AlertDialog alert = builder.create();
                alert.show();

            } else {

                // email is not blank

                try {
                    // fetch list of appointments
                    Map<String, String> appointments = UtilityManager
                            .getDbUtility(MainScroll.this).getAppointment();

                    // check if appointment has a date
                    if (appointments != null && appointments.containsKey("App_Date")) {

                        // show appointment details
                        Intent intent = new Intent(v.getContext(), AppointmentDetails.class);
                        startActivity(intent);

                    } else {

                        // if no appointment is found, show the booking system
                        Intent intent = new Intent(v.getContext(), BookingSystem.class);
                        startActivity(intent);
                    }
                } catch (NetworkException e) {
                    NotificationHandler.NetworkErrorDialog(MainScroll.this);
                }
            }
        }
    };

    /**
     * Enable navigation to the settings
     */
    private OnClickListener navigateToSettings = new OnClickListener() {

        @Override
        public void onClick(View v) {

            // save the activity to be navigated to
            Intent intent = new Intent(v.getContext(), Settings.class);

            // navigate to saved activity
            startActivity(intent);

            // use slide animation for the transition
            overridePendingTransition(R.anim.slide_bottom_in, R.anim.slide_bottom_out);
        }
     };
}