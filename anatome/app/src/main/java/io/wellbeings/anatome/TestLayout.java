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

        TextView t = (TextView) findViewById(R.id.bookedDate);
        TextView y = (TextView) findViewById(R.id.bookedTime);
        Context ctx = TestLayout.this;

        DbUtility db = new DbUtility();
        db.getUserComments(ctx);

    }
}
