package io.wellbeings.anatome;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;


/**
 * A simple {@link Fragment} subclass.
 */
public class HeartWidget extends Fragment implements Widget, View.OnClickListener {


    boolean counterIsIncreasing = true;
    boolean counterIsActive;
    int counterValue = 0;
    Timer textTimer = new Timer();

    private TimerTask counterTask;


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

    // an OnClick method for the start button
    public void startButtonOnClick(View v) {

        View circleView = (View) getView().findViewById(R.id.circleView);
        Animation circleAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.circle_animation);
        circleAnimation.setRepeatCount(Animation.INFINITE);
        circleView.startAnimation(circleAnimation);

        counterIsActive = true;
        setInstructionText("Started");

        counterTask = getCounterTask();

        //textTimer = new Timer();
        textTimer.schedule(counterTask, 0, 2000);
    }

    // an OnClick method for the stop button
    public void stopButtonOnClick(View v) {

        View circleView = (View) getView().findViewById(R.id.circleView);

        Animation circleAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.stop_animation);
        circleAnimation.setRepeatCount(Animation.INFINITE);
        circleView.startAnimation(circleAnimation);

        counterIsActive = false;
        setInstructionText("Stopped");
        counterTask.cancel();


    }

    public TimerTask getCounterTask() {

        return new TimerTask() {



            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        if (counterIsActive) {

                            setNumericalTimer(counterValue);


                            if (counterValue == 0) {

                                counterIsIncreasing = true;
                            }

                            if (counterValue == 5) {

                                counterIsIncreasing = false;
                            }

                            if (counterIsIncreasing) {

                                counterValue++;
                            } else {
                                counterValue--;
                            }

                        }

                    }
                });
            }
        };
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

    private void setInstructionText(String s) {

        TextView instructionalTextView = (TextView) (getView().findViewById(R.id.textView));
        instructionalTextView.setText(s);
    }

    private void setNumericalTimer(int i) {

        TextView counter = (TextView) (getView().findViewById(R.id.counter));


           counter.setText(Integer.toString(i));
    }
}