package io.wellbeings.anatome;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class LiverWidget extends Fragment implements Widget {

    /* Useful private variables. */

    // Store name of section.
    private final String SECTION = "liver";
    // Store view object for UI manipulation.
    private View v;

    // Necessary empty creation methods.
    public LiverWidget() {}
    public static LiverWidget newInstance() {
        return new LiverWidget();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment, storing view.
        v = inflater.inflate(R.layout.fragment_liver_widget, container, false);
        return v;
    }

    // Populate spinners with related information.
    private void initSpinners() {

        // Retrieve references to spinners.
        Spinner drinkSpinner = (Spinner) v.findViewById(R.id.drinkSpinner);
        Spinner volumeSpinner = (Spinner) v.findViewById(R.id.volumeSpinner);
        Spinner percentageSpinner = (Spinner) v.findViewById(R.id.percentageSpinner);

        /* Populate spinner options with correct localization. */

        // Drink spinner listener (one instance, anonymous).
        drinkSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    // TODO: Phil, fill case statements appropriately - have commented example.
                    case 0:
                        // volumeSpinner.setSelection(int);
                        break;
                }
            }
        });
        ArrayAdapter<String> drinkAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item,
                UtilityManager.getContentLoader(getContext()).getInfoTextAsList(SECTION, "drinks", ","));
        // Set style.
        drinkAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Attach adapter.
        drinkSpinner.setAdapter(drinkAdapter);

        // Volume spinner listener (one instance, anonymous).
        volumeSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    // TODO: Phil, fill case statements appropriately - have commented example.
                    case 0:
                        // Perform conversion and set name.
                }
            }
        });

    }

}
