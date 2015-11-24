package io.wellbeings.anatome;

import java.io.IOException;

/**
 * Provide specific access to main content
 * file, with bespoke functions.
 * 
 * @author Team WellBeings - Josh
 * @version 1.0
 */
public class ContentLoader extends XMLUtility {
	
	public ContentLoader() throws IOException {
		
		super("J:\\University Work\\Stage 2\\CSC2021 Software Engineering\\Eclipse Workspace\\anatome\\src\\content.xml",
				"J:\\University Work\\Stage 2\\CSC2021 Software Engineering\\Eclipse Workspace\\anatome\\src\\content-schema.xsd");
		
	}
	
}