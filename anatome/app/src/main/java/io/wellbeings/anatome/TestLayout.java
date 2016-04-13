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
        setContentView(R.layout.test_layout);


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
        String test;
        HashMap<String, String> appointments = new HashMap<>();

        DbUtility db = new DbUtility();
        db.getAppointment(ctx);
        try {
            test = db.new GetDataJSON("app", ctx).execute().get();
            System.out.println("/////////" + test);
            appointments = db.parseAppointment(test);

            TextView timeView = (TextView)findViewById(R.id.bookedTime);
            TextView dateView = (TextView)findViewById(R.id.bookedDate);

            dateView.setText(appointments.get("App_Date").toString());
            timeView.setText(appointments.get("App_Time").toString());
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

}
