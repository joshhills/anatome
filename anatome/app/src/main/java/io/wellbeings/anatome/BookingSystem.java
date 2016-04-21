package io.wellbeings.anatome;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.NumberPicker;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

public class BookingSystem extends AppCompatActivity {

    // Private fields.
    private TextView mSetDate, mSetTime, mBookingTitle;
    private Button mBackFromBooking, mBook;
    private ImageButton mOptions;
    private String[] appointments;
    private String pref = "either";
    private String timeTime = "09:00";
    final Calendar c = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setGeneralContentView();

        mOptions = (ImageButton) findViewById(R.id.bookingOptions);
        mOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                toOptions();
            }
        });

        //findViewById(R.id.bookFromBooking).setOnClickListener(navigateToTestLayout);

        setCurrentDateOnView();

    }

    /*
    TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            c.set(Calendar.HOUR_OF_DAY, hourOfDay);
            c.set(Calendar.MINUTE, minute);
            setCurrentDateOnView();
        }
    };*/


    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, monthOfYear);
            c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setCurrentDateOnView();
        }
    };

    // true - 24-hour format
    public void timeOnClick(View view) {

        String date = mSetDate.getText().toString();
        String newDate = null;

        SimpleDateFormat initial = new SimpleDateFormat("dd-MM-yy");
        SimpleDateFormat needed = new SimpleDateFormat("yyyy-MM-dd");

        try {
            newDate = needed.format(initial.parse(date));
            appointments = UtilityManager.getDbUtility(this).getAvailable(newDate.toString());


        }catch(Exception e) {
            System.out.println("Error: Unable to parse date");
        }


        RelativeLayout linearLayout = new RelativeLayout(BookingSystem.this);
        final NumberPicker aNumberPicker = new NumberPicker(BookingSystem.this);
        aNumberPicker.setMaxValue(appointments.length - 1);
        aNumberPicker.setMinValue(0);
        aNumberPicker.setDisplayedValues(appointments);

        aNumberPicker.setOnValueChangedListener(
                new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        timeTime = appointments[newVal];
                        mSetTime.setText(timeTime);
                    }
                }
        );

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(50, 50);
        RelativeLayout.LayoutParams numPicerParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        numPicerParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

        linearLayout.setLayoutParams(params);
        linearLayout.addView(aNumberPicker,numPicerParams);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(BookingSystem.this, TimePickerDialog.THEME_HOLO_LIGHT);
        alertDialogBuilder.setTitle("Select the time of your appointment:");
        alertDialogBuilder.setView(linearLayout);
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                System.out.println("Value Selected : " + aNumberPicker.getValue());

                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void dateOnClick(View view) {

        DatePickerDialog dp = new DatePickerDialog(BookingSystem.this, TimePickerDialog.THEME_HOLO_LIGHT, date,
                c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        Date newdate = c.getTime();
        dp.getDatePicker().setMinDate(newdate.getTime());
        dp.getDatePicker().setMaxDate(newdate.getTime() + 1209600000);
        dp.show();
    }

    // Formats date & time
    public void setCurrentDateOnView() {

        String dateFormat = "dd-MM-yy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.UK);
        mSetDate.setText(sdf.format(c.getTime()));

        mSetTime.setText(timeTime);
    }

    public void postBooking() {
        //get data from booking page
        String date = mSetDate.getText().toString();
        String time = mSetTime.getText().toString();
        String newDate = null;

        SimpleDateFormat initial = new SimpleDateFormat("dd-MM-yy");
        SimpleDateFormat needed = new SimpleDateFormat("yyyy-MM-dd");

        try {
            newDate = needed.format(initial.parse(date));
        }catch(Exception e) {
            System.out.println("Error: Unable to parse date");
        }

        UtilityManager.getDbUtility(this).addAppointment(time, newDate, pref);
        Toast.makeText(BookingSystem.this, "Appointment Booked", Toast.LENGTH_SHORT).show();
    }

    private void disableBookButton() {

        mBook.setEnabled(false);
        mBook.setTextColor(Color.parseColor("#BBBBBB"));
    }

    private void toMainScroll() {
        Intent i = new Intent(BookingSystem.this, MainScroll.class);
        startActivity(i);
    }

    private Typeface defineCustomFont() {

        AssetManager assetManager = getAssets();
        Typeface customFont = Typeface.createFromAsset(assetManager, "fonts/champagne.ttf");

        return customFont;
    }

    private void toOptions() {

        setContentView(R.layout.booking_options_layout);

        Typeface customFont = defineCustomFont();
        Button back = (Button) findViewById(R.id.backFromGenderOptionsButton);
        Button save = (Button) findViewById(R.id.saveGenderOptionsButton);
        TextView firstLine = (TextView) findViewById(R.id.genderOptionsFirstLine);
        TextView secondLine = (TextView) findViewById(R.id.genderOptionsSecondLine);
        TextView smallText = (TextView) findViewById(R.id.small_text_gender_options);
        RadioButton rb1 = (RadioButton) findViewById(R.id.radio_woman);
        RadioButton rb2 = (RadioButton) findViewById(R.id.radio_man);
        RadioButton rb3 = (RadioButton) findViewById(R.id.radio_nopreference);

        back.setTypeface(customFont);
        save.setTypeface(customFont);
        firstLine.setTypeface(customFont);
        secondLine.setTypeface(customFont);
        smallText.setTypeface(customFont);
        rb1.setTypeface(customFont);
        rb2.setTypeface(customFont);
        rb3.setTypeface(customFont);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setGeneralContentView();
            }
        });
    }

    public void selectGender(View v) {

        boolean checked = ((RadioButton) v).isChecked();
        Button saveBtn = (Button) findViewById(R.id.saveGenderOptionsButton);

        switch (v.getId()) {

            case R.id.radio_woman:
                if(checked) {
                    Toast.makeText(BookingSystem.this, "Woman", Toast.LENGTH_SHORT).show();
                    pref = "woman";
                }
                break;
            case R.id.radio_man:
                if(checked) {
                    Toast.makeText(BookingSystem.this, "Man", Toast.LENGTH_SHORT).show();
                    pref = "man";
                }
                break;
            case R.id.radio_nopreference:
                if(checked) {
                    Toast.makeText(BookingSystem.this, "None", Toast.LENGTH_SHORT).show();
                    pref = "either";
                }
                break;
        }

        saveBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setGeneralContentView();
            }
        });

    }

    public void setGeneralContentView() {

        setContentView(R.layout.booking_layout);

        mSetDate = (TextView) findViewById(R.id.setDate);
        mSetTime = (TextView) findViewById(R.id.setTime);
        mBackFromBooking = (Button) findViewById(R.id.backFromBooking);
        mBook = (Button) findViewById(R.id.bookFromBooking);
        mBookingTitle = (TextView) findViewById(R.id.bookingTitle);

        Typeface customFont = defineCustomFont();

        mSetDate.setTypeface(customFont);
        mSetTime.setTypeface(customFont);
        mBackFromBooking.setTypeface(customFont);
        mBook.setTypeface(customFont);
        mBookingTitle.setTypeface(customFont);

        mBackFromBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toMainScroll();
            }
        });

        mBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postBooking();
                disableBookButton();
                //switchView();
                Intent intent = new Intent(BookingSystem.this, TestLayout.class);
                startActivity(intent);
            }
        });

        mOptions = (ImageButton) findViewById(R.id.bookingOptions);
        mOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                toOptions();
            }
        });

        mOptions = (ImageButton) findViewById(R.id.bookingOptions);
        mOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                toOptions();
            }
        });

        setCurrentDateOnView();
    }
}
