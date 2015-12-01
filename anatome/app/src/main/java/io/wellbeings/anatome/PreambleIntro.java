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

    private OnFragmentInteractionListener mListener;

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
        // Inflate the layout for this fragment
        return view;
    }

    private void populateContent() {


        ((TextView) view.findViewById(R.id.preamble_header_intro))
                .setText(Preamble.cLoad.getNodeContentWithXPath(
                        "application/content[@lang='en']/section[@name='preamble']/headers/header[@id='intro']"));

        ((TextView) view.findViewById(R.id.preamble_information_intro))
                .setText(Preamble.cLoad.getNodeContentWithXPath(
                        "application/content[@lang='en']/section[@name='preamble']/information[@id='intro']"));

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
