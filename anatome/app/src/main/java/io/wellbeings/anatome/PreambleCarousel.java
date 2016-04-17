package io.wellbeings.anatome;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Control carousel navigation.
 */
public class PreambleCarousel extends Fragment {

    // Store number of items in carousel for code clarity.
    private int numTabs = Preamble.NUM_STEPS;
    // Display state of each tab.
    private ImageView[] nTabs = new ImageView[numTabs];
    // Store view.
    View view;
    // Initialise private index.
    private int index = 0;

    public PreambleCarousel() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_preamble_carousel, container, false);
        populateTabs();

        // Provide back button.
        Button backBtn = (Button) view.findViewById(R.id.carousel_back_button);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                android.support.v4.view.ViewPager vp = (android.support.v4.view.ViewPager) v.findViewById(R.id.preamble_carousel);

                if (index > 0) {
                    Preamble.mPager.setCurrentItem(index - 1, true);
                }

            }
        });
        // Make it initially invisible.
        backBtn.setVisibility(View.INVISIBLE);

        // Provide forward button.
        Button forwardBtn = (Button) view.findViewById(R.id.carousel_forward_button);
        forwardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Advance to next tab.
                if(index < Preamble.NUM_STEPS - 1) {
                    Preamble.mPager.setCurrentItem(index + 1, true);
                }
                //
                else if(index == Preamble.NUM_STEPS - 1) {
                    Intent intent = new Intent(getActivity(), MainScroll.class);
                    startActivity(intent);
                }

            }
        });

        // Inflate the layout for this fragment.
        return view;

    }

    // Display tabs graphically.
    private void populateTabs() {

        LinearLayout tabContainer = (LinearLayout) view.findViewById(R.id.tabs);
        ImageView image;
        LinearLayout.LayoutParams layoutParams;
        for(int i = 0; i < numTabs; i++) {

            image = new ImageView(getContext());
            layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(10, 0, 10, 0);
            image.setLayoutParams(layoutParams);
            image.setBackgroundDrawable(getResources().getDrawable(R.drawable.carouselbtninactive));
            nTabs[i] = image;
            tabContainer.addView(image);

        }
        nTabs[index].setBackgroundDrawable(getResources().getDrawable(R.drawable.carouselbtnactive));

    }

    // Control navigation.
    public void changeTab(final int i) {

        // If trying to advance past credentials, validate them.
        if(index == 2 && i == 3) {

            // Retrieve pager.
            android.support.v4.view.ViewPager vp = (android.support.v4.view.ViewPager) view.findViewById(R.id.preamble_carousel);

            /* Validate input. */

            String email = PreambleName.getCurrentEmail();
            String regex = "^(.+)@(.+)$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(email);

            // If the email is blank, remind the user they may put one in at a later date.
            if(email == null || "".equals(email.trim())) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setMessage(UtilityManager.getContentLoader(getContext()).getNotificationText("email-empty"))
                        .setCancelable(false)
                        .setPositiveButton(UtilityManager.getContentLoader(getContext()).getButtonText("ok"),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                AlertDialog alert = builder.create();
                alert.show();
            }
            else if(!matcher.matches()) {
                // Display error prompt.
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setTitle(UtilityManager.getContentLoader(getContext()).getButtonText("oops"))
                        .setMessage(UtilityManager.getContentLoader(getContext()).getNotificationText("email-invalid"))
                        .setCancelable(false)
                        .setPositiveButton(UtilityManager.getContentLoader(getContext()).getButtonText("ok"),
                                new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Preamble.mPager.setCurrentItem(i - 1, true);
                            }
                        });

                AlertDialog alert = builder.create();
                alert.show();
            }

            // At this point, user variables are okay, continue with save.
            UtilityManager.getUserUtility(getContext()).setEmail(PreambleName.getCurrentEmail());
            if(PreambleName.getCurrentName() == "null" || "".equals(PreambleName.getCurrentName())) {
                UtilityManager.getUserUtility(getContext()).setName("Guest");
            }
            else {
                UtilityManager.getUserUtility(getContext()).setName(PreambleName.getCurrentName());
            }

        }

        // Set the tab dot graphics correctly.
        nTabs[index].setBackgroundDrawable(getResources().getDrawable(R.drawable.carouselbtninactive));
        index = i;
        nTabs[index].setBackgroundDrawable(getResources().getDrawable(R.drawable.carouselbtnactive));

        // Set the button graphics correctly.
        if(index == 0) {
            ((Button) view.findViewById(R.id.carousel_back_button)).setVisibility(View.INVISIBLE);
        }
        else {
            ((Button) view.findViewById(R.id.carousel_back_button)).setVisibility(View.VISIBLE);
        }

    }

}
