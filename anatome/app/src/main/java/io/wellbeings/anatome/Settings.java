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
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Display settings to allow user
 * to change their device and profile preferences.
 */
public class Settings extends Activity {

    /**
     * On activity creation, set up canvas.
     *
     * @param savedInstanceState Previously cached state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Hide intrusive android status bars.
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_IMMERSIVE);

        // Load previous state if applicable.
        super.onCreate(savedInstanceState);

        // Load the corresponding view.
        setContentView(R.layout.activity_settings);

        // Set correct section values.
        initGUI();

        // Initialization of components.
        attachListeners();

    }

    // Alter theme and text accordingly.
    private void initGUI() {

        // Set the background of the layout container.
        Glide.with(this).load(R.drawable.settings_bg)
                .dontTransform()
                .override(1080,1844)
                .into((ImageView) findViewById(R.id.settings_bg));

        // Ensure multiple choice form fields are correct.
        ((Switch) findViewById(R.id.settings_network)).setChecked(
                UtilityManager.getUserUtility(this).isNetwork()
        );
        ((Switch) findViewById(R.id.settings_network)).setText(
                UtilityManager.getContentLoader(this).getButtonText("network")
        );
        ((Switch) findViewById(R.id.settings_notifications)).setChecked(
                UtilityManager.getUserUtility(this).isNotifications()
        );
        ((Switch) findViewById(R.id.settings_notifications)).setText(
                UtilityManager.getContentLoader(this).getButtonText("notifications")
        );

        // Populate content.

        ((TextView) findViewById(R.id.settings_device)).setText(
                UtilityManager.getContentLoader(this).getButtonText("device")
        );

        ((TextView) findViewById(R.id.settings_profile)).setText(
                UtilityManager.getContentLoader(this).getButtonText("profile")
        );

        ((TextView) findViewById(R.id.settings_name_label)).setText(
                UtilityManager.getContentLoader(this).getButtonText("name")
        );

        ((TextView) findViewById(R.id.settings_email_label)).setText(
                UtilityManager.getContentLoader(this).getButtonText("email")
        );

        ((Button) findViewById(R.id.settings_password_change)).setText(
                UtilityManager.getContentLoader(this).getButtonText("password-change")
        );

        ((Button) findViewById(R.id.settings_password_clear)).setText(
                UtilityManager.getContentLoader(this).getButtonText("password-remove")
        );

        ((Button) findViewById(R.id.settings_erase)).setText(
                UtilityManager.getContentLoader(this).getButtonText("erase")
        );

    }

    // Modulate set-up tasks for easy alteration.
    private void attachListeners() {

        // Notification toggle.
        ((Switch) findViewById(R.id.settings_notifications)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UtilityManager.getUserUtility(v.getContext()).allowNotifications(
                        !UtilityManager.getUserUtility((v.getContext())).isNotifications()
                );
            }
        });

        // Network toggle.
        ((Switch) findViewById(R.id.settings_network)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UtilityManager.getUserUtility(v.getContext()).allowNetwork(
                        !UtilityManager.getUserUtility((v.getContext())).isNetwork()
                );
            }
        });

        // Language spinner.
        Spinner langSpinner = (Spinner) findViewById(R.id.settings_language);
        String[] language = {"English", "French", "Spanish"};
        ArrayAdapter<String> stringArrayAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, language);
        langSpinner.setAdapter(stringArrayAdapter);

        langSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);

                // Set the correct language.
                switch (position) {
                    case 0:
                        UtilityManager.getUserUtility(view.getContext()).setLanguage("en");
                        break;
                    case 1:
                        UtilityManager.getUserUtility(view.getContext()).setLanguage("fr");
                        break;
                    case 2:
                        UtilityManager.getUserUtility(view.getContext()).setLanguage("es");
                        break;

                }

                // Reload the view.
                initGUI();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Set the correct language.
        switch(UtilityManager.getUserUtility(this).getLanguage()) {
            case "en":
                langSpinner.setSelection(0);
                break;
            case "fr":
                langSpinner.setSelection(1);
                break;
            case "es":
                langSpinner.setSelection(2);
                break;
        }

        // Name field.
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
                // Set the name to the most recent change.
                UtilityManager.getUserUtility(Settings.this).setName(nameText.getText().toString());
            }
        });

        // Email field.
        final EditText emailText = (EditText) findViewById(R.id.settings_email);
        emailText.setText(UtilityManager.getUserUtility(this).getEmail());

        String regex = "^(.+)@(.+)$";
        final Pattern pattern = Pattern.compile(regex);

        emailText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                // Get the text.
                String currentText = ((EditText) findViewById(R.id.settings_email)).getText().toString();

                // Check the email supplied.
                Matcher matcher = pattern.matcher(currentText);
                if ((currentText != null || !("".equals(currentText.trim()))) && !matcher.matches()) {
                    emailText.setError(
                            UtilityManager.getContentLoader(Settings.this).getButtonText("invalid-email")
                    );
                } else {
                    UtilityManager.getUserUtility(Settings.this).setEmail(currentText);
                }

            }
        });

        // Password change.
        ((Button) findViewById(R.id.settings_password_change)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            // Create overarching interaction.
            final AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this, AlertDialog.THEME_HOLO_LIGHT);
            builder.setTitle(
                    UtilityManager.getContentLoader(Settings.this).getButtonText("password-set")
            );

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
            builder.setPositiveButton(
                UtilityManager.getContentLoader(Settings.this).getButtonText("ok"),
                new DialogInterface.OnClickListener() {
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
                        builder.setTitle(UtilityManager.getContentLoader(Settings.this).getButtonText("password-confirm"));
                        // Change the purpose of the button.
                        builder.setPositiveButton(
                                UtilityManager.getContentLoader(Settings.this).getButtonText("confirm"),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // If password confirmation was successful...
                                        if (pwConfirm.getText().toString().equals(proposedPassword)) {
                                            // Display visual feedback.
                                            Toast.makeText(Settings.this,
                                                    UtilityManager.getContentLoader(Settings.this).getNotificationText("password-set"),
                                                    Toast.LENGTH_SHORT).show();
                                            // Store password.
                                            UtilityManager.getUserUtility(Settings.this).setPassword(
                                                    pwConfirm.getText().toString());
                                        } else {
                                            dialog.cancel();
                                            Toast.makeText(Settings.this,
                                                    UtilityManager.getContentLoader(Settings.this).getNotificationText("password-fail"),
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                        builder.show();
                    }
                });
            builder.setNegativeButton(
                    UtilityManager.getContentLoader(Settings.this).getButtonText("cancel"),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                        }
                });

            // Show the now prepared dialog.
            builder.show();

            }
        });

        // Password clear.
        ((Button) findViewById(R.id.settings_password_clear)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Remove password.
                UtilityManager.getUserUtility(Settings.this).setPassword(null);
                // Display visual feedback.
                Toast.makeText(Settings.this,
                        UtilityManager.getContentLoader(Settings.this).getNotificationText("password-clear"),
                        Toast.LENGTH_SHORT).show();
            }
        });

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
        ((ImageButton) findViewById(R.id.settings_back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this, MainScroll.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_top_in, R.anim.slide_top_out);
            }
        });

    }

}