package com.smarthome.devices;

import java.util.logging.Logger;

import com.smarthome.exceptions.UnsupportedActionException;

/**
 * The {@code AbstractDevice} class provides a common base for all smart devices
 * such as lights, doors, and thermostats. It holds the properties {@code id} and {@code type},
 * which are common to all devices, and implements the {@link Device} interface.
 */
public abstract class AbstractDevice implements Device {
    
    protected final int id;
    protected final String type;

    protected static final Logger logger = Logger.getLogger(AbstractDevice.class.getName());

    /**
     * Constructor to initialize common properties of devices.
     *
     * @param id   the unique identifier of the device
     * @param type the type of device (e.g., "light", "door", "thermostat")
     * @throws IllegalArgumentException if id or type is invalid
     */
    public AbstractDevice(int id, String type) {
        if (id <= 0) {
            throw new IllegalArgumentException("Device ID must be a positive integer.");
        }
        if (type == null || type.isEmpty()) {
            throw new IllegalArgumentException("Device type cannot be null or empty.");
        }
        this.id = id;
        this.type = type;
        logger.info("Initialized device ID: " + id + ", Type: " + type);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String deviceType() {
        return type;
    }

    @Override
    public abstract void turnOn(int id);

    @Override
    public abstract void turnOff(int id);

    @Override
    public abstract void update(String message) throws UnsupportedActionException;
}