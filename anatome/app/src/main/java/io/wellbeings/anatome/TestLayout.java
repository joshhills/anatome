package io.wellbeings.anatome;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

/**
 * Created by bettinaalexieva on 15/03/2016.
 */
public class TestLayout extends AppCompatActivity {

    private Button mBackFromBooked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_details);


        mBackFromBooked = (Button)findViewById(R.id.backFromBooked);

        AssetManager assetManager = getAssets();
        Typeface customFont = Typeface.createFromAsset(assetManager, "fonts/champagne.ttf");

        //mBackFromBooked.setTypeface(customFont);
        mBackFromBooked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainScroll.class);
                startActivity(intent);
            }
        });


        Context ctx = TestLayout.this;
        HashMap<String, String> appointments;

        appointments = UtilityManager.getDbUtility(this).getAppointment();

        TextView timeView = (TextView)findViewById(R.id.bookedTime);
        TextView dateView = (TextView)findViewById(R.id.bookedDate);

        String date = appointments.get("App_Date").toString();
        String time = appointments.get("App_Time").toString();

        dateView.setText(date);
        timeView.setText(time);

        NotificationHandler.pushNotification(TestLayout.this, "Booked Appointment:", "Time: " + time + "/nDate: " + date);

    }

}
