package com.smarthome.devices;

import com.smarthome.patterns.behavioural.DeviceObserver;

/**
 * The {@code Device} interface represents a smart home device that can be controlled.
 * Implementing classes should provide methods to turn the device on, turn it off,
 * and get the device's type.
 */
public interface Device extends DeviceObserver {

    /**
     * Turns the device on based on the provided unique identifier.
     *
     * @param id the unique identifier of the device to be turned on
     * @throws IllegalArgumentException if the provided id is invalid
     */
    void turnOn(int id);

    /**
     * Turns the device off based on the provided unique identifier.
     *
     * @param id the unique identifier of the device to be turned off
     * @throws IllegalArgumentException if the provided id is invalid
     */
    void turnOff(int id);

    /**
     * Returns the type of the device (e.g., light, thermostat, door).
     *
     * @return the type of the device as a String
     */
    String deviceType();

    /**
     * Returns the unique identifier of the device.
     *
     * @return the unique identifier of the device as an integer
     */
    int getId();
}