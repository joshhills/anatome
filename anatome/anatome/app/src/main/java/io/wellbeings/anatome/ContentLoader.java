package io.wellbeings.anatome;

import android.content.Context;
import android.util.Log;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * Provide specific access to main content
 * file, with bespoke functions.
 *
 * @author Team WellBeings - Josh
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

    /**
     * Get the title descriptor for a section.
     *
     * @param sectionName   The name of the section currently active.
     * @param elementID     The element for which to retrieve a title.
     * @return              The content of the title.
     */
    public String getHeaderText(String sectionName, String elementID) {
        return getNodeContentWithXPath("application/content[@lang='"
                + UtilityManager.getUserUtility(ctx).getLanguage()
                + "']/section[@name='" +
                sectionName + "']/headers/header[@id='" + elementID + "']");

    }

    /**
     * Get the informative content of a subsection.
     *
     * @param sectionName   The name of the section currently active.
     * @param infoID        The element for which to retrieve information.
     * @return              The informative content.
     */
    public String getInfoText(String sectionName, String infoID) {

        return getNodeContentWithXPath("application/content[@lang='"
                + UtilityManager.getUserUtility(ctx).getLanguage()
                + "']/section[@name='"
                + sectionName + "']/information[@id='" + infoID + "']");

    }

    /**
     * Retrieve useful links of a section.
     *
     * @param sectionName   The name of the section currently active.
     * @return              A string containing documented links as HTML.
     */
    public String getLinks(String sectionName) {

        // Encode as HMTL for easy formatting.
        String ln = "<html>";

        // Retrieve all links.
        NodeList links = getNodesWithXPath("application/content[@lang='"
                + UtilityManager.getUserUtility(ctx).getLanguage()
                + "']/section[@name='"
                + sectionName + "']/links/link");

        // Add each link to a text string formatted as HTML, trimming content.
        for(int i = 0; i < links.getLength(); i++) {
            ln += "<a href=\"" + links.item(i).getChildNodes().item(3).getTextContent().trim()
                + "\">" + links.item(i).getChildNodes().item(1).getTextContent().trim() + "</a><br />";
        }

        // Close tag and return.
        return ln + "</html>";

    }

    /**
     * Retrieve informative content from node as list of strings.
     *
     * @param sectionName   The name of the section currently active.
     * @param infoID        The element for which to retrieve information.
     * @param delim         The delimiter by which to separate elements.
     * @return              The informative content.
     */
    public List<String> getInfoTextAsList(String sectionName, String infoID, String delim) {

        // Retrieve formatting.
        String it = getNodeContentWithXPath("application/content[@lang='"
                    + UtilityManager.getUserUtility(ctx).getLanguage()
                    + "']/section[@name='"
                    + sectionName + "']/information[@id='" + infoID + "']");

        // Trim, split by delimiter and return as array.
        return Arrays.asList(it.trim().split(delim));

    }

}
