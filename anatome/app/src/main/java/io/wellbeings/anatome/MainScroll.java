package io.wellbeings.anatome;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import android.content.Intent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Main activity handles navigation to custom
 * informative sections, serves as a graphical
 * launchpad for app's key features.
 */
public class MainScroll extends Activity {


    /**
     * On activity creation, set up canvas.
     *
     * @param savedInstanceState    Previously cached state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Load previous state if applicable.
        super.onCreate(savedInstanceState);

        // Load the corresponding view.
        setContentView(R.layout.activity_main_scroll);

        // Initialization of components.
        attachListeners();




        // load the background image
        Glide.with(this)
                .load(R.drawable.mainscroll_background)
                .dontTransform()
                .override(1080, 5249)
                .into((ImageView) findViewById(R.id.mainscroll_background));

        // load the heart image
        Glide.with(this)
                .load(R.drawable.heart)
                .dontTransform()
                .override(1080, 1200)
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
                .into((ImageView) findViewById(R.id.footer));
    }

    // Modulate set-up tasks for easy alteration.
    private void attachListeners() {

        // Retrieve navigation components and set mutual listeners.
        findViewById(R.id.brain).setOnClickListener(navigateToSection);
        findViewById(R.id.heart).setOnClickListener(navigateToSection);
        findViewById(R.id.liver).setOnClickListener(navigateToSection);


        findViewById(R.id.bookingInfoButton).setOnClickListener(navigateToBookingSystem);


    }

    // Mutual re-usable interface type to manage section routing.
    private OnClickListener navigateToSection = new OnClickListener(){
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
                   Intent intent = new Intent(v.getContext(), BookingSystem.class);
                   startActivity(intent);
               }
           });


        }
    };
}
