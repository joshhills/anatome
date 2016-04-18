package io.wellbeings.anatome;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PreambleLock extends Fragment {

    View view;

    public PreambleLock() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_preamble_lock, container, false);

        populateContent();
        attachListeners();

        return view;
    }

    private void populateContent() {

        ((TextView) view.findViewById(R.id.preamble_header_lock))
                .setText(UtilityManager.getContentLoader(getContext()).getHeaderText("preamble", "lock"));

        ((TextView) view.findViewById(R.id.preamble_information_lock))
                .setText(UtilityManager.getContentLoader(getContext()).getInfoText("preamble", "lock"));

    }

    private void attachListeners() {

        /* Add a bespoke password creation button. */

        ((Button) view.findViewById(R.id.preamble_password)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Create overarching interaction.
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Set Password");

                // Create input.
                final EditText pwInput = new EditText(getContext());

                // Force numerical keyboard and hidden values.
                pwInput.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                // Force maximum length.
                pwInput.setFilters(new InputFilter[] {new InputFilter.LengthFilter(
                        UtilityManager.getUserUtility(getContext()).getPASSWORD_LENGTH()
                )});
                builder.setView(pwInput);

                // Dictate what the buttons do.
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Store the input.
                        final String proposedPassword = pwInput.getText().toString();
                        // Reset the input.
                        final EditText pwConfirm = new EditText(getContext());
                        pwConfirm.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        pwConfirm.setFilters(new InputFilter[]{new InputFilter.LengthFilter(
                                UtilityManager.getUserUtility(getContext()).getPASSWORD_LENGTH()
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
                                    Toast.makeText(getContext(), "Password set.",
                                            Toast.LENGTH_SHORT).show();
                                    // Store password.
                                    UtilityManager.getUserUtility(getContext()).setPassword(
                                            pwConfirm.getText().toString());
                                    // Continue to main activity.
                                    Intent intent = new Intent(getActivity(), MainScroll.class);
                                    startActivity(intent);
                                } else {
                                    dialog.cancel();
                                    Toast.makeText(getContext(), "Your passwords didn't match!",
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
