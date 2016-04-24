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
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Interactive subsection hinging on body part
 * provides a breathing control simulation and
 * extra advice to encourage users to relax.
 *
 * @author Team WellBeings - Lizzie, Abdulla
 */
public class HeartWidget extends Fragment implements Widget, View.OnClickListener {

    // Store name of section.
    private final String SECTION = "heart";

    // Store the view for code clarity.
    private View v;

    /* Private fields store state of interaction. */

    boolean counterIsIncreasing = true; // check if the timer is increasing
    boolean counterIsActive; // check if the timer is running
    int counterValue = 0; // store the breathing value count
    Timer textTimer = new Timer(); // creates a new timer for the breathing count
    Vibrator vibrateToUser; // vibration to user
    List<String> instructionalText; // Store a list of instructional messages
    private TimerTask counterTask; // a timer used to schedule the running of the counter

    /* Necessary lifecycle methods. */

    public HeartWidget() {
        // Required empty public constructor
    }

    public static HeartWidget newInstance() {
        HeartWidget fragment = new HeartWidget();
        Bundle args = new Bundle();
        return fragment;
    }

    /**
     *  Override native onPause method to ensure the
     *  timerTask is successfully paused.
     */
    @Override
    public void onPause() {
        super.onPause();

        // Cancel the timer and reset the status
        if (counterTask != null) {

            counterTask.cancel();
        }
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
        v = inflater.inflate(R.layout.fragment_heart_widget, container, false);

        initGUI();

        attachListeners();

        // store the instructional text
        instructionalText = getInstructions();
        
        // return the view
        return v;

    }

    public void initGUI() {

        // Set the content.

        ((Button) v.findViewById(R.id.heart_start)).setText(
                UtilityManager.getContentLoader(getContext()).getButtonText("start")
        );

        ((Button) v.findViewById(R.id.heart_stop)).setText(
                UtilityManager.getContentLoader(getContext()).getButtonText("stop")
        );

        ((TextView) v.findViewById(R.id.heart_advice)).setText(
                UtilityManager.getContentLoader(getContext()).getInfoText(SECTION, "advice-text")
        );

        // Get the typeface.
        Typeface bariol = UtilityManager.getThemeUtility(getContext()).getFont("Bariol");
        Typeface helvetica = UtilityManager.getThemeUtility(getContext()).getFont("Helvetica");

        // Set the typeface.
        ((TextView) v.findViewById(R.id.heart_instruction)).setTypeface(bariol);
        ((TextView) v.findViewById(R.id.heart_advice)).setTypeface(helvetica);

    }

    public void attachListeners() {

        // Set up the start button listener
        Button startButton = (Button) v.findViewById(R.id.heart_start);
        startButton.setOnClickListener(this);

        // Set up the stop button listener
        Button stopButton = (Button) v.findViewById(R.id.heart_stop);
        stopButton.setOnClickListener(this);

        // Set up the vibrate functionality.
        vibrateToUser = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

    }

    /**
     * Provide 'onClick' method for start button
     * to separate functionality - this starts
     * the animation and timer.
     */
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
     * Provide 'onClick' method for stop button
     * to separate functionality - this stops
     * the animation and resets the timer.
     */
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
     * A method to run a new timer task with
     * timer value and instructional text.
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
     * Override the onClick method for the
     * entire view to direct user interaction.
     *
     * @param v The element interacted with.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.heart_start:

                startButtonOnClick(v);
                break;

            case R.id.heart_stop:

                stopButtonOnClick(v);
                // reset the text
                setInstructionText(instructionalText.get(0));
                break;
        }
    }

    /**
     * Set the instructional text and
     * display to the user.
     *
     * @param s The text string to display.
     */
    private void setInstructionText(String s) {

        // fetch the textView for the instructions
        TextView instructionalTextView = (TextView) (getView().findViewById(R.id.heart_instruction));
        // set the text
        instructionalTextView.setText(s);

    }

    /**
     * Update and display the timer value
     * to the user.
     */
    private void setNumericalTimer(int i) {

        TextView counter = (TextView) (getView().findViewById(R.id.counter));
        counter.setText(Integer.toString(i));

    }

    /**
     * Fetches a list of strings for the
     * instructional text.
     */
    private List<String> getInstructions() {

        return UtilityManager.getContentLoader(getContext()).getInfoTextAsList(SECTION, "instructional-text", ",");

    }

}