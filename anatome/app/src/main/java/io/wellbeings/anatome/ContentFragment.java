package io.wellbeings.anatome;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 *  This class handles the loading of section information.
 */
public class ContentFragment extends Fragment implements Widget {

    // Store content view.
    View view;

    // Store section name.
    private String section;

    public ContentFragment() {
        // Required empty public constructor
    }

    public static ContentFragment newInstance() {
        ContentFragment fragment = new ContentFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_content, container, false);
        section = getArguments().getString("section");

        populateContent();

        return view;
    }

    // Populate frame UI components with local information.
    private void populateContent() {

        /* Sequentially populate pre-determined key sections of content. */

        ((TextView) view.findViewById(R.id.title)).setText(
                UtilityManager.getContentLoader(getContext()).getHeaderText(section, "title"));

        ((TextView) view.findViewById(R.id.titlecontent)).setText(
                UtilityManager.getContentLoader(getContext()).getInfoText(section, "title"));

        ((TextView) view.findViewById(R.id.tips)).setText(
                UtilityManager.getContentLoader(getContext()).getHeaderText(section, "tips"));

        ((TextView) view.findViewById(R.id.tipscontent)).setText(
                UtilityManager.getContentLoader(getContext()).getInfoText(section, "tips"));

        ((TextView) view.findViewById(R.id.links)).setText(
                UtilityManager.getContentLoader(getContext()).getHeaderText(section, "links"));

    }

}