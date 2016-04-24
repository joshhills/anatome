package io.wellbeings.anatome;

import android.app.Activity;
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
public class HeartWidget extends Fragment implements View.OnClickListener {

    boolean counterIsIncreasing = true; // check if the timer is increasing
    boolean counterIsActive; // check if the timer is running
    int counterValue = 0; // store the breathing value count
    Timer textTimer = new Timer(); // creates a new timer for the breathing count
    Vibrator vibrateToUser; // vibration to user
    List<String> instructionalText; // Store a list of instructional messages
    private final String SECTION = "heart";  // Store name of section.
    private TimerTask counterTask; // a timer used to schedule the running of the counter


    public HeartWidget() {
        // Required empty public constructor
    }

    public static HeartWidget newInstance() {

        HeartWidget fragment = new HeartWidget();
        Bundle args = new Bundle();
        return fragment;
    }

    /**
     *  override native onPause method to ensure the timerTask is successfully stopped
     */
    @Override
    public void onPause() {
        super.onPause();
        // cancel the timer and reset the status
        counterTask.cancel();
        counterIsActive = false;
    }

    @Override
    public void onResume() {
        super.onResume();
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

    /**
     * an OnClick method for the start button
     **/
    public void startButtonOnClick(View v) {

        // check the timer is not already running
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

    /**
     * an OnClick method for the stop button. This stops the animation and timer
     * **/
    public void stopButtonOnClick(View v) {

        // select the circle view
        View circleView = (View) getView().findViewById(R.id.circleView);

        // load the stop circle animation
        Animation circleAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.stop_animation);

        // stop animating the circle
        circleView.startAnimation(circleAnimation);

        // timer is no longer running
        counterIsActive = false;

        // reset the start value
        setNumericalTimer(0);

        // cancel the timer
        counterTask.cancel();
    }



    /**
     * A method to run a new timer task with timer value and instructional text
     */
    public TimerTask getCounterTask() {

        return new TimerTask() {


            @Override
            public void run() {


                Activity a = getActivity();

                // check there is a current activity
                if (a == null) {
                    return;
                }

                // run thread on this activity
                a.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        // set the timer value
                        if (counterIsActive) {
                            setNumericalTimer(counterValue);


                            // begin the timer
                            if (counterValue == 0) {

                                // send vibration to the user
                                vibrateToUser.vibrate(300);
                                // the timer is now running
                                counterIsIncreasing = true;
                                // display instructions to the user
                                setInstructionText(instructionalText.get(0));
                            }

                            // display instructional message to the user as they breathe in
                            if (counterValue == 1 && counterIsIncreasing) {

                                setInstructionText(instructionalText.get(1));
                            }

                            // display instructional message to the user as they breathe out
                            if (counterValue == 1 && !counterIsIncreasing) {

                                setInstructionText(instructionalText.get(3));
                            }

                            // timer ready to count down
                            if (counterValue == 2) {

                                counterIsIncreasing = false;
                            }

                            if (counterValue == 2 && !counterIsIncreasing) {

                                // vibrate to user
                                vibrateToUser.vibrate(300);
                                // display instructional text to the user
                                setInstructionText(instructionalText.get(2));
                            }

                            // increase the timer value
                            if (counterIsIncreasing) {

                                counterValue++;
                              // decrease the timer value
                            } else {
                                counterValue--;
                            }
                        }
                    }
                });
            }
        };
    }

    /**
     * override the onClick method for the view
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonStart:

                startButtonOnClick(v);
                break;

            case R.id.buttonStop:

                stopButtonOnClick(v);
                // reset the text
                setInstructionText(instructionalText.get(0));
                break;
        }
    }

    /**
     * set the instructional text and display to the user
     * @param s
     */
    private void setInstructionText(String s) {

        // fetch the textView for the instructions
        TextView instructionalTextView = (TextView) (getView().findViewById(R.id.textView));
        // set the text
        instructionalTextView.setText(s);
    }

    /**
     * sets the timer value and displays it to the user
     */
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