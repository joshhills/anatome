package io.wellbeings.anatome;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PreambleLanguage.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class PreambleLanguage extends Fragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;
    View view;

    public PreambleLanguage() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_preamble_language, container, false);
        ((ImageButton) view.findViewById(R.id.langBtnEnglish)).setOnClickListener(this);
        ((ImageButton) view.findViewById(R.id.langBtnFrench)).setOnClickListener(this);
        ((ImageButton) view.findViewById(R.id.langBtnSpanish)).setOnClickListener(this);

        return view;

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

    @Override
    public void onClick(View v) {

        switch(v.getId()) {
            case R.id.langBtnEnglish:
                System.out.println("English!");
                break;
            case R.id.langBtnFrench:
                System.out.println("French!");
                break;
            case R.id.langBtnSpanish:
                System.out.println("Spanish!");
                break;
        }

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
