package io.wellbeings.anatome;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PreambleIntro extends Fragment {

    View view;

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

        populateContent();

        return view;
    }

    private void populateContent() {

        ((TextView) view.findViewById(R.id.preamble_header_intro))
                .setText(UtilityManager.getContentLoader(getContext()).getHeaderText("preamble", "intro"));

        ((TextView) view.findViewById(R.id.preamble_information_intro))
                .setText(UtilityManager.getContentLoader(getContext()).getInfoText("preamble", "intro"));

    }

}
