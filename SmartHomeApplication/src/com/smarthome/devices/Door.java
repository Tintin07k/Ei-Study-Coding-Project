package com.smarthome.devices;

import java.util.logging.Logger;
import java.util.Objects;

/**
 * The {@code Door} class represents a smart home Door device.
 * It implements the {@link Device} interface and provides methods to control and query the door's status.
 */
public class Door extends AbstractDevice {

    private String status;

    private static final Logger logger = Logger.getLogger(Door.class.getName());

    /**
     * Constructs a new {@code Door} object with the specified id, type, and status.
     *
     * @param id     the unique identifier for the door
     * @param type   the type of the device
     * @param status the initial status of the door (e.g., "locked")
     * @throws IllegalArgumentException if invalid values are passed
     */
    public Door(int id, String type, String status) {
    	super(id,type);
        this.status = status;
        validateParameters(status);
        logger.info("Door " + id + " of type " + type + " initialized with status: " + status);
    }

    /**
     * Validates constructor parameters to ensure defensive programming.
     */
    private void validateParameters(String status) {
        if (status == null || status.isEmpty()) {
            throw new IllegalArgumentException("Door status cannot be null or empty.");
        }
    }

    /**
     * Sets the status of the door.
     *
     * @param status the new status of the door (e.g., "locked" or "unlocked")
     * @throws IllegalArgumentException if status is invalid
     */
    public void setStatus(String status) {
        if (status == null || status.isEmpty()) {
            throw new IllegalArgumentException("Invalid status value.");
        }
        this.status = status;
        logger.info("Door " + id + " status updated to: " + status);
    }

    /**
     * Turns the door on, which in the case of a door, could mean unlocking.
     */
    @Override
    public void turnOn(int id) {
        if (this.id == id) {
            this.setStatus("unlocked");
            System.out.println("Door " + id + " is Unlocked.");
            logger.info("Door " + id + " turned On (unlocked).");
        }
    }

    /**
     * Turns the door off, which could mean locking it.
     */
    @Override
    public void turnOff(int id) {
        if (this.id == id) {
            this.setStatus("locked");
            System.out.println("Door " + id + " is Locked.");
            logger.info("Door " + id + " turned Off (locked).");
        }
    }

    /**
     * Returns the type of device.
     */
    @Override
    public String deviceType() {
        return this.type;
    }

    /**
     * Returns the current status of the door.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Returns the unique ID of the door.
     */
    public int getId() {
        return id;
    }

    /**
     * Updates the door's state or status based on a message received from the central hub (Observer Pattern).
     *
     * @param message the message received by the device
     */
    @Override
    public void update(String message) {
        logger.info("Received update for Door " + getId() + ": " + message);
        // Implement response based on message, e.g., unlocking the door if instructed
    }

    /**
     * Provides equality check based on door ID.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Door)) return false;
        Door door = (Door) o;
        return id == door.id;
    }

    /**
     * Generates a hash code based on door ID.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Provides a string representation of the door's state.
     */
    @Override
    public String toString() {
        return "Door{id=" + id + ", type='" + type + "', status='" + status + "'}";
    }
}