package com.smarthome.exceptions;

/**
 * The {@code InvalidTriggerException} class represents a custom exception for invalid trigger conditions
 * in the smart home system.
 */
public class InvalidTriggerException extends Exception {
    
    private static final long serialVersionUID = 1L;

	/**
     * Constructs an InvalidTriggerException with the specified detail message.
     *
     * @param message the detail message to be saved for later retrieval by the {@link #getMessage()} method.
     */
    public InvalidTriggerException(String message) {
        super(message);
    }
}