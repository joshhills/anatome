package io.wellbeings.anatome;

/**
 * This interface links Android-specific
 * activity lifecycles to the datamodel to
 * ensure a clean heirarchy.
 * 
 * @author Team WellBeings - Josh
 * @version 1.0
 */
interface Section {
	
	// Each section must provide a way to populate fields with localized values.
	public STATUS populateContent();
	
}