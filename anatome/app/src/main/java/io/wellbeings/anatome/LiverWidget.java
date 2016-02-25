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

        // Retrieve references to spinners.
        final Spinner drinkSpinner = (Spinner) v.findViewById(R.id.drinkSpinner);
        final Spinner volumeSpinner = (Spinner) v.findViewById(R.id.volumeSpinner);
        final Spinner percentageSpinner = (Spinner) v.findViewById(R.id.percentageSpinner);

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
                if(position == 7){
                }
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
                    // TODO: Phil, fill case statements appropriately - have commented example.
                    case 0:
                        // percentageSpinner.setSelection(int);
                        break;
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
        
    }

    private int getNumberInput(){
        int i = 0;

        return i;
    }

}
