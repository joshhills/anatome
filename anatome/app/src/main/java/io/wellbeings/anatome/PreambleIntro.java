package io.wellbeings.anatome;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Introduce the user to the application,
 * display vital disclaimer.
 *
 * @author Team WellBeings - Josh
 */
public class PreambleIntro extends Fragment {

    // Store the view for code clarity.
    View view;

    /* Necessary lifecycle methods. */

    public PreambleIntro() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_preamble_intro, container, false);

        initGUI();

        return view;

    }

    private void initGUI() {

        // Set the textual content of elements.

        ((TextView) view.findViewById(R.id.preamble_header_intro))
                .setText(UtilityManager.getContentLoader(getContext()).getHeaderText("preamble", "intro"));

        ((TextView) view.findViewById(R.id.preamble_information_intro))
                .setText(UtilityManager.getContentLoader(getContext()).getInfoText("preamble", "intro"));

        ((TextView) view.findViewById(R.id.preamble_information_disclaimer))
                .setText(UtilityManager.getContentLoader(getContext()).getInfoText("preamble", "disclaimer"));

    }

}
