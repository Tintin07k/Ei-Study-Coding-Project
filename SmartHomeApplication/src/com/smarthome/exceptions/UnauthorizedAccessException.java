package com.smarthome.exceptions;

/**
 * The {@code UnauthorizedAccessException} class represents a custom exception 
 * for unauthorized access attempts in the smart home system.
 */
public class UnauthorizedAccessException extends Exception {
    
    private static final long serialVersionUID = 1L;

	/**
     * Constructs an UnauthorizedAccessException with the specified detail message.
     *
     * @param message the detail message to be saved for later retrieval by the {@link #getMessage()} method.
     */
    public UnauthorizedAccessException(String message) {
        super(message);
    }
}