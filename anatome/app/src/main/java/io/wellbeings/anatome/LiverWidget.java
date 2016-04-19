package io.wellbeings.anatome;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.TextView;
import android.content.DialogInterface;

/**
 * Interactive subsection hinging on body part
 * provides a unit calculator to monitor
 * alcohol consumption of user.
 *
 * @author Team WellBeings - Phil, Josh
 */
public class LiverWidget extends Fragment implements Widget {

    /* Useful private variables. */

    // Store name of section.
    private final String SECTION = "liver";
    // Store view object for UI manipulation.
    private View v;
    //variables for working out units
    private int volume;//volume in ml
    private double percentage;//percentage of alcohol


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
        final Spinner drinkSpinner = (Spinner) v.findViewById(R.id.liver_drink_spinner);
        final Spinner volumeSpinner = (Spinner) v.findViewById(R.id.liver_volume_spinner);
        final Spinner percentageSpinner = (Spinner) v.findViewById(R.id.liver_percentage_spinner);

        // Get buttons
        Button addButton = (Button) v.findViewById(R.id.liver_add_button);
        Button clearButton = (Button) v.findViewById(R.id.liver_clear_button);
        Button undoButton = (Button) v.findViewById(R.id.liver_undo_button);

        updateDisplay();

        // Get the text warning.
        ((TextView) v.findViewById(R.id.liver_threshold_warning)).setText(
                UtilityManager.getContentLoader(getContext()).getInfoText(SECTION, "threshold-warning")
        );

        /* Populate spinner options with correct localization. */

        drinkSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Intuitively fill the next options based on initial selection.
                switch (position) {
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

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        ArrayAdapter<String> drinkAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item,
                UtilityManager.getContentLoader(getContext()).getInfoTextAsList(SECTION, "drinks", ","));
        // Set style.
        drinkAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Attach adapter.
        drinkSpinner.setAdapter(drinkAdapter);

        ArrayAdapter<String> volumeAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item,
                UtilityManager.getContentLoader(getContext()).getInfoTextAsList(SECTION, "volume", ","));
        // Set style.
        volumeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Attach adapter.
        volumeSpinner.setAdapter(volumeAdapter);

        volumeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 7) {
                    otherDialog("enter volume", R.layout.dialog_other_option, 1);
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

        percentageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 8){
                    otherDialog("enter percentage", R.layout.dialog_other_option, 0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Attach functionality to the add button.
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform unit conversion to get volume.
                int[] volumeArray = {568, 284, 25, 50, 75, 250, 125};
                int pos = volumeSpinner.getSelectedItemPosition();
                if(pos < 7) {
                    volume = volumeArray[pos];
                }

                // Get percentage.
                if(percentageSpinner.getSelectedItemPosition() < 8) {
                    percentage = Integer.parseInt(percentageSpinner.getSelectedItem().toString()
                            .replace("%", ""));
                }

                addDrink((percentage * volume) / 1000);

                updateDisplay();
            }
        });

        // Attach functionality to the clear button.
        clearButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                setUnits(0);
                updateDisplay();
            }
        });

        undoButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                UtilityManager.getUserUtility(getActivity()).removeDrink();
                updateDisplay();
            }
        });

    }

    /* Visual methods. */

    // Update the display to reflect changes in unit calculation.
    private void updateDisplay(){

        // Retrieve references to text elements.
        final TextView unitDisplay = (TextView) v.findViewById(R.id.liver_unit_display);
        TextView warning = (TextView) v.findViewById(R.id.liver_warning);

        // Update unit display.
        //TODO: the word "units" needs to go in the xml so we can do different languages
        if(getUnits() != 1) {
            unitDisplay.setText(String.format("%.1f",getUnits()) + " Units");
        }
        else{
            unitDisplay.setText(String.format("%.1f",getUnits()) + " Unit");
        }

        // Update warnings.
        TextView thresholdWarning = (TextView) v.findViewById(R.id.liver_threshold_warning);
        if(getUnits() == 0){
            warning.setText(UtilityManager.getContentLoader(getContext()).getInfoText(SECTION, "threshold-1"));
            thresholdWarning.setVisibility(View.GONE);
        }
        else if(getUnits() < 2) {
            warning.setText(UtilityManager.getContentLoader(getContext()).getInfoText(SECTION, "threshold-2"));
            thresholdWarning.setVisibility(View.GONE);
        }
        else if(getUnits() < 5) {
            warning.setText(UtilityManager.getContentLoader(getContext()).getInfoText(SECTION, "threshold-3"));
            thresholdWarning.setVisibility(View.VISIBLE);
        }
        else if(getUnits() < 10) {
            warning.setText(UtilityManager.getContentLoader(getContext()).getInfoText(SECTION, "threshold-4"));
            thresholdWarning.setVisibility(View.VISIBLE);
        }
        else if(getUnits() < 15) {
            warning.setText(UtilityManager.getContentLoader(getContext()).getInfoText(SECTION, "threshold-5"));
            thresholdWarning.setVisibility(View.VISIBLE);
        }
        else{
            warning.setText(UtilityManager.getContentLoader(getContext()).getInfoText(SECTION, "threshold-6"));
            thresholdWarning.setVisibility(View.VISIBLE);
        }
    }

    private void pushVisualTransition(int state) {

        

    }


    private void otherDialog(String title, int layout,final int percentageOrVolume/*0 for percentage 1 for volume (temporary name)*/){


        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View myView = inflater.inflate(layout, null);
        builder.setView(myView)
                .setPositiveButton("Enter", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //get text out of text box
                        EditText myTextBox = (EditText) myView.findViewById(R.id.liver_edit_text);
                        //get text from edit text
                        String number = myTextBox.getText().toString();
                        //Try-catch structure unnecessary as the input is resticted by the xml document (dialog_other_option).
                        double input = Double.parseDouble(number);
                        //checks whether to set volume or percentage
                        if(percentageOrVolume == 0){//set percentage
                            if(input > 100){
                                percentage = 100;
                            }
                            else{
                                percentage = input;
                            }
                        }
                        else if(percentageOrVolume == 1){
                            volume = (int)input;
                        }

                        //close dialog box
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private double getUnits(){
        return UtilityManager.getUserUtility(getActivity()).getUnits();
    }

    private void setUnits(double units){
        UtilityManager.getUserUtility(getActivity()).setUnits(units);
    }

    private void addDrink(double units){
        UtilityManager.getUserUtility(getActivity()).addDrink(units);
    }
}