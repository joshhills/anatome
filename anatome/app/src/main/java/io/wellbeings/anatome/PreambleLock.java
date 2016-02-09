package io.wellbeings.anatome;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

        // Inflate the layout for this fragment
        return view;
    }

    private void populateContent() {

        ((TextView) view.findViewById(R.id.preamble_header_lock))
                .setText(Preamble.cLoad.getNodeContentWithXPath(
                        "application/content[@lang='en']/section[@name='preamble']/headers/header[@id='lock']"));

        ((TextView) view.findViewById(R.id.preamble_information_lock))
                .setText(Preamble.cLoad.getNodeContentWithXPath(
                        "application/content[@lang='en']/section[@name='preamble']/information[@id='lock']"));

    }

}
