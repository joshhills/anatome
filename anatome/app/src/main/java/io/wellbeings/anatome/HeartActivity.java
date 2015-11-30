package io.wellbeings.anatome;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class HeartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.heart_activity);

    }

    public void startButtonOnClick(View v) {

        View circleView = (View) findViewById(R.id.circleView);

            Animation circleAnimation = AnimationUtils.loadAnimation(this, R.anim.circle_animation);
            circleAnimation.setRepeatCount(Animation.INFINITE);
           circleView.startAnimation(circleAnimation);
        }


    public void stopButtonOnClick(View v) {

        View circleView = (View) findViewById(R.id.circleView);
        Animation circleAnimation = AnimationUtils.loadAnimation(this, R.anim.stop_animation);
        circleAnimation.setRepeatCount(Animation.INFINITE);
        circleView.startAnimation(circleAnimation);

    }
}

