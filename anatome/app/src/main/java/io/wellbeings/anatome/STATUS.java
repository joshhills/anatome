package io.wellbeings.anatome;

/**
 * Enum describing utility statuses
 * provides cleaner route to
 * defensible program-flow.
 * 
 * @author Team WellBeings - Josh
 * @version 1.0
 */
public enum STATUS {

	/* List of error statuses common to application. */
	FAIL(-1, STATUS_TYPE.GENERIC, "FAIL: Utility failed unexpectedly."),
	NONE(0, STATUS_TYPE.GENERIC, "NONE: Utility is inactive."),
	SUCCESS(1, STATUS_TYPE.GENERIC, "SUCCESS: Operation succeeded."),
	ACTIVE(2, STATUS_TYPE.GENERIC, "ACTIVE: Utility is active."),
    NONETWORK(-1, STATUS_TYPE.GENERIC, "No Network Connection"),
	XML_INVALID_SCHEMA(4, STATUS_TYPE.XML, "XML: Schema supplied is invalid."),
	XML_INVALID(5, STATUS_TYPE.XML, "XML: XML supplied does not conform to schema.");

	/* List of error ranges for further clarification. */
	enum STATUS_TYPE { GENERIC, XML, SQL }
	
	// Declare values stored by type.
    private int code;
    private STATUS_TYPE range;
    private String message;

    // Private constructor constrains enumeration definitions.
    private STATUS(int code, STATUS_TYPE range, String message) {
        this.code = code;
        this.range = range;
        this.message = message;
    }

    // Allow inquiry of just the code reference.
    public int getCode() {
        return code;
    }
    
    // Allow inquiry to the classification of status.
    public STATUS_TYPE getStatusType() {
    	return range;
    }
    
    // Allow inquiry of just the status descriptor.
    public String getMessage() {
    	return message;   	
    }
    
    // Override toString to provide a more helpfully formatted status.
    @Override
    public String toString() {
    	return "Status " + code + ": " + message;
    }

}