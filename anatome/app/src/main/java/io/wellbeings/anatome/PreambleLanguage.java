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
 */
public class PreambleLanguage extends Fragment implements View.OnClickListener {

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
    public void onClick(View v) {

        switch(v.getId()) {
            case R.id.langBtnEnglish:
                User.getInstance().setLang("en");
                System.out.println(User.getInstance().getLang());
                break;
            case R.id.langBtnFrench:
                User.getInstance().setLang("fr");
                System.out.println(User.getInstance().getLang());
                break;
            case R.id.langBtnSpanish:
                User.getInstance().setLang("es");
                System.out.println(User.getInstance().getLang());
                break;
        }

    }

}
