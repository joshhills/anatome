package io.wellbeings.anatome;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class PreambleCarousel extends Fragment {

    private int numTabs = Preamble.NUM_STEPS;
    private ImageView[] nTabs = new ImageView[numTabs];
    View view;
    private int index = 0;

    public PreambleCarousel() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_preamble_carousel, container, false);
        populateTabs();

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

        Button forwardBtn = (Button) view.findViewById(R.id.carousel_forward_button);
        forwardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(index < Preamble.NUM_STEPS - 1) {
                    Preamble.mPager.setCurrentItem(index + 1, true);
                }
                else if(index == Preamble.NUM_STEPS - 1) {
                    Intent intent = new Intent(getActivity(), MainScroll.class);
                    startActivity(intent);
                }

            }
        });

        // Inflate the layout for this fragment.
        return view;

    }

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

    public void changeTab(int i) {

        nTabs[index].setBackgroundDrawable(getResources().getDrawable(R.drawable.carouselbtninactive));
        index = i;
        nTabs[index].setBackgroundDrawable(getResources().getDrawable(R.drawable.carouselbtnactive));

    }

}
