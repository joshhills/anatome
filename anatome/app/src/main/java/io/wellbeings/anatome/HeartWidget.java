package io.wellbeings.anatome;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class HeartWidget extends Fragment implements Widget, View.OnClickListener {

    public HeartWidget() {
        // Required empty public constructor
    }

    public static HeartWidget newInstance() {
        HeartWidget fragment = new HeartWidget();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_heart_widget, container, false);

        // Set up the button listeners
        Button startButton = (Button) v.findViewById(R.id.buttonStart);
        startButton.setOnClickListener(this);

        Button stopButton = (Button) v.findViewById(R.id.buttonStop);
        stopButton.setOnClickListener(this);

        return v;
    }

    public void startButtonOnClick(View v) {

        View circleView = (View) getView().findViewById(R.id.circleView);
        Animation circleAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.circle_animation);
        circleAnimation.setRepeatCount(Animation.INFINITE);
        circleView.startAnimation(circleAnimation);
    }

    public void stopButtonOnClick(View v) {

        View circleView = (View) getView().findViewById(R.id.circleView);

        Animation circleAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.stop_animation);
        circleAnimation.setRepeatCount(Animation.INFINITE);
        circleView.startAnimation(circleAnimation);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonStart:

                startButtonOnClick(v);


                break;

            case R.id.buttonStop:

                stopButtonOnClick(v);

                break;
        }
    }
}