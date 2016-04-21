package io.wellbeings.anatome;

/**
 * Created by Callum on 21/04/16.
 */
public class NetworkException extends Exception{
        public NetworkException() { super(); }
        public NetworkException(String message) { super(message); }
        public NetworkException(String message, Throwable cause) { super(message, cause); }
        public NetworkException(Throwable cause) { super(cause); }
}
