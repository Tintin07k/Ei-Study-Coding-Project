package com.smarthome.exceptions;

/**
 * The {@code UnsupportedActionException} class represents a custom exception for unsupported actions
 * within the Smart Home System.
 */
public class UnsupportedActionException extends Exception {
    private static final long serialVersionUID = 1L; // Added for serialization

    /**
     * Constructs a new UnsupportedActionException with the specified detail message.
     *
     * @param message the detail message which is saved for later retrieval by the {@link #getMessage()} method.
     */
    public UnsupportedActionException(String message) {
        super(message);
    }

    /**
     * Constructs a new UnsupportedActionException with the specified detail message and cause.
     *
     * @param message the detail message which is saved for later retrieval by the {@link #getMessage()} method.
     * @param cause the cause of the exception (which is saved for later retrieval by the {@link #getCause()} method).
     */
    public UnsupportedActionException(String message, Throwable cause) {
        super(message, cause);
    }
}