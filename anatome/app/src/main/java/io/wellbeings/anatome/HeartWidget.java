package io.wellbeings.anatome;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * A simple {@link Fragment} subclass.
 */
public class HeartWidget extends Fragment implements Widget, View.OnClickListener {


    boolean counterIsIncreasing = true;
    boolean counterIsActive;
    // store the breathing value count
    int counterValue = 0;
    Timer textTimer = new Timer();
    // vibration to user
    Vibrator vibrateToUser;
    // Store a list of instructional messages
    List<String> instructionalText;
    // Store name of section.
    private final String SECTION = "heart";
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

        // Set up the start button listener
        Button startButton = (Button) v.findViewById(R.id.buttonStart);
        startButton.setOnClickListener(this);

        // Set up the stop button listener
        Button stopButton = (Button) v.findViewById(R.id.buttonStop);
        stopButton.setOnClickListener(this);

        // set up the vibrate functionality
        vibrateToUser = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

        // store the instructional text
        instructionalText = getInstructions();

        // set text font
        setDefautFont(v);
        
        // return the view
        return v;
    }

    // an OnClick method for the start button
    public void startButtonOnClick(View v) {

        if (counterIsActive) {

            return;
        }

        // select the moving circle
        View circleView = (View) getView().findViewById(R.id.circleView);

        // load the moving circle animation
        Animation circleAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.circle_animation);

        // continue animating indefinitely
        circleAnimation.setRepeatCount(Animation.INFINITE);

        // start the animation
        circleView.startAnimation(circleAnimation);

        // the counter is now active
        counterIsActive = true;

        // set the starting counter value
        counterValue = 0;

        // load the counter task method
        counterTask = getCounterTask();

        // set the duration and details of the timer
        textTimer.schedule(counterTask, 0, 2000);
    }

    // an OnClick method for the stop button
    public void stopButtonOnClick(View v) {

        // select the circle view
        View circleView = (View) getView().findViewById(R.id.circleView);

        // load the stop circle animation
        Animation circleAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.stop_animation);

        // repeat animation indefinitely
       // circleAnimation.setRepeatCount(Animation.INFINITE);

        // start animating the circle
        circleView.startAnimation(circleAnimation);

        counterIsActive = false;
        setNumericalTimer(0);
        //setInstructionText("Stopped");
        counterTask.cancel();



    }

    // changes the timer inside the circle & changes the instructional text
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

                                vibrateToUser.vibrate(300);
                                counterIsIncreasing = true;
                                setInstructionText(instructionalText.get(0));

                            }

                            if (counterValue == 1  && counterIsIncreasing) {

                                setInstructionText(instructionalText.get(1));
                            }

                            if (counterValue == 1 && !counterIsIncreasing) {

                                setInstructionText(instructionalText.get(1));
                            }

                            if (counterValue == 2) {

                                counterIsIncreasing = false;
                            }

                            if (counterValue == 2 && !counterIsIncreasing) {

                                vibrateToUser.vibrate(300);
                                setInstructionText(instructionalText.get(2));
                                counterIsIncreasing = false; }

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
                setInstructionText(instructionalText.get(0));

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

    private void setDefautFont(View v) {

        TextView text = (TextView) (v.findViewById(R.id.textView));
        TextView text2 = (TextView) (v.findViewById(R.id.textView2));
        Typeface fontBariol = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Bariol.ttf");
        text.setTypeface(fontBariol);
        text2.setTypeface(fontBariol);
    }


    // fetches a list of strings for the instructional text
    private List<String> getInstructions() {

        return UtilityManager.getContentLoader(getContext()).getInfoTextAsList(SECTION, "instructional_text", ",");

    }
}