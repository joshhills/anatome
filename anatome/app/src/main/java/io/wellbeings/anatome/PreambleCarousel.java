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
 * Activities that contain this fragment must implement the
 * {@link PreambleCarousel.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class PreambleCarousel extends Fragment {

    private OnFragmentInteractionListener mListener;
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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
