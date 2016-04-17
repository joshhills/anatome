package io.wellbeings.anatome;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


public class PreambleName extends Fragment {

    private static View view;

    public PreambleName() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_preamble_name, container, false);

        populateContent();

        // Inflate the layout for this fragment
        return view;
    }

    // Initialise GUI elements.
    private void populateContent() {

        /* Tutorialised information. */

        ((TextView) view.findViewById(R.id.preamble_header_name))
                .setText(UtilityManager.getContentLoader(getContext()).getHeaderText("preamble", "name"));

        ((TextView) view.findViewById(R.id.preamble_information_name))
                .setText(UtilityManager.getContentLoader(getContext()).getInfoText("preamble", "name"));

        /* Input types. */

        ((EditText) view.findViewById(R.id.preamble_user_name)).setHint(
                UtilityManager.getContentLoader(getContext()).getInfoText("preamble", "username-hint")
        );
        ((EditText) view.findViewById(R.id.preamble_email)).setHint(
                UtilityManager.getContentLoader(getContext()).getInfoText("preamble", "email-hint")
        );

    }

    /* Get crucial variables within fragment. */

    public static String getCurrentName() {
        return ((EditText) view.findViewById(R.id.preamble_user_name)).getText().toString();
    }

    public static String getCurrentEmail() {
        return ((EditText) view.findViewById(R.id.preamble_email)).getText().toString();
    }

}
