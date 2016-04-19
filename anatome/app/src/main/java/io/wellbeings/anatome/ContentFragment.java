package io.wellbeings.anatome;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.Inflater;

import cz.msebera.android.httpclient.client.cache.Resource;

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
<<<<<<< HEAD
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Context ctx = getContext();
        ArrayList<HashMap<String, String>> commentList;

        DbUtility db = new DbUtility();

        commentList = db.getComments("liver", ctx);

        //now use commentList to fill comments
    }

    @Override
=======
>>>>>>> master
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_content, container, false);
        section = getArguments().getString("section");

        populateContent();
        loadComments();

        return view;
    }

    // Populate frame UI components with local information.
    private void populateContent() {



        /* Style background elements. */

        ((LinearLayout) view.findViewById(R.id.content_container)).setBackgroundColor(
                ContextCompat.getColor(getContext(),
                        UtilityManager.getThemeUtility(getContext()).getColour(section + "_main_bg"))
        );

        /* Sequentially populate and style pre-determined key sections of content. */

        ((TextView) view.findViewById(R.id.content_title)).setText(
                UtilityManager.getContentLoader(getContext()).getHeaderText(section, "title"));
        ((TextView) view.findViewById(R.id.content_title)).setTextColor(ContextCompat.getColor(getContext(),
                        UtilityManager.getThemeUtility(getContext()).getColour(section + "_main_text"))
        );

        ((TextView) view.findViewById(R.id.content_titlecontent)).setText(
                UtilityManager.getContentLoader(getContext()).getInfoText(section, "title"));
        ((TextView) view.findViewById(R.id.content_titlecontent)).setTextColor(ContextCompat.getColor(getContext(),
                        UtilityManager.getThemeUtility(getContext()).getColour(section + "_main_text"))
        );

        ((TextView) view.findViewById(R.id.content_tips)).setText(
                UtilityManager.getContentLoader(getContext()).getHeaderText(section, "tips"));
        ((TextView) view.findViewById(R.id.content_tips)).setTextColor(ContextCompat.getColor(getContext(),
                        UtilityManager.getThemeUtility(getContext()).getColour(section + "_main_text"))
        );

        ((TextView) view.findViewById(R.id.content_tipscontent)).setText(
                UtilityManager.getContentLoader(getContext()).getInfoText(section, "tips"));
        ((TextView) view.findViewById(R.id.content_tipscontent)).setTextColor(ContextCompat.getColor(getContext(),
                        UtilityManager.getThemeUtility(getContext()).getColour(section + "_main_text"))
        );

        ((TextView) view.findViewById(R.id.content_links)).setText(
                UtilityManager.getContentLoader(getContext()).getHeaderText(section, "links"));
        ((TextView) view.findViewById(R.id.content_links)).setTextColor(ContextCompat.getColor(getContext(),
                        UtilityManager.getThemeUtility(getContext()).getColour(section + "_main_text"))
        );

        // Populate links, make them clickable.
        ((TextView) view.findViewById(R.id.content_linkscontent)).setText(Html.fromHtml(
                UtilityManager.getContentLoader(getContext()).getLinks(section)));
        ((TextView) view.findViewById(R.id.content_linkscontent)).setMovementMethod(LinkMovementMethod.getInstance());
        ((TextView) view.findViewById(R.id.content_linkscontent)).setTextColor(ContextCompat.getColor(getContext(),
                        UtilityManager.getThemeUtility(getContext()).getColour(section + "_accent_text"))
        );

        /* Load graphical element. */

        final int resourceId = getResources().getIdentifier(
                section + "_ico", "drawable", getContext().getPackageName()
        );
        ((ImageView) view.findViewById(R.id.content_graphic)).setImageResource(resourceId);


        view.findViewById(R.id.top_section_layout).setBackgroundColor(Color.BLUE);
        view.findViewById(R.id.back).setBackgroundColor(Color.YELLOW);

    }







    // Load custom social aspect of app via comments.
    private void loadComments() {

        // Get outer container.
        LinearLayout ll = (LinearLayout) view.findViewById(R.id.content_comments_container);

        // Retrieve user comments.
        // ArrayList<HashMap<String, String>> commentList =

        /******************/
        // TODO: Remove this test block.
        // comments.put("This is a comment, did you know? I swear, it is! How weird is that, but seriously you should kill yourself.", "Paul Oslow");
        // comments.put("This is a comment, awdgj  af ghat, but serioawdg j fifapwfawf ill yourself.", "Wiidg Ethox");
        // comments.put("This is a comment, did you know? awjf  aiwz gpaw jpw joptjrdhjp sop jjpa.", "Adma Aholdems");
        // comments.put("This is a comment, did you know? I swear, it is! How weird is that, but seriously you should kill yourself.", "Paul Oslow");
        /******************/

        /* Create visual element for every comment.
        for(String comment : comments.keySet()) {

            // Set and style comment content.
            TextView commentView = new TextView(getContext());
            commentView.setText(comment);
            commentView.setTextColor(ContextCompat.getColor(getContext(),
                            UtilityManager.getThemeUtility(getContext()).getColour(section + "_secondary_bg"))
            );
            commentView.setTextSize(18);
            commentView.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            // Add element.
            ll.addView(commentView);

            // Set and style author content.
            TextView authorView = new TextView(getContext());
            authorView.setText("- " + comments.get(comment));
            authorView.setTextColor(ContextCompat.getColor(getContext(),
                            UtilityManager.getThemeUtility(getContext()).getColour(section + "_accent_text"))
            );
            authorView.setTextSize(16);
            authorView.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            // Add element.
            ll.addView(authorView);

            // Add separator with margin.
            View v = new View(getContext());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    5
            );
            lp.setMargins(0, 20, 0, 20);
            v.setLayoutParams(lp);
            v.setBackgroundColor(ContextCompat.getColor(getContext(),
                    UtilityManager.getThemeUtility(getContext()).getColour(section + "_accent_text")));
            ll.addView(v);

<<<<<<< HEAD
        }
=======
        }*/

>>>>>>> master
    }

}