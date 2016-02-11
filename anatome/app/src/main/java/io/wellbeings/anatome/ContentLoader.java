package io.wellbeings.anatome;

import android.content.Context;
import java.io.IOException;
import java.io.InputStream;

/**
 * Provide specific access to main content
 * file, with bespoke functions.
 *
 * @author Team WellBeings - Josh
 * @version 1.0
 */
public class ContentLoader extends XMLUtility {

    /**
     * Constructor is protected; this prevents a component from creating another instance.
     */
    public ContentLoader(Context ctx, InputStream xmlDocument, InputStream xmlSchema) {

        // Call superclass with input streams to initiate utility.
        super(ctx, xmlDocument, xmlSchema);

    }

     /* Provide bespoke file interpolation. */

    public String getHeaderText(String sectionName, String elementID) {
        return getNodeContentWithXPath("application/content[@lang='"
                + UtilityManager.getUserUtility(ctx).getLanguage()
                + "']/section[@name='" +
                sectionName + "']/headers/header[@id='" + elementID + "']");

    }

    public String getInfoText(String sectionName, String infoID) {
        return getNodeContentWithXPath("application/content[@lang='"
                + UtilityManager.getUserUtility(ctx).getLanguage()
                + "']/section[@name='" +
                sectionName + "']/information[@id='" + infoID + "']");

    }


}
