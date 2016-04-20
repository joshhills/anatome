package io.wellbeings.anatome;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Settings.
 */
public class Settings extends Activity {

    /**
     * On activity creation, set up canvas.
     *
     * @param savedInstanceState    Previously cached state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Load previous state if applicable.
        super.onCreate(savedInstanceState);

        // Load the corresponding view.
        setContentView(R.layout.activity_settings);

        // Set correct section values.
        initDisplay();

        // Initialization of components.
        attachListeners();

        editName();

    }

    // Alter colour and headers accordingly.
    private void initDisplay() {



    }

    // Modulate set-up tasks for easy alteration.
    private void attachListeners() {

        // Notification toggle.
        ((Switch)findViewById(R.id.settings_notifications)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UtilityManager.getUserUtility(v.getContext()).allowNotifications(
                        !UtilityManager.getUserUtility((v.getContext())).isNotifications()
                );
            }
        });

        // Network toggle.
        ((Switch)findViewById(R.id.settings_network)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UtilityManager.getUserUtility(v.getContext()).allowNetwork(
                        !UtilityManager.getUserUtility((v.getContext())).isNetwork()
                );
            }
        });

        // Init language spinner.

        // Reset button.
        ((Button) findViewById(R.id.settings_erase)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reset user settings.
                UtilityManager.getUserUtility(Settings.this).reset();
                // Go to set-up new profile.
                Intent i = new Intent(Settings.this, Preamble.class);
                Settings.this.startActivity(i);
            }
        });

        // Exit button.
        ((ImageButton)findViewById(R.id.imageButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.this, MainScroll.class));
            }
        });

    }

    private void initLanguageSpinner() {
        Spinner langSpinner = (Spinner) findViewById(R.id.settings_language);

        String[] language = {"English", "French", "Spanish"};

        ArrayAdapter<String> stringArrayAdapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, language);

        langSpinner.setAdapter(stringArrayAdapter);

        langSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Intuitively fill the next options based on initial selection.
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);


                switch (position) {
                    case 0://English
                        UtilityManager.getUserUtility(view.getContext()).setLanguage("en");
                        break;
                    case 1://French
                        UtilityManager.getUserUtility(view.getContext()).setLanguage("fr");
                        break;
                    case 2://Spanish
                        UtilityManager.getUserUtility(view.getContext()).setLanguage("es");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        }

        public void editName() {

           final EditText nameText = (EditText) findViewById(R.id.settings_name);

            nameText.setText(UtilityManager.getUserUtility(this).getName());

            nameText.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    UtilityManager.getUserUtility(Settings.this).setName(nameText.getText().toString());
                }

            });
        }

}