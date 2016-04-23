package io.wellbeings.anatome;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import java.util.HashMap;


/**
 * Main activity handles navigation to custom
 * informative sections, serves as a graphical
 * launchpad for app's key features.
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

    @Override
    protected void onPause() {
        super.onPause();

        // Log scroll position for ease of use.
        scrollY = ((ScrollView) findViewById(R.id.mainscroll_scroll_container)).getScrollY();

    }

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

    private void initGUI() {

        // load the rocket background image
        Glide.with(this)
                .load(R.drawable.mainscroll_background_rocket)
                .dontTransform()
                .override(1080, 732)
                .into((ImageView) findViewById(R.id.mainscroll_rocket_background));

        // load the rocket animation image
        Glide.with(this)
                .load(R.drawable.rocket_animation)
                .dontTransform()
                .override(1080, 732)
                .animate(R.anim.rocket_animation)
                .into((ImageView) findViewById(R.id.rocket_animation));

        // load the dark fuel animation image
        Glide.with(this)
                .load(R.drawable.mainscroll_fuel_dark)
                .dontTransform()
                .override(1080, 732)
                .animate(R.anim.rocket_fuel_movement_animation)
                .into((ImageView) findViewById(R.id.mainscroll_fuel_dark));

        // load the light fuel animation image
        Glide.with(this)
                .load(R.drawable.mainscroll_fuel_light)
                .dontTransform()
                .override(1080, 732)
                .animate(R.anim.rocket_fuel_animation)
                .into((ImageView) findViewById(R.id.mainscroll_fuel_light));

        // load the kite background image
        Glide.with(this)
                .load(R.drawable.mainscroll_kite)
                .dontTransform()
                .override(1080, 732)
                .into((ImageView) findViewById(R.id.mainscroll_kite));

        // load the background image
        Glide.with(this)
                .load(R.drawable.mainscroll_background_upper)
                .dontTransform()
                .override(1080, 2700)
                .into((ImageView) findViewById(R.id.mainscroll_background_upper));

        // load the background image
        Glide.with(this)
                .load(R.drawable.mainscroll_background_lower)
                .dontTransform()
                .override(1080, 2638)
                .into((ImageView) findViewById(R.id.mainscroll_background_lower));

        // load the heart image
        Glide.with(this)
                .load(R.drawable.heart)
                .dontTransform()
                .override(1200, 1014)
                .animate(R.anim.heart_animation)
                .into((ImageView) findViewById(R.id.heart));

        //load the brain image
        Glide.with(this)
                .load(R.drawable.brain)
                .dontTransform()
                .override(1080, 1262)
                .animate(R.anim.brain_animation)
                .into((ImageView) findViewById(R.id.brain));

        //load the liver image
        Glide.with(this)
                .load(R.drawable.liver_front)
                .dontTransform()
                .override(1080, 662)
                .animate(R.anim.liver_animation)
                .into((ImageView) findViewById(R.id.liver));


        //load the background liver image
        Glide.with(this)
                .load(R.drawable.liver_back)
                .dontTransform()
                .override(1080, 662)
                .into((ImageView) findViewById(R.id.liver_back));

        //load the footer
        Glide.with(this)
                .load(R.drawable.footer)
                .dontTransform()
                .override(1080, 731)
                .into((ImageView) findViewById(R.id.mainscroll_footer));

        TextView t = (TextView)findViewById(R.id.main_scroll_text);
        t.setText(UtilityManager.getContentLoader(this).getInfoText("mainscroll", "welcome"));

        TextView footerTextTitle = (TextView)findViewById(R.id.mainscroll_organisation_title);
        footerTextTitle.setText(UtilityManager.getContentLoader(this).getHeaderText("mainscroll", "more_help"));

        TextView footerTextInfo = (TextView)findViewById(R.id.mainscroll_more_help_text);
        footerTextInfo.setText(UtilityManager.getContentLoader(this).getInfoText("mainscroll", "more_help_text"));

    }

    // Modulate set-up tasks for easy alteration.
    private void attachListeners() {

        // Retrieve navigation components and set mutual listeners.
        findViewById(R.id.brain).setOnClickListener(navigateToSection);
        findViewById(R.id.heart).setOnClickListener(navigateToSection);
        findViewById(R.id.liver).setOnClickListener(navigateToSection);
        findViewById(R.id.bookingInfoButton).setOnClickListener(navigateToBookingSystem);
        findViewById(R.id.rocket_animation).setOnClickListener(navigateToSettings);
        findViewById(R.id.settingsBtn).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainScroll.this, OrganizationActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_top_in, R.anim.slide_top_out);
            }
        });

    }

    // Mutual re-usable interface type to manage section routing.
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

    private OnClickListener navigateToBookingSystem = new OnClickListener() {
        @Override
        public void onClick(View v) {

            TextView needMoreHelpText = (TextView) findViewById(R.id.needMoreHelpText);
            ImageButton infoButtonOnMainScroll = (ImageButton) v;
            ImageButton bookImageButtonOnMainScroll = (ImageButton) findViewById(R.id.bookButtonOnMainScroll);

            infoButtonOnMainScroll.setVisibility(View.INVISIBLE);
            //needMoreHelpText.setVisibility(View.INVISIBLE);


            AssetManager assetManager = getAssets();
            Typeface customFontBariol = Typeface.createFromAsset(assetManager, "fonts/Bariol.ttf");
            Typeface customFontHelvetica = Typeface.createFromAsset(assetManager, "fonts/Helvetica.ttf");

            needMoreHelpText.setTextSize(20);
            needMoreHelpText.setText("Book an appointment with \nthe Wellbeing Service");
            bookImageButtonOnMainScroll.setVisibility(View.VISIBLE);

           bookImageButtonOnMainScroll.setOnClickListener(new OnClickListener() {
               @Override
               public void onClick(View v) {

                   String email = UtilityManager.getUserUtility(MainScroll.this).getEmail();

                   if(email == null || "".equals(email.trim())) {

                       AlertDialog.Builder builder = new AlertDialog.Builder(MainScroll.this, AlertDialog.THEME_HOLO_LIGHT);

                       builder.setMessage("You must set your email in settings to book an appointment!")
                               .setCancelable(false)
                               .setPositiveButton("Okay",
                                       new DialogInterface.OnClickListener() {
                                           public void onClick(DialogInterface dialog, int id) {
                                               dialog.cancel();
                                           }
                                       });

                       AlertDialog alert = builder.create();
                       alert.show();

                   }else {

                       try {
                           Context ctx = MainScroll.this;
                           HashMap<String, String> appointments;

                           appointments = UtilityManager.getDbUtility(ctx).getAppointment();

                           String date;
                           Boolean check;

                           try {
                               date = appointments.get("App_Date").toString();
                               check = true;
                           } catch (Exception e) {
                               check = false;
                           }

                           if (check) {
                               Intent intent = new Intent(v.getContext(), TestLayout.class);
                               startActivity(intent);
                           } else {

                               Intent intent = new Intent(v.getContext(), BookingSystem.class);
                               startActivity(intent);
                           }
                       }catch (NetworkException e) {
                           NotificationHandler.NetworkErrorDialog(MainScroll.this);
                       }

                   }
               }
           });


        }
    };

    private OnClickListener navigateToSettings = new OnClickListener() {
        @Override
        public void onClick(View v) {
            ((ImageView) findViewById(R.id.rocket_animation)).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), Settings.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_bottom_in, R.anim.slide_bottom_out);
                }
            });
        }
    };
}