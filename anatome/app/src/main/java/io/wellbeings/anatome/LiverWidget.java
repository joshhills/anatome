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
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class LiverWidget extends Fragment implements Widget {

    /* Useful private variables. */
    public double units;
    // Store name of section.
    private final String SECTION = "liver";
    // Store view object for UI manipulation.
    private View v;

    /* Necessary lifecycle methods. */
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

        // Initialise the GUI elements with necessary information.
        initGUI();

        return v;

    }

    // Attach functionality and relevant information to UI elements.
    private void initGUI() {
        //TODO: this is the wrong place to initialise this variable I'll fix it later
        units = 0;
        // Retrieve references to spinners.
        final Spinner drinkSpinner = (Spinner) v.findViewById(R.id.drinkSpinner);
        final Spinner volumeSpinner = (Spinner) v.findViewById(R.id.volumeSpinner);
        final Spinner percentageSpinner = (Spinner) v.findViewById(R.id.percentageSpinner);
        //get button
        Button addButton = (Button) v.findViewById(R.id.addButton);

        /* Populate spinner options with correct localization. */

        // Drink spinner listener (one instance, anonymous).
        drinkSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    // TODO: Phil, fill case statements appropriately - have commented example.
                    case 0://beer
                        volumeSpinner.setSelection(0);//pint
                        percentageSpinner.setSelection(7);//4%
                        break;
                    case 1://cider
                        volumeSpinner.setSelection(0);//pint
                        percentageSpinner.setSelection(7);//4%
                        break;
                    case 2://wine
                        volumeSpinner.setSelection(5);//large wine glass
                        percentageSpinner.setSelection(3);//12%
                        break;
                    case 3://Spirit
                        volumeSpinner.setSelection(2);//single measure
                        percentageSpinner.setSelection(0);//40%
                        break;
                    case 4://alcopop
                        volumeSpinner.setSelection(1);//1/2 pint
                        percentageSpinner.setSelection(7);//4%
                        break;
                    case 5://sours
                        volumeSpinner.setSelection(2);//single measure
                        percentageSpinner.setSelection(2);//15%
                        break;
                    /*commented out because i dont think this part is needed
                    default://other
                        volumeSpinner.setSelection(7);//other
                        percentageSpinner.setSelection(8);//other
                        break;
                        */
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        ArrayAdapter<String> drinkAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item,
                UtilityManager.getContentLoader(getContext()).getInfoTextAsList(SECTION, "drinks", ","));
        // Set style.
        drinkAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Attach adapter.
        drinkSpinner.setAdapter(drinkAdapter);

        // Volume spinner listener (one instance, anonymous).
        volumeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO: Phil, fill case statements appropriately
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ArrayAdapter<String> volumeAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item,
                UtilityManager.getContentLoader(getContext()).getInfoTextAsList(SECTION, "volume", ","));
        // Set style.
        volumeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Attach adapter.
        volumeSpinner.setAdapter(volumeAdapter);

        // percentage spinner listener (one instance, anonymous).
        percentageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    // TODO: Phil, fill case statements appropriately
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ArrayAdapter<String> percentageAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item,
                UtilityManager.getContentLoader(getContext()).getInfoTextAsList(SECTION, "percentage", ","));
        // Set style.
        percentageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Attach adapter.
        percentageSpinner.setAdapter(percentageAdapter);

        addButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                int volume;
                double percentage;
                //turn volume into ml
                switch(volumeSpinner.getSelectedItemPosition()){
                    case 0://pint
                        volume = 568;
                        break;
                    case 1://half pint
                        volume = 284;
                        break;
                    case 2://single
                        volume = 25;
                        break;
                    case 3://double
                        volume = 50;
                        break;
                    case 4://treble
                        volume = 75;
                        break;
                    case 5://large wine
                        volume = 250;
                        break;
                    case 6://small wine
                        volume = 125;
                        break;
                    default://other
                        volume = 0;
                        break;
                }
                //get the percentage (can probably do something cleverer than a switch in the future)
                switch(percentageSpinner.getSelectedItemPosition()){
                    case 0:
                        percentage = 40;
                        break;
                    case 1:
                        percentage = 37.5;
                        break;
                    case 2:
                        percentage = 15;
                        break;
                    case 3:
                        percentage = 12;
                        break;
                    case 4:
                        percentage = 10;
                        break;
                    case 5:
                        percentage = 7.5;
                        break;
                    case 6:
                        percentage = 5;
                        break;
                    case 7:
                        percentage = 4;
                        break;
                    default:
                        percentage = 0;
                        break;
                }

                units += (percentage*volume)/1000;//work out the units

                updateUnits();
            }
        });
        
    }

    private void updateUnits(){
        //get text view
        final TextView unitDisplay = (TextView) v.findViewById(R.id.unitDisplay);
        // the word units needs to go in the xml so we can do different languages
        if(units != 1) {
            unitDisplay.setText(String.format("%.1f",units) + " Units");
        }
        else{
            unitDisplay.setText(String.format("%.1f",units) + " Unit");
        }
    }
}
