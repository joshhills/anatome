package io.wellbeings.anatome;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.io.IOException;

public class Preamble extends AppCompatActivity implements Section, PreambleLanguage.OnFragmentInteractionListener, PreambleCarousel.OnFragmentInteractionListener {

    // Declare the section meta-information.
    private final String sectionName = "brain";

    // Store the section's content loader.
    ContentLoader cLoad = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Set the correct view.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preamble);

        // Attempt to initiate content loading.
        try {
            cLoad = new ContentLoader(getResources().openRawResource(R.raw.content),
                    getResources().openRawResource(R.raw.contentschema));
        } catch(IOException e) {
            // TODO: Provide visual prompt to utility failure.
        }

    }

    /**
     * Fill the text elements of the page with content from local '.xml' file,
     * based on user preferences.
     *
     * @return  Status of function - whether here were errors.
     */
    public STATUS populateContent() {

        /*
         * Here, you would grab each text element and call 'setText'
         * passing cLoad._____("xpath/to/correct/node") or similar
         * ContentLoader functions as arguments.
         *
         */

        return STATUS.SUCCESS;

    }

    public void onFragmentInteraction(Uri uri){
        //you can leave it empty
    }

}