package io.wellbeings.anatome;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
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
import android.widget.Toast;

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
        ((ImageButton)findViewById(R.id.settings_back)).setOnClickListener(new View.OnClickListener() {
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

    //This will change the user's name
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
                //set the name to what's written in the name text section
                UtilityManager.getUserUtility(Settings.this).setName(nameText.getText().toString());
            }

        });
    }

    public void checkEmail() {

        final EditText emailText = (EditText) findViewById(R.id.settings_email);

        emailText.setText(UtilityManager.getUserUtility(this).getEmail());

        final String email = ((EditText) findViewById(R.id.settings_email)).getText().toString();
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(email);


        emailText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if ((email != null || !("".equals(email.trim()))) && !matcher.matches()) {
                    emailText.setError("It's just wrong...... try again");
                } else {
                    emailText.setError(null);
                    UtilityManager.getUserUtility(Settings.this).setEmail(emailText.getText().toString());
                    emailText.setText(UtilityManager.getUserUtility(Settings.this).getEmail());
                }
            }

            // Build regex.

            // Check email is valid, if not, display error.

            // Otherwise, allow clean save.


        });


    }

    //This will reset all the user data including name, email, and password
    public void eraseUserData(){
        Button eraseButton = (Button) findViewById(R.id.settings_erase);
        eraseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UtilityManager.getUserUtility(Settings.this).reset();
                // Set-up new profile.
                Intent i = new Intent(Settings.this, Preamble.class);
                Settings.this.startActivity(i);
            }
        });
    }

    public void changePassword(){
        ((Button) findViewById(R.id.settings_password)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Create overarching interaction.
                final AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this, AlertDialog.THEME_HOLO_LIGHT);
                builder.setTitle("Set Password");

                // Create input.
                final EditText pwInput = new EditText(Settings.this);

                // Force numerical keyboard and hidden values.
                pwInput.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                // Force maximum length.
                pwInput.setFilters(new InputFilter[] {new InputFilter.LengthFilter(
                        UtilityManager.getUserUtility(Settings.this).getPASSWORD_LENGTH()
                )});
                builder.setView(pwInput);

                // Dictate what the buttons do.
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Store the input.
                        final String proposedPassword = pwInput.getText().toString();
                        // Reset the input.
                        final EditText pwConfirm = new EditText(Settings.this);
                        pwConfirm.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        pwConfirm.setFilters(new InputFilter[]{new InputFilter.LengthFilter(
                                UtilityManager.getUserUtility(Settings.this).getPASSWORD_LENGTH()
                        )});
                        builder.setView(pwConfirm);
                        builder.setTitle("Please confirm your password.");
                        // Change the purpose of the button.
                        builder.setPositiveButton("SET", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // If password confirmation was successful...
                                if (pwConfirm.getText().toString().equals(proposedPassword)) {
                                    // Display visual feedback.
                                    Toast.makeText(Settings.this, "Password set.",
                                            Toast.LENGTH_SHORT).show();
                                    // Store password.
                                    UtilityManager.getUserUtility(Settings.this).setPassword(
                                            pwConfirm.getText().toString());
                                    dialog.dismiss();
                                } else {
                                    dialog.cancel();
                                    Toast.makeText(Settings.this, "Your passwords didn't match!",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        builder.show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                // Show the now prepared dialog.
                builder.show();

            }
        });
    }

}