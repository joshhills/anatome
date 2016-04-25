package io.wellbeings.anatome;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.TextView;
import android.content.DialogInterface;
import com.bumptech.glide.Glide;

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

    /*
     * Store whether or not the user has been warned,
     * so as not to ignore them.
     */
    private boolean shouldBuzz = true;

    /* Store visual elements for code clarity. */

    private int[] liverGraphics = {
            R.drawable.liver_calculator_graphic_1,
            R.drawable.liver_calculator_graphic_2,
            R.drawable.liver_calculator_graphic_3,
            R.drawable.liver_calculator_graphic_4,
            R.drawable.liver_calculator_graphic_5,
            R.drawable.liver_calculator_graphic_6
    };

    /* Necessary lifecycle methods. */

    public LiverWidget() {}

    public static LiverWidget newInstance() {
        return new LiverWidget();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment, storing view.
        v = inflater.inflate(R.layout.fragment_liver_widget, container, false);

        // Initialise the GUI elements with necessary information.
        initGUI();

        attachListeners();

        return v;

    }

    // Attach functionality and relevant information to UI elements.
    public void initGUI() {

        // Retrieve references to spinners.
        final Spinner drinkSpinner = (Spinner) v.findViewById(R.id.liver_drink_spinner);
        final Spinner volumeSpinner = (Spinner) v.findViewById(R.id.liver_volume_spinner);
        final Spinner percentageSpinner = (Spinner) v.findViewById(R.id.liver_percentage_spinner);

        // Set the text warning content.
        ((TextView) v.findViewById(R.id.liver_threshold_warning)).setText(
                UtilityManager.getContentLoader(getContext()).getInfoText(SECTION, "threshold-warning")
        );

        /* Populate spinner options with correct localization. */

        // Drinks spinner.
        ArrayAdapter<String> drinkAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item,
                UtilityManager.getContentLoader(getContext()).getInfoTextAsList(SECTION, "drinks", ","));
        // Set style.
        drinkAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Attach adapter.
        drinkSpinner.setAdapter(drinkAdapter);

        // Volume spinner.
        ArrayAdapter<String> volumeAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item,
                UtilityManager.getContentLoader(getContext()).getInfoTextAsList(SECTION, "volume", ","));
        // Set style.
        volumeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Attach adapter.
        volumeSpinner.setAdapter(volumeAdapter);

        // Percent spinner.
        ArrayAdapter<String> percentageAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item,
                UtilityManager.getContentLoader(getContext()).getInfoTextAsList(SECTION, "percentage", ","));
        // Set style.
        percentageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Attach adapter.
        percentageSpinner.setAdapter(percentageAdapter);

        // Push the first graphic.
        updateDisplay();

    }

    public void attachListeners() {

        /* Attach smart-filtering functionality to spinner elements. */

        // Retrieve references to spinners.
        final Spinner drinkSpinner = (Spinner) v.findViewById(R.id.liver_drink_spinner);
        final Spinner volumeSpinner = (Spinner) v.findViewById(R.id.liver_volume_spinner);
        final Spinner percentageSpinner = (Spinner) v.findViewById(R.id.liver_percentage_spinner);

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

        volumeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 7) {
                    otherDialog(
                            UtilityManager.getContentLoader(getContext()).getButtonText("enter-volume"),
                            R.layout.dialog_other_option,
                            1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        percentageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 8) {
                    otherDialog(
                            UtilityManager.getContentLoader(getContext()).getButtonText("enter-percentage"),
                            R.layout.dialog_other_option,
                            0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        /* Attach functionality to calculator buttons. */

        // Get buttons
        Button addButton = (Button) v.findViewById(R.id.liver_add_button);
        final Button clearButton = (Button) v.findViewById(R.id.liver_clear_button);
        Button undoButton = (Button) v.findViewById(R.id.liver_undo_button);

        // Attach functionality to the add button.
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform unit conversion to get volume.
                int[] volumeArray = {568, 284, 25, 50, 75, 250, 125};
                int pos = volumeSpinner.getSelectedItemPosition();
                if (pos < 7) {
                    volume = volumeArray[pos];
                }

                // Get percentage.
                if (percentageSpinner.getSelectedItemPosition() < 8) {
                    percentage = Double.parseDouble(percentageSpinner.getSelectedItem().toString()
                            .replace("%", ""));
                }

                addDrink((percentage * volume) / 1000);

                updateDisplay();
            }
        });

        // Attach functionality to the clear button.
        clearButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                // Clear units.
                setUnits(0);
                // Reset buzzing.
                shouldBuzz = true;
                updateDisplay();
            }
        });

        // Attach functionality to the undo button.
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

        // Retrieve references to text elements that will need changing.
        final TextView unitDisplay = (TextView) v.findViewById(R.id.liver_unit_display);
        final TextView warning = (TextView) v.findViewById(R.id.liver_warning);
        final TextView thresholdWarning = (TextView) v.findViewById(R.id.liver_threshold_warning);

        // Update unit display.
        if(getUnits() == 1) {
            unitDisplay.setText(String.format("%.1f",getUnits()) + " "
                + UtilityManager.getContentLoader(getContext()).getButtonText("unit"));
        }
        else {
            unitDisplay.setText(String.format("%.1f",getUnits()) + " "
                    + UtilityManager.getContentLoader(getContext()).getButtonText("units"));
        }

        /*
         * Update advice and corresponding graphic
         * in if-else tree to abide by thresholds.
         */
        if(getUnits() == 0){
            warning.setText(UtilityManager.getContentLoader(getContext()).getInfoText(SECTION, "threshold-1"));
            thresholdWarning.setVisibility(View.GONE);
            pushVisualTransition(1);
        }
        else if(!(getUnits() >= 2)) {
            warning.setText(UtilityManager.getContentLoader(getContext()).getInfoText(SECTION, "threshold-2"));
            thresholdWarning.setVisibility(View.GONE);
            pushVisualTransition(2);
        }
        else if(!(getUnits() >= 3)) {
            warning.setText(UtilityManager.getContentLoader(getContext()).getInfoText(SECTION, "threshold-3"));
            thresholdWarning.setVisibility(View.GONE);
            pushVisualTransition(2);
        }
        else if(!(getUnits() >= 6)) {
            warning.setText(UtilityManager.getContentLoader(getContext()).getInfoText(SECTION, "threshold-4"));
            thresholdWarning.setVisibility(View.VISIBLE);
            pushVisualTransition(3);

            // Vibrate as this is a key threshold.
            if(shouldBuzz) {
                ((Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE)).vibrate(200);
                shouldBuzz = false;
            }

        }
        else if(!(getUnits() >= 10)) {
            warning.setText(UtilityManager.getContentLoader(getContext()).getInfoText(SECTION, "threshold-5"));
            thresholdWarning.setVisibility(View.VISIBLE);
            pushVisualTransition(4);
        }
        else if(!(getUnits() >= 16)) {
            warning.setText(UtilityManager.getContentLoader(getContext()).getInfoText(SECTION, "threshold-6"));
            thresholdWarning.setVisibility(View.VISIBLE);
            pushVisualTransition(5);
        }
        else if(getUnits() > 16){
            warning.setText(UtilityManager.getContentLoader(getContext()).getInfoText(SECTION, "threshold-7"));
            thresholdWarning.setVisibility(View.VISIBLE);
            pushVisualTransition(6);
        }

    }

    /**
     * Change the graphical display to reflect
     * the user's interaction with the calculator.
     *
     * @param state The state of transition.
     */
    private void pushVisualTransition(int state) {

        // Load the correct graphic corresponding to the text threshold.
        Glide.with(this)
                .load(liverGraphics[state - 1])
                .dontAnimate()
                .into((ImageView) v.findViewById(R.id.liver_illustration));

    }

    private void otherDialog(String title, int layout,final int percentageOrVolume/*0 for percentage 1 for volume (temporary name)*/){

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View myView = inflater.inflate(layout, null);
        builder.setView(myView)
                .setPositiveButton(
                        UtilityManager.getContentLoader(getContext()).getButtonText("enter"),
                        new DialogInterface.OnClickListener() {
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