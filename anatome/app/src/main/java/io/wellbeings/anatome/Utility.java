package io.wellbeings.anatome;

import java.io.IOException;

import org.xml.sax.SAXException;

/**
 * This interface provides a common branch
 * to aggregate utility classes under a
 * start-up routine.
 * 
 * @author Team WellBeings - Josh
 * @version 1.0
 */
interface Utility {
	
	// Each utility will load and close.
	public STATUS initialize() throws IOException, NetworkException;
	public STATUS shutdown();
	
	// Each utility will log its state.
	public STATUS getState();
	
}