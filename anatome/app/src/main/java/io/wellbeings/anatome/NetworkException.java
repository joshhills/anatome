package io.wellbeings.anatome;

/**
 * Provide a bespoke, named exception to
 * aid code clarity.
 *
 * @author Team WellBeings - Callum
 */
public class NetworkException extends Exception{
        public NetworkException() { super(); }
        public NetworkException(String message) { super(message); }
        public NetworkException(String message, Throwable cause) { super(message, cause); }
        public NetworkException(Throwable cause) { super(cause); }
}
